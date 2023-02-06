package org.aguzman.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;


//#1-) Con host.docker.internal le decimos a docker que se va a comunicar externamente con la bd que tenemos instalado en el computador
//        #1.1-) spring.datasource.url=jdbc:mysql://host.docker.internal:3307/msvc_usuarios?serverTimezone=America/Caracas&allowPublicKeyRetrieval=false
//        #1.2-) y se vuelve a generar el compilado con mvn de esta manera
//        #1.3-) ./mvnw clean package -DskipTests
//Este es el nombre del contenedor que es el mismo nombre de red para cominicarnos
//@FeignClient(name = "msvc-cursos",url = "msvc-cursos:8002")
@FeignClient(name="msvc-cursos", url = "${msvc.cursos.url}")
public interface CursoClienteRest {
    @DeleteMapping("/eliminar-usuariofrom/{id}")
    void eliminarUsuario(@PathVariable Long id);
}
