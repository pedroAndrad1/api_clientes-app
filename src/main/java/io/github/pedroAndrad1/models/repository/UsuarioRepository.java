package io.github.pedroAndrad1.models.repository;

import io.github.pedroAndrad1.models.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//A JpaRepository recebe dois parametros, o primeiro e a entity que sera persistida e a segundo e o
//tipo da chave primaria da entity
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    //Acredito que para isso funcionar, o findByPropertie precisa que a Propertie comece com letra maiscula
    //e o resto seja igual como esta escrito na classe Usuario.
    Optional<Usuario> findByUsername(String username);

    boolean existsByUsername(String username);
}
