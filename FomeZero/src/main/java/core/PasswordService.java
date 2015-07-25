package core;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Created by c_r_s_000 on 13/06/2015.
 */
public class PasswordService {

    public static String hashPassword(String plaintext) {
        return BCrypt.hashpw(plaintext, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plaintext , String hashed) {
        return BCrypt.checkpw(plaintext, hashed);
    }
}
