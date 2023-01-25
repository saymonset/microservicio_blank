package org.aguzman.springcloud.msvc.cursos.services;

import org.aguzman.springcloud.msvc.cursos.entity.Curso;
import org.aguzman.springcloud.msvc.cursos.repositories.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CursoServiceImpl implements CursoService{
    @Autowired
    private CursoRepository r;

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
    @Transactional(readOnly = false)
    public Curso guardar(Curso curso) {
        return this.r.save(curso);
    }

    @Override
    @Transactional(readOnly = false)
    public void eliminar(Long id) {
        this.r.deleteById(id);
    }
}
