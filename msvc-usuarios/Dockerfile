#Esta imagen de java debe ser  la misma version que se tiene instalada en la mauina y enb el ide
FROM openjdk:18.0

#Carpeta de trabajo de nuestra imagen
WORKDIR /app

COPY ./target/msvc-usuarios-0.0.1-SNAPSHOT.jar .

EXPOSE 8001

#Cuando ejecutamos un contenedor, vamos a un punto de entrada para ejecutar un comando
ENTRYPOINT ["java", "-jar" , "msvc-usuarios-0.0.1-SNAPSHOT.jar"]

#docker build .
#Puerto exterior el primero 8002
#Puerto interior: 8001
# docker run -p 8002:8001 image#
#docker st


