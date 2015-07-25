package jpa.facade;

import jpa.JpaUtil;
import jpa.dao.EmpresaDAO;
import jpa.interfaces.IEmpresa;
import model.Empresa;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 14/06/2015.
 */
public class EmpresaFacade {

    private static final Logger logger = Logger.getLogger(EmpresaFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private IEmpresa iEmpresa;

    public EmpresaFacade(){
        this.iEmpresa = new EmpresaDAO(entityManager);
    }

    public Empresa buscaPorId(Long id) throws Exception {
        Empresa empresa;
        try{
            empresa = iEmpresa.buscaPorId(id);
        }catch(Exception e){
            logger.error("Não foi possível localizar a empresa id " + id);
            throw e;
        }
        return empresa;
    }

    public Empresa buscaPorCnpj(Long cnpj) throws Exception {
        Empresa empresa;
        try{
            empresa = iEmpresa.buscaPorCnpj(cnpj);
        }catch (Exception e){
            logger.error("Não foi possível localizar a empresa cnpj " + cnpj);
            throw e;
        }
        return empresa;
    }

    public List<Empresa> empresas() throws Exception {
        List<Empresa> empresas;
        try{
            empresas = iEmpresa.empresas();
        }catch(Exception e){
            logger.error("Não foi possível localizar a lista de empresas");
            throw e;
        }
        return empresas;
    }

    public void adiciona(Empresa empresa) throws Exception {
        Empresa e = iEmpresa.buscaPorCnpj(empresa.getCnpj());
        if(e != null){
            throw new Exception("Empresa já cadastrada");
        }
        iEmpresa.adiciona(empresa);
    }

    public void remove(Empresa empresa) throws Exception {
        try{
            iEmpresa.remove(empresa);
        }catch(Exception e){
            logger.error("Não foi possível remover");
            throw e;
        }
    }

    public void altera(Empresa empresa) throws Exception {
        Empresa e = iEmpresa.buscaPorId(empresa.getId());
        if(e == null){
            throw new Exception("Empresa não cadastrada");
        }
        iEmpresa.altera(empresa);
    }

}
