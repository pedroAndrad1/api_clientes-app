package io.github.pedroAndrad1.config;

import io.github.pedroAndrad1.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/*Config do WebScurity do spring */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsuarioService usuarioService;

    //Configura para autenticar os usuarios do banco na hora do login
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
             //Para pegar os usuarios e comparar a senha
            .userDetailsService(usuarioService)
            //Desencriptador para usar na senha que veio via POST para comparar
            .passwordEncoder(passwordEncoder());//Usando o definido abaixo
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //Vamos usar o auth e nao outra app para a authentication, entao na na ha necessidade do crf
                .csrf().disable()
                .cors()
        .and()
                //O spring security nao fara o controle de sessao, pois sera usado um token criado pelo oauth
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    //Encriptador e Desencriptador da senhas. Nesse caso, retorna um que nao faz nada
    @Bean
    public PasswordEncoder passwordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
