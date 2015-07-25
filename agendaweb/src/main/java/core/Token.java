/**
 * 
 */
package core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author c_r_s_000
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Token {
	
	String token;

	public Token(){
		
	}
	
	public Token(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token){
		this.token = token;
	}
}