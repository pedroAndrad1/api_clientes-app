package io.github.pedroAndrad1.rest;

import io.github.pedroAndrad1.rest.exceptions.ApiErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

/**Configura as respostas de erro para ficarem mais palataveis */
@RestControllerAdvice
public class ApplicationControllerAdvice {

    //Para lidar com excessoes e qual o tipo que a fucntion atua
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleValidation(MethodArgumentNotValidException ex){
        BindingResult bindingResult = ex.getBindingResult();

        //Pegando as messages de error em uma List
        List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    //Pegando as mensagens de erro
                    .map(objectError -> objectError.getDefaultMessage())
                     //Coletando as messages da stream em uma List
                    .collect(Collectors.toList());
        return new ApiErrors(errorMessages);
    }

    //Os metedos de DELETE E GET lancam um ResponseStatusException
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex){
        //Pegando a mensagem
        //Usando o getReason em vez do getMessage para nao colocar o HTTP Status junto da mensagem
        //Para nao ficar assim: 400 BAD_REQUEST "Já há outro usuário com esse login. Por favor, escolha outro."
        String errorMessage = ex.getReason();
        //Pegando o Status que pode ser qualquer um
        HttpStatus httpStatus = ex.getStatus();
        //Criando um ApiErrors para colocar dentro do ReponseEntity.
        //O ReponseEntity e so um wrapper para qualquer coisa, que nessa caso e a ApiErrors
        ApiErrors apiErrors = new ApiErrors(errorMessage);
        return new ResponseEntity(apiErrors, httpStatus);


    }
}
