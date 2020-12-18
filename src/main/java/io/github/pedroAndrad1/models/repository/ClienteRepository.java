package io.github.pedroAndrad1.models.repository;

import io.github.pedroAndrad1.models.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//A JpaRepository recebe dois parametros, o primeiro e a entity que sera persistida e a segundo e o
//tipo da chave primaria da entity
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
}
