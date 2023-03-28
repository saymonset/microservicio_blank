package org.aguzman.springcloud.msvc.usuarios.controllers;

import org.aguzman.springcloud.msvc.usuarios.models.entity.Usuario;
import org.aguzman.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService s;

    @GetMapping("/")
    public List<Usuario> listar(){
        return this.s.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
//        ctrl + alt + v  -> es atajo en itellijidea
        Optional<Usuario> usuario = this.s.porId(id);
        if (usuario.isPresent()){
           return ResponseEntity.ok().body(usuario);
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> crear  (@Valid @RequestBody Usuario usuario, BindingResult result){

        if (result.hasErrors()){
            return validar(result);
        }
        if (!usuario.getEmail().isEmpty() && this.s.existByEmail(usuario.getEmail())){
            return  ResponseEntity.badRequest()
                    .body(Collections
                            .singletonMap("mensaje","Ya existe un usuarios con ese correo electronico"));
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(this.s.guardar(usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable(name = "id") Long id){



        if (result.hasErrors()){
            return validar(result);
        }
        Optional<Usuario> o = this.s.porId(id);
        if (o.isPresent()){

            if (!usuario.getEmail().isEmpty() &&
                    !usuario.getEmail().equalsIgnoreCase(o.get().getEmail()) &&
                    this.s.findByEmail(usuario.getEmail()).isPresent()){
                return  ResponseEntity.badRequest()
                        .body(Collections
                                .singletonMap("mensaje","Ya existe un usuarios con ese correo electronico"));
            }

            Usuario usuarioDb = o.get();
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.s.guardar(usuarioDb));
        }
       return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable(name = "id") Long id){
        Optional<Usuario> o = this.s.porId(id);
        if (o.isPresent()){
            this.s.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @GetMapping("/usuarios-por-curso")
    //Si fuera por @RequestBody el json que llega del cliente, debe ser enviado tipo post
    //Si es con el verbo tipo @Get, debes pasar los datos por @RequestParam
    public ResponseEntity<?> alumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(this.s.listarPorIds(ids));

    }

//    Se mueve el bloque con
    //ctrl + shift + flecha
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e ->{
            errores.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
