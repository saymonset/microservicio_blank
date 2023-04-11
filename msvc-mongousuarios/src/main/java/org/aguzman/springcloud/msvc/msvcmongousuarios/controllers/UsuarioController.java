package org.aguzman.springcloud.msvc.msvcmongousuarios.controllers;


import org.aguzman.springcloud.msvc.msvcmongousuarios.models.entity.Usuario;
import org.aguzman.springcloud.msvc.msvcmongousuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService s;

    @Autowired
    private ApplicationContext context;


    @GetMapping("/crash")
    public void crash(){
        ((ConfigurableApplicationContext) this.context).close();
    }


    @GetMapping("/")
    public ResponseEntity<?> listar(){
        Usuario user = new Usuario();
        user.setEmail("saymon_set@HOTMAIL.COM");
        return ResponseEntity.ok(this.s.listar());
        //return this.s.listar();
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


    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(e ->{
            errores.put(e.getField(), "El campo " + e.getField() + " " + e.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
