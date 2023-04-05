package org.aguzman.springcloud.msvc.msvcmongousuarios.services;

import org.aguzman.springcloud.msvc.msvcmongousuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Usuario guardar(Usuario usuario);

    Optional<Usuario> findByEmail(String email);

    boolean existByEmail(String email);
}
