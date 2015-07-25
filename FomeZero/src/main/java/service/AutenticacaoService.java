package service;

import core.PasswordService;
import core.Token;
import enums.PerfilUsuario;
import filter.AuthUtils;
import jpa.facade.UsuarioFacade;
import model.Cliente;
import model.Funcionario;
import model.Usuario;
import org.apache.log4j.Logger;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Optional;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
@Path("/auth")
public class AutenticacaoService {


    private static final Logger logger = Logger.getLogger(AutenticacaoService.class);
    public static final String LOGING_ERROR_MSG = "Login ou senha incorretos";

    @POST
    @Path("login")
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(String informacoesLogin, @Context HttpServletRequest request) throws Exception{
        JSONObject jsonObject = new JSONObject(informacoesLogin);
        String login = jsonObject.getString("login");
        String senha = jsonObject.getString("senha");
        Usuario usuario = new Usuario();
        Cliente cliente = new Cliente();
        Funcionario funcionario = new Funcionario();
        usuario.setLogin(login);
        usuario.setSenha(senha);
        UsuarioFacade usuarioFacade = new UsuarioFacade();

        try{
            Optional<Usuario> foundUserRoot = Optional.ofNullable(usuarioFacade.buscaPorLogin(usuario.getLogin()));


            if(foundUserRoot.isPresent() && PasswordService.checkPassword(usuario.getSenha(), foundUserRoot.get().getSenha())){
                usuario = foundUserRoot.get();

                Token token = AuthUtils.createToken(request.getRemoteHost(), usuario.getId(),  usuario.getLogin(), usuario.getPerfil().toString());

                return Response.ok().entity(token).build();
            }


        }catch(Exception e){
            logger.error("Nao foi possivel fazer o login do usuario "+login+":"+e);
        }

        logger.info("Usuario "+login+" tentou efetuar o login");

        return Response.status(Response.Status.UNAUTHORIZED).entity(LOGING_ERROR_MSG).build();
    }

    @POST
    @Path("signup")
    public Response signup(@Valid String informacoesLogin, @Context HttpServletRequest request) throws Exception{
        JSONObject jsonObject = new JSONObject(informacoesLogin);
        Usuario usuario = new Usuario();
        usuario.setLogin(jsonObject.getString("login"));
        usuario.setPerfil(PerfilUsuario.CLIENTE);
        usuario.setSenha(jsonObject.getString("senha"));
        usuario.setSenha(PasswordService.hashPassword(usuario.getSenha()));
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        usuarioFacade.adiciona(usuario);
        Token token = AuthUtils.createToken(request.getRemoteHost(), usuario.getId(),  usuario.getLogin(), usuario.getPerfil().toString());
        logger.info("Usuário "+usuario.getLogin() + " efetuou login");
        return Response.ok().entity(token).build();
    }


}
