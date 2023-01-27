#Esta imagen de java debe ser  la misma version que se tiene instalada en la mauina y enb el ide
#Usamos el patron de diseno builder que es tener la primera instalarcion del jdk y la usamos nuevamente mas
#abajo llamandola nuevamente pero solo para ejecutar
FROM openjdk:18.0 as builder

#Carpeta de trabajo de nuestra imagen
#El pom principal va en app
#en msvc-usuario va el pom de msvc-usuario  y resto de fuentes sin target
WORKDIR /app/msvc-usuarios

#Vamos a compilar yempaquetar dentro de docker  con maven
#Copiamos el pom parent a app
COPY ./pom.xml /app
#Bajamos toda las liobrerias de mvn para optimizqar la capa que sea una de las primeras y
# a la hora de cambiar fuentes del src, no bajar nuevamente las dependencias de maven porque estan en chqache por una capa anterior
#Copiamos los fuentes a la ruta /app/msvc-usuarios con el punto que es el area de trabajo
COPY ./msvc-usuarios/.mvn ./.mvn
COPY ./msvc-usuarios/mvnw .
COPY ./msvc-usuarios/pom.xml .

#saltamos el test y main carpeta de maven. saltamos el reempaquetado de spring y borramos la carpeta target
#RUN ./mvnw clean package -Dmaven.test.skip -Dmaven.main.skip -Dspring-boot-repackage.skip && rm -r ./target/
RUN ./mvnw dependency:go-offline
#Ahora si copiamos todo el codigo fuente en WORKDIR /app/msvc-usuarios/src
COPY ./msvc-usuarios/src ./src
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
docker cp nombreContenedor:/app/logs .\logs

#copiamos el msvc-usuarios-0.0.1-SNAPSHOT.jar de builder a la nueva capa de openjdk del workdir que es app
COPY --from=builder /app/msvc-usuarios/target/msvc-usuarios-0.0.1-SNAPSHOT.jar .
EXPOSE 8001
#El msvc-usuarios-0.0.1-SNAPSHOT.jar esta en la raiz, esta en app
#ENTRYPOINT ["java", "-jar" , "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

#CMD es menos estricto y se puede sobreescribir al crear el contenedor con linea de comando
#como por ejemplo: docker run -p 8001:8002 -d --rm usuarios -it /bin/sh
#Entramos a /bin/sh
CMD ["java", "-jar" , "msvc-usuarios-0.0.1-SNAPSHOT.jar"]
#Cuando ejecutamos un contenedor, vamos a un punto de entrada para ejecutar un comando
# /app/msvc-usuarios con el punto que es el area de trabajo
#ENTRYPOINT ["java", "-jar" , "./target/msvc-usuarios-0.0.1-SNAPSHOT.jar"]


#Puerto exterior el primero 8002
#Puerto interior: 8001
# docker run -p 8002:8001 image#
#docker st

#Con este comando, copiamos el pom.xml de la raiz y el mcsv-usuarios con su pom.xml t mvnw para compilarlos internamente en docker
#el -t es tag que es etiquetar
# docker build -t usuarios . -f ./msvc-usuarios/Dockerfile

# prune       Remove all stopped containers
#Docker container prune
#docker container --help
# Remove unused images
#docker image prune
#Con rm si logras parar el contenedor, se auto elimina
#docker run -p 8001:8002 -d --rm usuarios
#Ejecutar algo en la shell del contenedor apenas se crea
#docker run -p 8001:8002 -d --rm usuarios -it /bin/sh

#Para pasar un archivo x al contenedor de la carpeta de trabajao
#  docker cp  ./Login.java nombreContenedorCorriendo:/app/Login.java

#Para pasar un archivo x del contenedor a nuestra maquina
#  docker cp  nombreContenedorCorriendo:/app/Login.java .\Login.java
#  docker cp  nombreContenedorCorriendo:/app .\test

