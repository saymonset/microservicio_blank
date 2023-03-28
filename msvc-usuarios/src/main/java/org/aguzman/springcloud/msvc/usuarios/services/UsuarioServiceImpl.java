package org.aguzman.springcloud.msvc.usuarios.services;

import org.aguzman.springcloud.msvc.usuarios.clients.CursoClienteRest;
import org.aguzman.springcloud.msvc.usuarios.models.entity.Usuario;
import org.aguzman.springcloud.msvc.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    private UsuarioRepository repository;

    @Autowired
    private CursoClienteRest cliente;

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listar() {
        return (List<Usuario>) this.repository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> porId(Long id) {
        return this.repository.findById(id);
    }
    @Override
    @Transactional
    public Usuario guardar(Usuario usuario) {
        return this.repository.save(usuario);
    }

    @Override
  //  @Transactional
    public void eliminar(Long id) {
        Optional<Usuario> u = porId( id);
        if (u.isPresent()){
            this.repository.delete(u.get());
            this.cliente.eliminarUsuario(id);
        }

    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> listarPorIds(Iterable<Long> ids) {

        return (List<Usuario>) this.repository.findAllById(ids);
    }

    @Override
    public Optional<Usuario> findByEmail(String email) {
        //Nombre del metodo
        //return this.repository.findByEmail(email);
        //Por @Query
        return this.repository.porEmail(email);
    }

    @Override
    public boolean existByEmail(String email) {
        return this.repository.existsByEmail(email);
    }
}
