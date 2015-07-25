/**
 * 
 */
package jpa.interfaces;

import java.util.List;

import model.UsuarioModel;

/**
 * @author c_r_s_000
 *
 */
public interface UsuarioDao {
	
	UsuarioModel buscaPorId(Long id);
	List<UsuarioModel> listaUsuarios();
	UsuarioModel buscaPorLogin(String login);
	void altera(UsuarioModel usuarioModel);
	void adiciona(UsuarioModel usuarioModel);
	void remove(UsuarioModel usuarioModel);

}
