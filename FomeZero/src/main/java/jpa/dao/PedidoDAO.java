package jpa.dao;

import jpa.interfaces.IPedido;
import model.Pedido;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class PedidoDAO implements IPedido {

    private static final Logger logger = Logger.getLogger(PedidoDAO.class);
    private EntityManager entityManager;

    public PedidoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Pedido buscaPorId(Long id) {
        Pedido pedido = entityManager.find(Pedido.class, id);
        return pedido;
    }

    public List<Pedido> pedidos() {
        TypedQuery<Pedido> query = entityManager.createQuery("select p from Pedido p", Pedido.class);
        List<Pedido> pedidos = query.getResultList();
        return pedidos;
    }

    public void adiciona(Pedido pedido) {
        entityManager.persist(pedido);
    }

    public void remove(Pedido pedido) {
        entityManager.remove(pedido);
    }

    public void altera(Pedido pedido) {
        entityManager.merge(pedido);
    }
}
