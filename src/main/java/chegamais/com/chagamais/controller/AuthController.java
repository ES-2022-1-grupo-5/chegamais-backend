package chegamais.com.chagamais.controller;

import chegamais.com.chagamais.controller.DTO.LoginDTO;
import chegamais.com.chagamais.controller.Form.UsuarioForm;
import chegamais.com.chagamais.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid UsuarioForm usuarioForm) {
        // adicionar checagem de email já existente

        this.usuarioService.adicionar(usuarioForm.converterParaDTO());

        return new ResponseEntity<>("Usuário cadastrado com sucesso", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody @Valid LoginDTO loginDTO) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha()));
        SecurityContextHolder.getContext().setAuthentication(auth); // mover para a lógica de serviço

        return new ResponseEntity<>("Usuário logado com sucesso", HttpStatus.OK);
    }
}
