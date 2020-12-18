package io.github.pedroAndrad1.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;

/**
 * Classe para configuracao das requisicoes url (CROSS-ORIGIN).
 * Para possibilitar a comunicao vinda de urls de outros dominios, diferentes do da api.
 */

@Configuration
public class WebConfig {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        //A principio vou liberar para tudo, dominios, headers e metodos.
        //Vou criar um coringao para facilitar
        List<String> all = Arrays.asList("*");

        //A instancia abaixo serve para configurar o CROSS-ORIGIN (CORS)
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //Liberando acesso para todos os dominios
        corsConfiguration.setAllowedOrigins(all);
        //Liberando qualquer tipo de Header na requisicao
        corsConfiguration.setAllowedHeaders(all);
        //Liberando para qualquer tipo de request (GET, POST ETC)
        corsConfiguration.setAllowedMethods(all);
        //Possibilitando o uso de credencias
        corsConfiguration.setAllowCredentials(true);

        //A instancia abaixo encapsula essas configs
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //Setando as configs para qualquer caminho da API, com o coringa "/**"
        source.registerCorsConfiguration("/**", corsConfiguration);

        //Esse source e encpasulado por outra instancia
        //ATENCAO O IMPORT DEVE SER import org.springframework.web.filter.CorsFilter;
        CorsFilter corsFilter = new CorsFilter(source);

        //Por fim, o corsFilter sera registrado na isntancia abaixo, que sera o retorno
        FilterRegistrationBean<CorsFilter> filterRegistration =
                                            new FilterRegistrationBean<>(corsFilter);

        //Pondo esse filterRegistration no topo da ordem de filters, so para garantir
        filterRegistration.setOrder(Ordered.HIGHEST_PRECEDENCE);

        return filterRegistration;

    }

}
