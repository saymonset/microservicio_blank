package org.aguzman.springcloud.msvc.cursos.controller;

import feign.FeignException;
import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;
import org.aguzman.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

@RestController
public class CursoController {
    @Autowired
    private CursoService s;

    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok().body(this.s.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Curso> detalle(@PathVariable Long id){
        //Optional<Curso> o = this.s.porId(id);
        Optional<Curso> o = this.s.porIdConUsuarios(id);
        if (o.isPresent()){
            return ResponseEntity.ok().body(o.get());
        }
       return ResponseEntity.notFound().build();
    }
    @PostMapping("/")
    public ResponseEntity<?> crear(@Valid @RequestBody Curso curso, BindingResult result){
        if (result.hasErrors()){
            return validar(result);
        }
        Curso cursoDb = this.s.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@Valid @RequestBody Curso curso,BindingResult result, @PathVariable(name="id") Long id ){

        if (result.hasErrors()){
            return validar(result);
        }

        Optional<Curso> o = this.s.porId(id);
        if (o.isPresent()){
            Curso cursoDb = o.get();
            cursoDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(this.s.guardar(cursoDb));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Curso> eliminar(@PathVariable(name="id") Long id ){
        Optional<Curso> o = this.s.porId(id);

        if (o.isPresent()){
            this.s.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable(name="cursoId") Long cursoId) {
        Optional<Usuario> o = null;
        try{
            o =  this.s.asignarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections
                            .singletonMap("mensaje","No existe usuario por id o error de la comunicacion:"+e.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return  ResponseEntity.notFound().build();
    }

    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable(name="cursoId") Long cursoId) {
        Optional<Usuario> o = null;
        try{
            o =  this.s.crearUsuario(usuario, cursoId);
        }catch (FeignException e){
              return ResponseEntity
                      .status(HttpStatus.NOT_FOUND)
                      .body(Collections
                              .singletonMap("mensaje","No se pudo crear el usuario o error de la comunicacion:"+e.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return  ResponseEntity.notFound().build();
    }

    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable(name="cursoId") Long cursoId) {
        Optional<Usuario> o = null;
        try{
            o =  this.s.eliminarUsuario(usuario, cursoId);
        }catch (FeignException e){
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Collections
                            .singletonMap("mensaje","No existe usuario por id o error de la comunicacion:"+e.getMessage()));
        }

        if (o.isPresent()) {
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return  ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-usuariofrom/{id}")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long id){
        this.s.eliminarCursoUsuarioPorId(id);
        return ResponseEntity.noContent().build();
    }
    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e ->{
            errores.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
