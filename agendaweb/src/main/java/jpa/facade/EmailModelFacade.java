package jpa.facade;

import jpa.JpaUtil;
import jpa.implementacao.JpaEmailModelDao;
import jpa.interfaces.EmailDao;
import model.ContatoModel;
import model.EmailModel;
import org.apache.log4j.Logger;
import org.hibernate.validator.constraints.Email;

import javax.persistence.EntityManager;
import java.util.List;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
public class EmailModelFacade {

    private static final Logger logger = Logger.getLogger(EmailModelFacade.class);
    private EntityManager entityManager = JpaUtil.getEntityManager();
    private EmailDao emailDao;

    public EmailModelFacade(){
        this.emailDao = new JpaEmailModelDao(entityManager);
    }

    public void adicionaEmail(EmailModel emailModel){
        emailDao.adiciona(emailModel);
    }

    public void alteraEmail(EmailModel emailModel){
        emailDao.altera(emailModel);
    }

    public void removeEmail(EmailModel emailModel){
        emailDao.remove(emailModel);
    }

    public List<EmailModel> listaDeEmails(){
        List<EmailModel> lista = emailDao.emails();
        return lista;
    }

    public EmailModel buscaEmailPorId(Long id){
        EmailModel emailModel = emailDao.buscaPorId(id);
        return emailModel;
    }
}
