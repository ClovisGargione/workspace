/**
 * 
 */
package jpa.facade;

import java.util.List;

import javax.persistence.EntityManager;

import jpa.JpaUtil;
import jpa.implementacao.JpaUsuarioModelDao;
import jpa.interfaces.UsuarioDao;
import model.UsuarioModel;

import org.apache.log4j.Logger;

/**
 * @author c_r_s_000
 *
 */
public class UsuarioModelFacade {
	
	private final static Logger logger = Logger.getLogger(UsuarioModelFacade.class);
	private EntityManager entityManager = JpaUtil.getEntityManager();
	private UsuarioDao usuarioDao;
	
	public UsuarioModelFacade(){
		this.usuarioDao = new JpaUsuarioModelDao(entityManager);
	}
	
	public EntityManager getEntityManager(){
		return entityManager;
	}
	
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public void adiciona(UsuarioModel u) throws Exception{

		UsuarioModel usuario = usuarioDao.buscaPorLogin(u.getLogin());
		if(usuario != null){
			throw new Exception("Login já cadastrado");
		}
		usuarioDao.adiciona(u);
		
	}
	
	public void altera(UsuarioModel usuario) throws Exception{
		
		//deve retornar o mesmo usuário
		UsuarioModel usuarioBd = usuarioDao.buscaPorId(usuario.getId());
		usuario.getContatos();
		//verificando se já existe outro usuário com o mesmo login
		if(usuarioBd == null){
			throw new Exception("Login não cadastrado");
		}
		usuario.setContatos(usuarioBd.getContatos());
		usuarioDao.altera(usuario);
	}
	
	public UsuarioModel obterPorId(Long id) throws Exception{
		try {
			UsuarioModel usuario = usuarioDao.buscaPorId(id);
			return usuario;
		} catch (Exception e) {
			logger.error("Nao foi possivel localizar o usuario com Id " + id);
			throw e;
		}
		
	}
	
	public UsuarioModel obterPorLogin(String login) throws Exception{
		try {
			UsuarioModel usuario = usuarioDao.buscaPorLogin(login);
			return usuario;
		} catch (Exception e) {
			logger.error("Nao foi possivel localizar o usuario com login " + login);
			throw e;
		}
		
	}
	
	
	
	public void remove(UsuarioModel usuario) throws Exception{
		try {
			usuarioDao.remove(usuario);
		} catch (Exception e) {
			logger.error("Nao foi possivel remover o usuario " + usuario.getNome() );
		}
		logger.info("Removido o usuario " + usuario.getNome());
	}
	
	public List<UsuarioModel> lista() throws Exception{
		
		try {
			List<UsuarioModel> listaUsuarios = usuarioDao.listaUsuarios();
			return listaUsuarios;
		} catch (Exception e) {
			logger.error("Erro na listagem de usuarios:"+e);
			throw e;
		}
		
		
	}

}
