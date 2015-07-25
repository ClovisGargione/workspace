package jpa.interfaces;

import model.Favoritos;

import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public interface IFavoritos {

    Favoritos buscaPorId(Long id);
    List<Favoritos> meusFavoritos();
    void adiciona(Favoritos favoritos);
    void remove(Favoritos favoritos);
    void altera(Favoritos favoritos);
}
