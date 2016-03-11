package com.example.schemes.nao;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.sqlite.SQLiteException;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.schemes.DAO.SchemeDAO;
import com.example.schemes.R;
import com.example.schemes.model.Scheme;
import com.example.schemes.ui.HomeActivity;
import com.example.schemes.ui.SchemeListFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;


/**
 * Created by LNJPC on 04-03-2016.
 */
public class FetchSchemeData extends AsyncTask<Void, Void, ArrayList<Scheme>> {

    private static final String link = "http://mobile-dev.letsreap.com/mobile-api/v1/3eba480b13642409957cdde42e315afbde4b312ba259717aa0bdc09944deee3c0799061800b7854a6252b41895a26a0128e24c0b8efd56c571cc6f8bf7c0f5d1/schemes?store-pincode=411045";

    private  Activity mActivity ;
    private static ProgressDialog progressDialog ;

    public  FetchSchemeData ( Activity activity){
        mActivity = activity;
    }


   public   int checkNetwork (){

        ConnectivityManager cm = (ConnectivityManager) mActivity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) return 0 ;
        else return 1 ;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if (checkNetwork() == 0) {
            progressDialog = new ProgressDialog(mActivity);
            progressDialog.setMessage("Fetching");
            progressDialog.setTitle("Search Schemes");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.show();
        }
        else
        {
            this.cancel(true);
            Toast toast = Toast.makeText(mActivity,"Check Network Connection",Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected ArrayList<Scheme> doInBackground(Void... params) {




        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            httpURLConnection.connect();

           InputStream inputStream = httpURLConnection.getInputStream();
            if (inputStream == null) return  null;
            else {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                String line = bufferedReader.readLine();
                StringBuffer schemeJsonString = new StringBuffer(line) ;


                try {

          long rowsid =   insertSchemeDataFromJSON(schemeJsonString.toString(), mActivity);
                }catch (JSONException | SQLiteException | ParseException e) {
                    Log.d("CorrectDate","***********");
                    e.printStackTrace();
                }
            }

            try {
                    SchemeDAO schemeDAO = new SchemeDAO(mActivity);
                 return   schemeDAO.getAllSchemes();

            }catch (SQLiteException | ParseException e) {e.printStackTrace();}

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    long insertSchemeDataFromJSON(String jsonString, Context context) throws JSONException, SQLiteException, ParseException {

        int rows = 0 ;
        int schemeIdCount = 0;
        SchemeDAO schemeDAO ;
        JSONObject jsonObject = new JSONObject(jsonString);
        String status = jsonObject.getString("status");
        if (status.equals("ok")) {
            JSONObject jsonDataObject = new JSONObject();
            jsonDataObject = jsonObject.getJSONObject("data");

            SimpleDateFormat dateFormat =  new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());

           Iterator<String> iterator = jsonDataObject.keys();
            JSONArray jsonDataArray = jsonDataObject.names();


            while(iterator.hasNext()){

                JSONObject schemeJsonObject = jsonDataObject.getJSONObject(iterator.next());
                Scheme mscheme = new Scheme( jsonDataArray.getInt(schemeIdCount),schemeJsonObject.getString("brand-name"), schemeJsonObject.getInt("drug-id"),
                        schemeJsonObject.getString("name"), schemeJsonObject.getString("manufacturer"),
                        schemeJsonObject.getString("scheme"), new Date(dateFormat.parse(schemeJsonObject.getString("end-date")).getTime()),
                        schemeJsonObject.getString("is-fav"));

                schemeDAO = new SchemeDAO(context);

               long rowId = schemeDAO.insertScheme(mscheme);
                schemeIdCount++;
                if (rowId == -1) Log.d(getClass().toString(),"insertFailed");
                else rows++;
            }
        }
        return  rows;
    }


    @Override
    protected void onPostExecute(ArrayList<Scheme> schemeArrayList) {

        if(schemeArrayList !=null) {
            SchemeListFragment.SchemeAdapter schemeAdapter = new SchemeListFragment.SchemeAdapter(mActivity, R.layout.schemelist_item, schemeArrayList);
            ((ListView) mActivity.findViewById(R.id.list)).setAdapter(schemeAdapter);
            progressDialog.dismiss();
            ((HomeActivity) mActivity).setSchemeArrayList(schemeArrayList);
            ((HomeActivity) mActivity).setSchemeAdapter(schemeAdapter);

        }

    }

}
