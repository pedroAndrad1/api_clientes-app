package io.github.pedroAndrad1.rest;

import io.github.pedroAndrad1.models.entity.Cliente;
import io.github.pedroAndrad1.models.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController //Recebe http e as repostas das functions ja estao em formato http
@RequestMapping("/api/clientes")//Url para contatar este Controller
public class ClienteController {

    @Autowired
    private ClienteRepository repository;

    //Retorna todos os clientes
    @GetMapping //Acionado no /api/clientes
    public List<Cliente> getAll(){
        return this.repository.findAll();
    }

    @PostMapping//E acionada em POST
    @ResponseStatus(HttpStatus.CREATED) //Reposta de sucesso, o default e OK
    //O RequestBody e para dizer que a request recebe um json e deve parsear para a Cliente
    //O Valid e para o spring validar o request antes do banco
    public Cliente salvar(@RequestBody @Valid Cliente cliente){
            return repository.save(cliente);
    }

    @GetMapping("{id}") //E acionada em get e recebe um parametro chamado de id
    //Para pegar o parametro e preciso a annotation PathVariable. Como o nome do parametro e igual da variavel
    //nao sera necessario @PathVariable("nome_do_parametro")
    public Cliente getById(@PathVariable Integer id){
        return  repository
                .findById(id)
                //Caso nao seja achado, retorna um 404
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Cliente não encontrado") );
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        //Nao vou fazer "repository.deleteById(id);"
        //Em vez, vou fazer um find e um map do find para poder fazer algo que esse cliente nao exista no banco
        repository.findById(id)
                    .map(cliente -> {
                        repository.delete(cliente);
                        //Tenho que retornar algo. Podia retornar o cliente, mas vou retornar Void
                        return Void.TYPE;
                    })
                    .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado"));
    }

    @PutMapping("{id}")
    public void updateCliente(@PathVariable Integer id, @RequestBody @Valid Cliente clienteAtualizado){
        repository.findById(id)
                .map(cliente -> {

                    //Por não ser possivel enviar apenas as mudancas desejadas na request devido a Validacao
                    //em Cliente, essa feature sera implementada no FrontEnd

                    //Irei setar no cliente pego do banco apenas ps updates que foram recebidos.
                    //Assim nao torna obrigatorio enviar tudo, apenas o que quer mudar.
                    //if(clienteAtualizado.getNome() != null)
                        cliente.setNome(clienteAtualizado.getNome());

                    //if(clienteAtualizado.getCpf() != null)
                        cliente.setCpf(clienteAtualizado.getCpf());

                    //O cliente vindo do banco ja tem id, quando uma instancia com um id que ja exista no banco
                    //e salva neste, o banco atualiza o item com esse id.

                    return repository.save(cliente);

                })
                .orElseThrow( () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente não encontrado") );
    }
}
