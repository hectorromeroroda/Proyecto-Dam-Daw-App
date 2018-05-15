package com.example.hector.proyectodamdaw;

import android.app.Application;

/**
 * Created by Hector on 15/05/2018.
 */

public class GlobalVariables extends Application{


    private static int idUserSqlite;

    public static int getIdUserSqlite() {
        return idUserSqlite;
    }

    public static void setIdUserSqlite(int id_UserSqlite) {
        idUserSqlite = id_UserSqlite;
    }



}
