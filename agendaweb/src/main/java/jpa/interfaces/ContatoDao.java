package jpa.interfaces;

import model.ContatoModel;
import model.UsuarioModel;

import java.util.List;

/**
 * Created by c_r_s_000 on 25/05/2015.
 */
public interface ContatoDao {

    ContatoModel buscaPorId(Long id);
    List<ContatoModel> listaDeContatos();
    void alteraContato(ContatoModel contatoModel);
    void removeContato(ContatoModel contatoModel);
    void adicionaContato(ContatoModel contatoModel);

}
