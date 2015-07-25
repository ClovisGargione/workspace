package jpa.interfaces;

import model.Pedido;
import model.Produto;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface IProduto {

    Produto buscaPorId(Long id);
    List<Produto> produtos();
    void adiciona(Produto produto);
    void remove(Produto produto);
    void altera(Produto produto);
}
