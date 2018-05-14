package com.example.hector.proyectodamdaw.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.hector.proyectodamdaw.Content.Communitie;

/**
 * Created by Hector on 19/04/2018.
 */

public class AppDataSources {

    public static final String table_COMMUNITY_USER = "CommunityUser";
    public static final String table_USER = "User";
    public static final String table_COMM = "Community";
    public static final String USER_FIRST_NAME = "UserFirstName";
    public static final String USER_LAST_NAME = "UserLastName";
    public static final String USER_EMAIL = "UserEmail";
    public static final String USER_ID_SQLITE = "IdUserSqlite";
    public static final String USER_STIKIES = "UserStikies";
    public static final String USER_PUBLIC_PROFILE = "UserPublicProfile";
    public static final String USER_TOKEN = "UserToken";
    public static final String USER_REMEMBER_ME = "UserRememberMe";
    public static final String COMMUNITY_ID = "IdCommunity";
    public static final String COMMUNITY_ROLE = "UserRole";
    public static final String COMMUNITY_MEMBERS = "NumUsers";
    public static final String COMMUNITY_PUBLIC = "IsPublic";
    public static final String COMMUNITY_CONTENTS = "NumContent";
    public static final String COMMUNITY_NAME = "Name";
    public static final String COMMUNITY_DESCRIPTION = "Description";
    public static final String COMMUNITY_USER_INVITED = "UserInvited";


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

    // FUNCIONS QUE RETORNAN CURSORES-----------------------------------------------------------------------------------------------------------------
    public Cursor rememmberMeUserLogin(int idUser) {

        String selectQuery = " SELECT UserRememberMe FROM User WHERE _id= '" + idUser + "'";
        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor userIdSqlite(String userEmail) {

        String selectQuery = " SELECT _id FROM User WHERE UserEmail= '" + userEmail + "'";
        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor typeProfile(int idUser) {
        // Retorna el campo tipo de perfil publico/privado
        String selectQuery = " SELECT UserPublicProfile FROM User WHERE _id= '" + idUser + "'";
        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor searchIdCommunitie(String idCommunitie) {

        String selectQuery = " SELECT _id FROM Community WHERE IdCommunity= '" + idCommunitie + "'";
        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor todasComunities() {
        String selectQuery = "SELECT * FROM Community";

        return dbR.rawQuery(selectQuery, null);


    }
    // FUNCIONES DE MANIPULACION DE DATOS-----------------------------------------------------------------------------------------------------------------------
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

    public void saveCommunity(int numMembers, boolean isPublic, int numContents, String name, String description, String communityId) {
        ContentValues values = new ContentValues();
        values.put(COMMUNITY_ID, communityId);
        values.put(COMMUNITY_MEMBERS, numMembers);
        values.put(COMMUNITY_PUBLIC, isPublic);
        values.put(COMMUNITY_CONTENTS, numContents);
        values.put(COMMUNITY_NAME, name);
        values.put(COMMUNITY_DESCRIPTION, description);
        dbW.insert(table_COMM,null,values);
    }

    public void saveCommunityUser(String communityId, int userId, String userRole, boolean userInvited) {
        ContentValues values = new ContentValues();
        values.put(COMMUNITY_ID, communityId);
        values.put(USER_ID_SQLITE, userId);
        values.put(COMMUNITY_ROLE, userRole);
        values.put(COMMUNITY_USER_INVITED, userInvited);

        dbW.insert(table_COMMUNITY_USER,null,values);
    }

    public void saveCommunityUserinvited(int numMembers, boolean isPublic, int numContents, String name, String description, String id, boolean UserInvited) {
        ContentValues values = new ContentValues();
        values.put(COMMUNITY_ID, id);
        values.put(COMMUNITY_MEMBERS, numMembers);
        values.put(COMMUNITY_PUBLIC, isPublic);
        values.put(COMMUNITY_CONTENTS, numContents);
        values.put(COMMUNITY_NAME, name);
        values.put(COMMUNITY_DESCRIPTION, description);
        values.put(COMMUNITY_USER_INVITED, UserInvited);
        dbW.insert(table_COMM,null,values);
    }

    public void saveEditTypeProfile(boolean profileState, int idUser) {

        String UpdateQuery = "UPDATE User SET UserPublicProfile = '" + profileState + "' WHERE _id= '" + idUser + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

    public void updateUserLoginTokenRememberMe(String userToken, boolean rememberMe, int idUser) {
        // Modificar el valor del token  y el estado de rememberMe del usuario
        String UpdateQuery = "UPDATE User SET UserToken = '" + userToken + "', UserRememberMe= '" + rememberMe + "' WHERE _id= '" + idUser + "'";;

        dbW.rawQuery(UpdateQuery,null);
    }

    public void updateUserLogin(int stikies, boolean profileIsPublic, String email, int idUser) {

        String UpdateQuery = "UPDATE User SET UserStikies = '" + stikies + "', UserPublicProfile= '" + profileIsPublic + "', UserEmail= '" + email + "' WHERE _id= '" + idUser + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

    public void updateCommunity(int numMembers, boolean isPublic, int numContents, String name, String description, String idCommunity) {

        String UpdateQuery = "UPDATE Community SET NumUsers = '" + numMembers + "', IsPublic= '" + isPublic + "'"+ ", NumContent= '"
                + numContents + "'"+ ", Name= '" + name + "'"+ ", Description= '" + description + "' WHERE IdCommunity= '" + idCommunity + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

    public void updateCommunityUserInvited(int numMembers, boolean isPublic, int numContents, String name, String description, String idCommunity) {

        String UpdateQuery = "UPDATE Community SET NumUsers = '" + numMembers + "', IsPublic= '" + isPublic + "'"+ ", NumContent= '"
                + numContents + "'"+ ", Name= '" + name + "'"+ ", Description= '" + description + "' WHERE IdCommunity= '" + idCommunity + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

    public static Communitie extraerCommunity(Cursor cursor){
        Communitie communitie = new Communitie();
        communitie.setName(cursor.getString(2));
        communitie.setDescription(cursor.getString(3));
        communitie.setNumUsers(cursor.getInt(4));
        return communitie;
    }

}


