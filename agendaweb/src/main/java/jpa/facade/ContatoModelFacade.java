package jpa.facade;

import jpa.JpaUtil;
import jpa.implementacao.JpaContatoModelDao;
import jpa.interfaces.ContatoDao;
import model.ContatoModel;
import model.UsuarioModel;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 25/05/2015.
 */
public class ContatoModelFacade {

    private final static Logger logger = Logger.getLogger(ContatoModelFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private ContatoDao contatoDao;

    public ContatoModelFacade(){
        this.contatoDao = new JpaContatoModelDao(entityManager);
    }

    public void adicionaContato(ContatoModel contatoModel){
        contatoDao.adicionaContato(contatoModel);
    }

    public void alteraContato(ContatoModel contatoModel){
        contatoDao.alteraContato(contatoModel);
    }

    public void removeContato(ContatoModel contatoModel){
        contatoDao.removeContato(contatoModel);
    }

    public List<ContatoModel> listaDeContatos(){
        List<ContatoModel> lista = contatoDao.listaDeContatos();
        return lista;
    }

    public ContatoModel buscaContatoPorId(Long id){
        ContatoModel contatoModel = contatoDao.buscaPorId(id);
        return contatoModel;
    }
}
