package jpa.dao;

import jpa.interfaces.IEntregas;
import model.Entregas;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class EntregasDAO implements IEntregas {

    private static final Logger logger = Logger.getLogger(EntregasDAO.class);
    private EntityManager entityManager;

    public EntregasDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Entregas buscapPorId(Long id) {
        Entregas entregas = entityManager.find(Entregas.class, id);
        return entregas;
    }

    public List<Entregas> entregas() {
        TypedQuery<Entregas> query = entityManager.createQuery("select e from Entregas e", Entregas.class);
        List<Entregas> listaDeEntregas = query.getResultList();
        return listaDeEntregas;
    }

    public void adiciona(Entregas entrega) {
        entityManager.persist(entrega);
    }

    public void remove(Entregas entrega) {
        entityManager.remove(entrega);
    }

    public void altera(Entregas entrega) {
        entityManager.merge(entrega);
    }
}
