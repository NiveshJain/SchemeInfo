package com.example.schemes.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.schemes.model.Scheme;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by LNJPC on 04-03-2016.
 */
public class SchemeDAO {


    SchemeDbHelper mSchemeDbHelper ;

    public  SchemeDAO (Context context)
    {
        mSchemeDbHelper = SchemeDbHelper.getInstance(context);
    }

    public  long insertScheme (Scheme scheme) {

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        ContentValues contentValues = new ContentValues();
        if (scheme != null) {
            contentValues.put(Contract.Scheme.COLUMN_SCHEME_ID,scheme.getScheme_id());
            //   contentValues.put(Contract.Scheme.COLUMN_MRP,scheme.getMrp());
            //   contentValues.put(Contract.Scheme.COLUMN_START_DATE,scheme.getStartDate());
            contentValues.put(Contract.Scheme.COLUMN_SCHEME,scheme.getScheme());
            contentValues.put(Contract.Scheme.COLUMN_DRUG_ID,scheme.getDrugId());
            contentValues.put(Contract.Scheme.COLUMN_DRUG_NAME,scheme.getName());
            contentValues.put(Contract.Scheme.COLUMN_BRAND_NAME,scheme.getBrandName());
            contentValues.put(Contract.Scheme.COLUMN_MANUFACTURER,scheme.getManufacturer());
            contentValues.put(Contract.Scheme.COLUMN_END_DATE,simpleDateFormat.format(scheme.getEndDate()));
            contentValues.put(Contract.Scheme.COLUMN_IS_FAV,scheme.getIs_fav());

            return mSchemeDbHelper.getWritableDatabase().insertWithOnConflict(Contract.Scheme.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);

        }
        else
        {Log.d(getClass().toString(),"schemeObjectNull") ;
            return -1;
        }
        //Code for avoiding duplicate entries , but JsonObject's key "scheme" is parsing as null.
     /*  ArrayList<Scheme> schemeArrayList =  this.getAllSchemes();
        if(!schemeArrayList.isEmpty()) {
            for (int i = 0; i < schemeArrayList.size(); i++) {
               int drugId =  schemeArrayList.get(i).getDrugId();
               String schemeString =  schemeArrayList.get(i).getScheme();
                if(!(scheme.getScheme().equals(schemeString))&& scheme.getDrugId() != drugId) {
                    return mSchemeDbHelper.getWritableDatabase().insertWithOnConflict(Contract.Scheme.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
                }
            }
        }

        else
            return mSchemeDbHelper.getWritableDatabase().insertWithOnConflict(Contract.Scheme.TABLE_NAME, null, contentValues, SQLiteDatabase.CONFLICT_IGNORE);
*/

    }

    public ArrayList<Scheme> getAllSchemes () throws ParseException {

        ArrayList<Scheme> arrayList = new ArrayList<Scheme>();

        final String query = "SELECT * FROM " + Contract.Scheme.TABLE_NAME + ";";

       Cursor cursor = mSchemeDbHelper.getReadableDatabase().rawQuery(query,null);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        if (cursor.getCount()> 0 && cursor.moveToFirst()) {

            do{

                Scheme scheme = new Scheme();
                scheme.setBrandName(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_BRAND_NAME)));
                scheme.setName(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_DRUG_NAME)));
                scheme.setDrugId(cursor.getInt(cursor.getColumnIndex(Contract.Scheme.COLUMN_DRUG_ID)));
                scheme.setManufacturer(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_MANUFACTURER)));
            //    scheme.setMrp(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_MRP)));
                scheme.setScheme(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_SCHEME)));
           //     scheme.setStartDate(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_START_DATE)));
                scheme.setEndDate(simpleDateFormat.parse(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_END_DATE))));
                scheme.setIs_fav(cursor.getString(cursor.getColumnIndex(Contract.Scheme.COLUMN_IS_FAV)));
                arrayList.add(scheme);
            }while (cursor.moveToNext());

            cursor.close();
            return arrayList;
        }

        else {

             return null;
        }


    }

}
