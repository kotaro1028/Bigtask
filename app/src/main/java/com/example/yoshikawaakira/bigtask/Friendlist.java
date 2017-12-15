package com.example.yoshikawaakira.bigtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class Friendlist extends AppCompatActivity {
    ListView listView;
    private java.util.List<ContactDetail> list = new ArrayList<>();
    DataBaseHelper dataBaseHelper;

    Button back;


    String json_string;
    JSONObject jsonObject;
    JSONArray jsonArray;

    String path;
    String name;
    String email;
    String phone;
    String gender;
    String dob;


    String id;
    ContactAdapterList adapter;
   // int idint;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendlist);
        listView = findViewById(R.id.list);
        json_string =  getIntent().getExtras().getString("result");
        back = findViewById(R.id.back);

//        Toast.makeText(this, json_string, Toast.LENGTH_SHORT).show();
        showContacts();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(),Home.class);
                startActivity(intent3);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    JSONObject jo;
                    jo = jsonArray.getJSONObject(position);
                    name = jo.getString("name");
                    email = jo.getString("email");
                    path = jo.getString("image_path");
                    phone = jo.getString("phone");
                    gender = jo.getString("gender");
                    dob = jo.getString("dob");

                    Intent intent = new Intent(getApplicationContext(), Yourfriends.class);
                    intent.putExtra("name", name);
                    intent.putExtra("email", email);
                    intent.putExtra("path", path);
                    intent.putExtra("phone", phone);
                    intent.putExtra("gender", gender);
                    intent.putExtra("dob", dob);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();


                }
            }
        });



    }

    private void showContacts() {
            if(json_string.equals("")){
                Toast.makeText(getApplicationContext(),"NO Data",Toast.LENGTH_SHORT).show();
            }else{
                try {
                    jsonObject = new JSONObject(json_string);
                    jsonArray = jsonObject.getJSONArray("data");
                    int count = 0;

                    while (count<jsonArray.length()){
                        JSONObject jo = jsonArray.getJSONObject(count);
                        path = jo.getString("image_path") ;
                        name =jo.getString("name");

                        ContactDetail detail = new ContactDetail();

                        detail.setPath("http://192.168.1.111/bigtaskphp/"+path);
                        detail.setName(name);

                        list.add(detail);

                        count++;
                    }
                    adapter = new ContactAdapterList(Friendlist.this,R.layout.friendlist,list);
                    listView.setAdapter(adapter);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


    }
}
