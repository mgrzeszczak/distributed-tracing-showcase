# distributed-tracing-showcase
Showcase project for distributed tracing using jaeger, elasticsearch, k8s.

## TODO
- use opentelemetry operator for jaeger 2 - https://github.com/jaegertracing/jaeger-operator#jager-v2-operator
- setup ingress for services and jaeger GUI
- setup installation without java (using docker builder)
- secure jaeger GUI using reverse proxy (for instance oauth2-proxy) with keycloak?
- setup kafka streaming ingestion

## Requirements
- docker
- java21
- helm

## How to setup
1. Install kind in order to be able to create local k8s cluster: https://kind.sigs.k8s.io/docs/user/quick-start/
2. Create a kind cluster with local registry (source https://kind.sigs.k8s.io/examples/kind-with-registry.sh)
   ```
   ./kind-with-registry.sh
   ```
3. Setup k8s, elasticsearch and jaeger (some of those may need to be retried if some errors occur, previous operations might need more time)
   ```
   kubectl create -f k8s/elastic-crds.yaml # source https://download.elastic.co/downloads/eck/2.16.1/crds.yaml
   kubectl apply -f k8s/cert-manager.yaml # source https://github.com/jetstack/cert-manager/releases/download/v1.11.0/cert-manager.yaml
   kubectl apply -f k8s/elastic-operator.yaml # source https://download.elastic.co/downloads/eck/2.16.1/operator.yaml
   kubectl apply -f k8s/jaeger-operator.yaml # source https://github.com/jaegertracing/jaeger-operator/releases/download/v1.65.0/jaeger-operator.yaml
   kubectl apply -f k8s/elastic-cluster.yaml
   kubectl create secret generic jaeger-secret --from-literal=ES_PASSWORD=`kubectl get secret quickstart-es-elastic-user -o go-template='{{.data.elastic | base64decode}}'` --from-literal=ES_USERNAME=elastic
   kubectl apply -f k8s/jaeger-cluster.yaml
   ```
4. Build and deploy services
   ```
   cd services
   ./mvnw clean package && docker compose build && docker compose push
   cd ../infrastructure
   kubectl apply -f services.yaml
   ```
   
5. Now we need to expose jaeger-search gui and cart-service to send some requests. We can either do it with Ingress or just use kubectl port-forward.
   #### With ingress
   Install traefik with helm
   ```
   helm install traefik traefik/traefik --namespace traefik --create-namespace
   kubectl apply -f k8s/traefik-crds.yaml # source https://raw.githubusercontent.com/traefik/traefik/v3.3/docs/content/reference/dynamic-configuration/kubernetes-crd-definition-v1.yml
   kubectl apply -f k8s/jaeger-route.yaml
   kubectl apply -f k8s/cart-service-route.yaml
   ```
   Add cart-service.local and jaeger.local to /etc/hosts and point them to 127.0.0.1.
   ```
   cat /etc/hosts
   127.0.0.1 localhost jaeger.local cart-service.local
   ...
   ```
   Expose traefik outside of the cluster:
   ```
   kubectl port-forward service/traefik 80:80 -n traefik
   ```
   Send requests to cart-service and then visit jaeger.
   ```
   curl cart-service.local/api/cart
   curl cart-service.local/api/cart
   curl cart-service.local/api/cart
   ```
   Visit http://jaeger.local 

   #### With port-forward
   Expose cart-service and jaeger gui (Optional, )
   ```
   kubectl port-forward service/jaeger-query 16686 # this will hang
   kubectl port-forward service/cart-service 8080:80 # this will hang
   ```
   Send some requests to cart-service and check jaeger GUI
   ```
   curl localhost:8080/api/cart # do a couple of these
   ```
   Visit http://localhost:16686

