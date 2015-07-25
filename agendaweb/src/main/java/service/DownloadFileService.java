package service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import filter.AuthUtils;
import jpa.facade.UsuarioModelFacade;
import model.UsuarioModel;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.*;
import java.text.ParseException;

/**
 * Created by c_r_s_000 on 06/06/2015.
 */
@Path("filedownload")
public class DownloadFileService {

    private UsuarioModelFacade usuarioModelFacade = new UsuarioModelFacade();
    private static final Logger logger = Logger.getLogger(DownloadFileService.class);

    @GET
    @Path("/get-file/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFile(@PathParam("id") Long id){
        UsuarioModel usuarioModel = null;
        try {
            usuarioModel = usuarioModelFacade.obterPorId(id);
        } catch (Exception e) {
            logger.error("Não foi possível encontrar o usuário id:" + id + "motivo -> "+e.getMessage());
        }
        String filePath = usuarioModel.getLocalImagem();
        if(filePath != null && !"".equals(filePath)){
            File file_ = new File(filePath);
            Response.ResponseBuilder response = Response.ok(file_);
            response.header("Content-Disposition", "attachment; filename = " + file_.getName());
            return response.build();
        }
        return Response.ok("file path null").build();
    }
}
