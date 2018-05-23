package com.example.n009654.aidldemoserver;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.n009654.aidldemoserver.fragment.OneFragment;
import com.example.n009654.aidldemoserver.fragment.SecondFragment;
import com.example.n009654.aidldemoserver.fragment.TwoFragment;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements BlankFragment.OnFragmentInteractionListener,OneFragment.OnFragmentInteractionListener,TwoFragment.OnFragmentInteractionListener,SecondFragment.OnFragmentInteractionListener {

    private ViewPager viewpager;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        list=new ArrayList<Fragment>();
        list.add(new OneFragment());
        list.add(new TwoFragment());
        list.add(new SecondFragment());

        initView();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                startActivity(intent);
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
    }

    private void initView() {
        viewpager = (ViewPager) findViewById(R.id.view_pager);
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        viewpager.setAdapter(adapter);
        TabLayout tabs = (TabLayout) findViewById(R.id.tablayout);
        tabs.setSelectedTabIndicatorColor(getResources().getColor(R.color.colorAccent));
        tabs.addTab(tabs.newTab().setText("Tab1"));
        tabs.addTab(tabs.newTab().setText("Tab2"));
        tabs.addTab(tabs.newTab().setText("Tab3"));
        tabs.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewpager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    class MyAdapter extends FragmentPagerAdapter{

           public MyAdapter(FragmentManager fm) {
               super(fm);
           }

           @Override
        public Fragment getItem(int position) {
        return list.get(position);
       }

       @Override
       public int getCount() {
        return list.size();
       }
}
}
