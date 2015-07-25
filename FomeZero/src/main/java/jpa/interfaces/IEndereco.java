package jpa.interfaces;

import model.Endereco;
import model.Pedido;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface IEndereco {

    Endereco buscaPorId(Long id);
    Endereco buscaPorCep(Long cep);
    List<Endereco> enderecos();
    void adiciona(Endereco endereco);
    void remove(Endereco endereco);
    void altera(Endereco endereco);
}
