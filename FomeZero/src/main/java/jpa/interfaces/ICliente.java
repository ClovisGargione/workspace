package jpa.interfaces;

import model.Cliente;
import model.Usuario;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface ICliente {

    Cliente buscaPorId(Long id);
    Cliente buscaPorUsuario(Usuario usuario);
    Cliente buscaPorLogin(String login);
    List<Cliente> clientes();
    void adiciona(Cliente cliente);
    void remove(Cliente cliente);
    void altera(Cliente cliente);
}
