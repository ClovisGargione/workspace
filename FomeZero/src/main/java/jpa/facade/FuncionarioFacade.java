package jpa.facade;


import jpa.JpaUtil;
import jpa.dao.FuncionarioDAO;
import jpa.interfaces.IFuncionario;
import model.Funcionario;
import model.Usuario;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class FuncionarioFacade {

    private static final Logger logger = Logger.getLogger(FuncionarioFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IFuncionario iFuncionario;

    public FuncionarioFacade() {
        this.iFuncionario = new FuncionarioDAO(entityManager);
    }

    public Funcionario buscaPorId(Long id) throws Exception {
        Funcionario funcionario;
        try{
            funcionario = iFuncionario.buscaPorId(id);
        }catch (Exception e){
            logger.error("Não foi possível localizar o funcionário id "+id);
            throw e;
        }
        return funcionario;
    }

    public Funcionario buscaPorNome(String login) throws Exception {
        Funcionario funcionario;
        try{
            funcionario = iFuncionario.buscaPorNome(login);
        }catch(Exception e){
            logger.error("Não foi possível localizar o funcionario login "+ login);
            throw e;
        }
        return funcionario;
    }

    public List<Funcionario> funcionarios() throws Exception {
        List<Funcionario> funcionarios;
        try{
            funcionarios = iFuncionario.funcionarios();
        }catch (Exception e){
            logger.error("Não foi possível localizar a lista de funcionários");
            throw  e;
        }
        return funcionarios;
    }

    public void adiciona(Funcionario funcionario) throws Exception {
        Funcionario f = iFuncionario.buscaPorNome(funcionario.getNome());
        if(f != null){
            throw new Exception("Funcionário já existe");
        }
        iFuncionario.adiciona(funcionario);
    }

    public void remove(Funcionario funcionario) throws Exception {
        try{
            iFuncionario.remove(funcionario);
        }catch (Exception e){
            logger.error("Não foi possível remover o funcionário " + funcionario.getNome());
            throw e;
        }
    }

    public void altera(Funcionario funcionario) throws Exception {
        Funcionario f = iFuncionario.buscaPorId(funcionario.getId());
        if(f == null){
            throw new Exception("Funcionário não cadastrado");
        }
        iFuncionario.altera(funcionario);
    }

    public Funcionario buscaPorUsuario(Usuario usuario) throws Exception {
        Funcionario f = iFuncionario.buscaPorUsuario(usuario);

        return f;

    }
}
