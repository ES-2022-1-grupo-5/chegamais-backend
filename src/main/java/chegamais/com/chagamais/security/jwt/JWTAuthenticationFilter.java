package chegamais.com.chagamais.security.jwt;

import chegamais.com.chagamais.controller.DTO.UsuarioDTO;
import chegamais.com.chagamais.model.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static chegamais.com.chagamais.security.securityConfig.SecurityConstants.*;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {


    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/api/auth/login"); //url que vai ser chamada para fazer o login
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UsuarioDTO credenciais = new ObjectMapper()
                    .readValue(request.getInputStream(), UsuarioDTO.class);

            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                    credenciais.getEmail(), credenciais.getSenha(), new ArrayList<>());

            Authentication auth = authenticationManager.authenticate(authToken);
            return auth;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) throws IOException {
        String token = JWT.create()
                .withIssuer("auth0")
                .withSubject(((Usuario) auth.getPrincipal()).getEmail())
                .withExpiresAt(new java.util.Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(Algorithm.HMAC512(SECRET_KEY.getBytes()));

        String body = ((Usuario) auth.getPrincipal()).getId() + ";" + token;
        response.getWriter().write(body);
        response.getWriter().flush();
    }

}
