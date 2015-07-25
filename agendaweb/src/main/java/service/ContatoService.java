package service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import dto.RetornoContatos;
import filter.AuthUtils;
import jpa.facade.ContatoModelFacade;
import jpa.facade.UsuarioModelFacade;
import model.ContatoModel;
import model.UsuarioModel;
import org.apache.log4j.Logger;
import org.json.JSONObject;
import util.MetodosUteis;
import util.PhoneDecilmalFormat;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by c_r_s_000 on 25/05/2015.
 */
@Path("secure/contatos")
public class ContatoService {

    private final static Logger logger = Logger.getLogger(ContatoService.class);
    private ContatoModelFacade contatoModelFacade = new ContatoModelFacade();
    private MetodosUteis uteis = new MetodosUteis();
    private UsuarioModelFacade usuarioModelFacade = new UsuarioModelFacade();

    @Context
    HttpServletRequest request;

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public ContatoModel adicionaContato(@Context HttpServletRequest request, String contatoModel){
        HttpServletRequest httpRequest = request;
        String authHeader = httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY);
        ContatoModel contatoModel1 = new ContatoModel();
        UsuarioModel usuarioModel;
        List<ContatoModel> contatos = new ArrayList<ContatoModel>();
        JSONObject jsonObject = new JSONObject(contatoModel);
        try {
            JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            Long id = Long.valueOf(claimSet.getSubject());
            usuarioModel = usuarioModelFacade.obterPorId(id);
            contatoModel1 = uteis.montaContatoPorJSON(jsonObject);
            contatos.add(contatoModel1);
            usuarioModel.getContatos().add(contatoModel1);
            usuarioModelFacade.altera(usuarioModel);
        } catch (Exception e) {
            logger.error("Não foi possível adicionar o contato " + contatoModel1.getNome() + " motivo " + e.getMessage());
        }

        return contatoModel1;
    }

    @PUT
    @Consumes({MediaType.APPLICATION_JSON})
    public ContatoModel alteraContato(String contatoModel){
        JSONObject jsonObject = new JSONObject(contatoModel);
        ContatoModel contatoModel1 = new ContatoModel();
        try {
            contatoModel1 = uteis.montaContatoPorJSON(jsonObject);
            contatoModel1.setId(jsonObject.getLong("id"));
            contatoModelFacade.alteraContato(contatoModel1);
        } catch (Exception e) {
            logger.error("Não foi possível alterar ");
        }

        return contatoModel1;
    }

    @DELETE
    @Path("{id}")
    @Consumes({MediaType.APPLICATION_JSON})
    public void removeContato(@PathParam("id") Long id){
        ContatoModel contatoModel = contatoModelFacade.buscaContatoPorId(id);
        contatoModelFacade.removeContato(contatoModel);
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<RetornoContatos> listaDeContatos(@Context HttpServletRequest request){
        List<RetornoContatos> listaContatosDTO = new ArrayList<RetornoContatos>();
        HttpServletRequest httpRequest = request;
        String authHeader = httpRequest.getHeader(AuthUtils.AUTH_HEADER_KEY);
        UsuarioModel usuarioModel;
        try {
            JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            Long id = Long.valueOf(claimSet.getSubject());
            UsuarioModelFacade usuarioModelFacade = new UsuarioModelFacade();
            usuarioModel = usuarioModelFacade.obterPorId(id);
            listaContatosDTO.addAll(uteis.montaListaCOntatosDTO(usuarioModel.getContatos()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaContatosDTO;
    }

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON})
    public RetornoContatos buscaContatoPorId(@PathParam("id") Long id){
        ContatoModel contatoModel = contatoModelFacade.buscaContatoPorId(id);
        RetornoContatos retornoContatos = uteis.montaObjetoRetornoContatos(contatoModel);
        return retornoContatos;
    }

}
