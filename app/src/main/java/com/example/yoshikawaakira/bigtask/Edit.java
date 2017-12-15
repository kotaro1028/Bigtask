package com.example.yoshikawaakira.bigtask;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;

public class Edit extends AppCompatActivity {
    EditText nameEX,emailEX,passEX,phoneEX;

    Button editbtn,backbtn;

    String id;
    String name;
    String email;
    String pass;
    String phone;

    DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        nameEX = findViewById(R.id.name);
        emailEX = findViewById(R.id.email);
        passEX = findViewById(R.id.pass);
        phoneEX = findViewById(R.id.phone);

        editbtn = findViewById(R.id.edit);
        backbtn = findViewById(R.id.back);
        id = getIntent().getStringExtra("id");
        nameEX.setText(getIntent().getStringExtra("name"));
        emailEX.setText(getIntent().getStringExtra("email"));
        passEX.setText(getIntent().getStringExtra("pass"));
        phoneEX.setText(getIntent().getStringExtra("phone"));

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(),Home.class);
                startActivity(intent3);
            }
        });
       // Toast.makeText(this, getIntent().getStringExtra("id"), Toast.LENGTH_SHORT).show();
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = nameEX.getText().toString();
                email = emailEX.getText().toString();
                pass = passEX.getText().toString();
                phone = phoneEX.getText().toString();
                String method = "edit";
//                Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),email,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),pass,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),phone,Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(),method,Toast.LENGTH_SHORT).show();
                dataBaseHelper = new DataBaseHelper(Edit.this);


                if(name.equals("")||email.equals("")||pass.equals("")||phone.equals("")){
                    Toast.makeText(Edit.this, "What are you doing??", Toast.LENGTH_SHORT).show();
                }else{

                    try {
                       // Toast.makeText(Edit.this, method+name+email+pass+phone+id, Toast.LENGTH_SHORT).show();
                        Toast.makeText(Edit.this, dataBaseHelper.execute(method, name, email, pass, phone, id).get(), Toast.LENGTH_SHORT).show();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

    }
}
