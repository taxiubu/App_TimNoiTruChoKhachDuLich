package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.myapplication.Model.Residence;

import java.util.ArrayList;
import java.util.List;

public class SQLFavoriteItem extends SQLiteOpenHelper {
    private static final String TAG = "SQLFavoriteItem";
    static final String DB_NAME_TABLE = "SQLFavoriteItem";
    static final String DB_NAME= "SQLFavoriteItem.db";
    static final int VERSION= 1;
    SQLiteDatabase sqLiteDatabase;
    ContentValues contentValues;
    Cursor cursor;
    public SQLFavoriteItem(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQLFavoriteItem = "CREATE TABLE SQLFavoriteItem ( "+
                "name TEXT," +
                "type TEXT," +
                "address TEXT," +
                "price TEXT," +
                "rate TEXT," +
                "rateStar TEXT," +
                "imageLink TEXT," +
                "webLink TEXT )";

        db.execSQL(SQLFavoriteItem);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion!= newVersion){
            sqLiteDatabase.execSQL("drop table if exists "+ DB_NAME_TABLE);
            onCreate(db);
        }
    }
    public void insertItem(Residence residence){
        sqLiteDatabase = getWritableDatabase();
        contentValues= new ContentValues();
        contentValues.put("name", residence.getName());
        contentValues.put("type", residence.getType());
        contentValues.put("address", residence.getAddress());
        contentValues.put("price", residence.getPrice());
        contentValues.put("rate", residence.getRate());
        contentValues.put("rateStar", residence.getRateStar());
        contentValues.put("imageLink", residence.getImageLink());
        contentValues.put("webLink", residence.getWebLink());

        sqLiteDatabase.insert(DB_NAME_TABLE, null, contentValues);
        closeDB();
    }

    public List<Residence> getAllItem(){
        List<Residence> itemSaves= new ArrayList<>();
        Residence itemSave;

        sqLiteDatabase= getReadableDatabase();
        cursor = sqLiteDatabase.query(false, DB_NAME_TABLE, null, null,
                null, null, null, null, null);
        while (cursor.moveToNext()){
            String name= cursor.getString(cursor.getColumnIndex("name"));
            String type= cursor.getString(cursor.getColumnIndex("type"));
            String address= cursor.getString(cursor.getColumnIndex("address"));
            String price= cursor.getString(cursor.getColumnIndex("price"));
            String rate= cursor.getString(cursor.getColumnIndex("rate"));
            String rateStar= cursor.getString(cursor.getColumnIndex("rateStar"));
            String imageLink= cursor.getString(cursor.getColumnIndex("imageLink"));
            String webLink= cursor.getString(cursor.getColumnIndex("webLink"));
            itemSave= new Residence(name,type,address,price,rate,rateStar,imageLink,webLink);
            itemSaves.add(itemSave);
        }
        closeDB();
        return itemSaves;

    }

    public int deleteItem(String name){
        sqLiteDatabase= getWritableDatabase();
        return sqLiteDatabase.delete(DB_NAME_TABLE, "name=?", new String[]{name});
    }
    public boolean deleteAll(){
        int result;
        sqLiteDatabase = getWritableDatabase();
        result=sqLiteDatabase.delete(DB_NAME_TABLE, null, null);
        closeDB();
        if (result==1)
            return true;
        else return false;
    }
    private void closeDB() {
        if (sqLiteDatabase != null) sqLiteDatabase.close();
        if (contentValues != null) contentValues.clear();
        if (cursor != null) cursor.close();
    }
}
