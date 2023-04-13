# Construye Microservicios Spring Boot, Docker, Kubernetes, Spring Cloud, LoadBalancer, Security JWT, Amazon AWS ECS y EKS
kubectl get all
# Buscar todos los deployments
 kubectl get deployments
 kubectl get deploy
 
# Eliminar deployment
kubectl delete deployment mysql8

# Creamos un archivo declarativo para el microservico mongo
kubectl create deployment mongoDB --image=mongo:latest --port=27017 --dry-run=client -o yaml > deployment-node.yaml
kubectl create deployment msvc-usuarios --image=aguzf/usuarios:latest --port=8001

# Otra forma de crear el deployment de forma declarativa
kubectl apply -f ./deployment-mongousuarios.yaml

# Obtenemos url de minikube
minikube service msvc-mongousuarios --url

# Seccion 14 -> 132
kubectl apply -f ./deployment-mysql.yaml
kubectl get pods
kubectl describe pod nombre_pod
kubectl logs nombre_pod

# Servicios para poder acceder a los pods. Esto es el nombre que exponemos a los demas microservicios
kubectl expose deployment mysql8 --port=3306 --type=ClusterIP
kubectl expose deployment msvc-usuarios --port=8003 --type=LoadBalancer 
kubectl get services
kubectl describe service mysql8
kubectl describe service msvc-mongousuarios

# Dashboar de minukube
  minikube dashboard


# Seccion 14->138. Creamos archivos yaml de deployment y servicios
# Sacamos el servicio con formato yaml
kubectl get service mongodb -o yaml > svc-mongodb.yaml
kubectl get service msvc-mongousuarios -o yaml > msvc-mongousuarios.yaml

# Borramos deployments
kubectl delete deployment msvc-mongousuarios

# Borramos deployments de forma declarativa
kubectl delete -f ./deployment-mongousuarios.yaml


# sacamos el deployment en formato yaml y se hacen las replicas siempre manejando ese archivo. En la imagen si se 
# coloca a latest, siempre cargara la ultima imagen
kubectl create deployment msvc-mongousuarios --image=saymonset/usuariosmongo:v2 --port=8003 --dry-run=client -o yaml > deployment-mongousuarios.yaml

# Seccion 14->136: Actaulizar nuestro codigo, subimos la imgen y creamos nuevamente el pod y el servicio
# Se hace el docker file de la imagen nuevamente y se sube a docker hub
docker build -t mongousuarios . -f ./msvc-mongousuarios/Dockerfile
docker tag mongousuarios saymonset/usuariosmongo
docker push saymonset/usuariosmongo
# Ahora se busca el nombre del container que es: usuariosmongo:
kubectl describe pod msvc-mongousuarios-796449f8bc-26kjq

# se ejecuta esta instruccion. Nombre del servicio es:  msvc-mongousuarios
kubectl set image deployment msvc-mongousuarios usuariosmongo=saymonset/usuariosmongo:v2

# Seccion 14->137. Aumentramos replicas de los pods
kubectl scale deployment msvc-mongousuarios --replicas=4

# Volumenes PersistenVolumeClaim
   kubectl apply -f ./mongo-pv.yaml
   kubectl apply -f ./mongo-pvc.yaml
   kubectl get pv
   kubectl get pv
   kubectl apply -f ./deployment-mongodb.yaml 



