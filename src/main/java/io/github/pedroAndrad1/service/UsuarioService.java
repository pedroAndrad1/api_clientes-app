package io.github.pedroAndrad1.service;

import io.github.pedroAndrad1.models.entity.Usuario;
import io.github.pedroAndrad1.models.repository.UsuarioRepository;
import io.github.pedroAndrad1.rest.exceptions.LoginExistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//Para pegar o Usuario do banco e transforma-lo em um UserDetail para o AuthenticationManager
//comparar senha recebida com a senha do UserDetail.
//Formalidades de Java
@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository repository;

    //Isso esta aqui pois ha uma logica de negocio que e "usuarios nao tem usarnames iguais"
    //e logicas de negocios ficam na camada de service
    public Usuario salvarUsuario(Usuario usuario) throws LoginExistenteException{
        boolean exists = repository.existsByUsername(usuario.getUsername());
        if(exists){
            throw new LoginExistenteException();
        }

        return repository.save(usuario);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = repository
                                .findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("Login não cadastrado"));

        //Contruindo um UserDetail atraves de um builder.
        return User
                .builder()
                .username(usuario.getUsername())
                .password(usuario.getPassword())
                .roles("USER") //No so a role de USER, nao ha admins etc
                .build();
    }
}
