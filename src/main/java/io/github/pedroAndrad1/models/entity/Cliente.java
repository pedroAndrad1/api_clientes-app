package io.github.pedroAndrad1.models.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;

@Entity //Para ser uma tabela no banco de dados
@Data //Functions padrao de entity's, como getters, setters, hash, etc
@AllArgsConstructor //Prove um cosntrutor com todos os atributos
@NoArgsConstructor //Prove um cosntrutor com nenhum dos atributos
@Builder
public class Cliente {

    //Cada @Column significa que aquele atributo e uma coluna na tabela

    @Id //Chave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) //O banco se encarrega de gerar o Id
    private Integer id;

    @Column(nullable = false, length = 150) //Required e no maximo 150 caracteres
    //Validacao de campo obrigatorio e mensagem de erro
    @NotEmpty(message = "{campo.nome.obrigatorio}")
    private String nome;

    @Column(nullable = false)//Required
    @NotEmpty(message = "{campo.cpf.obrigatorio}")
    @CPF(message = "{campo.cpf.invalido}")//Validacao para cpf brasileiro e mensagem de erro
    @Length(max = 11, message = "{campo.cpf.length}")
    private String cpf;

    @CreationTimestamp//Atribui a data atual na variavel
    @Column(name = "data_cadastro", updatable = false)//No banco nao vai ser camelCase e nao podera ser atualizado
    @JsonFormat(pattern = "dd/MM/yyyy") //Formata a data
    private LocalDate dataCadastro;


}
