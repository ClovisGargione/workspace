package service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import filter.AuthUtils;
import jpa.facade.EmailModelFacade;
import jpa.facade.UsuarioModelFacade;
import model.EmailModel;
import model.UsuarioModel;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.List;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
@Path("secure/email")
public class EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class);
    private EmailModelFacade emailModelFacade = new EmailModelFacade();
    private UsuarioModelFacade usuarioModelFacade = new UsuarioModelFacade();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public EmailModel enviarEmail(@Context HttpServletRequest request, EmailModel emailModel){
        String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        UsuarioModel usuarioModel;
        try {
            JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            Long id = Long.valueOf(claimSet.getSubject());
            usuarioModel = usuarioModelFacade.obterPorId(id);
            usuarioModel.getEmails().add(emailModel);
            usuarioModelFacade.altera(usuarioModel);
        } catch (Exception e) {
            logger.error("Erro ao enviar email motivo -> " + e.getMessage());
        }

        return emailModel;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<EmailModel> listaDeEmails(@Context HttpServletRequest request){
        String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        UsuarioModel usuarioModel = null;
        try {
            JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            Long id = Long.valueOf(claimSet.getSubject());
            usuarioModel = usuarioModelFacade.obterPorId(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        List<EmailModel> emails = usuarioModel.getEmails();
        return emails;
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public void remover(@PathParam("{id}") Long id){
        EmailModel emailModel = emailModelFacade.buscaEmailPorId(id);
        emailModelFacade.removeEmail(emailModel);
    }
}
