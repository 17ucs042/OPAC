package com.appsaga.opac1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelperKey extends SQLiteOpenHelper {
    public static final String DATABASE_NAME=" key.db";
    public static final String TABLE_NAME="Key_table";
    public static final String COL_1="name";
    public static final String COL_2=" ";

    public static final String COL_3="key";

    public DatabaseHelperKey(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME+" (name TEXT ,date TEXT,key TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public  boolean insertData(String name,String date ,String key)
    {
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,key);
        long result=db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
        {return false;}
        else
            return  true;
    }
    public boolean update(String name,String date,String description){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL_1,name);
        contentValues.put(COL_2,date);
        contentValues.put(COL_3,description);


        db.update(TABLE_NAME,contentValues,"name = ?",new String[] {name});
        return  true;
    }
    public Cursor getAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res= db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }
}
