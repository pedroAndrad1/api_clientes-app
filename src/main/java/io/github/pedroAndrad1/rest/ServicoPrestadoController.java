package io.github.pedroAndrad1.rest;

import io.github.pedroAndrad1.models.entity.Cliente;
import io.github.pedroAndrad1.models.entity.ServicoPrestado;
import io.github.pedroAndrad1.models.repository.ClienteRepository;
import io.github.pedroAndrad1.models.repository.ServicoPrestadoRepository;
import io.github.pedroAndrad1.rest.dto.ServicoPrestadoDTO;
import io.github.pedroAndrad1.util.BigDecimalConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController //Recebe http e as repostas das functions ja estao em formato http
@RequestMapping("/api/servicos-prestados")//Url para contatar este Controller
@RequiredArgsConstructor //Cria um construtor para as propriedades com fina (obrigatorias) e as injeta
public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository repository;
    private final BigDecimalConverter bigDecimalConverter;

    //Como na requisicao vai vir so a id do cliente, preciso de uma classe intermediaria (DTO) para
    //pegar essa id e usar para achar o cliente e ai sim instanciar um obj ServicoPrestado
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto){
       // if(dto.getDescricao().length() > 150){
       //     throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Máximo de 150 caracteres para a descrição");
       // }

        //Parseando a data de string para localDate
        LocalDate data = LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        //Pegando o cliente pelo id
        Cliente cliente = clienteRepository
                            .findById(dto.getClienteId())
                            //Se o cliente nao existir, lancar uma exception
                            .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.BAD_REQUEST, "O cliente não está registrado")
                            );
        //Montando o ServicoPrestado
        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData(data);
        servicoPrestado.setCliente(cliente);
        //E preciso converter de real para bigDecimal, mais detalhes na classe BigDecimalConverter
        servicoPrestado.setValor( bigDecimalConverter.converterRealParaBigDecimal(dto.getValor()) );

        //Salvando e retornando a reposta da requisicao
        return repository.save(servicoPrestado);
    }

    @GetMapping
    //Dessa vez nao vou receber um objeto no body da funcao, entao, em vez de usar
    //a annotation @RequestBody, usarei a annotation @RequestParam para cada parametro da url.
    //Serao dois, sendo um nao obrigatorio.
    public List<ServicoPrestado> pesquisar(
            @RequestParam(value = "nome", required = false, defaultValue = "") String nome,
            @RequestParam(value = "mes") Integer mes
            ){
        //Vou usar uma funcao customizada do repository de ServicoPrestado
        return repository.findByNomeClienteAndMes("%"+nome+"%", mes);
        //As % sao coringas para retornar o nome podendo ter qualquer coisa antes ou depois.
        //Assim, se nao passar nada, traz todos os nomes.
    }

    @GetMapping("/all")
    //Retorna todos os serviços
    public List<ServicoPrestado> getAll(){
        return repository.findAll();
    }
}
