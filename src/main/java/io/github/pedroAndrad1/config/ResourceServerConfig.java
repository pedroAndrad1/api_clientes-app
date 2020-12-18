package io.github.pedroAndrad1.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //Aqui sera configurado qeum pode acessar as api's
        //Cada usuario tem uma Role: ADMIN, USER etc.

        http
                .authorizeRequests()
                //Como essa api faz o cadastro, entoa nao precisa de authentication
                .antMatchers("/api/usuarios").permitAll()
                //As demais e necessario estar autenticado (logado)
                //O "**" e um coringa que significa "e qualquer segmento de rota depois"
                .antMatchers(
                        "/api/clientes/**",
                        "/api/servicos-prestados/**"
                ).authenticated()
                .anyRequest().denyAll(); //Qualuer outra rota sera negada.
    }
}
