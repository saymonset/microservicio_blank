# Entrar al bash de mysql de un pod
kubectl exec mysql8-7fdc978c6b-847zb bash

# Entramos a mysql
 mysql -uroot -psasa

# microservicio_blank
Esqueleto de microservicios

Rama donde solo esta el microservicio usuario y microservicio curso tapa inicial. Sin comunicarse entre ellos, solo es validación, curo con bd PostgreSQL,usuario con mysql y docker esas bd.

Mcvs0-basico

# attachment al terminal con docker -it  (iterativo terminal)
docker run -p 8001:8001 --rm -it usuarios /bin/sh
# /bin/sh Es  un comando que deberia sustituir al ENTRYPOINT, pero que no surte efecto porque es
# inmutable. Por lo tanto ENTRYPOINT se sustituye por CMD que si es inmutable.

# Para copiar un archivo desde nuestra computadora a la carpeta de trabajo de docker es:
docker cp  ./Login.java name_contenedor:/app/Login.java

# Desde docker,copiamos un archivo a nuestra maquina local
docker cp name_contenedor:/app/Login.java /opt/simon/pc_linux/Login2.java

# Desde docker,copiamos toda una carpeta a nuestra maquina local
docker cp name_contenedor:/app /opt/simon/pc_linux/test

docker cp name_contenedor:/app/logs /opt/simon/pc_linux/logs

# Volumenes de nuestra bd mysql. y postgres
# Aqui creamos un volumen y docker en cualquier lugar desconocido de la maquina local, va  aguardar este directorio /var/lib/mysql
# que es donde se guarda los datos de la bd mysql y loguarda fuera del contenedor
docker run -d -p 3307:3306 --name mysql8 --network spring -e MYSQL_ROOT_PASSWORD=sasa -e MYSQL_DATABASE=msvc_usuarios
   -v data-mysql:/var/lib/mysql --restart=always mysql:8

docker run -d -p 5532:5432 --name postgres14 --network spring -e POSTGRES_PASSWORD=sasa -e POSTGRES_DB=msvc_cursos
-v data-postgres:/var/lib/postgresql/data --restart=always postgres:14-alpine

# Entrar en bd
 docker exec -it postgres14 bash

 docker exec -it mysql8 bash

# Entramos a bd
psql -Upostgres
\l
\c
\dt
\d+
mysql -Uroot -psasa
show database
use database

# Variables de ambiente en un archivo .env  Seccion 10->84
docker run -d --rm -p 8001:8001 --env-file=./msvc-usuarios/.env --name msvc-usuarios --network spring saymonset/usuarios
docker run -d --rm -p 8002:8002 --env-file=./msvc-cursos/.env --name msvc-cursos --network spring saymonset/cursos
