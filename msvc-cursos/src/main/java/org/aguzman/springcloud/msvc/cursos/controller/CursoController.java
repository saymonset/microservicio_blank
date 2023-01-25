package org.aguzman.springcloud.msvc.cursos.controller;

import org.aguzman.springcloud.msvc.cursos.entity.Curso;
import org.aguzman.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
        Optional<Curso> o = this.s.porId(id);
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

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e ->{
            errores.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
