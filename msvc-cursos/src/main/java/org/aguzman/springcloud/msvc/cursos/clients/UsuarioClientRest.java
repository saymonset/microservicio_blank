package org.aguzman.springcloud.msvc.cursos.clients;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "msvc-usuarios", url = "http://localhost:8001")
public interface UsuarioClientRest {

    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);

    @PostMapping("/")
     Usuario crear  ( @RequestBody Usuario usuario);

    @GetMapping("/usuarios-por-curso")
    //Si fuera por @RequestBody el json que llega del cliente, debe ser enviado tipo post
    //Si es con el verbo tipo @Get, debes pasar los datos por @RequestParam
    List<Usuario> alumnosPorCurso(@RequestParam Iterable<Long> ids);
}
