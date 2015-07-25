package jpa.dao;

import jpa.interfaces.IProduto;
import model.Produto;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class ProdutoDAO implements IProduto {

    private static final Logger logger = Logger.getLogger(PedidoDAO.class);
    private EntityManager entityManager;

    public ProdutoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Produto buscaPorId(Long id) {
        Produto produto = entityManager.find(Produto.class, id);
        return produto;
    }

    public List<Produto> produtos() {
        TypedQuery<Produto> query = entityManager.createQuery("select p from Produto p", Produto.class);
        List<Produto> produtos = query.getResultList();
        return produtos;
    }

    public void adiciona(Produto produto) {
        entityManager.persist(produto);
    }

    public void remove(Produto produto) {
        entityManager.remove(produto);
    }

    public void altera(Produto produto) {
        entityManager.merge(produto);
    }
}
