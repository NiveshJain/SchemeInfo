package com.example.schemes.DAO;

import android.net.Uri;

/**
 * Created by LNJPC on 04-03-2016.
 */
public class Contract  {


    public static final String CONTENT_AUTHORITY = "com.example.schemeinfo";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static String PATH_SCHEME = "scheme";


    public static class Scheme {

        public  static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SCHEME).build();


        public  static final String TABLE_NAME = "schemes";

        public static final String COLUMN_SCHEME_ID = "scheme_id";

        public static final String COLUMN_SCHEME = "scheme";

        public  static final String COLUMN_DRUG_ID = "drug_id";

        public static final String COLUMN_BRAND_NAME = "brand_name";

        public  static final String COLUMN_MANUFACTURER = "manufacturer";

        public  static final String COLUMN_DRUG_NAME = "drug_name";

     //   public static final String COLUMN_MRP = "mrp";

     //   public static final String COLUMN_START_DATE = "start_date";

        public static final String COLUMN_END_DATE = "end_date";

        public static final String COLUMN_IS_FAV = "is_fav";


    }
}
