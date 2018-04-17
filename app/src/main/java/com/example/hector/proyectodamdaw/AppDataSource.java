package com.example.hector.proyectodamdaw;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * Created by Ivan on 11/04/2018.
 */

public class AppDataSource {

    //Selects constants

    private AppHelper databaseHelper;
    private SQLiteDatabase  databaseWritable, databaseReadable;

    public AppDataSource(Context ctx) {
        databaseHelper = new AppHelper(ctx);
        open();
    }

    protected void finalize(){
        databaseReadable.close();
        databaseWritable.close();
    }

    private void open(){
        databaseWritable = databaseHelper.getWritableDatabase();
        databaseReadable = databaseHelper.getReadableDatabase();
    }
}
