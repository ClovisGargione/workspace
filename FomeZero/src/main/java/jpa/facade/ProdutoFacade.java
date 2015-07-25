package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.ProdutoDAO;
import jpa.interfaces.IProduto;
import model.Favoritos;
import model.Produto;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class ProdutoFacade {

    private static final Logger logger = Logger.getLogger(ProdutoFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IProduto iProduto;

    public ProdutoFacade(){
        this.iProduto = new ProdutoDAO(entityManager);
    }

    public Produto buscaPorId(Long id) throws Exception {
        Produto produto;
        try{
            produto = iProduto.buscaPorId(id);
        }catch (Exception e){
            logger.error("Não foi possível localizar o produto id " + id);
            throw e;
        }
        return produto;
    }

    public List<Produto> produtos() throws Exception {
        List<Produto> produtos;
        try{
            produtos = iProduto.produtos();
        }catch(Exception e){
            logger.error("Não foi possível localizar a lista de produtos");
            throw e;
        }
        return produtos;
    }

    public void adiciona(Produto produto) throws Exception {
        try{
            iProduto.adiciona(produto);
        }catch (Exception e){
            logger.error("Não foi possível adicionar o produto");
            throw e;
        }
    }

    public void remove(Produto produto) throws Exception {
        try{
            iProduto.remove(produto);
        }catch (Exception e){
            logger.error("Não foi possível remover o produto");
            throw e;
        }
    }

    public void alterar(Produto produto) throws Exception {
        try{
            iProduto.altera(produto);
        }catch (Exception e){
            logger.error("Não foi possível alterar o produto");
            throw e;
        }
    }

}
