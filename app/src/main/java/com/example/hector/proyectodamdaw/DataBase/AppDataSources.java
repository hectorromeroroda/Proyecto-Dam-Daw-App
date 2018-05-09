package com.example.hector.proyectodamdaw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Hector on 19/04/2018.
 */

public class AppDataSources {

    public static final String table_USER = "User";
    public static final String USER_FIRST_NAME = "UserFirstName";
    public static final String USER_LAST_NAME = "UserLastName";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_STIKIES = "UserStikies";
    public static final String USER_PUBLIC_PROFILE = "UserPublicProfile";
    public static final String USER_TOKEN = "UserToken";
    public static final String USER_REMEMBER_ME = "UserRememberMe";


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
    public Cursor rememmberMeUserLogin() {

        String selectQuery = " SELECT UserRememberMe FROM User";

        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor typeProfile() {
        // Retorna el campo tipo de perfil publico/privado
        String selectQuery = " SELECT UserPublicProfile FROM User";

        return dbR.rawQuery(selectQuery,null);
    }





    // FUNCIONES DE MANIPULACION DE DATOS-----------------------------------------------
    public void saveUserRegister( String firstName, String lastName, String userEmail, int userStikies, Boolean userPublicProfile, String userToken, Boolean rememberMe) {
        // Guardar los datos del registro del usuario
        ContentValues values = new ContentValues();
        values.put(USER_FIRST_NAME, firstName);
        values.put(USER_LAST_NAME, lastName);
        values.put(USER_EMAIL, userEmail);
        values.put(USER_STIKIES, userStikies);
        values.put(USER_PUBLIC_PROFILE, userPublicProfile);
        values.put(USER_TOKEN, userToken);
        values.put(USER_REMEMBER_ME, rememberMe);
        dbW.insert(table_USER,null,values);
    }

    public void saveUserLogin(String userToken, boolean rememberMe) {
        // Modificar el valor del token  y el estado de rememberMe del usuario
        String UpdateQuery = "UPDATE User SET UserToken = '" + userToken + "', UserRememberMe= '" + rememberMe + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

    public void saveEditTypeProfile(boolean profileState) {

        String UpdateQuery = "UPDATE User SET UserPublicProfile = '" + profileState + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

}
