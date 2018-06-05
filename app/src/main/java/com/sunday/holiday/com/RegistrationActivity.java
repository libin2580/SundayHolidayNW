package com.sunday.holiday.com;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.tapadoo.alerter.Alerter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kotlin.Pair;

import static com.sunday.holiday.com.Constants.BASE_URL;

/**
 * Created by libin on 5/19/2018.
 */

public class RegistrationActivity extends AppCompatActivity {
    @BindView(R.id.close)
    ImageView close;
    @BindView(R.id.textView3)
    TextView textView3;
    @BindView(R.id.edt_username)
    EditText edtUsername;
    @BindView(R.id.edt_email)
    EditText edtEmail;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.edt_cnfrmpass)
    EditText edtCnfrmpass;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.bt_signup)
    Button btSignup;
    String usernam,pass,pass2;

    List<Pair<String, String>> params;
    private String android_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ButterKnife.bind(this);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        btSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android_id = Settings.Secure.getString(RegistrationActivity.this.getContentResolver(),Settings.Secure.ANDROID_ID);

                usernam = edtUsername.getText().toString();
                pass = edtPass.getText().toString();
                pass2=edtCnfrmpass.getText().toString();
                if (usernam.matches("") || pass.matches("")) {
                    Alerter.create(RegistrationActivity.this)
                        .setTitle("Empty Fields")
                        //.setText("Alert text...")
                        .show();
            }
                else if(!pass.equalsIgnoreCase(pass2)){
                    Alerter.create(RegistrationActivity.this)
                            .setTitle("Password do not match")
                            //.setText("Alert text...")
                            .show();
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    params = new ArrayList<Pair<String, String>>() {{
                        add(new Pair<String, String>("username",usernam));
                        add(new Pair<String, String>("password",pass));
                        add(new Pair<String, String>("device_token",android_id));
                        add(new Pair<String, String>("device", "android"));

                    }};
                    Fuel.post(BASE_URL+"signUpAPI.php",params).responseString(new com.github.kittinunf.fuel.core.Handler<String>() {
                        @Override
                        public void success(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, String s) {

                            progressBar.setVisibility(View.GONE);
                            try {

                                System.out.println("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>" + s);
                                JSONObject jobj = new JSONObject(s);
                                String status = jobj.getString("status");
                                if(status.equalsIgnoreCase("false")){
                                    String message= jobj.getString("message");
                                    Alerter.create(RegistrationActivity.this)
                                            .setTitle(message)
                                            // .setText("Alert text...")
                                            .show();
                                }
                                else if(status.equalsIgnoreCase("true")){
                                    String data= jobj.getString("data");

                                    Alerter.create(RegistrationActivity.this)
                                            .setTitle(data)
                                            // .setText("Alert text...")
                                            .show();
                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            Intent in = new Intent(getApplicationContext(), LoginActivity.class);
                                            startActivity(in);
                                            finish();                                        }
                                    }, 2000);

                                }



                            } catch(JSONException e){
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void failure(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, FuelError fuelError) {

                        }
                    });

                    /*Intent in = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(in);
                    finish();*/
                }
            }
        });

    }
}
