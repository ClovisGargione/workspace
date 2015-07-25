package business.interfaces;

import dto.RetornoUsuarioDTO;

/**
 * Created by c_r_s_000 on 21/06/2015.
 */
public interface IUsuarioBusiness {

    void atualizaUsuario(RetornoUsuarioDTO retornoUsuarioDTO) throws Exception;
}
