package com.opra.alumniportalmanagement;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
   // private TabItem tabItem;
    private ViewPager viewPager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tabLayout = (TabLayout)findViewById(R.id.TabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

//        tabLayout.addTab(tabLayout.newTab().setText(R.string.TAllAlumnus));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.TSearchByCompany));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.TSearchByYear));
//        tabLayout.addTab(tabLayout.newTab().setText(R.string.Report));


        final TabAdapter tabAdapter = new TabAdapter(this,getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(tabAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_logout:
                SharedPreferences.Editor editor = getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE).edit();
                editor.putString("emailId", "");
                editor.putBoolean("isLogin",false);
                editor.apply();
                finish();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity(); // or finish();
    }
}