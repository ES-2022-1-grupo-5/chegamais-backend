package chegamais.com.chagamais.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import chegamais.com.chagamais.controller.DTO.GrupoDTO;
import chegamais.com.chagamais.controller.DTO.UsuarioDTO;
import chegamais.com.chagamais.model.Grupo;
import chegamais.com.chagamais.repository.GrupoRepository;

@Service
public class GrupoService implements ServiceInteface<GrupoDTO>{

    @Autowired
    private GrupoRepository grupoRepository;

    @Override
    public List<GrupoDTO> obterTodos() {
        List<Grupo> Grupos = grupoRepository.findAll();

        return this.converterLista(Grupos);
    }

    @Override
    public GrupoDTO obterPorId(Long id) {
        Optional<Grupo> GrupoOp = grupoRepository.findById(id);

        return this.converterOptional(GrupoOp, false);
    }

    @Override
    public GrupoDTO adicionar(GrupoDTO dto) {
        dto.setId(null);


        Grupo Grupo = dto.converterParaModel();

        grupoRepository.save(Grupo);

        dto.setId(Grupo.getId());


        return dto;
    }

    @Override
    public GrupoDTO atualizar(GrupoDTO dto, Long id) {
        Optional<Grupo> GrupoOp = grupoRepository.findById(id);

        Grupo Grupo = GrupoOp.get();

        this.verificarEAtualizar(Grupo, dto);

        grupoRepository.save(Grupo);
        
        


        return  this.converterModelParaDTO(Grupo);
    }

    @Override
    public GrupoDTO deletarPorId(Long id) {
        Optional<Grupo> GrupoOp = grupoRepository.findById(id);

        if(!GrupoOp.isPresent()){
            return null;
        }

        GrupoDTO GrupoDTO = this.converterOptional(GrupoOp, true);

        grupoRepository.deleteById(id);

        return GrupoDTO;
    }

    public UsuarioDTO inserirUsuarioNoGrupo(UsuarioDTO usuarioDto, GrupoDTO grupoDTO){

        grupoDTO.converterParaModel().getMembros().add(usuarioDto.converterParaModel());
        return usuarioDto;

    }

     //funcoes auxiliares

     private GrupoDTO converterModelParaDTO(Grupo Grupo){
        GrupoDTO dto = new  GrupoDTO(Grupo.getNome());
        dto.setId(Grupo.getId());

        return dto;

    }

    private List<GrupoDTO> converterLista(List<Grupo> Grupos){
    	List<GrupoDTO> DTOs = new ArrayList<GrupoDTO>();
    	
    	for(Grupo Grupo: Grupos) {
    		GrupoDTO dto = this.converterModelParaDTO(Grupo);
    		DTOs.add( dto);
    	}
    	
    	return DTOs;
    	
    }

    private GrupoDTO converterOptional(Optional<Grupo> GrupoOp, Boolean confere){

        if(!confere){
        if(!GrupoOp.isPresent()){
            return null;
        }
    }

        Grupo Grupo = GrupoOp.get();
        GrupoDTO dto = this.converterModelParaDTO(Grupo);


        return dto;

    }

    private void verificarEAtualizar(Grupo Grupo, GrupoDTO GrupoDTO){

        String nomeDTO = GrupoDTO.getNome();
        if(nomeDTO != null ){
            if(nomeDTO != ""){
                Grupo.setNome(nomeDTO);
            }
        }

        

    }

    

    
    
}
