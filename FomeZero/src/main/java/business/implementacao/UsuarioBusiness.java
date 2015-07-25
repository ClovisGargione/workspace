package business.implementacao;

import business.interfaces.IUsuarioBusiness;
import core.PasswordService;
import dto.RetornoUsuarioDTO;
import enums.Cargo;
import enums.PerfilUsuario;
import jpa.facade.*;
import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by c_r_s_000 on 21/06/2015.
 */
public class UsuarioBusiness  implements IUsuarioBusiness {

    private static final String ADMINISTRADOR = "ADMINISTRADOR";
    private static final String CLIENTE = "CLIENTE";
    private static final String FUNCIONARIO = "FUNCIONARIO";

    @Override
    public void atualizaUsuario(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        atualizarUsuarioPorPerfil(retornoUsuarioDTO);
    }

    private void atualizarUsuarioPorPerfil(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        String perfil = retornoUsuarioDTO.getPerfil();
        switch (perfil){
            case ADMINISTRADOR:
                atualizarUsuarioAdministrador(retornoUsuarioDTO);
                break;
            case CLIENTE:
                atualizarUsuarioCliente(retornoUsuarioDTO);
                break;
            case FUNCIONARIO:
                atualizarUsuarioFuncionario(retornoUsuarioDTO);
                break;
        }
    }

    public void salvarUsuarioEmpresa(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
           UsuarioFacade usuarioFacade = new UsuarioFacade();
           Usuario usuario = new Usuario();

           usuario.setSenha(PasswordService.hashPassword(retornoUsuarioDTO.getSenha()));
           usuario.setPerfil(PerfilUsuario.FUNCIONARIO);
           usuario.setLogin(retornoUsuarioDTO.getLogin());
           usuarioFacade.adiciona(usuario);
           cadastraEmpresaFuncionario(usuario, retornoUsuarioDTO);

    }

    private void cadastraEmpresaFuncionario(Usuario usuario, RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        Endereco endereco = new Endereco();
        EnderecoFacade enderecoFacade = new EnderecoFacade();

        endereco.setRua(retornoUsuarioDTO.getEndereco().getRua());
        endereco.setNumero(retornoUsuarioDTO.getEndereco().getNumero());
        endereco.setBairro(retornoUsuarioDTO.getEndereco().getBairro());
        endereco.setCep(Long.valueOf(retornoUsuarioDTO.getEndereco().getCep().replaceAll("[^0-9]", "")));
        endereco.setComplemento(retornoUsuarioDTO.getEndereco().getComplemento());
        enderecoFacade.adiciona(endereco);


        Funcionario funcionario = new Funcionario();
        FuncionarioFacade funcionarioFacade = new FuncionarioFacade();

        funcionario.setEmail(retornoUsuarioDTO.getEmail());
        funcionario.setCargo(retornaCargo(retornoUsuarioDTO));
        funcionario.setNome(retornoUsuarioDTO.getNome());
        funcionario.setTelefone(Long.valueOf(retornoUsuarioDTO.getTelefone().replaceAll("[^0-9]", "")));
        funcionario.setEndereco(endereco);
        funcionario.setUsuario(usuario);
        funcionarioFacade.adiciona(funcionario);


        Endereco enderecoEmpresa = new Endereco();

        enderecoEmpresa.setBairro(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getBairro());
        enderecoEmpresa.setRua(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getRua());
        if(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getComplemento() != null) {
            enderecoEmpresa.setComplemento(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getComplemento());
        }
        enderecoEmpresa.setNumero(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getNumero());
        enderecoEmpresa.setCep(Long.valueOf(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getCep().replaceAll("[^0-9]", "")));


        Empresa empresa = new Empresa();

        empresa.setNome(retornoUsuarioDTO.getEmpresaDTO().getNome());
        empresa.setEmail(retornoUsuarioDTO.getEmpresaDTO().getEmail());
        empresa.setTelefone(Long.valueOf(retornoUsuarioDTO.getEmpresaDTO().getTelefone().replaceAll("[^0-9]", "")));
        empresa.setCnpj(Long.valueOf(retornoUsuarioDTO.getEmpresaDTO().getCnpj().replaceAll("[^0-9]", "")));
        empresa.setEndereco(endereco);
        if(empresa.getFuncionario()== null) {
            List<Funcionario> lista = new ArrayList<>();
            lista.add(funcionario);
            empresa.setFuncionario(lista);
        }else{
            empresa.getFuncionario().add(funcionario);
        }
        EmpresaFacade empresaFacade = new EmpresaFacade();
        empresaFacade.adiciona(empresa);

    }

    private void atualizarUsuarioFuncionario(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario usuario = usuarioFacade.buscaPorId(retornoUsuarioDTO.getIdUsuario());
        usuario.setSenha(PasswordService.hashPassword(retornoUsuarioDTO.getSenha()));
        usuario.setPerfil(PerfilUsuario.FUNCIONARIO);
        usuario.setLogin(retornoUsuarioDTO.getLogin());
        atualizaFuncionario(usuario, retornoUsuarioDTO);
        usuarioFacade.altera(usuario);

    }

    private void atualizaFuncionario(Usuario usuario, RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {

        FuncionarioFacade funcionarioFacade = new FuncionarioFacade();
        Funcionario funcionario  = funcionarioFacade.buscaPorUsuario(usuario);
        Endereco endereco = funcionario.getEndereco();
        endereco.setRua(retornoUsuarioDTO.getEndereco().getRua());
        endereco.setNumero(retornoUsuarioDTO.getEndereco().getNumero());
        endereco.setBairro(retornoUsuarioDTO.getEndereco().getBairro());
        endereco.setCep(Long.valueOf(retornoUsuarioDTO.getEndereco().getCep().replaceAll("[^0-9]", "")));
        endereco.setComplemento(retornoUsuarioDTO.getEndereco().getComplemento());
        funcionario.setEmail(retornoUsuarioDTO.getEmail());
        funcionario.setCargo(retornaCargo(retornoUsuarioDTO));
        funcionario.setNome(retornoUsuarioDTO.getNome());
        funcionario.setTelefone(Long.valueOf(retornoUsuarioDTO.getTelefone().replaceAll("[^0-9]", "")));
        funcionario.setEndereco(endereco);
        funcionario.setUsuario(usuario);
        funcionarioFacade.altera(funcionario);

    }

    public void atualizarDadosEmpresa(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        Endereco enderecoEmpresa = new Endereco();
        enderecoEmpresa.setBairro(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getBairro());
        enderecoEmpresa.setRua(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getRua());
        enderecoEmpresa.setComplemento(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getComplemento());
        enderecoEmpresa.setNumero(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getNumero());
        enderecoEmpresa.setCep(Long.valueOf(retornoUsuarioDTO.getEmpresaDTO().getEndereco().getCep().replace("[^0-9]", "")));


        Empresa empresa = new Empresa();
        empresa.setNome(retornoUsuarioDTO.getEmpresaDTO().getNome());
        empresa.setEmail(retornoUsuarioDTO.getEmpresaDTO().getEmail());
        empresa.setTelefone(Long.valueOf(retornoUsuarioDTO.getEmpresaDTO().getTelefone().replaceAll("[^0-9]", "")));
        empresa.setCnpj(Long.valueOf(retornoUsuarioDTO.getEmpresaDTO().getCnpj().replace("[^0-9]", "")));
        empresa.setEndereco(enderecoEmpresa);
        EmpresaFacade empresaFacade = new EmpresaFacade();
        empresaFacade.altera(empresa);
    }

    public Cargo retornaCargo(RetornoUsuarioDTO retornoUsuarioDTO){
        String cargo = retornoUsuarioDTO.getCargo();
        switch (cargo){
            case "MOTOBOY":
                return Cargo.MOTOBOY;
            case "ATENDENTE":
                return Cargo.ATENDENTE;
            case "GERENTE":
                return Cargo.GERENTE;
            case "CAIXA":
                return Cargo.CAIXA;
            case "GARCOM":
                return Cargo.GARCOM;
            case "COZINHEIRO":
                return Cargo.COZINHEIRO;
            case "CHAPEIRO":
                return Cargo.CHAPEIRO;
            case "PIZZAIOLO":
                return Cargo.PIZZAIOLO;

        }
        return null;
    }

    private void atualizarUsuarioCliente(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        Usuario usuario = usuarioFacade.buscaPorId(retornoUsuarioDTO.getIdUsuario());
        usuario.setSenha(usuario.getSenha());
        usuario.setLogin(retornoUsuarioDTO.getLogin());
        usuario.setPerfil(PerfilUsuario.CLIENTE);
        atualizaCliente(usuario, retornoUsuarioDTO);
        usuarioFacade.altera(usuario);
    }

    private void atualizaCliente(Usuario usuario, RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        ClienteFacade clienteFacade = new ClienteFacade();
        Cliente cliente = clienteFacade.buscaPorUsuario(usuario);
        if(cliente == null){
            cliente = new Cliente();
        }
        cliente.setUsuario(usuario);
        cliente.setTelefone(Long.valueOf(retornoUsuarioDTO.getTelefone().replaceAll("[^0-9]", "")));
        cliente.setNome(retornoUsuarioDTO.getNome());
        cliente.setEmail(retornoUsuarioDTO.getEmail());
        Endereco endereco = new Endereco();
        endereco.setBairro(retornoUsuarioDTO.getEndereco().getBairro());
        endereco.setCep(Long.valueOf(retornoUsuarioDTO.getEndereco().getCep().replace("-", "")));
        endereco.setNumero(retornoUsuarioDTO.getEndereco().getNumero());
        if(retornoUsuarioDTO.getEndereco().getComplemento() != null) {
            endereco.setComplemento(retornoUsuarioDTO.getEndereco().getComplemento());
        }
        endereco.setRua(retornoUsuarioDTO.getEndereco().getRua());
        EnderecoFacade enderecoFacade = new EnderecoFacade();
        enderecoFacade.adiciona(endereco);
        cliente.setEndereco(endereco);
        if(cliente.getId() == null) {
            clienteFacade.adiciona(cliente);
        }else{
            clienteFacade.altera(cliente);
        }
    }

    private void atualizarUsuarioAdministrador(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(retornoUsuarioDTO.getIdUsuario());
        usuario.setLogin(retornoUsuarioDTO.getLogin());
        usuario.setPerfil(PerfilUsuario.ADMINISTRADOR);
        usuario.setSenha(PasswordService.hashPassword(retornoUsuarioDTO.getSenha()));
        UsuarioFacade usuarioFacade = new UsuarioFacade();
        usuarioFacade.altera(usuario);
    }


}
