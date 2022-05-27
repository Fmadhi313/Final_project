package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * Created by Mitch on 2016-05-13.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "School.db";
    public static final String TABLE_NAME = "Students";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_FIRSTNAME = "FIRSTNAME";
    public static final String COLUMN_SURNAME = "SURNAME";
    public static final String COLUMN_FATHERSNAME = "FATHERSNAME";
    public static final String COLUMN_NATIONALID = "NATIONALID";
    public static final String COLUMN_DATAEOFBIRTH = "DATAEOFBIRTH";
    public static final String COLUMN_GENDER = "GENDER";

    /* Constructor */
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    /* Code runs automatically when the dB is created */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +
                " (ID INTEGER PRIMARY KEY, " +
                " FIRSTNAME TEXT,SURNAME TEXT,FATHERSNAME TEXT,NATIONALID TEXT,DATAEOFBIRTH TEXT,GENDER TEXT)";
        db.execSQL(createTable);
    }

    /* Every time the dB is updated (or upgraded) */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /* Basic function to add data. REMEMBER: The fields
       here, must be in accordance with those in
       the onCreate method above.
    */
    public boolean addData(String ID, String FName, String Sname, String Father, String Nid, String Dob,String Gender ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, ID);
        contentValues.put(COLUMN_FIRSTNAME, FName );
        contentValues.put(COLUMN_SURNAME, Sname);
        contentValues.put(COLUMN_FATHERSNAME, Father);
        contentValues.put(COLUMN_NATIONALID, Nid);
        contentValues.put(COLUMN_DATAEOFBIRTH, Dob);
        contentValues.put(COLUMN_GENDER, Gender);

        long result = db.insert(TABLE_NAME, null, contentValues);

        //if data are inserted incorrectly, it will return -1
        if(result == -1) {return false;} else {return true;}
    }


    public boolean deleteData(String ID) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=" + ID, null) > 0;
    }
    public Cursor ViewEmployee(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME,null);
        return data;
    }
    public boolean Update(String ID, String FName, String Sname, String Father, String Nid, String Dob,String Gender ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ID, ID);
        contentValues.put(COLUMN_FIRSTNAME, FName );
        contentValues.put(COLUMN_SURNAME, Sname);
        contentValues.put(COLUMN_FATHERSNAME, Father);
        contentValues.put(COLUMN_NATIONALID, Nid);
        contentValues.put(COLUMN_DATAEOFBIRTH, Dob);
        contentValues.put(COLUMN_GENDER, Gender);

        db.update(TABLE_NAME,contentValues,"ID="+ID,new String[]{});

        return true;
    }
}
