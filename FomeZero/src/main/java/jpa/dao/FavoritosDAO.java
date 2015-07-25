package jpa.dao;

import jpa.interfaces.IFavoritos;
import model.Favoritos;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class FavoritosDAO implements IFavoritos {

    private static final Logger logger = Logger.getLogger(FavoritosDAO.class);
    private EntityManager entityManager;

    public FavoritosDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Favoritos buscaPorId(Long id) {
        Favoritos favoritos = entityManager.find(Favoritos.class, id);
        return favoritos;
    }

    public List<Favoritos> meusFavoritos() {
        TypedQuery<Favoritos> favoritos = entityManager.createQuery("select f from Favoritos f", Favoritos.class);
        List<Favoritos> listaFavoritos = favoritos.getResultList();
        return listaFavoritos;
    }

    public void adiciona(Favoritos favoritos) {
        entityManager.persist(favoritos);
    }

    public void remove(Favoritos favoritos) {
        entityManager.remove(favoritos);
    }

    public void altera(Favoritos favoritos) {
        entityManager.merge(favoritos);
    }
}
