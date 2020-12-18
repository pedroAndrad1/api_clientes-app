package io.github.pedroAndrad1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Primeira classe a ser usada na app
 **/

@SpringBootApplication //Para ser uma classe "utilizavel"
@RestController //Para controlar requests http
public class VendasApplication {

    //Ess function e chamada nessa url
    @GetMapping("/hello")
    public String helloWorld(){
        return "Hello World";
    }

    public static void main(String[] args) {
        //Recebe uma classe com um decorator acima e argumentos para rodar a app
        SpringApplication.run(VendasApplication.class, args);
    }
}
