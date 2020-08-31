package util;

import java.text.DecimalFormat;

public class Util {
   // is ma ham kara ga jo number ha asa show
      //  1000  === 1,000
    public static  String formatnumber(int value)
    {

        DecimalFormat formater=new DecimalFormat("#,###,###");
        String formated=formater.format(value);
        return formated;

    }



}
