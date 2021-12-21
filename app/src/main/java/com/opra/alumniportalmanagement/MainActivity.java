package com.opra.alumniportalmanagement;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


public class MainActivity extends AppCompatActivity {


    String URL = "http://192.168.43.111/ALM/";
    JSONParser jsonParser = new JSONParser();
    String email;
    Boolean isLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        getSupportActionBar().hide(); // hide the title bar

        setContentView(R.layout.activity_main);

        SharedPreferences loginPrefs = getSharedPreferences("LoginPrefsFile", Context.MODE_PRIVATE);
        email = loginPrefs.getString("emailId", "NA");
        isLogin = loginPrefs.getBoolean("isLogin", false);

       // if (!isLogin) {
            Fragment mFragment = null;
            mFragment = new LoginFragment();
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, mFragment).commit();
//        }
//        else
//        {
//            Intent HomeActivity = new Intent(getApplication(),HomeActivity);
//        }
    }
}

