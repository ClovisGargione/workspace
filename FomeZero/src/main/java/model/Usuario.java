package model;

import enums.PerfilUsuario;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@javax.persistence.SequenceGenerator(name = "SEQUSUARIOS",   sequenceName = "SEQUSUARIOS",   allocationSize = 1, initialValue = 1)
public class Usuario {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "SEQUSUARIOS")
    private Long id;


    @Column(length=20)
    private String login;

    @Column(length=200)
    private String senha;

    @Enumerated(EnumType.STRING)
    private PerfilUsuario perfil;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public PerfilUsuario getPerfil() {
        return perfil;
    }

    public void setPerfil(PerfilUsuario perfil) {
        this.perfil = perfil;
    }
}
