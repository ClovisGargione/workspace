package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.UsuarioDAO;
import jpa.interfaces.IUsuario;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class UsuarioFacade {

    private final static Logger logger = Logger.getLogger(UsuarioFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IUsuario iUsuario;

    public UsuarioFacade(){
        iUsuario = new UsuarioDAO(entityManager);
    }

    public void adiciona(Usuario usuario) throws Exception {
        Usuario u = iUsuario.buscaPorLogin(usuario.getLogin());
        if(u != null){
            throw new Exception("Usuário já cadastrado");
        }
        iUsuario.adiciona(usuario);
    }

    public void altera(Usuario usuario) throws Exception{
        Usuario u = iUsuario.buscaPorId(usuario.getId());
        if(u == null){
            throw new Exception("Usuário não cadastrado");
        }
        iUsuario.altera(usuario);
    }

    public void remove(Usuario usuario) throws Exception{
        try {
            iUsuario.remove(usuario);
        }catch(Exception e){
            logger.error("Nao foi possivel remover o usuario " + usuario.getLogin() );
            throw e;
        }
        logger.error("Usuário removido com sucesso" + usuario.getLogin() );
    }

    public List<Usuario> usuarios() throws Exception {
        List<Usuario> usuarios;
        try {
            usuarios = iUsuario.usuarios();
        }catch (Exception e){
            logger.error("Não foi possível obter a lista de usuários");
            throw e;
        }
        return usuarios;
    }

    public Usuario buscaPorId(Long id) throws Exception {
        Usuario usuario;
        try {
            usuario = iUsuario.buscaPorId(id);
        }catch(Exception e){
            logger.error("Não foi possível obter o usuário id: "+id);
            throw e;
        }
        return usuario;
    }

    public Usuario buscaPorLogin(String login) throws Exception {
        Usuario usuario;
        try {
            usuario = iUsuario.buscaPorLogin(login);
        }catch(Exception e){
            logger.error("Não foi possível obter o usuário: "+login);
            throw e;
        }
        return usuario;
    }


}
