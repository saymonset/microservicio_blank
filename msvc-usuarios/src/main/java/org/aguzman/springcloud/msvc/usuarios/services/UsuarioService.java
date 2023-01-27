package org.aguzman.springcloud.msvc.usuarios.services;

import org.aguzman.springcloud.msvc.usuarios.models.entity.Usuario;

import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario>   porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    List<Usuario> listarPorIds(Iterable<Long> ids);

    Optional<Usuario> findByEmail(String email);

    boolean existByEmail(String email);

}