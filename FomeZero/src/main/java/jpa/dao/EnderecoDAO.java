package jpa.dao;

import jpa.interfaces.IEndereco;
import model.Endereco;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class EnderecoDAO implements IEndereco {

    private static final Logger logger =  Logger.getLogger(EnderecoDAO.class);
    private EntityManager entityManager;

    public EnderecoDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Endereco buscaPorId(Long id) {
        Endereco endereco = entityManager.find(Endereco.class, id);
        return endereco;
    }

    public Endereco buscaPorCep(Long cep) {
        TypedQuery<Endereco> query = entityManager.createQuery("select e from Endereco e where e.cep = :cep", Endereco.class);
        query.setParameter("cep", cep);
        Endereco endereco = query.getSingleResult();
        return endereco;
    }

    public List<Endereco> enderecos() {
        TypedQuery<Endereco> query = entityManager.createQuery("select e from Endereco e", Endereco.class);
        List<Endereco> enderecos = query.getResultList();
        return enderecos;
    }

    public void adiciona(Endereco endereco) {
        entityManager.persist(endereco);
    }

    public void remove(Endereco endereco) {
        entityManager.remove(endereco);
    }

    public void altera(Endereco endereco) {
        entityManager.merge(endereco);
    }
}
