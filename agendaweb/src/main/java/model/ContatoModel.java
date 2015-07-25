package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by c_r_s_000 on 25/05/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@javax.persistence.SequenceGenerator(name = "SEQCONTATOS",   sequenceName = "SEQCONTATOS",   allocationSize = 1, initialValue = 1)
public class ContatoModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQCONTATOS")
    private Long id;

    @Column(length = 60)
    private String nome;

    @Column(length = 100)
    private String email;

    @Column
    private Long telefone;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataNascimento;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

}

