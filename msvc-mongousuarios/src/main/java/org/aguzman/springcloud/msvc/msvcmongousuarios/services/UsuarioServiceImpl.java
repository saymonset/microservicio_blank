package org.aguzman.springcloud.msvc.msvcmongousuarios.services;

import org.aguzman.springcloud.msvc.msvcmongousuarios.models.entity.Usuario;
import org.aguzman.springcloud.msvc.msvcmongousuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) this.repository.findAll();
    }

    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return this.repository.save(usuario);
    }


    @Override
    public Optional<Usuario> findByEmail(String email) {
        return this.repository.findByEmail(email);
    }

    @Override
    public boolean existByEmail(String email) {
        return this.repository.findByEmail(email).isPresent();
    }


}
