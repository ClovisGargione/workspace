package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@javax.persistence.SequenceGenerator(name = "SEQCLIENTES",   sequenceName = "SEQCLIENTES",   allocationSize = 1, initialValue = 1)
public class Cliente {

    @Id
    @GeneratedValue(strategy=GenerationType.TABLE, generator = "SEQCLIENTES")
    private Long id;

    @Column(length=60)
    private String nome;

    @Column(length=50)
    private String email;

    @Column
    private Long telefone;

    @OneToOne
    private Endereco endereco;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="cliente_id")
    private List<Pedido> pedidos;

    @OneToOne
    private Usuario usuario;

    @OneToOne
    private Favoritos favoritos;

    public Long getTelefone() {
        return telefone;
    }

    public void setTelefone(Long telefone) {
        this.telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public Favoritos getFavoritos() {
        return favoritos;
    }

    public void setFavoritos(Favoritos favoritos) {
        this.favoritos = favoritos;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
