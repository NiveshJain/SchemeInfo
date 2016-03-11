package com.example.schemes.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.schemes.R;
import com.example.schemes.model.Scheme;
import com.example.schemes.nao.FetchSchemeData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by LNJPC on 03-03-2016.
 */
 public class SchemeListFragment extends Fragment   {

    public SchemeListFragment () {}


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        new FetchSchemeData(getActivity()).execute();
        return  inflater.inflate(R.layout.schemelistfragment,container,false);
    }

    public static class SchemeAdapter extends ArrayAdapter<Scheme> {

        private ArrayList<Scheme> mArraySchemeList ;


        public SchemeAdapter(Context context, int resource, ArrayList<Scheme> objects) {
            super(context, resource, objects);
            mArraySchemeList = objects ;
        }


        public ArrayList<Scheme> getmArraySchemeList (){
            return mArraySchemeList;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                 convertView = layoutInflater.inflate(R.layout.schemelist_item, parent, false);
            }
                Scheme scheme = mArraySchemeList.get(position);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                if (scheme != null && convertView !=null) {
                    ( (TextView) convertView.findViewById(R.id.scheme)).setText(scheme.getScheme());
                    ( (TextView) convertView.findViewById(R.id.brand)).setText(scheme.getBrandName());
                   // ( (TextView) convertView.findViewById(R.id.mrp)).setText(scheme.getMrp());
                    ( (TextView) convertView.findViewById(R.id.manufacturer)).setText(scheme.getManufacturer());
                    ((TextView) convertView.findViewById(R.id.end_date)).setText(simpleDateFormat.format(scheme.getEndDate()));

                }
            return convertView;

        }


        @Override
        public int getCount() {
            return super.getCount();
        }
    }



}
