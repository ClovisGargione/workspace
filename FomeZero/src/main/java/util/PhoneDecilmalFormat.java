package util;

import java.text.DecimalFormat;

/**
 * Created by c_r_s_000 on 21/06/2015.
 */
public class PhoneDecilmalFormat {


    public String formatter(Long phoneFmt){
        DecimalFormat phoneDecimalFmt = new DecimalFormat("0000000000");
        String phoneRawString= phoneDecimalFmt.format(phoneFmt);

        java.text.MessageFormat phoneMsgFmt=new java.text.MessageFormat("({0}) {1}-{2}");
        //suposing a grouping of 3-3-4
        String[] phoneNumArr={phoneRawString.substring(0, 2),
                phoneRawString.substring(2,6),
                phoneRawString.substring(6)};

        return phoneMsgFmt.format(phoneNumArr);
    }
}
