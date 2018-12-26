package com.piyushjagtap.www.localdatabaseproject.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static String TAG = "DatabaseHelper";

    //Database version
    private  static final int DATABASE_VERSION = 5;

    //Database name
    private static final String DATABASE_NAME = "notes_db";

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    //Variables for database query
    public static final String TABLE_NAME = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NOTE = "note";
    public static final String COLUMN_TIMESTAMP = "timestamp";
    public static final String CREATE_TABLE =  " CREATE TABLE " + TABLE_NAME + "("
            +COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            +COLUMN_NOTE + " TEXT,"
            +COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
            + ")";

    //Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreateCalled: " + CREATE_TABLE);
        //create notes table
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgradeCalled: ");
        Log.d(TAG, "oldVersion: " + oldVersion + "newVersion" + newVersion);

    }

    public long insertNote(String note){
        //get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        //'id' and 'timestamp' will be inserted automatically
        values.put(COLUMN_NOTE,note);

        //insert row
        long id = db.insert(TABLE_NAME,null,values);

        //close db connection
        db.close();

        //return newly inserted row id
        return id;
    }
}
