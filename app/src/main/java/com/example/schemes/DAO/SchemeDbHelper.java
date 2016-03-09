package com.example.schemes.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LNJPC on 04-03-2016.
 */
public class SchemeDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 5;

    private static final String DATABASE_NAME = "scheme.db";

    private static SchemeDbHelper mSchemeDbHelper ;


    public static synchronized SchemeDbHelper getInstance (Context context){

        if(mSchemeDbHelper == null) {
            mSchemeDbHelper = new SchemeDbHelper(context.getApplicationContext());
        }
        return mSchemeDbHelper;
    }


 public SchemeDbHelper (Context context) {
     super(context,DATABASE_NAME,null,DATABASE_VERSION);
 }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_SCHEME_TABLE = "CREATE TABLE " + Contract.Scheme.TABLE_NAME + " ("+
                Contract.Scheme.COLUMN_SCHEME_ID + " INTEGER PRIMARY KEY, " +
                Contract.Scheme.COLUMN_SCHEME + " TEXT, " +
                Contract.Scheme.COLUMN_DRUG_ID + " INTEGER NOT NULL, " +
                Contract.Scheme.COLUMN_DRUG_NAME + " TEXT NOT NULL, " +
                Contract.Scheme.COLUMN_BRAND_NAME + " TEXT NOT NULL, " +
                Contract.Scheme.COLUMN_MANUFACTURER + " TEXT NOT NULL, " +
              //  Contract.Scheme.COLUMN_MRP + " TEXT NOT NULL, "+
              //  Contract.Scheme.COLUMN_START_DATE+ "TEXT NOT NULL, "+
                Contract.Scheme.COLUMN_END_DATE+ " TEXT NOT NULL, "+
                Contract.Scheme.COLUMN_IS_FAV + " TEXT NOT NULL " +
                ");";

        db.execSQL(SQL_CREATE_SCHEME_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.Scheme.TABLE_NAME);
        onCreate(db);
    }
}
