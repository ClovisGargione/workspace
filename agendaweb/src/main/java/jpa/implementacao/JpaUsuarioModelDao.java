/**
 * 
 */
package jpa.implementacao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.apache.log4j.Logger;

import model.UsuarioModel;
import jpa.interfaces.UsuarioDao;

/**
 * @author c_r_s_000
 *
 */
public class JpaUsuarioModelDao implements UsuarioDao {
	
	private EntityManager entityManager;
	private final static Logger logger = Logger.getLogger(JpaUsuarioModelDao.class);
	
	public JpaUsuarioModelDao(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	/* (non-Javadoc)
	 * @see jpa.interfaces.UsuarioDao#buscaPorId(java.lang.Long)
	 */
	public UsuarioModel buscaPorId(Long id) {
		UsuarioModel model = entityManager.find(UsuarioModel.class, id);
		return model;
	}

	/* (non-Javadoc)
	 * @see jpa.interfaces.UsuarioDao#listaUsuarios()
	 */
	public List<UsuarioModel> listaUsuarios() {
		TypedQuery<UsuarioModel> usuarios = entityManager
				.createQuery("select u from UsuarioModel u",UsuarioModel.class);
		return usuarios.getResultList();
		
	}

	/* (non-Javadoc)
	 * @see jpa.interfaces.UsuarioDao#buscaPorLogin(java.lang.String)
	 */
	public UsuarioModel buscaPorLogin(String login) {
		TypedQuery<UsuarioModel> query = entityManager.createQuery("select u from UsuarioModel u where u.login = :login", UsuarioModel.class);
		query.setParameter("login", login);
		UsuarioModel usuario = null;
		try {
			usuario = query.getSingleResult();
		} catch (Exception e) {
			//nao faz nada.. nao achou o usuario, retorna null
		}
		return usuario;
		
	}

	/* (non-Javadoc)
	 * @see jpa.interfaces.UsuarioDao#altera(model.UsuarioModel)
	 */
	public void altera(UsuarioModel usuarioModel) {
		try{
			entityManager.merge(usuarioModel);
		}catch(Exception e){
			logger.error(e);
		}

	}

	/* (non-Javadoc)
	 * @see jpa.interfaces.UsuarioDao#adiciona(model.UsuarioModel)
	 */
	public void adiciona(UsuarioModel usuarioModel) {
		try{
			entityManager.persist(usuarioModel);
			//entityManager.flush();
			//entityManager.refresh(usuarioModel);
		}catch(Exception e){
			logger.error(e.getMessage());
			logger.info(e.getMessage());
			e.printStackTrace();
		}

	}

	/* (non-Javadoc)
	 * @see jpa.interfaces.UsuarioDao#remove(model.UsuarioModel)
	 */
	public void remove(UsuarioModel usuarioModel) {
		entityManager.remove(usuarioModel);

	}

}
