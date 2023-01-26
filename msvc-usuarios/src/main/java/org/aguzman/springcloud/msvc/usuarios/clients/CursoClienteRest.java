package org.aguzman.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "msvc-cursos",url = "http://localhost:8002")
public interface CursoClienteRest {
    @DeleteMapping("/eliminar-usuariofrom/{id}")
    void eliminarUsuario(@PathVariable Long id);
}
