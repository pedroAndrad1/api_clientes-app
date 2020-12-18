package io.github.pedroAndrad1.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

//Como na requicao vai vir so a id do cliente, preciso de uma classe intermediaria (DTO) para
//pegar essa id e usar para achar o cliente e ai sim instanciar um obj ServicoPrestado
@NoArgsConstructor //Cria um construtor sem argumentos
@Data //Functions padrao de entity's, como getters, setters, constructors, hash, etc
public class ServicoPrestadoDTO {
    @NotNull(message = "{campo.cliente.obrigatorio}")
    private Integer clienteId;
    @NotEmpty(message = "{campo.descricao.obrigatorio}")
    @Length(max = 150, message = "{campo.descricao.max}")
    private String descricao;
    @NotEmpty(message = "{campo.valor.obrigatorio}")
    private String valor;
    @NotEmpty(message = "{campo.data.obrigatorio}")
    private String data;
}
