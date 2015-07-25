package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@SequenceGenerator(name = "SEQDESTINATARIO",   sequenceName = "SEQDESTINATARIO",   allocationSize = 1, initialValue = 1)
public class DestinatarioModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "SEQDESTINATARIO")
    private Long id;

    @Column
    private String email;

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
}
