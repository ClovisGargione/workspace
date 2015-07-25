package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.ClienteDAO;
import jpa.interfaces.ICliente;
import model.Cliente;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class ClienteFacade {

    private static final Logger logger = Logger.getLogger(ClienteFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private ICliente iCliente;

    public ClienteFacade() {
        this.iCliente = new ClienteDAO(entityManager);
    }

    public Cliente buscaPorId(Long id) throws Exception {
        Cliente cliente;
        try{
            cliente = iCliente.buscaPorId(id);
        }catch (Exception e){
            logger.error("Não foi possível localizar o cliente id: "+id);
            throw e;
        }
        return cliente;
    }

    public Cliente buscaPorUsuario(Usuario usuario) throws Exception {
        Cliente cliente;
        try{
            cliente = iCliente.buscaPorUsuario(usuario);
        }catch (Exception e){
            logger.error("Não foi possível localizar o cliente");
            throw e;
        }
        return cliente;
    }

    public Cliente buscaPorLogin(String login) throws Exception {
        Cliente cliente;
        try{
            cliente = iCliente.buscaPorLogin(login);
        }catch(Exception e){
            logger.error("Não foi possível localizar o cliente login: "+login);
            throw e;
        }
        return cliente;
    }

    public List<Cliente> clientes() throws Exception {
        List<Cliente> lista = null;
        try{
            lista = iCliente.clientes();
        }catch(Exception e){
            logger.error("Não foi possível localizar a lista de clientes");
            throw e;
        }
        return lista;
    }

    public void adiciona(Cliente cliente) throws Exception {

        Cliente c = iCliente.buscaPorLogin(cliente.getNome());
        if(c != null){
            throw new Exception("Cliente já cadastrado");
        }
        iCliente.adiciona(cliente);

    }

    public void remove(Cliente cliente) throws Exception {
        try{
            iCliente.remove(cliente);
        }catch(Exception e){
            logger.error("Não foi possível remover o cliente "+cliente.getNome());
            throw e;
        }
    }

    public void altera(Cliente cliente) throws Exception {
        Cliente c = iCliente.buscaPorId(cliente.getId());
        if(c == null){
            throw new Exception("Cliente não cadastrado");
        }
        iCliente.altera(cliente);
    }



}
