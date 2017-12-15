package com.example.yoshikawaakira.bigtask;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Home extends AppCompatActivity {
    TextView nametv, idtv;
    ImageView imageView;

    Button camerabtn, uploadbtn, gallerybtn, allbtn, editbtn, logout;

    DataBaseHelper dataBaseHelper;

    private static final int REQUEST_IMAGE_CAPTURE_CODE = 100;
    private static final int PERMISSIONS_REQUEST_CAMERA = 200;
    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL = 300;
    private static final int SELECT_PICTURE = 400;

    String method;

    String time;
    Uri fileuri = null;
    String imgname;
    String encoded_string;

    String id;
    String name;
    String email;
    String pass;
    String phone;

    String path;

    JSONObject jsonObject;
    JSONArray jsonArray;

    private ContactDetail contactDetail;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        nametv = findViewById(R.id.name);
        idtv = findViewById(R.id.id);
        imageView = findViewById(R.id.youricon);

        camerabtn = findViewById(R.id.camera);
        uploadbtn = findViewById(R.id.upload);
        gallerybtn = findViewById(R.id.gallery);
        allbtn = findViewById(R.id.all);
        editbtn = findViewById(R.id.edit);
        logout = findViewById(R.id.logout);
        sharedPreferences = getSharedPreferences("logindata",MODE_PRIVATE);
        id = sharedPreferences.getString("id",null);
        dataBaseHelper = new DataBaseHelper(this);
        Toast.makeText(this, id, Toast.LENGTH_SHORT).show();
        String result = null;
        try {
            result = dataBaseHelper.execute("loginuser",id).get();
            contactDetail = new ContactDetail();
            jsonObject = new JSONObject(result);
            jsonArray = jsonObject.getJSONArray("data");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                nametv.setText(jo.getString("name"));
                idtv.setText(jo.getString("id"));
                id = jo.getString("id");
                name = jo.getString("name");
                email = jo.getString("email");
                pass = jo.getString("pass");
                phone = jo.getString("phone");
                path = jo.getString("image_path") ;
                contactDetail.setPath("http://192.168.1.111/bigtaskphp/"+path);


                Picasso.with(Home.this)
                        .load(contactDetail.getPath())
                        .resize(200,200)
                        .into(imageView);
                count++;

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getApplicationContext(),Login.class);
                startActivity(intent3);
            }
        });
        editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(Home.this,Edit.class);
                intent3.putExtra("id",id);
                intent3.putExtra("name",name);
                intent3.putExtra("email",email);
                intent3.putExtra("pass",pass);
                intent3.putExtra("phone",phone);
                startActivity(intent3);
            }
        });
        allbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper = new DataBaseHelper(Home.this);
                dataBaseHelper.execute("selectall",id);
            }
        });
        uploadbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Toast.makeText(Home.this,encoded_string , Toast.LENGTH_SHORT).show();
                if(encoded_string != null){
                method = "image_upload";
                dataBaseHelper = new DataBaseHelper(Home.this);
                String name = String.valueOf(dataBaseHelper.execute(method,encoded_string,imgname,id));
                    Toast.makeText(Home.this, name, Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Home.this,"HOGIHOGI~~~~" , Toast.LENGTH_SHORT).show();
                }
            }
        });
        gallerybtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(i,SELECT_PICTURE);
            }
        });

        camerabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, PERMISSIONS_REQUEST_CAMERA);
                }else{
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_WRITE_EXTERNAL);
                    }
                    imageCapture();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent4 = new Intent(Home.this, com.example.yoshikawaakira.bigtask.Gallery.class);
               startActivity(intent4);
            }
        });

    }

    private void imageCapture() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileuri = getOutputFromUri();
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE_CODE);
    }

    private Uri getOutputFromUri() {
        File storageFolder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "1211");
        if (!storageFolder.exists()) {
            if (!storageFolder.mkdirs()) {
                return null;
            }
        }
        time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File imagefile = new File(storageFolder.getPath() + File.separator + "IMG_" + time + ".jpeg");
        imgname = "IMG_" + time + ".jpeg";

        return Uri.fromFile(imagefile);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE_CODE && resultCode == RESULT_OK) {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(fileuri.getPath(), options);
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] imgArray = stream.toByteArray();
            encoded_string = Base64.encodeToString(imgArray,0);


        }
        else if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK){
            fileuri = data.getData();
            String[] file_path_colum = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(fileuri, file_path_colum, null, null, null);
            int colum_index_num = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String image_path = cursor.getString(colum_index_num);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 8;
            final Bitmap bitmap = BitmapFactory.decodeFile(image_path);
            imageView.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] imgArray = stream.toByteArray();
            encoded_string = Base64.encodeToString(imgArray,0);
            time = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
            imgname = "IMG_" + time + ".jpeg";
        }
        else {
            Toast.makeText(getApplicationContext(), "sorry for image capture", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_CAMERA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                imageCapture();
            } else {
                Toast.makeText(this, "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
