package io.github.pedroAndrad1.models.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity //Para ser uma tabela no banco de dados
@Data //Functions padrao de entity's, como getters, setters, constructors, hash, etc
@NoArgsConstructor //Prove um cosntrutor com nenhum dos atributos
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //Cada usuario deve ter um username unico. Podia ate usar isso como Id, mas deixa o de cima mesmo
    @Column(unique = true)
    @NotNull(message = "{campo.username.obrigatorio}")
    private String username;
    @Column
    @NotNull(message = "{campo.password.obrigatorio}")
    private String password;
}
