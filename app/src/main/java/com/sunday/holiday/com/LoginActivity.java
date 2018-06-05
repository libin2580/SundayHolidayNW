package com.sunday.holiday.com;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.github.kittinunf.fuel.Fuel;
import com.github.kittinunf.fuel.core.FuelError;
import com.nispok.snackbar.Snackbar;
import com.tapadoo.alerter.Alerter;

import org.json.JSONArray;
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

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.edit_usernm)
    EditText editUsernm;
    @BindView(R.id.edt_pass)
    EditText edtPass;
    @BindView(R.id.frgt)
    TextView frgt;
    @BindView(R.id.txt)
    TextView txt;
    @BindView(R.id.txtregister)
    TextView txtregister;
    @BindView(R.id.redister_lay)
    LinearLayout redisterLay;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.bt_login)
    Button btLogin;
String usernam,pass;
    List<Pair<String, String>> params;
    private String android_id;
    JSONArray array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        ButterKnife.bind(this);
        txtregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(getApplicationContext(), RegistrationActivity.class);
                startActivity(in);
                //finish();
            }
        });
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android_id = Settings.Secure.getString(LoginActivity.this.getContentResolver(),Settings.Secure.ANDROID_ID);

                usernam = editUsernm.getText().toString();
                pass = edtPass.getText().toString();
                if (usernam.matches("") || pass.matches("")) {
                    Alerter.create(LoginActivity.this)
                            .setTitle("Empty Fields")
                            //.setText("Alert text...")
                            .show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    params = new ArrayList<Pair<String, String>>() {{
                        add(new Pair<String, String>("username",usernam));
                        add(new Pair<String, String>("password",pass));
                        add(new Pair<String, String>("device_token",android_id));
                        add(new Pair<String, String>("device","android"));

                    }};
                    Fuel.post(BASE_URL+"loginWebservice.php",params).responseString(new com.github.kittinunf.fuel.core.Handler<String>() {
                        @Override
                        public void success(com.github.kittinunf.fuel.core.Request request, com.github.kittinunf.fuel.core.Response response, String s) {

                            progressBar.setVisibility(View.GONE);
                            try {

                                System.out.println("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>" + s);
                                JSONObject jobj = new JSONObject(s);
                                String status = jobj.getString("status");
                                if(status.equalsIgnoreCase("false")){
                                    String message= jobj.getString("message");
                                    Alerter.create(LoginActivity.this)
                                            .setTitle(message)
                                           // .setText("Alert text...")
                                            .show();
                                }

                                else if(status.equalsIgnoreCase("true")){
                                    JSONObject jsonObj = null;

                                    final String data = jobj.getString("data");
                                    System.out.println("<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>"+data);

                                    jsonObj = new JSONObject(data);

                                    String user_id=jsonObj.getString("userid");
                                    String email=jsonObj.getString("email");

                                    SharedPreferences preferences = getApplicationContext().getSharedPreferences("MyPref", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();

                                    editor.putString("user_id", user_id);
                                    editor.putString("email", email);
                                    editor.commit();


                                    final Handler handler = new Handler();
                                    handler.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                            // Do something after 5s = 5000ms
                                            Intent in = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(in);
                                            finish();                                        }
                                    }, 1000);

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