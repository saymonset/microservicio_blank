Deployment: Objeto administra los pods a traves de nosotros. Los crea y asi no lo creamos manualmente si no a traves 
 del deployment con la herramienta de kuberntes.
 Maneja, administra los pods.

# Entrar al bash de mysql de un pod
kubectl exec mysql8-7fdc978c6b-847zb bash
mysql -uroot -psasa


# Docker
  Docker build -t usuarios . -f /mscv-usuarios/Dockerfile

  RUN  chmod +x mvnw

docker tag usuarios saymonset/usuarios
  
# Chequear
minikube status

# Parar la maquina del kubernetes o cluster
minikube stop

# start la maquina del kubernetes o cluster

# Windows 10 o 11 Pro
minikube start --driver=hyperv

# Windows home o mac
minikube start --driver=virtualbox

# Chequear
minikube status

# kubectl es un cliente que se comunica al cluster de kubernetes para crear recursos: deployment, service, pod etc...
 kubectl help
 
kubectl create -h

# Creamos un deployment llamado mysql8
# Forma imperativa
kubectl create deployment mysql8 --image=mysql:8 --port=3306 
 
# Forma declarativa
kubectl create deployment mysql8 --image=mysql:8 --port=3306 --dry-run=client -o yaml > deployment-mysql.yaml

# Lista de deployments. Para obtener recursos
kubectl get deployments
# Atajo a deployments
kubectl get deploy

kubectl get pods

kubectl describe pods nombre_del_pod

# Eliminar un recurso
kubectl delete deployment mysql8

# Elimina un pods

kubectl delete pods mysql8



kubectl logs pods nombre_del_pod
 
# Forma delarativa
kubectl apply -f ./deployment-mysql.yaml
kubectl get pods
kubectl describe pods ogs si colocaamos pods o no
# Logs: Siempre es del pod
kubectl logs pods nombre_del_pod
# Es lo mismo en 
kubectl logs nombre_del_pods 


# Creamos un servicio para comunicarnos con los pods. Es como una ip unica para todos
 kubectl expose deployment mysql8 --port=3306 --type=ClusterIp
 kubectl expose deployment mysql8 --port=3306 --type=NodePort
 kubectl expose deployment mysql8 --port=3306 --type=LoadBalancer

# Listamos los service creados
kubectl get services
kubectl get svc

kubectl describe service nombre_del_servicio

# Nos permote ver todo el escenario completo
kubectl get svc

kubectl create deployment msvc-usuarios --image=saymonset/usuarios:latest --port=8001
kubectl get deployments
kubectl get pods
kubctl  get all
kubectl logs pods nombre_del_pod
kubectl describe deployment deployment_msvc-usuarios
kubectl describe pod pod_msvc-usuarios

# Creamos un servicio para comunicarnos con los pods. Es como una ip unica para todos
# LoadBalancer porque necesitamos exponer el servicio de forma externa y tambien interna
kubectl expose deployment msvc-usuarios --port=8001 --type=LoadBalancer
# Listamos los service creados
kubectl get services
kubectl get svc

#Para obtener la url externa en modo local y comunicarnos dese fuera, porque a nivel de la nube ya nos la asigna, es de esta mnera
#Genera la url externa
minikube service msvc-usuarios --url

# Actualizando imagen de kubernet del pod
# kubectl describe pod nombre_del_pod para saber el nombre del container, en este caso es usuarios
# usuarios es el nombre del contenedor al describir el pod o log del pod
kubectl  set image deployment msvc-usuarios usuarios=saymonset/usuarios:latest

# Colocar varias replicas de un container
kubectl scale deployment msvc-usuarios --replicas=3
 
# Generamos nuestro archivo yaml
 kubectl get service mysql8 -o yaml > svc-mysql8.yaml

 
kubectl create deployment msvc-usuarios --image=saymonset/usuarios:latest --port=8001 --dry-run=cient -o yaml > deployment-usuarios.yaml
kubectl get service msvc-usuarios -o yaml > svc-usuarios.yaml

# Eliminar un deployment
 kubectl delete -f ./deployment-usuarios.yaml

# Crear un deployment 
kubectl apply -f ./deployment-usuarios.yaml

# Interface grafica para administrar nuestros pods en web
minikube dashboard

# Base 64, convertrir 

  Seccion 16 -> 153
  https://www.base64encode.org/

Gui-completa-de-docker-kubernetes-con-spring-boot
