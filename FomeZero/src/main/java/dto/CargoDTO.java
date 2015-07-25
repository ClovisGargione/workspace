package dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by c_r_s_000 on 21/06/2015.
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class CargoDTO {

    private String cargo;

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
