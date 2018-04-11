package com.example.hector.proyectodamdaw;

/**
 * Created by Hector on 11/04/2018.
 */

public class Comprobations {

    protected boolean checkEmptyFields(String texto) {
        boolean vacio=false ;

        if ( (texto == null) || (texto.equals(""))){
            vacio=true;
        }
        return vacio;
    }
}
