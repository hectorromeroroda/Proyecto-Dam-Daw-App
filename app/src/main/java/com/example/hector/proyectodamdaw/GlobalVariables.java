package com.example.hector.proyectodamdaw;

import android.app.Application;

/**
 * Created by Hector on 15/05/2018.
 */

public class GlobalVariables extends Application{

    private static GlobalVariables instance;

    public   int idUserSqlite;


    public  int getIdUserSqlite() {
        return idUserSqlite;
    }

    public  void setIdUserSqlite(int id_UserSqlite) {
        idUserSqlite = id_UserSqlite;
    }

    public static synchronized GlobalVariables getInstance(){
        if(instance==null){
            instance=new GlobalVariables();
        }
        return instance;
    }



}
