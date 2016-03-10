package com.example.schemes.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.schemes.R;
import com.example.schemes.model.Scheme;
import com.example.schemes.nao.FetchSchemeData;

import java.util.ArrayList;
import java.util.Collections;


/**
 * Created by LNJPC on 03-03-2016.
 */
public class HomeActivity extends AppCompatActivity {

    private ArrayList<Scheme> mArrayList ;
    private SchemeListFragment.SchemeAdapter mSchemeAdapter ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Toolbar mtoolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mtoolbar);

        ViewPager mViewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentAdapter mAdapter = new FragmentAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);

    }

    public void setSchemeArrayList (ArrayList<Scheme> schemeArrayList){
        mArrayList = schemeArrayList;
    }

    public void setSchemeAdapter (SchemeListFragment.SchemeAdapter schemeAdapter){
        mSchemeAdapter = schemeAdapter;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case (R.id.sortbylastdate_asc):
                sortAscentding();
                break;
            case (R.id.sortbylastdate_desc):
                sortDescending();
                break;
            case (R.id.refresh):
                new FetchSchemeData(this).execute();
                break;
        }
        return true;
    }

    private void sortDescending() {

       Collections.sort(mArrayList, Scheme.EndDateDescComparator);
        mSchemeAdapter.notifyDataSetChanged();
    }

    private void sortAscentding() {

        Collections.sort(mArrayList,Scheme.EndDateAscComparator);
        mSchemeAdapter.notifyDataSetChanged();
    }

    private static class FragmentAdapter extends FragmentPagerAdapter {

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return new SchemeListFragment();
        }

        @Override
        public int getCount() {
            return 1;
        }
    }
}
