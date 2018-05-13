package com.example.hector.proyectodamdaw;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hector on 11/04/2018.
 */

public class Comprobations {

    public boolean checkEmptyFields(String texto) {
        boolean empty=false ;

        if ( (texto == null) || (texto.equals(""))){
            empty=true;
        }
        return empty;
    }

    public   boolean checkStringsEquals(String strFirst, String strSecond){
        boolean equals = false;

        if (strFirst.equals(strSecond)){
            equals = true;
        }

        return equals;
    }

    public   boolean checkEmailFormat(String strEmail){
        boolean formatCorrect = false;

        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(strEmail);

        if (mather.find() == true) {
            formatCorrect=true;
        }

        return formatCorrect;
    }

}
