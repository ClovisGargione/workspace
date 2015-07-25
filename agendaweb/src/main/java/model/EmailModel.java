package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by c_r_s_000 on 03/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@javax.persistence.SequenceGenerator(name = "SEQEMAIL",   sequenceName = "SEQEMAIL",   allocationSize = 1, initialValue = 1)
public class EmailModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQEMAIL")
    private Long id;

    @Column(length =  500)
    private String conteudo;

    @Column(length = 150)
    private String localAnexo;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE}, fetch=FetchType.EAGER)
    @JoinColumn(name="email_id")
    private List<DestinatarioModel> destinatarios;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public String getLocalAnexo() {
        return localAnexo;
    }

    public void setLocalAnexo(String localAnexo) {
        this.localAnexo = localAnexo;
    }

    public List<DestinatarioModel> getDestinatarios() {
        return destinatarios;
    }

    public void setDestinatarios(List<DestinatarioModel> destinatarios) {
        this.destinatarios = destinatarios;
    }

}
