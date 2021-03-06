package com.example.notes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String TABLE_NAME="notestable";
    private static final String COL_ID="id";
    private static final String COL_TITLE="notes_title";
    private static final String COL_NOTE="notes_note";

    private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+"("+COL_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+COL_TITLE+
            " TEXT,"+COL_NOTE+" TEXT)";



    public DBHelper(@Nullable Context context) {
        super(context, "notes.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insertDataToDatabase(Info info,SQLiteDatabase database){
        ContentValues cv=new ContentValues();
        cv.put(COL_TITLE,info.iTitle);
        cv.put(COL_NOTE,info.iNote);
        database.insert(TABLE_NAME, null,cv);
    }

    public ArrayList<Info> getNotesInfoFromDatabase(SQLiteDatabase database){
        ArrayList<Info> notesList = new ArrayList<>();

        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME,null);

        if(cursor.moveToFirst()){
            do{
                Info newNotesInfo=new Info();
                newNotesInfo.id=cursor.getInt(cursor.getColumnIndex(COL_ID));
                newNotesInfo.iTitle=cursor.getString(cursor.getColumnIndex(COL_TITLE));
                newNotesInfo.iNote=cursor.getString(cursor.getColumnIndex(COL_NOTE));

                notesList.add(newNotesInfo);

            }while(cursor.moveToNext());
        }
        cursor.close();

        return notesList;
    }

    public void deleteNoteData(SQLiteDatabase database,Info info){
        database.delete(TABLE_NAME,COL_ID+"="+info.id,null);

    }

    public void updateNoteData(SQLiteDatabase database,Info info){
        ContentValues cv= new ContentValues();
        cv.put(COL_TITLE,info.iTitle);
        cv.put(COL_NOTE,info.iNote);

        database.update(TABLE_NAME,cv,COL_ID+"="+info.id,null);
    }
}
