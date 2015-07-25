package jpa.interfaces;

import model.Empresa;
import model.Pedido;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface IPedido {

    Pedido buscaPorId(Long id);
    List<Pedido> pedidos();
    void adiciona(Pedido pedido);
    void remove(Pedido pedido);
    void altera(Pedido pedido);
}
