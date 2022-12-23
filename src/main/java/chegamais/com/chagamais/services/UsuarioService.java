package chegamais.com.chagamais.services;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import chegamais.com.chagamais.controller.DTO.UsuarioDTO;
import chegamais.com.chagamais.model.Usuario;
import chegamais.com.chagamais.repository.UsuarioRepository;

import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService implements ServiceInteface<UsuarioDTO> {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDTO> obterTodos() {

        List<Usuario> usuarios = usuarioRepository.findAll();

        return this.converterLista(usuarios);
    }

    @Override
    public UsuarioDTO obterPorId(Long id) {

        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        return this.converterOptional(usuarioOp, false);
    }

    @Transactional(rollbackFor = Exception.class) //garante que se der erro, não salva no banco
    @Override
    public UsuarioDTO adicionar(UsuarioDTO dto) {

        dto.setId(null);

        dto.setSenha(passwordEncoder.encode(dto.getSenha()));

        Usuario usuario = dto.converterParaModel();

        usuarioRepository.save(usuario);

        dto.setId(usuario.getId());

        return dto;
    }

    @Override
    public UsuarioDTO atualizar(UsuarioDTO dto, Long id) {

        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        Usuario usuario = usuarioOp.get();

        this.verificarEAtualizar(usuario, dto);

        usuarioRepository.save(usuario);


        return  this.converterModelParaDTO(usuario);
    }

    @Override
    public UsuarioDTO deletarPorId(Long id) {

        Optional<Usuario> usuarioOp = usuarioRepository.findById(id);

        if(!usuarioOp.isPresent()){
            return null;
        }

        UsuarioDTO usuarioDTO = this.converterOptional(usuarioOp, true);

        usuarioRepository.deleteById(id);

        return usuarioDTO;
    }


    //funcoes auxiliares

    private UsuarioDTO converterModelParaDTO(Usuario usuario){
        UsuarioDTO dto = new  UsuarioDTO(usuario.getNome(), usuario.getDataNascimento(), 
        usuario.getPosicaoFavorita(), usuario.getEmail(), usuario.getSenha());
        dto.setId(usuario.getId());

        return dto;

    }

    private List<UsuarioDTO> converterLista(List<Usuario> usuarios){
    	List<UsuarioDTO> DTOs = new ArrayList<UsuarioDTO>();
    	
    	for(Usuario usuario: usuarios) {
    		UsuarioDTO dto = this.converterModelParaDTO(usuario);
    		DTOs.add( dto);
    	}
    	
    	return DTOs;
    	
    }

    private UsuarioDTO converterOptional(Optional<Usuario> usuarioOp, Boolean confere){

        if(!confere){
        if(!usuarioOp.isPresent()){
            return null;
        }
    }

        Usuario usuario = usuarioOp.get();
        UsuarioDTO dto = this.converterModelParaDTO(usuario);


        return dto;

    }

    private void verificarEAtualizar(Usuario usuario, UsuarioDTO usuarioDTO){

        String nomeDTO = usuarioDTO.getNome();
        if(nomeDTO != null ){
            if(nomeDTO != ""){
                usuario.setNome(nomeDTO);
            }
        }

        String emailDTO = usuarioDTO.getEmail();
        if(emailDTO != null ){
            if(emailDTO != ""){
                usuario.setEmail(emailDTO);
            }
        }

        String senhaDTO = usuarioDTO.getSenha();
        if(senhaDTO != null){
            if(senhaDTO != ""){
                usuario.setSenha(senhaDTO);
            }
        }

        String posicaoDTO = usuarioDTO.getPosicaoFavorita();
        if(posicaoDTO != null){
            if(posicaoDTO != ""){
                usuario.setPosicaoFavorita(posicaoDTO);;
            }
        }
        
        String dataNascimentoDTO = usuarioDTO.getDataNascimento();
        if(dataNascimentoDTO != null){
            usuario.setDataNascimento(dataNascimentoDTO);

        }

    }

}
