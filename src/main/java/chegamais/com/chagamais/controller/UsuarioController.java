package chegamais.com.chagamais.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import chegamais.com.chagamais.controller.DTO.UsuarioDTO;
import chegamais.com.chagamais.controller.Form.UsuarioForm;
import chegamais.com.chagamais.controller.Form.UsuarioFormUpdate;
import chegamais.com.chagamais.controller.Response.UsuarioResponse;
import chegamais.com.chagamais.services.UsuarioService;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private  UsuarioService usuarioService;

    @GetMapping
    public List<UsuarioResponse> listar(){

        List<UsuarioDTO> Usuarios = this.usuarioService.obterTodos();

        return this.converterLista(Usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponse> selecionarPorId(@PathVariable Long id){

        UsuarioDTO UsuarioDTO = usuarioService.obterPorId(id);

        return this.gerarResposta(UsuarioDTO, 200);
    }

    /* Método em AuthController
    @PostMapping
    public ResponseEntity<UsuarioResponse> cadastrar(@RequestBody @Valid UsuarioForm UsuarioForm){

        UsuarioDTO UsuarioDTO = this.usuarioService.adicionar(UsuarioForm.converterParaDTO());

        return this.gerarResposta(UsuarioDTO, 200);
    }
     */

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponse> atualizar(@RequestBody UsuarioFormUpdate UsuarioFormUpdate, @PathVariable Long id){

        UsuarioDTO UsuarioDTO = this.usuarioService.atualizar(UsuarioFormUpdate.converterParaDTO(), id);

        return this.gerarResposta(UsuarioDTO, 200);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UsuarioResponse> deletarPorId(@PathVariable Long id){

        UsuarioDTO UsuarioDTO = this.usuarioService.deletarPorId(id);

        return this.gerarResposta(UsuarioDTO, 200);
    }



     //funcoes auxiliares
     private ResponseEntity<UsuarioResponse> gerarResposta(UsuarioDTO UsuarioDTO, int status) {

        if(UsuarioDTO == null){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        
        return ResponseEntity.status(status).body(UsuarioDTO.converterParaResponse());
    }
    
    private List<UsuarioResponse> converterLista(List<UsuarioDTO> usuarios){
    	List<UsuarioResponse> responses = new ArrayList<UsuarioResponse>();
    	
    	for(UsuarioDTO dto: usuarios) {
    		responses.add(dto.converterParaResponse());
    	}
    	
    	return responses;
    }

    public  UsuarioService getUsuarioService() {
        return usuarioService;
    }

  

    
    
}
