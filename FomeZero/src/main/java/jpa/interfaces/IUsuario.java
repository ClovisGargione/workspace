package jpa.interfaces;

import model.Usuario;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface IUsuario {

    Usuario buscaPorId(Long id);
    Usuario buscaPorLogin(String login);
    List<Usuario> usuarios();
    void adiciona(Usuario usuario);
    void remove(Usuario usuario);
    void altera(Usuario usuario);
}
