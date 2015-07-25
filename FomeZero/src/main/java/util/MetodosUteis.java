package util;

import dto.EnderecoDTO;
import dto.RetornoUsuarioDTO;
import enums.PerfilUsuario;
import jpa.facade.ClienteFacade;
import jpa.facade.EnderecoFacade;
import jpa.facade.FuncionarioFacade;
import jpa.facade.UsuarioFacade;
import model.Cliente;
import model.Endereco;
import model.Funcionario;
import model.Usuario;
import org.apache.log4j.Logger;

/**
 * Created by c_r_s_000 on 20/06/2015.
 */
public class MetodosUteis {

    private Logger logger = Logger.getLogger(MetodosUteis.class);
    private static final String ADMINISTRADOR = "ADMINISTRADOR";
    private static final String CLIENTE = "CLIENTE";
    private static final String FUNCIONARIO = "FUNCIONARIO";

    public RetornoUsuarioDTO retornoUsuarioDTO(Long id) throws Exception {

        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario usuario = usuarioFacade.buscaPorId(id);
        RetornoUsuarioDTO retornoUsuarioDTO = retornaUsuarioPorPerfil(usuario);

        return retornoUsuarioDTO;

    }

    private RetornoUsuarioDTO retornaUsuarioPorPerfil(Usuario usuario) throws Exception {
        String perfil = usuario.getPerfil().toString();
        RetornoUsuarioDTO retornoUsuarioDTO = new RetornoUsuarioDTO();
        switch (perfil){
            case ADMINISTRADOR:
                retornoUsuarioDTO = montaObjetoUsuarioAdministrador(usuario);
                break;
            case FUNCIONARIO:
                retornoUsuarioDTO = montaObjetoUsuarioEmpresa(usuario);
                break;
            case CLIENTE:
                retornoUsuarioDTO = montaObjetoUsuarioCliente(usuario);
        }
        return retornoUsuarioDTO;
    }

    private RetornoUsuarioDTO montaObjetoUsuarioCliente(Usuario usuario) throws Exception {
        PhoneDecilmalFormat phoneDecilmalFormat = new PhoneDecilmalFormat();
        RetornoUsuarioDTO retornoUsuarioDTO = new RetornoUsuarioDTO();
        retornoUsuarioDTO.setIdUsuario(usuario.getId());
        retornoUsuarioDTO.setLogin(usuario.getLogin());
        retornoUsuarioDTO.setPerfil(usuario.getPerfil().toString());
        ClienteFacade clienteFacade = new ClienteFacade();
        Cliente cliente = clienteFacade.buscaPorUsuario(usuario);
        if(cliente != null) {
            retornoUsuarioDTO.setNome(cliente.getNome());
            retornoUsuarioDTO.setEmail(cliente.getEmail());
            retornoUsuarioDTO.setTelefone(phoneDecilmalFormat.formatter(cliente.getTelefone()));
            EnderecoDTO enderecoDTO = new EnderecoDTO();
            enderecoDTO.setBairro(cliente.getEndereco().getBairro());
            enderecoDTO.setCep(MascarasUtil.format("#####-###", cliente.getEndereco().getCep()));
            enderecoDTO.setRua(cliente.getEndereco().getRua());
            enderecoDTO.setComplemento(cliente.getEndereco().getComplemento());
            enderecoDTO.setNumero(cliente.getEndereco().getNumero());
            retornoUsuarioDTO.setEndereco(enderecoDTO);
        }
        return retornoUsuarioDTO;
    }

    private RetornoUsuarioDTO montaObjetoUsuarioEmpresa(Usuario usuario) throws Exception {
        PhoneDecilmalFormat phoneDecilmalFormat = new PhoneDecilmalFormat();
        RetornoUsuarioDTO retornoUsuarioDTO = new RetornoUsuarioDTO();
        FuncionarioFacade funcionarioFacade = new FuncionarioFacade();
        Funcionario funcionario = funcionarioFacade.buscaPorUsuario(usuario);
        retornoUsuarioDTO.setIdUsuario(usuario.getId());
        retornoUsuarioDTO.setPerfil(usuario.getPerfil().toString());
        retornoUsuarioDTO.setCargo(funcionario.getCargo().toString());
        retornoUsuarioDTO.setLogin(usuario.getLogin());
        retornoUsuarioDTO.setEmail(funcionario.getEmail());
        retornoUsuarioDTO.setNome(funcionario.getNome());
        retornoUsuarioDTO.setTelefone(phoneDecilmalFormat.formatter(funcionario.getTelefone()));
        EnderecoDTO enderecoDTO = new EnderecoDTO();
        enderecoDTO.setRua(funcionario.getEndereco().getRua());
        enderecoDTO.setBairro(funcionario.getEndereco().getBairro());
        enderecoDTO.setComplemento(funcionario.getEndereco().getComplemento());
        enderecoDTO.setNumero(funcionario.getEndereco().getNumero());
        enderecoDTO.setCep(MascarasUtil.format("#####-###", funcionario.getEndereco().getCep()));
        retornoUsuarioDTO.setEndereco(enderecoDTO);
        return retornoUsuarioDTO;
    }

    private RetornoUsuarioDTO montaObjetoUsuarioAdministrador(Usuario usuario) {
        RetornoUsuarioDTO retornoUsuarioDTO = new RetornoUsuarioDTO();
        retornoUsuarioDTO.setIdUsuario(usuario.getId());
        retornoUsuarioDTO.setPerfil(usuario.getPerfil().toString());
        retornoUsuarioDTO.setLogin(usuario.getLogin());
        return retornoUsuarioDTO;
    }


}
