package com.example.hector.proyectodamdaw.Otros;

import android.app.Application;

/**
 * Created by Hector on 15/05/2018.
 */

public class GlobalVariables extends Application{

    private static GlobalVariables instance;

    public   int idUserSqlite;
    public   boolean RefreshData = false;
    public  String communityId;


    public  int getIdUserSqlite() {
        return idUserSqlite;
    }

    public  void setIdUserSqlite(int id_UserSqlite) {
        idUserSqlite = id_UserSqlite;
    }

    public  boolean getRefreshData() {
        return RefreshData;
    }

    public  void setRefreshData(boolean refreshData) {
        RefreshData = refreshData;
    }

    public  String getCommunityId() {
        return communityId;
    }

    public  void setCommunityId(String idCommunity) {
        communityId = idCommunity;
    }

    public static synchronized GlobalVariables getInstance(){
        if(instance==null){
            instance=new GlobalVariables();
        }
        return instance;
    }



}