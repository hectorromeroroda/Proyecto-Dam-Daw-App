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
    public static final String USER_ID = "_id";
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
    public Cursor rememmberMeUserLogin() {

        String selectQuery = " SELECT _id FROM User WHERE UserRememberMe= 1 ";
        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor userIdSqlite(String userEmail) {

        String selectQuery = " SELECT _id FROM User WHERE UserEmail= '" + userEmail + "'";
        return dbR.rawQuery(selectQuery,null);
    }

    public Cursor searchUserToken(int idSqlite) {

        String selectQuery = " SELECT UserToken FROM User WHERE _id= " + idSqlite + "";
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
    public Cursor todasComunitiesPertenece(int idUserSqlite) {
        String selectQuery = "SELECT * FROM Community WHERE IdCommunity IN (SELECT IdCommunity FROM CommunityUser WHERE IdUserSqlite= '" + idUserSqlite + "' AND UserInvited= 0)";

        return dbR.rawQuery(selectQuery, null);
    }

    public Cursor todasComunitiesInvited(int idUserSqlite) {
        String selectQuery = "SELECT * FROM Community WHERE IdCommunity IN (SELECT IdCommunity FROM CommunityUser WHERE IdUserSqlite= '" + idUserSqlite + "' AND UserInvited= 1)";

        return dbR.rawQuery(selectQuery, null);
    }

    public Cursor allOtherCommunities() {
        String selectQuery = "SELECT * FROM Community WHERE IdCommunity NOT IN (SELECT IdCommunity FROM CommunityUser)";

        return dbR.rawQuery(selectQuery, null);
    }

    // FUNCIONES DE MANIPULACION DE DATOS-----------------------------------------------------------------------------------------------------------------------
    public void saveUserRegister( String firstName, String lastName, String userEmail, int userStikies, Boolean userPublicProfile, String userToken, int rememberMe) {
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

    public void saveProposal(String nombre, String descripcion,String pregunta,  String respuestaSi, String respuestaNo, String comunidadId, boolean yaVotada) {
        ContentValues values = new ContentValues();
        values.put("propositionTitle", nombre);
        values.put("propositionDescription", descripcion);
        values.put("propositionPregunta", pregunta);
        values.put("propositionRespuestaSi", respuestaSi);
        values.put("propositionRespuestaNo", respuestaNo);
        values.put("CommunityId", comunidadId);
        values.put("propositionYaVotada", yaVotada);
        dbW.insert("Proposition",null,values);
    }
    public void savePost(String nombre, String descripcion,String contenido,  String comunidadId) {
        ContentValues values = new ContentValues();
        values.put("postTitle", nombre);
        values.put("postDescription", descripcion);
        values.put("postContent", contenido);
        values.put("CommunityId", comunidadId);
        dbW.insert("Post",null,values);
    }
    public void savePoll(String titulo, String descripcion,String contenido, String fechaInicio, String fechaFinal,  String comunidadId, boolean yaVotada, String pregunta1,
                          String respuesta1a, String respuesta1b, String pregunta2, String respuesta2a, String respuesta2b,  String pregunta3, String respuesta3a, String respuesta3b,
                          String pregunta4, String respuesta4a, String respuesta4b,  String pregunta5, String respuesta5a, String respuesta5b) {
        ContentValues values = new ContentValues();
        values.put("PollTitle", titulo);
        values.put("PollDescription", descripcion);
        values.put("PollContenido", contenido);
        values.put("PollStartDate", fechaInicio);
        values.put("PollFinishDate", fechaFinal);
        values.put("PollCommunityId", comunidadId);
        values.put("PollYaVotada", yaVotada);
        values.put("pollPregunta1", pregunta1);
        values.put("pollRespuesta1a", respuesta1a);
        values.put("pollRespuesta1b", respuesta1b);
        values.put("pollPregunta2", pregunta2);
        values.put("pollRespuesta2a", respuesta2a);
        values.put("pollRespuesta2b", respuesta2b);
        values.put("pollPregunta3", pregunta3);
        values.put("pollRespuesta3a", respuesta3a);
        values.put("pollRespuesta3b", respuesta3b);
        values.put("pollPregunta4", pregunta4);
        values.put("pollRespuesta4a", respuesta4a);
        values.put("pollRespuesta4b", respuesta4b);
        values.put("pollPregunta5", pregunta5);
        values.put("pollRespuesta5a", respuesta5a);
        values.put("pollRespuesta5b", respuesta5b);
        dbW.insert("Poll",null,values);
    }

    public void saveEditTypeProfile(boolean profileState, int idUser) {

        String UpdateQuery = "UPDATE User SET UserPublicProfile = '" + profileState + "' WHERE _id= '" + idUser + "'";

        dbW.rawQuery(UpdateQuery,null);
    }

    public void updateUserLoginTokenRememberMe(String userToken, int rememberMe, int idUser) {
        ContentValues values = new ContentValues();
        values.put(USER_TOKEN, userToken);
        values.put(USER_REMEMBER_ME, rememberMe);

        dbW.update(table_USER,values, USER_ID + " = ?", new String[] { String.valueOf(idUser) });
    }

    public void updateUserRememberMe( int rememberMe, int state) {
        ContentValues values = new ContentValues();
        values.put(USER_REMEMBER_ME, rememberMe);

        dbW.update(table_USER,values, USER_REMEMBER_ME + " = ?", new String[] { String.valueOf(state) });
    }

    public void updateUserLogin(int stikies, boolean profileIsPublic, String email, int idUser) {

        String UpdateQuery = "UPDATE User SET UserStikies = '" + stikies + "', UserPublicProfile= '" + profileIsPublic + "', UserEmail= '" + email + "' WHERE _id= " + idUser + "";

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


