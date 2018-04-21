package com.example.hector.proyectodamdaw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hector on 19/04/2018.
 */

public class AppDataSources {

    public static final String table_USER = "User";
    public static final String USER_FIRST_NAME = "UserFirstName";
    public static final String USER_LAST_NAME = "UserLastName";
    public static final String USER_ALIAS_NAME = "UserAliasName";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_STIKIES = "UserStikies";
    public static final String USER_PUBLIC_PROFILE = "UserPublicProfile";
    public static final String USER_TOKEN = "UserToken";
    public static final String MEDIA_ID = "MediaId";

    private AppHelper dbHelper;
    private SQLiteDatabase dbW, dbR;

    // CONSTRUCTOR
    public AppDataSources(Context cntx) {
        // En el constructor directament obro la comunicació amb la base de dades
        dbHelper = new AppHelper (cntx);
        // Amb aquest metode, construeixo dos databases un per llegir i l'altre per alterar
        open();
    }

    private void open() {
        dbW = dbHelper.getWritableDatabase();
        dbR = dbHelper.getReadableDatabase();
    }

    // DESTRUCTOR
    protected void finalize () {
        // Cerramos los databases
        dbW.close();
        dbR.close();
    }

    // FUNCIONS QUE RETORNAN CURSORES--------------------





    // FUNCIONES DE MANIPULACION DE DATOS-----------------------------------------------
    public void saveUserRegister( String firstName, String lastName, String userAlias, String userEmail, int userStikies, Boolean userPublicProfile, String userToken) {
        // Guardar los datos del registro del usuario
        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME, firstName);
        values.put(USER_LAST_NAME, lastName);
        values.put(USER_ALIAS_NAME, userAlias);
        values.put(USER_EMAIL, userEmail);
        values.put(USER_STIKIES, userStikies);
        values.put(USER_PUBLIC_PROFILE, userPublicProfile);
        values.put(USER_TOKEN, userToken);
        dbW.insert(table_USER,null,values);
    }

    public void saveUserLogin(String userEmail, String userToken) {
        // Modificar el valor del token del usuario
        ContentValues values = new ContentValues();
        values.put(USER_TOKEN, userToken);

        dbW.update(table_USER,values, USER_EMAIL + " = ?", new String[] { String.valueOf(userEmail) });
    }

}
