package com.example.yoshikawaakira.bigtask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Yourfriends extends AppCompatActivity {
    TextView fname,femail,fphone,fdob;
    ImageView genderimg,icon;

    Button back;

    String gender;
    String path;

    ContactDetail contactDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yourfriends);
        fname = findViewById(R.id.name);
        femail = findViewById(R.id.email);
        fphone = findViewById(R.id.phone);
        fdob = findViewById(R.id.dob);
        genderimg = findViewById(R.id.gender);
        icon = findViewById(R.id.friendicon);

        back = findViewById(R.id.back);
        fname.setText(getIntent().getExtras().getString("name"));
        femail.setText(getIntent().getExtras().getString("email"));
        fphone.setText(getIntent().getExtras().getString("phone"));
        fdob.setText(getIntent().getExtras().getString("dob"));

        //Toast.makeText(this, "http://192.168.1.111/bigtaskphp/"+getIntent().getExtras().getString("path"), Toast.LENGTH_SHORT).show();
      // contactDetail.setPath("http://192.168.1.111/bigtaskphp/"+getIntent().getExtras().getString("path"));
        path = "http://192.168.1.111/bigtaskphp/"+getIntent().getExtras().getString("path");

        gender = getIntent().getExtras().getString("gender");
        if(gender.equals("Male")){
            genderimg.setImageResource(R.drawable.otoko);
        }else{
            genderimg.setImageResource(R.drawable.onna);
        }
        Picasso.with(this)
                .load(path)
                .resize(200,200)
                .into(icon);

    }
}
