package jpa.dao;

import jpa.interfaces.ICliente;
import model.Cliente;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class ClienteDAO implements ICliente {

    private final static Logger logger = Logger.getLogger(ClienteDAO.class);
    private EntityManager entityManager;

    public ClienteDAO(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public Cliente buscaPorId(Long id) {
        Cliente cliente = entityManager.find(Cliente.class, id);
        return cliente;
    }

    public Cliente buscaPorUsuario(Usuario usuario){
        TypedQuery<Cliente> query = entityManager.createQuery("select c from Cliente c where c.usuario= :usuario", Cliente.class);
        query.setParameter("usuario", usuario);
        Cliente cliente = null;
        try {
            cliente = query.getSingleResult();
        }catch (Exception e){
            //nao faz nada.. nao achou o cliente, retorna null
        }
        return cliente;
    }

    public Cliente buscaPorLogin(String nome) {
        TypedQuery<Cliente> query = entityManager.createQuery("select c from Cliente c where c.nome=:nome", Cliente.class);
        query.setParameter("nome",nome);
        Cliente cliente = null;
        try {
            cliente = query.getSingleResult();
        }catch (Exception e){
            //nao faz nada.. nao achou o cliente, retorna null
        }
        return cliente;
    }

    public List<Cliente> clientes() {
        TypedQuery<Cliente> query = entityManager.createQuery("select c from Cliente c", Cliente.class);
        List<Cliente> clientes = query.getResultList();
        return clientes;
    }

    public void adiciona(Cliente cliente) {
        entityManager.persist(cliente);
    }

    public void remove(Cliente cliente) {
        entityManager.remove(cliente);
    }

    public void altera(Cliente cliente) {
        entityManager.merge(cliente);
    }
}
