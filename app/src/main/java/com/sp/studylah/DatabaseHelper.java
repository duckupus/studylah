package com.sp.studylah;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "assignments.db";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DatabaseContract.AssignmentEntry.TABLE_NAME + " (" +
                    DatabaseContract.AssignmentEntry._ID + " INTEGER PRIMARY KEY," +
                    DatabaseContract.AssignmentEntry.COLUMN_TYPE + " TEXT," +
                    DatabaseContract.AssignmentEntry.COLUMN_DATE + " INTEGER," +
                    DatabaseContract.AssignmentEntry.COLUMN_DESCRIPTION + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + DatabaseContract.AssignmentEntry.TABLE_NAME;

    public DatabaseHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
