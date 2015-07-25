package jpa.dao;

import jpa.interfaces.IFuncionario;
import model.Funcionario;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class FuncionarioDAO implements IFuncionario {

    private static final Logger logger = Logger.getLogger(FuncionarioDAO.class);
    private EntityManager entityManager;

    public FuncionarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Funcionario buscaPorId(Long id) {
        Funcionario funcionario = entityManager.find(Funcionario.class, id);
        return funcionario;
    }

    public Funcionario buscaPorNome(String nome) {
        TypedQuery<Funcionario> query = entityManager.createQuery("select f from Funcionario f where f.nome = :nome", Funcionario.class);
        query.setParameter("nome",nome);
        Funcionario funcionario = null;
        try {
            funcionario = query.getSingleResult();
        } catch (Exception e) {
            //nao faz nada.. nao achou o usuario, retorna null
        }

        return funcionario;
    }

    public List<Funcionario> funcionarios() {
        TypedQuery<Funcionario> query = entityManager.createQuery("select f from Funcionario f", Funcionario.class);
        List<Funcionario> funcionarios = query.getResultList();
        return funcionarios;
    }

    public void adiciona(Funcionario funcionario) {
        entityManager.persist(funcionario);
    }

    public void remove(Funcionario funcionario) {
        entityManager.remove(funcionario);
    }

    public void altera(Funcionario funcionario) {
        entityManager.merge(funcionario);
    }

    @Override
    public Funcionario buscaPorUsuario(Usuario usuario) {
        TypedQuery<Funcionario> query = entityManager.createQuery("select f from Funcionario f where f.usuario =:usuario", Funcionario.class);
        query.setParameter("usuario",usuario);
        Funcionario funcionarios = null;
        try {
            funcionarios = query.getSingleResult();
        }catch (Exception e){

        }
        return funcionarios;
    }
}
