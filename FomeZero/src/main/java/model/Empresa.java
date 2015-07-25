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
@javax.persistence.SequenceGenerator(name = "SEQEMPRESA",   sequenceName = "SEQEMPRESA",   allocationSize = 1, initialValue = 1)
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "SEQEMPRESA")
    private Long id;

    @Column(length=60)
    private String nome;

    @Column(length=50)
    private String email;

    @Column
    private Long telefone;

    @OneToOne
    private Endereco endereco;

    @Column
    private Long cnpj;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private List<Funcionario> funcionarios;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private List<Produto> produtos;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private List<Pedido> pedidos;

    @OneToMany(cascade= {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, fetch=FetchType.EAGER)
    @JoinColumn(name="empresa_id")
    private List<Entregas> entregas;

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

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public List<Funcionario> getFuncionario() {
        return funcionarios;
    }

    public void setFuncionario(List<Funcionario> funcionarios) {
        this.funcionarios = funcionarios;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public List<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
    }

    public List<Entregas> getEntregas() {
        return entregas;
    }

    public void setEntregas(List<Entregas> entregas) {
        this.entregas = entregas;
    }
}
