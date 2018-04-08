package com.example.hector.proyectodamdaw;

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
        String comunity =
                "CREATE TABLE Community (_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Name VARCHAR(45)," +
                        "Max_Users INT," +
                        "Media_ID)";
        String post =
                "CREATE TABLE Post(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Title VARCHAR(45)," +
                        "Text VARCHAR(500," +
                        "Publication_Date DATE," +
                        "UserId INT," +
                        "CommunityId INT)";
        String poll =
                "CREATE TABLE Poll(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "Title VARCHAR(45)," +
                        "Text VARCHAR(500," +
                        "Start_Date DATE," +
                        "Finish_Date DATE," +
                        "TotalVotesDone INT," +
                        "UserId INT," +
                        "CommunityId INT)";
        String proposition =
                "CREATE TABLE Proposition(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "propositionTitle VARCHAR(45)," +
                        "propositionText VARCHAR(500," +
                        "propositionVoted INT," +
                        "UserId INT," +
                        "CommunityId INT)";
        String user =
                "CREATE TABLE User(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "UserName VARCHAR(45)," +
                        "UserFirstSurname VARCHAR(45)," +
                        "UserSecondSurname VARCHAR(45)," +
                        ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
