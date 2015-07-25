package service;

import core.PasswordService;
import jpa.facade.UsuarioModelFacade;
import model.UsuarioModel;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by c_r_s_000 on 31/03/2015.
 */
@Path("secure/usuarios")
public class UsuarioService {

    private final static Logger logger = Logger.getLogger(UsuarioService.class);
    private UsuarioModelFacade usuarioFacade = new UsuarioModelFacade();



    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public UsuarioModel alterarUsuario(UsuarioModel usuario) throws Exception{
        try{
            UsuarioModel usuarioModel = usuarioFacade.obterPorId(usuario.getId());
            usuario.setLocalImagem(usuarioModel.getLocalImagem());
            usuario.setSenha(usuarioModel.getSenha());
            usuarioFacade.altera(usuario);
        }catch(Exception e){
            logger.error("Nao foi possivel alterar o usuario: "+e);
            throw e;
        }
        return usuario;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public UsuarioModel encontreUsuario(@PathParam("id") Long id){
        try {
            UsuarioModel usuario = usuarioFacade.obterPorId(new Long(id));

            //novo objeto para retornar o usuário SEM a senha e evitar que o filtro de transa��es sete a senha para vazia no objeto original
            UsuarioModel novoUsuario = new UsuarioModel();

            novoUsuario.setId(usuario.getId());
            novoUsuario.setLogin(usuario.getLogin());
            novoUsuario.setNome(usuario.getNome());
            novoUsuario.setEmail(usuario.getEmail());
            novoUsuario.setLocalImagem(usuario.getLocalImagem());
            return novoUsuario;

        } catch (Exception e) {
            logger.error("Usuario com ID "+id+" nao encontrado.");
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
    }




    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<UsuarioModel> listaDeUsuario(){
        List<UsuarioModel> lista = null;
        try {
            lista = usuarioFacade.lista();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

}
