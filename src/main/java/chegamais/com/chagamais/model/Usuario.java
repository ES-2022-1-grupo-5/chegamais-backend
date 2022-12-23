package chegamais.com.chagamais.model;


import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Collection;


@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull @NotEmpty 
    private String nome;
    @NotNull @NotEmpty
    private String dataNascimento;
    private String posicaoFavorita;
    @NotNull @NotEmpty 
    private String email;
   @NotNull @NotEmpty 
    private String senha;
    
    
    public Usuario() {

    }


    public Usuario(@NotNull @NotEmpty String nome, @NotNull @NotEmpty String dataNascimento, String posicaoFavorita,
            @NotNull @NotEmpty String email, @NotNull @NotEmpty String senha) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.posicaoFavorita = this.analisarPosicaoFavorita(posicaoFavorita);
        this.email = email;
        this.senha = senha;
    }


    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }


    public String getNome() {
        return nome;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getDataNascimento() {
        return dataNascimento;
    }


    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }


    public String getPosicaoFavorita() {
        return posicaoFavorita;
    }


    public void setPosicaoFavorita(String posicaoFavorita) {
        this.posicaoFavorita = this.analisarPosicaoFavorita(posicaoFavorita);
       }


    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }


    public String getSenha() {
        return senha;
    }


    public void setSenha(String senha) {
        this.senha = senha;
    }

    private String analisarPosicaoFavorita(String posicao){
        if( posicao == null || posicao == ""){
            return "Não delcarada";
        }
        else {
            return posicao;
        }
    }

}
