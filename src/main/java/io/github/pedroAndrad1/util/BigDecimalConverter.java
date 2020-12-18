package io.github.pedroAndrad1.util;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component //torna a classe injetavel
public class BigDecimalConverter {

    //O valor de servico vai vir em Real 1.000,00. Deve ser convertido para 1000.00
    //1.000,00 -> 1000.00
    public BigDecimal converterRealParaBigDecimal(String value){
        if(value == null) return null; //Checando para evitar NullPointerException

        //Primeiro, trocar os pontos por nada e depois trocar as virgulas por pontos.
        value = value.replace(".", "").replace(",", ".");

        return new BigDecimal(value);
    }
}
