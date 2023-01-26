package org.aguzman.springcloud.msvc.cursos.services;

import org.aguzman.springcloud.msvc.cursos.clients.UsuarioClientRest;
import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;
import org.aguzman.springcloud.msvc.cursos.models.entity.CursoUsuario;
import org.aguzman.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service

public class CursoServiceImpl implements CursoService{
    @Autowired
    private CursoRepository r;

    @Autowired
    private UsuarioClientRest client;

    @Override
    @Transactional(readOnly = true)
    public List<Curso> listar() {
        return (List<Curso>) this.r.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Curso> porId(Long id) {
        return this.r.findById(id);
    }

    @Override
    @Transactional( readOnly = true)
    public Optional<Curso> porIdConUsuarios(Long id) {
        Optional<Curso> o = this.r.findById(id);
        if (o.isPresent()){
            Curso curso = o.get();
            List<Long> ids = new ArrayList<>();
            if (!curso.getCursoUsuarios().isEmpty()){
                 ids = curso.getCursoUsuarios().stream().map(e-> e.getUsuarioId()
                ).collect(Collectors.toList());
                curso.setUsuarios(this.client.alumnosPorCurso(ids));
            }

            return Optional.of(curso);
        }
        return Optional.empty();
    }

    @Override
    @Transactional(readOnly = false)
    public Curso guardar(Curso curso) {
        return this.r.save(curso);
    }

    @Override
    @Transactional(readOnly = false)
    public void eliminar(Long id) {
        this.r.deleteById(id);
    }

    @Override
    @Transactional
    public void eliminarCursoUsuarioPorId(Long id) {
        this.r.eliminarCursoUsuarioPorId(id);

    }

    @Override
    @Transactional
    public Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.r.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMsvc = this.client.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            this.r.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.r.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMsvc = this.client.crear(usuario);
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.addCursoUsuario(cursoUsuario);
            this.r.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId) {
        Optional<Curso> o = this.r.findById(cursoId);
        if (o.isPresent()){
            Usuario usuarioMsvc = this.client.detalle(usuario.getId());
            Curso curso = o.get();
            CursoUsuario cursoUsuario = new CursoUsuario();
            cursoUsuario.setUsuarioId(usuarioMsvc.getId());

            curso.removeCursoUsuario(cursoUsuario);
            this.r.save(curso);
            return Optional.of(usuarioMsvc);
        }
        return Optional.empty();
    }
}
