package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.PedidoDAO;
import jpa.interfaces.IPedido;
import model.Favoritos;
import model.Pedido;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class PedidoFacade {

    private static final Logger logger = Logger.getLogger(PedidoFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IPedido iPedido;

    public PedidoFacade(){
        this.iPedido = new PedidoDAO(entityManager);
    }

    public Pedido buscaPorId(Long id) throws Exception {
        Pedido pedido;
        try{
            pedido = iPedido.buscaPorId(id);
        }catch (Exception e){
            logger.error("Não foi possível localizar o pedido id " + id);
            throw e;
        }
        return pedido;
    }

    public List<Pedido> pedidos() throws Exception {
        List<Pedido> pedidos;
        try{
            pedidos = iPedido.pedidos();
        }catch(Exception e){
            logger.error("Não foi possível localizar a lista de pedidos");
            throw e;
        }
        return pedidos;
    }

    public void adiciona(Pedido pedido) throws Exception {
        try{
            iPedido.adiciona(pedido);
        }catch (Exception e){
            logger.error("Não foi possível adicionar o pedido");
            throw e;
        }
    }

    public void remove(Pedido pedido) throws Exception {
        try{
            iPedido.remove(pedido);
        }catch (Exception e){
            logger.error("Não foi possível remover o pedido");
            throw e;
        }
    }

    public void alterar(Pedido pedido) throws Exception {
        try{
            iPedido.altera(pedido);
        }catch (Exception e){
            logger.error("Não foi possível alterar o pedido");
            throw e;
        }
    }
}
