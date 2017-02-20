package com.mohitpaudel.sqlitedatabasehelper.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.mohitpaudel.sqlitedatabasehelper.MainActivity;
import com.mohitpaudel.sqlitedatabasehelper.model.MyWish;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by equinoxmohit on 2/20/17.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final String TAG = "DatabaseHandler";
    private final ArrayList<MyWish> wishList=new ArrayList<>();

    public DatabaseHandler(Context context) {
        super(context,Constants.DATABASE_NAME,null,Constants.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //creating  table
        String CREATE_WISHES_TABLE="CREATE TABLE " +Constants.TABLE_NAME +
                "(" + Constants.KEY_ID + " INTEGER PRIMARY KEY, " +Constants.TITLE_NAME
                +" TEXT, " + Constants.CONTENT_NAME +" TEXT, " +Constants.DATE_NAME +" LONG);";
        //executing the sql command
        db.execSQL(CREATE_WISHES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" +Constants.TABLE_NAME);

        //create a new table
        onCreate(db);
    }

    //add wishes
    public void addWishes(MyWish wish){
        SQLiteDatabase db=this.getWritableDatabase();
        //datastructure like hashmap,key-value pair
        ContentValues values=new ContentValues();
        values.put(Constants.TITLE_NAME,wish.getTitle());
        values.put(Constants.CONTENT_NAME,wish.getContent());

        //asking the system to add the current date
        values.put(Constants.DATE_NAME,java.lang.System.currentTimeMillis());

        //inserting the values to database
        db.insert(Constants.TABLE_NAME,null,values);

        Log.d(TAG, "addWishes: Wishes added in db");

        db.close();
    }

    //get wishes
    public ArrayList<MyWish> getWishes(){
        String selectQuery ="SELECT * FROM " +Constants.TABLE_NAME;

        SQLiteDatabase db=this.getReadableDatabase();

        Cursor cursor=db.query(Constants.TABLE_NAME,new String[]{Constants.KEY_ID,
        Constants.TITLE_NAME,Constants.CONTENT_NAME, Constants.DATE_NAME},null,null,null,null, Constants.DATE_NAME +"DESC");

        //looping through the cursor
        if(cursor.moveToFirst()){
            do{
                MyWish wish=new MyWish();
                wish.setTitle(cursor.getString(cursor.getColumnIndex(Constants.TITLE_NAME)));
                wish.setContent(cursor.getString(cursor.getColumnIndex(Constants.CONTENT_NAME)));

                //getting the date
                java.text.DateFormat dateFormat=java.text.DateFormat.getDateInstance();
                String dateData=dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(Constants.DATE_NAME))).getTime());

                wish.setRecordDate(dateData);
                wishList.add(wish); //adding to the arraylist
            }while (cursor.moveToNext());
        }
        return wishList;
    }

}
