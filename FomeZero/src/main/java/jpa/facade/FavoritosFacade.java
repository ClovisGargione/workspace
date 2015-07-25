package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.FavoritosDAO;
import jpa.interfaces.IFavoritos;
import model.Favoritos;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class FavoritosFacade {

    private static final Logger logger = Logger.getLogger(FavoritosFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IFavoritos iFavoritos;

    public FavoritosFacade(){
        this.iFavoritos = new FavoritosDAO(entityManager);
    }

    public Favoritos buscaPorId(Long id) throws Exception {
        Favoritos favoritos;
        try{
            favoritos = iFavoritos.buscaPorId(id);
        }catch (Exception e){
            logger.error("Não foi possível localizar os favoritos");
            throw e;
        }
        return favoritos;
    }

    public List<Favoritos> meusFavoritos() throws Exception {
        List<Favoritos> favoritos;
        try{
            favoritos = iFavoritos.meusFavoritos();
        }catch(Exception e){
            logger.error("Não foi possível localizar meus favoritos");
            throw e;
        }
        return favoritos;
    }

    public void adiciona(Favoritos favoritos) throws Exception {
        try{
            iFavoritos.adiciona(favoritos);
        }catch (Exception e){
            logger.error("Não foi possível adicionar aos meus favoritos");
            throw e;
        }
    }

    public void remove(Favoritos favoritos) throws Exception {
        try{
            iFavoritos.remove(favoritos);
        }catch (Exception e){
            logger.error("Não foi possível remover meus favoritos");
            throw e;
        }
    }

    public void alterar(Favoritos favoritos) throws Exception {
        try{
            iFavoritos.altera(favoritos);
        }catch (Exception e){
            logger.error("Não foi possível alterar meus favoritos");
            throw e;
        }
    }


}
