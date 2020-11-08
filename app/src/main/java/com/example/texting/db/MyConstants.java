package com.example.texting.db;

public class MyConstants {
    public static final String TABLE_NAME = "my_table";
    public static final String _ID = "_id";
    public static final String TITLE = "title";
    public static final String DATE = "date";
    public static final String DISC = "disc";
    public static final String DB_NAME = "my_db.db";
    public static final int DB_VERSION = 1;

    public static final String TABLE_STRUCT = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + _ID + " INTEGER PRIMARY KEY," + TITLE + " TEXT," +
            DATE + " TEXT," + DISC + " TEXT)";

    public static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;
}

