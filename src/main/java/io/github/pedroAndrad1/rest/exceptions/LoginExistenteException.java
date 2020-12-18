package io.github.pedroAndrad1.rest.exceptions;

public class LoginExistenteException extends RuntimeException {

    public LoginExistenteException(){
        super("Já há outro usuário com esse login. Por favor, escolha outro.");
    }
}
