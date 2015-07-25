package jpa.interfaces;

import model.Entregas;

import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public interface IEntregas {

    Entregas buscapPorId(Long id);
    List<Entregas> entregas();
    void adiciona(Entregas entrega);
    void remove(Entregas entrega);
    void altera(Entregas entrega);
}
