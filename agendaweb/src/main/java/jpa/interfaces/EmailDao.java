package jpa.interfaces;

import model.EmailModel;

import java.util.List;

/**
 * Created by c_r_s_000 on 04/06/2015.
 */
public interface EmailDao {

    EmailModel buscaPorId(Long id);
    List<EmailModel> emails();
    void adiciona(EmailModel emailModel);
    void altera(EmailModel emailModel);
    void remove(EmailModel emailModel);

}
