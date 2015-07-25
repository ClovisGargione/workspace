/**
 *
 */
package service;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import jpa.facade.UsuarioModelFacade;
import model.UsuarioModel;

import org.apache.log4j.Logger;
import org.json.JSONObject;

import com.nimbusds.jose.JOSEException;

import core.PasswordService;
import core.Token;
import dto.RetornoLoginDTO;
import filter.AuthUtils;

/**
 * @author c_r_s_000
 *
 */
@Path("/auth")
public class AutenticacaoService {

	private final static Logger logger = Logger.getLogger(AutenticacaoService.class);

	public static final String LOGING_ERROR_MSG = "Login ou senha incorretos";

	@Context
	UriInfo uriInfo;

	@Context
	HttpServletRequest request;

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	public Response login(String informacoesLogin, @Context HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject(informacoesLogin);
		UsuarioModel user = new UsuarioModel();
		user.setLogin(json.getString("login"));
		user.setSenha(json.getString("senha"));
		UsuarioModelFacade usuarioFacade = new UsuarioModelFacade();


		try {

			Optional<UsuarioModel> foundUser = Optional.ofNullable(usuarioFacade.obterPorLogin(user.getLogin()));

			if (foundUser.isPresent()
					&& PasswordService.checkPassword(user.getSenha(), foundUser.get().getSenha())) {

				UsuarioModel usuario = foundUser.get();

				Token token = AuthUtils.createToken(request.getRemoteHost(), usuario.getId(), usuario.getNome(), usuario.getLogin());

				RetornoLoginDTO retorno = new RetornoLoginDTO();
				retorno.setToken(token.getToken());
				retorno.setIdUsuario(foundUser.get().getId());
				retorno.setLogin(foundUser.get().getNome());
				Response resp = Response.ok().entity(retorno).build();
				logger.info("Usuario "+user.getLogin()+" efetuou o login");
				return resp;
			}
		} catch (Exception e) {
			logger.error("Nao foi possivel fazer o login do usuario "+user.getLogin()+":"+e);
		}

		logger.info("Usuario "+user.getLogin()+" tentou efetuar o login");

		return Response.status(Status.UNAUTHORIZED).entity(LOGING_ERROR_MSG).build();

	}

	@POST
	@Path("signup")
	public Response signup(@Valid String informacoesLogin, @Context HttpServletRequest request) throws Exception {
		JSONObject json = new JSONObject(informacoesLogin);
		UsuarioModel user = new UsuarioModel();
		user.setNome(json.getString("nome"));
		user.setLogin(json.getString("login"));
		user.setEmail(json.getString("email"));
		user.setSenha(json.getString("senha"));
		UsuarioModelFacade facade = new UsuarioModelFacade();
		user.setSenha(PasswordService.hashPassword(user.getSenha()));
		facade.adiciona(user);
		UsuarioModel savedUser = user;
		Token token = AuthUtils.createToken(request.getRemoteHost(), savedUser.getId(), savedUser.getNome(), savedUser.getLogin());
		RetornoLoginDTO retorno = new RetornoLoginDTO();
		retorno.setToken(token.getToken());
		retorno.setIdUsuario(user.getId());
		retorno.setLogin(user.getNome());
		Response resp = Response.ok().entity(retorno).build();
		logger.info("Usuario "+user.getLogin()+" efetuou o login");
		return resp;
	}


}
