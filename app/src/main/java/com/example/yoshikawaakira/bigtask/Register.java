package com.example.yoshikawaakira.bigtask;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class Register extends AppCompatActivity {
    EditText name,email,pass,phone;
    TextView login;
    Button calender1,register,shot;
    RadioGroup radiogroup;
    RadioButton gender;
    ImageView yourface;

    String bod;
    DataBaseHelper dataBaseHelper;
    Calendar calendar =  Calendar.getInstance() ;

    Uri fileuri = null;

    private static final int REQUEST_IMAGE_CAPTURE_CODE = 100;
    private static final int PERMISSIONS_REQUEST_CAMERA = 200;
    private static final int PERMISSIONS_REQUEST_WRITE_EXTERNAL = 300;
    private static final int SELECT_PICTURE = 400;

    String time;
    String imgname;
    String encoded_string;
    String result;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);
        phone = findViewById(R.id.phone);

        login = findViewById(R.id.login);

        calender1 = (Button) findViewById(R.id.calender);
        register =  (Button) findViewById(R.id.register);
        shot =  (Button) findViewById(R.id.shot);

        radiogroup = (RadioGroup) findViewById(R.id.radiogroup);

        yourface = (ImageView) findViewById(R.id.yourface);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getApplicationContext(),Login.class);
                startActivity(intent2);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataBaseHelper = new DataBaseHelper(Register.this);

                int radio_id = radiogroup.getCheckedRadioButtonId();
                gender = findViewById(radio_id);

                String uname = name.getText().toString();
                String uphone = phone.getText().toString();
                String upass = pass.getText().toString();
                String uemail = email.getText().toString();
                String ucalender = bod;
                String uimgname = imgname;
                String Uencoded_string = encoded_string;

                if (gender != null && ucalender != null) {
                    String fgender = gender.getText().toString();
                    if (uname.equals("") || uphone.equals("") || uemail.equals("") || upass.equals("") || uimgname.equals("IMG_null.jpeg") ||Uencoded_string.equals("")) {
                        Toast.makeText(getApplicationContext(), "put into every information", Toast.LENGTH_SHORT).show();
                    } else {
                        String method = "register";
                        try {
                            result = dataBaseHelper.execute(method, uname, uemail, upass, uphone, fgender, ucalender, uimgname,Uencoded_string).get();
                           if(result.equals("successfully")){
                               Intent intent2 = new Intent(getApplicationContext(),Login.class);
                               startActivity(intent2);
                           }else{
                               Toast.makeText(Register.this, "fail", Toast.LENGTH_SHORT).show();
                           }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "put into every information", Toast.LENGTH_SHORT).show();
                }
            }

        });

        calender1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        Register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                        Date date = new Date(year -1900,month,day);

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

                        bod = simpleDateFormat.format(date);

                    }
                },calendar.get(calendar.YEAR),calendar.get(calendar.MONTH),calendar.get(calendar.DATE));
                datePickerDialog.show();

            }

        });

        shot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Register.this);
                builder.setTitle("Select your icon image");
                builder.setMessage("you want to chose camera or gallery");
                builder.setNegativeButton("BACK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
                builder.setNeutralButton("Camera", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
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
                builder.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent1 = new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent1,SELECT_PICTURE);
                    }
                });
                AlertDialog box = builder.create();
                box.show();
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
            yourface.setImageBitmap(bitmap);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,stream);
            byte[] imgArray = stream.toByteArray();
            encoded_string = Base64.encodeToString(imgArray,0);
        }
        else if(requestCode == SELECT_PICTURE && resultCode == RESULT_OK)
            {
                fileuri = data.getData();
                String[] file_path_colum = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(fileuri, file_path_colum, null, null, null);
                int colum_index_num = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                String image_path = cursor.getString(colum_index_num);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inSampleSize = 8;
                final Bitmap bitmap = BitmapFactory.decodeFile(image_path);
                yourface.setImageBitmap(bitmap);
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
