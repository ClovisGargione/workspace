package jpa.interfaces;

import model.Funcionario;
import model.Usuario;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface IFuncionario {

    Funcionario buscaPorId(Long id);
    Funcionario buscaPorNome(String nome);
    List<Funcionario> funcionarios();
    void adiciona(Funcionario funcionario);
    void remove(Funcionario funcionario);
    void altera(Funcionario funcionario);
    Funcionario buscaPorUsuario(Usuario usuario);
}
