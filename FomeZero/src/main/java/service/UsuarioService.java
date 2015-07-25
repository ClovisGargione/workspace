package service;

import business.implementacao.UsuarioBusiness;
import business.interfaces.IUsuarioBusiness;
import dto.RetornoUsuarioDTO;
import org.apache.log4j.Logger;
import util.MetodosUteis;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by c_r_s_000 on 20/06/2015.
 */
@Path("secure/usuario")
public class UsuarioService {

    private final static Logger logger = Logger.getLogger(ModulosService.class);

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public RetornoUsuarioDTO atualizar(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        IUsuarioBusiness business = new UsuarioBusiness();
        try {
            business.atualizaUsuario(retornoUsuarioDTO);
        } catch (Exception e) {
            logger.error("Não foi possível atualizar o usuario " + retornoUsuarioDTO.getNome());
            throw e;
        }

        return retornoUsuarioDTO;
    }


    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public RetornoUsuarioDTO buscaUsuarioPorId(@PathParam("id") Long id){
        MetodosUteis uteis = new MetodosUteis();
        RetornoUsuarioDTO retornoUsuarioDTO = new RetornoUsuarioDTO();
        try {
            retornoUsuarioDTO = uteis.retornoUsuarioDTO(id);
        } catch (Exception e) {
            logger.error("Não foi possível encontrar o usuario " + id);
        }
        return retornoUsuarioDTO;
    }

}
