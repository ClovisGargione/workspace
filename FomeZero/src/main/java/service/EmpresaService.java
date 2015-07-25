package service;

import business.implementacao.UsuarioBusiness;
import business.interfaces.IUsuarioBusiness;
import dto.CargoDTO;
import dto.RetornoUsuarioDTO;
import enums.Cargo;
import enums.PerfilUsuario;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_r_s_000 on 21/06/2015.
 */
@Path("secure/empresa")
public class EmpresaService {

    private final static Logger logger = Logger.getLogger(ModulosService.class);

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    public RetornoUsuarioDTO cadastroDeEmpresa(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        retornoUsuarioDTO.setPerfil(PerfilUsuario.FUNCIONARIO.toString());
        UsuarioBusiness business = new UsuarioBusiness();

        try {
            business.salvarUsuarioEmpresa(retornoUsuarioDTO);
        } catch (Exception e) {
            logger.error("Não foi possível cadastrar a empresa");
            throw e;
        }

        return retornoUsuarioDTO;
    }

    @GET
    @Path("cargos")
    @Produces({MediaType.APPLICATION_JSON})
    public List<CargoDTO> cargos(){
        List<CargoDTO> cargos = new ArrayList<>();
        CargoDTO cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.ATENDENTE.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.PIZZAIOLO.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.CHAPEIRO.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.COZINHEIRO.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.CAIXA.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.GARCOM.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.GERENTE.toString());
        cargos.add(cargoDTO);

        cargoDTO = new CargoDTO();
        cargoDTO.setCargo(Cargo.MOTOBOY.toString());
        cargos.add(cargoDTO);

        return cargos;
    }
}
