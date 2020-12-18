package io.github.pedroAndrad1.rest;

import io.github.pedroAndrad1.models.entity.Usuario;
import io.github.pedroAndrad1.rest.exceptions.LoginExistenteException;
import io.github.pedroAndrad1.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController //Recebe http e as repostas das functions ja estao em formato http
@RequestMapping("/api/usuarios")//Url para contatar este Controller
@RequiredArgsConstructor //Cria um construtor para as propriedades com fina (obrigatorias) e as injeta
public class UsuarioController {

    private final UsuarioService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void salvar(@RequestBody @Valid Usuario user){
      try {
          service.salvarUsuario(user);
      }
      catch (LoginExistenteException e){
          throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
      }
    }

}
