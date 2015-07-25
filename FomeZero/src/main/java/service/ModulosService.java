package service;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.JWTClaimsSet;
import dto.ModulosDTO;
import enums.PerfilUsuario;
import filter.AuthUtils;
import jpa.facade.ClienteFacade;
import jpa.facade.FuncionarioFacade;
import jpa.facade.UsuarioFacade;
import model.Cliente;
import model.Funcionario;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static enums.PerfilUsuario.ADMINISTRADOR;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
@Path("secure/navbar")
public class ModulosService {

    private final static Logger logger = Logger.getLogger(ModulosService.class);
    private static final String ADMINISTRADOR = "ADMINISTRADOR";
    private static final String CLIENTE = "CLIENTE";
    private static final String FUNCIONARIO = "FUNCIONARIO";

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public List<ModulosDTO> modulos(@Context HttpServletRequest request) throws ParseException, JOSEException {
        String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
        String perfil = (String) claimSet.getCustomClaim("perfil");
        List<ModulosDTO> modulos = modulos(perfil);
        return modulos;

    }

    @GET
    @Path("link")
    @Produces({MediaType.APPLICATION_JSON})
    public ModulosDTO linkDadosPessoais(@Context HttpServletRequest request) throws ParseException, JOSEException {
        String authHeader = request.getHeader(AuthUtils.AUTH_HEADER_KEY);
        JWTClaimsSet claimSet = (JWTClaimsSet) AuthUtils.decodeToken(authHeader);
        String perfil = (String) claimSet.getCustomClaim("perfil");
        ModulosDTO modulosDTO = montaLinkDadosPessoais(perfil);
        return modulosDTO;
    }

    private ModulosDTO montaLinkDadosPessoais(String perfil) {
        ModulosDTO modulosDTO = new ModulosDTO();
        switch (perfil){
            case CLIENTE :
                modulosDTO.setLink("#/usuarios/edit/");
                break;
            case FUNCIONARIO:
                modulosDTO.setLink("#/usuarios/empresa/edit/");
                break;
            case ADMINISTRADOR:
                modulosDTO.setLink("#/usuarios/adm/edit/");
        }
        return modulosDTO;
    }


    private List<ModulosDTO> modulos(String perfil){
        List<ModulosDTO> modulos = new ArrayList<>();
        if(perfil.equals(PerfilUsuario.ADMINISTRADOR.toString())){
            ModulosDTO modulosDTO = new ModulosDTO();
            modulosDTO.setIcone("mif-shop");
            modulosDTO.setNome("Cadastros");
            modulosDTO.setLink("#/empresa/cadastro");
            modulos.add(modulosDTO);
            return modulos;
        }
        if(perfil.equals(PerfilUsuario.CLIENTE.toString())){
            ModulosDTO modulosDTO = new ModulosDTO();
            modulosDTO.setNome("Pedidos");
            modulosDTO.setIcone("mif-cart");
            modulosDTO.setLink("#/pedidos/consulta");
            modulos.add(modulosDTO);
            return modulos;
        }
        if(perfil.equals(PerfilUsuario.FUNCIONARIO.toString())){
            ModulosDTO modulosDTO1 = new ModulosDTO();
            modulosDTO1.setIcone("mif-shop");
            modulosDTO1.setNome("Dados da empresa");
            modulosDTO1.setLink("#/empresa/edit");
            modulos.add(modulosDTO1);
            return modulos;
        }

        return null;

    }



}
