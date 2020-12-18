package io.github.pedroAndrad1.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Value("{security.jwt.signing-key}")//Injetando o valor das properties
    private String signingKey;


    //Gera um token Jwt
    @Bean //Nao se esqueca do @Bean!!!!!!!!!!!
    public TokenStore tokenStore(){
        return new JwtTokenStore(jwtAccessTokenConverter());
    }

    //E preciso passar o objeto abaixo para o jwtTokenStore.
    //Para ser possivel converter o token que vai vir do front-end
    // Ele vai ser criado com a signKey
    @Bean//Nao se esqueca do @Bean!!!!!!!!!!!
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter jwtAccessTokenConverter = new JwtAccessTokenConverter();
        jwtAccessTokenConverter.setSigningKey(signingKey);//Setando a minha chave
        return  jwtAccessTokenConverter;
    }

    //Configura os tokens
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .tokenStore(tokenStore())
                .accessTokenConverter(jwtAccessTokenConverter()) //e preciso passar o converter aqui tbm
                .authenticationManager(authenticationManager);

    }

    //Criando um client_ID e um secret em memoria para a app que vai acessar as apis.
    //Como so vai ser uma, isso e viavel.
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                .inMemory()
                //Login da app
                .withClient("app-vendas-pedro-andrade")
                //senha
                .secret("acai-com-leite-em-po")
                //scopos pra app poder fazer gets e posts
                .scopes("read","write")
                //fluxo de authorization que estou usando
                .authorizedGrantTypes("password")
                //tempo em segundos que o token vale, vou colocar uma hora
                .accessTokenValiditySeconds(60 * 60);
    }
}
