package io.github.pedroAndrad1.models.repository;

import io.github.pedroAndrad1.models.entity.ServicoPrestado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServicoPrestadoRepository extends JpaRepository<ServicoPrestado, Integer> {
    //Como o atributo NomeCliente nao existe, sera preciso escrever a query com a
    //annotation @Query
    @Query("select s from ServicoPrestado s" +
            " join s.cliente c " +
            "where upper(c.nome) like upper(:nome) and MONTH(s.data) = :mes")
    //Para os parametros na query serem reconhecidos, os paramteros da funcao precisam
    //ser annotadados com @Param
    List<ServicoPrestado> findByNomeClienteAndMes(@Param("nome") String nome,
                                                  @Param("mes") Integer mes);
}
