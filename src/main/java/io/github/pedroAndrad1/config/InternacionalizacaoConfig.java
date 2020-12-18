package io.github.pedroAndrad1.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

/**
 * Conigura as mensagens das validations
 * */

@Configuration
public class InternacionalizacaoConfig {

    @Bean// torna a function escaneavel
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:messages");//Local do do arquivo com as configuracoes
        messageSource.setDefaultEncoding("ISO-8859-1");//Encoding que reconhece a ortografia brasileira
        messageSource.setDefaultLocale(Locale.getDefault());//Seta o LOCALE da maquina do user

        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean(){
        //Armazena o MessageSource para poder ser usado
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());

        return bean;
    }
}
