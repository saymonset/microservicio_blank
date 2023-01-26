package org.aguzman.springcloud.msvc.cursos.services;

import org.aguzman.springcloud.msvc.cursos.models.Usuario;
import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;

import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Optional<Curso> porIdConUsuarios(Long id);
    Curso guardar(Curso curso);
    void  eliminar(Long id);

    void eliminarCursoUsuarioPorId(Long id);

//  Esta logica de negocio se obtiene de datos de otro servico

 Optional<Usuario> asignarUsuario(Usuario usuario, Long cursoId);
 Optional<Usuario> crearUsuario(Usuario usuario, Long cursoId);
 Optional<Usuario> eliminarUsuario(Usuario usuario, Long cursoId);



}
