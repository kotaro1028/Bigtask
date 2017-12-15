package com.example.yoshikawaakira.bigtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Full extends AppCompatActivity {

    ImageView imageView;

    ContactDetailImg contactDetailImg;
    ContactDetail contactDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full);

        imageView = findViewById(R.id.full);
        String path = getIntent().getStringExtra("img");
       // Toast.makeText(this, path, Toast.LENGTH_SHORT).show();
        contactDetailImg = new ContactDetailImg();

        contactDetailImg.setImgpath("http://192.168.1.111/bigtaskphp/"+path);


        Picasso.with(Full.this)
                .load(contactDetailImg.getImgpath())
                .resize(200,200)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Gallery.class);
                startActivity(intent);
            }
        });
    }

}
