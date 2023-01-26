package org.aguzman.springcloud.msvc.cursos.repositories;


import org.aguzman.springcloud.msvc.cursos.models.entity.Curso;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CursoRepository extends CrudRepository<Curso, Long> {

//    @Modifying: Cada vez que se haga un update, insert o delete  debe anotarse con esta anotacion para
//    que se realize  efectivamente esta operacion
    @Modifying
    @Query("delete from CursoUsuario cu where cu.usuarioId=?1")
    void eliminarCursoUsuarioPorId(Long id);
}
