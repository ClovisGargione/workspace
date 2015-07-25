package model;

import enums.StatusPedido;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@javax.persistence.SequenceGenerator(name = "SEQPEDIDO",   sequenceName = "SEQPEDIDO",   allocationSize = 1, initialValue = 1)
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQPEDIDO")
    private Long id;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="pedido_id")
    private List<Produto> produtos;

    @Enumerated
    private StatusPedido statusPedido;

    @ManyToOne
    private Cliente cliente;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public StatusPedido getStatusPedido() {
        return statusPedido;
    }

    public void setStatusPedido(StatusPedido statusPedido) {
        this.statusPedido = statusPedido;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
