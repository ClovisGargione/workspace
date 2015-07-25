package jpa.dao;

import jpa.interfaces.IUsuario;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class UsuarioDAO implements IUsuario {

    private final static Logger logger = Logger.getLogger(UsuarioDAO.class);
    private EntityManager entityManager;

    public UsuarioDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Usuario buscaPorId(Long id) {
        Usuario usuario = entityManager.find(Usuario.class, id);
        return usuario;
    }

    public Usuario buscaPorLogin(String login) {

        TypedQuery<Usuario> query = entityManager.createQuery("select u from Usuario u where u.login = :login", Usuario.class);
        query.setParameter("login", login);
        Usuario usuario = null;
        try {
            usuario = query.getSingleResult();
        } catch (Exception e) {
            //nao faz nada.. nao achou o usuario, retorna null
        }
        return usuario;
    }

    public List<Usuario> usuarios() {

        TypedQuery<Usuario> usuarios = entityManager.createQuery("select u from Usuario u",Usuario.class);
        return usuarios.getResultList();
    }

    public void adiciona(Usuario usuario) {
        entityManager.persist(usuario);
    }

    public void remove(Usuario usuario) {
        entityManager.remove(usuario);
    }

    public void altera(Usuario usuario) {
        entityManager.merge(usuario);
    }
}
