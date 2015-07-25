package util;

import javax.swing.text.MaskFormatter;
import java.text.ParseException;

/**
 * Created by c_r_s_000 on 21/06/2015.
 */
public class MascarasUtil {

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
