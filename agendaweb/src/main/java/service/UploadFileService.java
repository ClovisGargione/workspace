package service;

import com.nimbusds.jwt.JWTClaimsSet;
import filter.AuthUtils;
import jpa.facade.UsuarioModelFacade;
import model.UsuarioModel;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.jfree.util.Log;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.*;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
@Path("secure/fileupload")
public class UploadFileService {

    private UsuarioModelFacade usuarioModelFacade = new UsuarioModelFacade();
    private static  final String DIRETORIO_IMAGEM = "/uploaded/";

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@Context HttpServletRequest request,
                               @FormDataParam("file") InputStream uploadedInputStream,
                               @FormDataParam("file") FormDataContentDisposition fileDetail){

        String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        JWTClaimsSet claimSet = null;
        try {
            claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
            Long id = Long.valueOf(claimSet.getSubject());
            UsuarioModel usuarioModel = usuarioModelFacade.obterPorId(id);
            String theFilePath = DIRETORIO_IMAGEM + usuarioModel.getId();
            File directory = criarDiretorio(theFilePath);
            limparDiretorio(directory);
            String uploadedFileLocation = theFilePath + "/" + fileDetail.getFileName();
            usuarioModel.setLocalImagem(uploadedFileLocation);
            usuarioModelFacade.altera(usuarioModel);
            // salvando em disco
            writeToFile(uploadedInputStream, uploadedFileLocation);
        } catch (Exception e) {
            Log.error("Erro ao realizar upload motivo -> " + e.getMessage());
        }


        String output = "Upload realizado com sucesso em " + DIRETORIO_IMAGEM;



        return Response.status(javax.ws.rs.core.Response.Status.OK).entity(output).build();

    }

    private File criarDiretorio(String path){
        File directory = new File(path);
        if(!directory.exists()){
            directory.mkdir();
        }
        return directory;
    }

    private void limparDiretorio(File dir){
        for (File file: dir.listFiles()) {
            file.delete();
        }
    }

    private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
        try {
            OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
            int read = 0;
            byte[] bytes = new byte[1024];

            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.flush();
            out.close();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }
}
