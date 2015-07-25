package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@javax.persistence.SequenceGenerator(name = "SEQFAVORITOS",   sequenceName = "SEQFAVORITOS",   allocationSize = 1, initialValue = 1)
public class Favoritos {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQFAVORITOS")
    private Long id;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="favorito_id")
    private List<Empresa> restaurantesFavoritos;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="favorito_id")
    private List<Produto> produtosFavoritos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Empresa> getRestaurantesFavoritos() {
        return restaurantesFavoritos;
    }

    public void setRestaurantesFavoritos(List<Empresa> restaurantesFavoritos) {
        this.restaurantesFavoritos = restaurantesFavoritos;
    }

    public List<Produto> getProdutosFavoritos() {
        return produtosFavoritos;
    }

    public void setProdutosFavoritos(List<Produto> produtosFavoritos) {
        this.produtosFavoritos = produtosFavoritos;
    }
}
