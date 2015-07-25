package jpa.implementacao;

import jpa.interfaces.EmailDao;
import model.EmailModel;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
public class JpaEmailModelDao implements EmailDao {

    private EntityManager entityManager;
    private static final Logger logger = Logger.getLogger(JpaEmailModelDao.class);

    public JpaEmailModelDao(EntityManager entityManager){
        this.entityManager = entityManager;
    }

    public EmailModel buscaPorId(Long id) {
        EmailModel emailModel = entityManager.find(EmailModel.class, id);
        return emailModel;
    }

    public List<EmailModel> emails() {
        TypedQuery<EmailModel> emails = entityManager.createQuery("select e from EmailModel e", EmailModel.class);
        List<EmailModel> listaDeEmails = emails.getResultList();
        return listaDeEmails;
    }

    public void adiciona(EmailModel emailModel) {
        entityManager.persist(emailModel);
    }

    public void altera(EmailModel emailModel) {
        entityManager.merge(emailModel);
    }

    public void remove(EmailModel emailModel) {
        entityManager.remove(emailModel);
    }
}
