/**
 * 
 */
package core;

import org.mindrot.jbcrypt.BCrypt;

/**
 * @author c_r_s_000
 *
 */
public class PasswordService {
	
	public static String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}
	
	public static boolean checkPassword(String plaintext , String hashed) {
		return BCrypt.checkpw(plaintext, hashed);
	}

}
