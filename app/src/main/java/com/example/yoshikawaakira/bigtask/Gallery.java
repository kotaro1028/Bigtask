package com.example.yoshikawaakira.bigtask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Gallery extends AppCompatActivity {

    GridView gv;
   DataBaseHelper dataBaseHelper;
    String userid;

    SharedPreferences sharedPreferences;

    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;
    String result;
    ContactDetailImg contactDetail;

    String path;


    private List<ContactDetailImg> list = new ArrayList<>();

    ImageAdapter imageAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        dataBaseHelper = new DataBaseHelper(Gallery.this);

        final String method = "galleryimg";
        sharedPreferences = getSharedPreferences("logindata", MODE_PRIVATE);
        userid = sharedPreferences.getString("id", null);

        try {
            result = dataBaseHelper.execute(method, userid).get();
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("data");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                path = jo.getString("imgpath");

                contactDetail = new ContactDetailImg();

                contactDetail.setImgpath("http://192.168.1.111/bigtaskphp/"+path);
                contactDetail.setImgname(jo.getString("imgname"));

                list.add(contactDetail);

                count++;

            }



        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        gv = findViewById(R.id.grid);
        imageAdapter = new ImageAdapter(Gallery.this,R.layout.imglist,list);
        gv.setAdapter(imageAdapter);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try {
                    JSONObject jo;
                    jo = jsonArray.getJSONObject(position);
                    path = jo.getString("imgpath");
//                    Toast.makeText(Gallery.this, path, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(),Full.class);
                    intent.putExtra("img",path);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        });
        gv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent1 = new Intent(getApplicationContext(),Home.class);
                startActivity(intent1);
                return true;
            }
        });

    }
}

