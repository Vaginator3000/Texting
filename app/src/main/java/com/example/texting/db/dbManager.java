package com.example.texting.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class dbManager {
    private Context context;
    private MyDbHelper myDbHelper;
    private SQLiteDatabase db;


    public dbManager(Context context) {
        this.context = context;
        myDbHelper = new MyDbHelper(context);
    }

    public void openDb() {
        db = myDbHelper.getWritableDatabase();
    }

    public void insertToDb(String title, String date, String disc) {
        ContentValues cv = new ContentValues();
        cv.put(MyConstants.TITLE, title);
        cv.put(MyConstants.DATE, date);
        cv.put(MyConstants.DISC, disc);

        db.insert(MyConstants.TABLE_NAME, null, cv);
    }

    public List<containItem> readFromDb() {
        List<String> titleList = new ArrayList<>();
        List<String> dateList = new ArrayList<>();
        List<containItem> itemList = new ArrayList<>();
        Cursor cursor = db.query(MyConstants.TABLE_NAME, null, null,
                null, null, null, null);

        while (cursor.moveToNext()) {
            containItem item;
            String title = cursor.getString(cursor.getColumnIndex(MyConstants.TITLE));
            String date = cursor.getString(cursor.getColumnIndex(MyConstants.DATE));

            if (date.equals("")) item = new containItem(title);
            else item = new containItem(title, date);
            itemList.add(item);
        }

        cursor.close();
        return itemList;
    }

    public void closeDb() {
        myDbHelper.close();
    }
}
