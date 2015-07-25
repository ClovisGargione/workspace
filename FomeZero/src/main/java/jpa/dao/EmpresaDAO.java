package jpa.dao;

import com.sun.istack.internal.logging.Logger;
import jpa.interfaces.IEmpresa;
import model.Empresa;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class EmpresaDAO implements IEmpresa{

    private final static Logger logger = Logger.getLogger(EmpresaDAO.class);
    private EntityManager entityManager;

    public EmpresaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Empresa buscaPorId(Long id) {
        Empresa empresa = entityManager.find(Empresa.class, id);
        return empresa;
    }

    public Empresa buscaPorCnpj(Long cnpj){
        TypedQuery<Empresa> query = entityManager.createQuery("select e from Empresa e where e.cnpj = :cnpj", Empresa.class);
        query.setParameter("cnpj",cnpj);
        Empresa empresa = null;
        try {
            empresa = query.getSingleResult();
        }catch(Exception e){

        }
        return empresa;
    }

    public List<Empresa> empresas() {
        TypedQuery<Empresa> query = entityManager.createQuery("select e from Empresa e", Empresa.class);
        List<Empresa> empresas = query.getResultList();
        return empresas;
    }


    public void adiciona(Empresa empresa) {
        entityManager.persist(empresa);
    }

    public void remove(Empresa empresa) {
        entityManager.remove(empresa);
    }

    public void altera(Empresa empresa) {
        entityManager.merge(empresa);
    }
}
