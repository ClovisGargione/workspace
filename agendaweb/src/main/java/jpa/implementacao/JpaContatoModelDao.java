package jpa.implementacao;

import jpa.interfaces.ContatoDao;
import model.ContatoModel;
import model.UsuarioModel;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 25/05/2015.
 */
public class JpaContatoModelDao implements ContatoDao {

    private EntityManager entityManager;
    private final static Logger logger = Logger.getLogger(JpaContatoModelDao.class);

    public JpaContatoModelDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }


    public ContatoModel buscaPorId(Long id) {
        ContatoModel contatoModel = entityManager.find(ContatoModel.class, id);
        return contatoModel;
    }

    public List<ContatoModel> listaDeContatos() {
        TypedQuery<ContatoModel> contatos = entityManager.createQuery("select c from ContatoModel c", ContatoModel.class);
        List<ContatoModel> listaDeContatos = contatos.getResultList();
        return listaDeContatos;
    }

    public void alteraContato(ContatoModel contatoModel) {
        entityManager.merge(contatoModel);

    }

    public void removeContato(ContatoModel contatoModel) {
        entityManager.remove(contatoModel);
    }

    public void adicionaContato(ContatoModel contatoModel) {
        entityManager.persist(contatoModel);
    }
}
