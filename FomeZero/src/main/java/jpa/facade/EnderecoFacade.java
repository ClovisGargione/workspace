package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.EnderecoDAO;
import jpa.interfaces.IEndereco;
import model.Empresa;
import model.Endereco;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class EnderecoFacade {

    private static final Logger logger = Logger.getLogger(EnderecoFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IEndereco iEndereco;

    public EnderecoFacade(){
        this.iEndereco = new EnderecoDAO(entityManager);
    }

    public Endereco buscaPorId(Long id) throws Exception {
        Endereco endereco;
        try{
            endereco = iEndereco.buscaPorId(id);
        }catch(Exception e){
            logger.error("Não foi possível localizar o endereco id: " + id);
            throw e;
        }
        return endereco;
    }

    public Endereco buscaPorCep(Long cep) throws Exception {
        Endereco endereco;
        try{
            endereco = iEndereco.buscaPorCep(cep);
        }catch(Exception e){
            logger.error("Não foi possível localizar o endereco cep: " + cep);
            throw e;
        }
        return endereco;
    }

    public List<Endereco> enderecos() throws Exception {
        List<Endereco> enderecos;
        try{
            enderecos = iEndereco.enderecos();
        }catch(Exception e){
            logger.error("Não foi possível localizar a lista de endereços");
            throw e;
        }
        return enderecos;
    }

    public void adiciona(Endereco endereco) throws Exception {
        try {
            iEndereco.adiciona(endereco);
        }catch(Exception e){
            logger.error("Não foi possível adicionar o endereço");
            throw e;
        }
    }

    public void remover(Endereco endereco) throws Exception {
        try{
            iEndereco.remove(endereco);
        }catch(Exception e){
            logger.error("Não foi possível remover o endereço");
            throw e;
        }
    }

    public void altera(Endereco endereco) throws Exception {
        try{
            iEndereco.altera(endereco);
        }catch(Exception e){
            logger.error("Não foi possível atualizar o endereço");
            throw e;
        }
    }

}
