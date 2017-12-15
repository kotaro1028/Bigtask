package com.example.yoshikawaakira.bigtask;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URLEncoder;

/**
 * Created by yoshikawaakira on 2017/12/12.
 */

class DataBaseHelper extends AsyncTask<String,Void,String> {
    Context context;
    int aaa = 0;
    SharedPreferences sharedPreferences;

    public DataBaseHelper(Context context){
        this.context = context;
    }
    @Override
    protected String doInBackground(String... strings) {
        String method = strings[0];
        if(method.equals("register")){
            String name = strings[1];
            String email = strings[2];
            String pass = strings[3];
            String phone = strings[4];
            String gender = strings[5];
            String dob = strings[6];
            String imgname = strings[7];
            String encoded_string = strings[8];

            HttpURLConnection httpURLConnection = Connection.connect();
            try {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("register","UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8")+"&"+
                        URLEncoder.encode("gender","UTF-8")+"="+URLEncoder.encode(gender,"UTF-8")+"&"+
                        URLEncoder.encode("dob","UTF-8")+"="+URLEncoder.encode(dob,"UTF-8")+"&"+
                        URLEncoder.encode("img_name","UTF-8")+"="+URLEncoder.encode(imgname,"UTF-8")+"&"+
                        URLEncoder.encode("encoded_string","UTF-8")+"="+URLEncoder.encode(encoded_string,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                is.close();

                return "successfully";
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "noooooo";
        }
        else if(method.equals("login")){
            String name = strings[1];
            String pass = strings[2];

            HttpURLConnection httpURLConnection = Connection.connect();
            try {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("login","UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n") ;
                }

                String result = stringBuilder.toString();
                bufferedReader.close();
                is.close();

                httpURLConnection.disconnect();
                aaa= 2;
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("image_upload")){
            String encoded_string = strings[1];
            String imgname = strings[2];
            String userid = strings[3];

            HttpURLConnection httpURLConnection = Connection.connect();

            try {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("image_upload","UTF-8")+"&"+
                        URLEncoder.encode("encoded_string","UTF-8")+"="+URLEncoder.encode(encoded_string,"UTF-8")+"&"+
                        URLEncoder.encode("imgname","UTF-8")+"="+URLEncoder.encode(imgname,"UTF-8")+"&"+
                        URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8");


                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                is.close();


                return "successfully";

            } catch (IOException e) {
                e.printStackTrace();
            }
            return "noooooo";
        }else if(method.equals("selectall")){
            String id = strings[1];
            HttpURLConnection httpURLConnection = Connection.connect();
            try {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("selectall","UTF-8")+"&"+
                            URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n") ;
                }

                String result = stringBuilder.toString().trim();
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                aaa = 1;
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else if(method.equals("edit")){
                String name = strings[1];
                String email = strings[2];
                String pass = strings[3];
                String phone = strings[4];
                String id = strings[5];

            HttpURLConnection httpURLConnection = Connection.connect();
            try {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("edit","UTF-8")+"&"+
                        URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8")+"&"+
                        URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"+
                        URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"+
                        URLEncoder.encode("pass","UTF-8")+"="+URLEncoder.encode(pass,"UTF-8")+"&"+
                        URLEncoder.encode("phone","UTF-8")+"="+URLEncoder.encode(phone,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                is.close();

                httpURLConnection.disconnect();
                return "success";
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("loginuser")){
            String id = strings[1];
            HttpURLConnection httpURLConnection = Connection.connect();
            try {
            OutputStream os = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
            String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("loginuser","UTF-8")+"&"+
                    URLEncoder.encode("id","UTF-8")+"="+URLEncoder.encode(id,"UTF-8");

            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            os.close();

            InputStream is = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while((line = bufferedReader.readLine()) != null){
                stringBuilder.append(line + "\n") ;
            }

            String result = stringBuilder.toString();
            bufferedReader.close();
            is.close();

            httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else if(method.equals("galleryimg")){
            String userid = strings[1];

            HttpURLConnection httpURLConnection = Connection.connect();
            try {
                OutputStream os = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data = URLEncoder.encode("method","UTF-8")+"="+URLEncoder.encode("galleryimg","UTF-8")+"&"+
                        URLEncoder.encode("userid","UTF-8")+"="+URLEncoder.encode(userid,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    stringBuilder.append(line + "\n") ;
                }

                String result = stringBuilder.toString();
                bufferedReader.close();
                is.close();

                httpURLConnection.disconnect();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        if (aaa==1){
            Intent intent = new Intent(context,Friendlist.class);
            intent.putExtra("result",s);
            context.startActivity(intent);
        }
        else if(aaa == 2){
            String result = s.trim();
            if (result.equals("")){
                Toast.makeText(context, "Username & password wrong", Toast.LENGTH_SHORT).show();
            }else{
                try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                int count = 0;
                String id;
                while(count<jsonArray.length()){
                    JSONObject jo = jsonArray.getJSONObject(count);
                    id= jo.getString("id");
                    Toast.makeText(context, id, Toast.LENGTH_SHORT).show();
                    sharedPreferences = context.getSharedPreferences("logindata",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("id",id);
                    editor.commit();
                    count++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//
//            Toast.makeText(context, s, Toast.LENGTH_SHORT).show();
            Intent i = new Intent(context,Home.class);
            context.startActivity(i);
            }
//
        }


    }
}
