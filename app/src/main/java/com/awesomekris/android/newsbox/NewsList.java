package com.awesomekris.android.newsbox;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.awesomekris.android.newsbox.data.NewsContract;
import com.awesomekris.android.newsbox.sync.NewsBoxSyncAdapter;

import java.util.ArrayList;

public class NewsList extends AppCompatActivity {

    private final static String POSITION = "POSITION";

    TabLayout mTabLayout;
    ArrayList<String> mTabArray = new ArrayList<String>();
    ViewPager mViewPager;
    NewsListFragmentPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsBoxSyncAdapter.initializeSyncAdapter(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Cursor sectionCursor = getContentResolver().query(NewsContract.SectionEntry.CONTENT_URI,null,null,null,null);
        if(sectionCursor.getCount() != 0){
            mTabArray.clear();
            while(sectionCursor.moveToNext()){
                int sectionIndex = sectionCursor.getColumnIndex(NewsContract.SectionEntry.COLUMN_SECTION_ID);
                String sectionName = sectionCursor.getString(sectionIndex);
                mTabArray.add(sectionName);
            }
        }
        sectionCursor.close();

//        Bundle arguments = new Bundle();
//        arguments.putStringArrayList(NewsListFragment.TAG_ARRAY, mTabArray);
//        NewsListFragment fragment = new NewsListFragment();
//        fragment.setArguments(arguments);


        mTabLayout = (TabLayout) findViewById(R.id.section_tab);
        mViewPager = (ViewPager) findViewById(R.id.view_pager);
        mPagerAdapter = new NewsListFragmentPagerAdapter(getSupportFragmentManager(),this, mTabArray);


        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        mTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(POSITION,mTabLayout.getSelectedTabPosition());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mViewPager.setCurrentItem(savedInstanceState.getInt(POSITION));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
