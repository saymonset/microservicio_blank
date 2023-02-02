#Esta imagen de java debe ser  la misma version que se tiene instalada en la mauina y enb el ide
#Usamos el patron de diseno builder que es tener la primera instalarcion del jdk y la usamos nuevamente mas
#abajo llamandola nuevamente pero solo para ejecutar
FROM openjdk:18.0 as builder

#Carpeta de trabajo de nuestra imagen
#El pom principal va en app
#en msvc-usuario va el pom de msvc-usuario  y resto de fuentes sin target
WORKDIR /app/msvc-cursos

#Vamos a compilar yempaquetar dentro de docker  con maven
#Copiamos el pom parent a app
COPY ./pom.xml /app
#Bajamos toda las liobrerias de mvn para optimizqar la capa que sea una de las primeras y
# a la hora de cambiar fuentes del src, no bajar nuevamente las dependencias de maven porque estan en chqache por una capa anterior
#Copiamos los fuentes a la ruta /app/msvc-cursos con el punto que es el area de trabajo
COPY ./msvc-cursos/.mvn ./.mvn
COPY ./msvc-cursos/mvnw .
COPY ./msvc-cursos/pom.xml .

#saltamos el test y main carpeta de maven. saltamos el reempaquetado de spring y borramos la carpeta target
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot-repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
#Ahora si copiamos todo el codigo fuente en WORKDIR /app/msvc-cursos/src
COPY ./msvc-cursos/src ./src
RUN ./mvnw clean package -DskipTests




#Usamos el patron de diseno builder que es tener la primera instalacion del jdk y la usamos nuevamente
#Aqui se llama nuevamente para ejecutar
FROM openjdk:18.0
WORKDIR /app
#Al correr  esta segunda imagen del builder, creamos esta carpeta logs en la carpeta de
#trabajo app
RUN mkdir ./logs
#Para sacar los logs a nuestro equipo es: \
    #./logs se refiere a nuestro equipo en el directorio donde estemps parados y crea la \
#    carpeta logs
#docker cp nombreContenedor:/app/logs .\logs

#copiamos el msvc-cursos-0.0.1-SNAPSHOT.jar de builder a la nueva capa de openjdk del workdir que es app
COPY --from=builder /app/msvc-cursos/target/msvc-cursos-0.0.1-SNAPSHOT.jar .
EXPOSE 8002
#El msvc-cursos-0.0.1-SNAPSHOT.jar esta en la raiz, esta en app
#ENTRYPOINT ["java", "-jar" , "msvc-cursos-0.0.1-SNAPSHOT.jar"]

#CMD es menos estricto y se puede sobreescribir al crear el contenedor con linea de comando
#como por ejemplo: docker run -p 8001:8002 -d --rm cursos -it /bin/sh
#Entramos a /bin/sh
CMD ["java", "-jar" , "msvc-cursos-0.0.1-SNAPSHOT.jar"]
#Cuando ejecutamos un contenedor, vamos a un punto de entrada para ejecutar un comando
# /app/msvc-cursos con el punto que es el area de trabajo
#ENTRYPOINT ["java", "-jar" , "./target/msvc-cursos-0.0.1-SNAPSHOT.jar"]


#Puerto exterior el primero 8002
#Puerto interior: 8001
# docker run -p 8002:8001 image#
#docker st

#Con este comando, copiamos el pom.xml de la raiz y el mcsv-cursos con su pom.xml t mvnw para compilarlos internamente en docker
#el -t es tag que es etiquetar
# docker build -t cursos . -f ./msvc-cursos/Dockerfile
# docker build -t cursos:latest . -f ./msvc-cursos/Dockerfile
# prune       Remove all stopped containers
#Docker container prune
#docker container --help
# Remove unused images
#docker image prune
#Con rm si logras parar el contenedor, se auto elimina
#docker run -p 8001:8002 -d --rm cursos
#Ejecutar algo en la shell del contenedor apenas se crea
#docker run -p 8001:8002 -d --rm cursos -it /bin/sh

#Para pasar un archivo x al contenedor de la carpeta de trabajao
#  docker cp  ./Login.java nombreContenedorCorriendo:/app/Login.java

#Para pasar un archivo x del contenedor a nuestra maquina
#  docker cp  nombreContenedorCorriendo:/app/Login.java .\Login.java
#  docker cp  nombreContenedorCorriendo:/app .\test

#docker network --help
#creamos la red
#docker network  create spring

#Postgres
# Guardamos los datos de postgres en un volumen, y con restart iniciamos la bd cada ves que este apagada
#docker run --name postgresql -itd --restart always \
#  --env 'DB_USER=dbuser' --env 'DB_PASS=dbuserpass' \
#  sameersbn/postgresql:12-20200524
#docker run -d -p 5433:5432 -e POSTGRES_PASSWORD=123456 -e POSTGRES_DB=msvc_cursos -v data-postgres:/var/lib/postgresql/data --restart=always --name postgres14 --network spring  postgres:14-alpine


#Entrar en bd
# docker exec -it postgres14 bash

#entramos a bd
# psql -Upostgres

#Listar todas las bd
#\l

#Seleccionar una bd
#\c msvc_cursos;

#listar tables
#\dt
#\d+   Describe una table

#Creamos el; microservicio
#docker run -p 8002:8002 -d --rm --name msvc-cursos --network spring cursos:latest
