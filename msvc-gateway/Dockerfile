#Definimos una variable de ambiente argumento
#Los argumentos se usan para la construccion de la imagen
ARG MSVC_NAME=msvc-gateway

#Esta imagen de java debe ser  la misma version que se tiene instalada en la maquina y en el ide
#Usamos el patron de diseno builder que es tener la primera instalarcion del jdk y la usamos nuevamente mas
#abajo llamandola nuevamente pero solo para ejecutar
FROM openjdk:18.0 as builder

#declaramos en esta capa la variable de argumento declarada arriba
ARG MSVC_NAME

#Carpeta de trabajo de nuestra imagen
#El pom principal va en app
#en msvc-usuario va el pom de msvc-usuario  y resto de fuentes sin target
WORKDIR /app/msvc-gateway

#Vamos a compilar yempaquetar dentro de docker  con maven
#Copiamos el pom parent a app
COPY ./pom.xml /app
#Bajamos toda las liobrerias de mvn para optimizqar la capa que sea una de las primeras y
# a la hora de cambiar fuentes del src, no bajar nuevamente las dependencias de maven porque estan en chqache por una capa anterior
#Copiamos los fuentes a la ruta /app/msvc-gateway con el punto que es el area de trabajo
COPY ./$MSVC_NAME/.mvn ./.mvn
COPY ./$MSVC_NAME/mvnw .
COPY ./$MSVC_NAME/pom.xml .

#saltamos el test y main carpeta de maven. saltamos el reempaquetado de spring y borramos la carpeta target
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot-repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
#Ahora si copiamos todo el codigo fuente en WORKDIR /app/msvc-gateway/src
COPY ./$MSVC_NAME/src ./src
RUN ./mvnw clean package -DskipTests




#Usamos el patron de diseno builder que es tener la primera instalacion del jdk y la usamos nuevamente
#Aqui se llama nuevamente para ejecutar
FROM openjdk:18.0

#declaramos en esta capa la variable de argumento declarada arriba
ARG MSVC_NAME

WORKDIR /app
#Al correr  esta segunda imagen del builder, creamos esta carpeta logs en la carpeta de
#trabajo app
RUN mkdir ./logs
#Para sacar los logs a nuestro equipo es: \
    #./logs se refiere a nuestro equipo en el directorio donde estemps parados y crea la \
#    carpeta logs
#docker cp nombreContenedor:/app/logs .\logs

#copiamos el msvc-gateway-0.0.1-SNAPSHOT.jar de builder a la nueva capa de openjdk del workdir que es app
ARG TARGET_FOLDER=/app/$MSVC_NAME/target
COPY --from=builder $TARGET_FOLDER/msvc-gateway-0.0.1-SNAPSHOT.jar .

#Seteamos nuestra variable de ambiente con PORT  8000
ARG PORT_APP=8090
ENV PORT $PORT_APP
EXPOSE $PORT
#El msvc-gateway-0.0.1-SNAPSHOT.jar esta en la raiz, esta en app
#ENTRYPOINT ["java", "-jar" , "msvc-gateway-0.0.1-SNAPSHOT.jar"]

#CMD es menos estricto y se puede sobreescribir al crear el contenedor con linea de comando
#como por ejemplo: docker run -p 8001:8002 -d --rm usuarios -it /bin/sh
#Entramos a /bin/sh
CMD ["java", "-jar" , "msvc-gateway-0.0.1-SNAPSHOT.jar"]
#Cuando ejecutamos un contenedor, vamos a un punto de entrada para ejecutar un comando
# /app/msvc-gateway con el punto que es el area de trabajo
#ENTRYPOINT ["java", "-jar" , "./target/msvc-gateway-0.0.1-SNAPSHOT.jar"]

#Creamos contenedor mongoDB solito
#docker run -d -p 27018:27017 --network spring --name mongoDB mongo:latest
#Puerto exterior el primero 8002
#Puerto interior: 8001
# docker run -p 8002:8001 image#
#docker st

#Con este comando, copiamos el pom.xml de la raiz y el mcsv-usuarios con su pom.xml t mvnw para compilarlos internamente en docker
#el -t es tag que es etiquetar

#Creamos la imagen
# docker build -t gateway:latest . -f ./msvc-gateway/Dockerfile
# docker tag gateway saymonset/gateway
# docker push saymonset/gateway
#Container crear: Sobreescribiendo variables de ambiente usando archivos
# docker run -p 8003:8003 --env-file ./msvc-gateway/.env -d --rm --name msvc-gateway --network spring mongousuarios:latest


# prune       Remove all stopped containers
#Docker container prune
#docker container --help

# Remove unused images
#docker image prune

#Sobreescribimos la variable de ambiente
# docker run -p 8001:8090 --env PORT=8090 -d --rm --name msvc-gateway --network spring usuarios:latest
# docker run -p 8001:8090 -e PORT=8090 -d --rm --name msvc-gateway --network spring usuarios:latest

#Sobreescribiendo variables de ambiente usando archivos
# docker run -p 8001:8001 --env-file ./msvc-gateway/.env -d --rm --name msvc-gateway --network spring usuarios:latest

#Ejecutar algo en la shell del contenedor apenas se crea
#docker run -p 8001:8002 -d --rm usuarios -it /bin/sh

#Para pasar un archivo x al contenedor de la carpeta de trabajao
#  docker cp  ./Login.java nombreContenedorCorriendo:/app/Login.java

#Para pasar un archivo x del contenedor a nuestra maquina
#  docker cp  nombreContenedorCorriendo:/app/Login.java .\Login.java
#  docker cp  nombreContenedorCorriendo:/app .\test

#Ver ayuda de las redesde en docker
#docker network --help

#creamos la red
#docker network  create spring

#Creamos el; microservico
#docker run -p 8001:8001 -d --rm --name msvc-gateway --network spring usuarios:latest

#MYSQL
# Guardamos los datos de mysql en un volumen, y con restart iniciamos la bd cada ves que este apagada
#docker run -d -p 3308:3306 --name mysql8 -v data-mysql:/var/lib/mysql  --restart=always --network spring -e MYSQL_USER=ecological -e MYSQL_PASSWORD=ecological -e MYSQL_ROOT_PASSWORD=123456 -e MYSQL_DATABASE=MSVC_USUARIOS mysql:8

#docker container inspect msvc-gateway