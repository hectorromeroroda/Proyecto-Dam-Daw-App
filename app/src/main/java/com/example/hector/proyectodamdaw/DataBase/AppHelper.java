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
                "CREATE TABLE Community (_id INTEGER PRIMARY KEY," +
                        "Name VARCHAR(45)," +
                        "MaxUsers INT," +
                        "MediaId)";
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
        String user =
                "CREATE TABLE User(_id INTEGER PRIMARY KEY," +
                        "UserName VARCHAR(45)," +
                        "UserFirstSurname VARCHAR(45)," +
                        "UserSecondSurname VARCHAR(45)," +
                        "UserLoginName VARCHAR(45)," +
                        "UserPassword VARCHAR(45)," +
                        "UserEmail VARCHAR(45)," +
                        "UserStikies TINYINT(1)," +
                        "UserPublicProfile BIT," +
                        "UserActivated BIT," +
                        "UserTelephone VARCHAR(15)," +
                        "MediaId INT)";
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
        String optionPoll =
                "CREATE TABLE (_id INTEGER PRIMARY KEY," +
                        "OptionTitle VARCHAR(45)," +
                        "OptionDescription VARCHAR(500)," +
                        "OptionResult INT," +
                        "PollId INT," +
                        "FOREIGN KEY (PollId) REFERENCES Poll( _id))";
        String postComment =
                "CREATE TABLE PostComment (_id INTEGER PRIMARY KEY," +
                        "CommentText VARCHAR(500)," +
                        "CommentPublicationDate DATE," +
                        "PostId INT," +
                        "UserId INT," +
                        "FOREIGN KEY (PostId) REFERENCES Post(_id)," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)" +
                        ")";
        String pollComment =
                "CREATE TABLE PollComment (_id INTEGER PRIMARY KEY," +
                        "PollText VARCHAR(500)," +
                        "PollPublicationDate DATE," +
                        "PollId INT," +
                        "UserId INT," +
                        "FOREIGN KEY (PostId) REFERENCES Post(_id)," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)" +
                        ")";
        String propositionComment =
                "CREATE TABLE PropositionComment (_id INTEGER PRIMARY KEY," +
                        "PropositionText VARCHAR(500)," +
                        "CommentPublicationDate DATE," +
                        "PropositionId INT," +
                        "UserId INT," +
                        "FOREIGN KEY (PostId) REFERENCES Post(_id)," +
                        "FOREIGN KEY (UserId) REFERENCES User(_id)" +
                        ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
