package core;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by c_r_s_000 on 13/06/2015.
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
