package org.aguzman.springcloud.msvc.msvcmongousuarios.repositories;

import org.aguzman.springcloud.msvc.msvcmongousuarios.models.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends MongoRepository<Usuario, String> {


    Optional<Usuario> findByEmail(String email);


}
