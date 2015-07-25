package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.EntregasDAO;
import jpa.interfaces.IEntregas;
import model.Entregas;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class EntregasFacade {

    private static final Logger logger = Logger.getLogger(EntregasFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IEntregas iEntregas;

    public EntregasFacade(){
        this.iEntregas = new EntregasDAO(entityManager);
    }

    public Entregas buscaPorId(Long id) throws Exception {
        Entregas entregas;
        try{
            entregas = iEntregas.buscapPorId(id);
        }catch(Exception e){
            logger.error("Não foi possível localizar a entrega id: " +id);
            throw e;
        }
        return entregas;
    }

    public List<Entregas> entregas() throws Exception {
        List<Entregas> entrega;
        try{
            entrega = iEntregas.entregas();
        }catch(Exception e){
            logger.error("Não foi possível localizar a lista de entregas");
            throw e;
        }
        return  entrega;
    }

    public void adiciona(Entregas entregas) throws Exception {
        try{
            iEntregas.adiciona(entregas);
        }catch (Exception e) {
            logger.error("Não foi possível adicionar a entrega");
            throw e;
        }
    }

    public void remove(Entregas entregas) throws Exception {
        try{
            iEntregas.remove(entregas);
        }catch (Exception e) {
            logger.error("Não foi possível adicionar a entrega");
            throw e;
        }
    }

    public void altera(Entregas entregas) throws Exception {
        try{
            iEntregas.altera(entregas);
        }catch (Exception e) {
            logger.error("Não foi possível adicionar a entrega");
            throw e;
        }
    }




}
