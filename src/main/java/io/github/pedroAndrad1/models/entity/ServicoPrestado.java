package io.github.pedroAndrad1.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity //Para ser uma tabela no banco de dados
@Data //Functions padrao de entity's, como getters, setters, constructors, hash, etc
public class ServicoPrestado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 150)
    private String descricao;

    @ManyToOne //Muitos servicos esstarao relacionados a um cliente
    @JoinColumn(name = "id_cliente") //Nome da chave estrangeira
    private Cliente cliente;

    @Column
    private BigDecimal valor;

    @Column
    @JsonFormat(pattern = "dd/MM/yyyy") //Formata a data
    private LocalDate data;

}
