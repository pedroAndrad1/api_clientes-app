package io.github.pedroAndrad1.rest.exceptions;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

/** Uma lista de errors da Api */
public class ApiErrors {
    @Getter
    List<String> errors;

    public ApiErrors(List<String> errors){
        this.errors = errors;
    }

    //Quando so existe uma mensagem de erro, e preciso parsear para uma List
    public ApiErrors(String error){
        this.errors = Arrays.asList(error);
    }
}
