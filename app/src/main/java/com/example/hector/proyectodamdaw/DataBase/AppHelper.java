package com.example.hector.proyectodamdaw.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ivan on 05/04/2018.
 */

public class AppHelper extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "demovote";

    public AppHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

     /*COMENTAR DB CACHE FALTA DE TABLAS*/
        String comunity =
                "CREATE TABLE Community (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "IdCommunity VARCHAR(45) NOT NULL," +
                        "Name VARCHAR(45)NOT NULL," +
                        "Description VARCHAR(300) NOT NULL," +
                        "NumUsers INT," +
                        "NumContent INT," +
                        "IsPublic BIT," +
                        "MediaId)";
        sqLiteDatabase.execSQL(comunity);
        String comunityUser =
                "CREATE TABLE CommunityUser (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "IdCommunity VARCHAR(45) NOT NULL," +
                        "IdUserSqlite INT NOT NULL," +
                        "UserInvited BIT," +
                        "UserRole VARCHAR(45)NOT NULL," +
                        "FOREIGN KEY (IdCommunity) REFERENCES Community(IdCommunity)," +
                        "FOREIGN KEY (IdUserSqlite) REFERENCES User(_id)" +
                        ")";
        sqLiteDatabase.execSQL(comunityUser);
        String post =
                "CREATE TABLE Post(_id INTEGER PRIMARY KEY," +
                        "Title VARCHAR(45)," +
                        "Text VARCHAR(500)," +
                        "PublicationDate DATE," +
                        "UserId INT," +
                        "CommunityId INT," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)," +
                        "FOREIGN KEY (CommunityId) REFERENCES Community(_id)" +
                        ")";
        sqLiteDatabase.execSQL(post);
        String poll =
                "CREATE TABLE Poll(_id INTEGER PRIMARY KEY," +
                        "Title VARCHAR(45)," +
                        "Text VARCHAR(500)," +
                        "StartDate DATE," +
                        "FinishDate DATE," +
                        "TotalVotesDone INT," +
                        "UserId INT," +
                        "CommunityId INT," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)," +
                        "FOREIGN KEY (CommunityId) REFERENCES Community(_id)" +
                        ")";
        sqLiteDatabase.execSQL(poll);
        String proposition =
                "CREATE TABLE Proposition(_id INTEGER PRIMARY KEY," +
                        "propositionTitle VARCHAR(45)," +
                        "propositionText VARCHAR(500)," +
                        "propositionVoted INT," +
                        "UserId INT," +
                        "CommunityId INT," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)," +
                        "FOREIGN KEY (CommunityId) REFERENCES Community(_id)" +
                        ")";
        sqLiteDatabase.execSQL(proposition);
        String user =
                "CREATE TABLE User(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "UserFirstName VARCHAR(45) NOT NULL," +
                        "UserLastName VARCHAR(45) NOT NULL," +
                        "UserEmail VARCHAR(45) NOT NULL," +
                        "UserStikies TINYINT(1) NOT NULL," +
                        "UserPublicProfile BIT NOT NULL," +
                        "UserToken VARCHAR(150)," +
                        "UserRememberMe BIT NOT NULL," +
                        "UserRol VARCHAR(45)," +
                        "MediaId INT)";
        sqLiteDatabase.execSQL(user);
        String messages =
                "CREATE TABLE Message (_id INTEGER PRIMARY KEY," +
                        "Text VARCHAR(500)," +
                        "Sended Date," +
                        "Recived Date," +
                        "UserSenderId INT," +
                        "UserReceiverId INT," +
                        "FOREIGN KEY (UserSenderId) REFERENCES User(_id)," +
                        "FOREIGN KEY (UserReceiverId) REFERENCES User(_id)" +
                        ")";
        sqLiteDatabase.execSQL(messages);
        String optionPoll =
                "CREATE TABLE optionPoll (_id INTEGER PRIMARY KEY," +
                        "OptionTitle VARCHAR(45)," +
                        "OptionDescription VARCHAR(500)," +
                        "OptionResult INT," +
                        "PollId INT," +
                        "FOREIGN KEY (PollId) REFERENCES Poll( _id))";
        sqLiteDatabase.execSQL(optionPoll);
        String postComment =
                "CREATE TABLE PostComment (_id INTEGER PRIMARY KEY," +
                        "CommentText VARCHAR(500)," +
                        "CommentPublicationDate DATE," +
                        "PostId INT," +
                        "UserId INT," +
                        "FOREIGN KEY (PostId) REFERENCES Post(_id)," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)" +
                        ")";
        sqLiteDatabase.execSQL(postComment);
        String pollComment =
                "CREATE TABLE PollComment (_id INTEGER PRIMARY KEY," +
                        "PollText VARCHAR(500)," +
                        "PollPublicationDate DATE," +
                        "PollId INT," +
                        "UserId INT," +
                        "FOREIGN KEY (PollId) REFERENCES Post(_id)," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)" +
                        ")";
        sqLiteDatabase.execSQL(pollComment);
        String propositionComment =
                "CREATE TABLE PropositionComment (_id INTEGER PRIMARY KEY," +
                        "PropositionText VARCHAR(500)," +
                        "CommentPublicationDate DATE," +
                        "PropositionId INT," +
                        "UserId INT," +
                        "FOREIGN KEY (PropositionId) REFERENCES Post(_id)," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)" +
                        ")";
        sqLiteDatabase.execSQL(propositionComment);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
