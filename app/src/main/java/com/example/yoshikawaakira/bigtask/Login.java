package com.example.yoshikawaakira.bigtask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class Login extends AppCompatActivity {
    EditText nameed,passed;
    Button login;

    DataBaseHelper dataBaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        nameed = findViewById(R.id.name);
        passed = findViewById(R.id.pass);

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper = new DataBaseHelper(Login.this);

                String name = nameed.getText().toString();
                String pass = passed.getText().toString();

                if(!name.equals("") && !pass.equals("")){
                    String method = "login";
                    dataBaseHelper.execute(method,name,pass);

                }else{
                    Toast.makeText(getApplicationContext(),"put any information your friends",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
