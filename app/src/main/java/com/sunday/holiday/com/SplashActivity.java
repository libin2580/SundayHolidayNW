package com.sunday.holiday.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;

import butterknife.ButterKnife;

/**
 * Created by libin on 5/19/2018.
 */

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ButterKnife.bind(this);


        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity

                SharedPreferences preferencesuser_id = getSharedPreferences("MyPref", MODE_PRIVATE);
                String   user_id = preferencesuser_id.getString("user_id", null);

                if (user_id != null) {
                    Intent i = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(i);

                    finish();
                    System.out.println("userid" + user_id);
                }else {
                    Intent i = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(i);

                    finish();
                }
            }
        }, SPLASH_TIME_OUT);




    }
    }
