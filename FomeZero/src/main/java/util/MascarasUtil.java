package util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * Created by c_r_s_000 on 21/06/2015.
 *
 */
public class MascarasUtil {

    /**
     *
     * @param pattern
     * @param value
     * @return
     * exemplo
     * // CEP - resultado: 81580-200
    format("#####-###", "81580200");
    // CPF - resultado 012.345.699-01
    format("###.###.###-##", "01234569905");
    // CNPJ - resultado: 01.234.569/9052-34
    format("##.###.###/####-##", "01234569905234");
    */
    public static String format(String pattern, Object value) {
        MaskFormatter mask;
        try {
            mask = new MaskFormatter(pattern);
            mask.setValueContainsLiteralCharacters(false);
            return mask.valueToString(value);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
