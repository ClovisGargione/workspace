package jpa.interfaces;

import model.Empresa;
import model.Funcionario;

import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public interface IEmpresa {

    Empresa buscaPorId(Long id);
    Empresa buscaPorCnpj(Long cnpj);
    List<Empresa> empresas();
    void adiciona(Empresa empresa);
    void remove(Empresa empresa);
    void altera(Empresa empresa);
}
