package com.example.yoshikawaakira.bigtask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by yoshikawaakira on 2017/12/12.
 */

public class Connection {

    public static HttpURLConnection connect(){
        try {

            URL url1 = new URL("http://192.168.1.111/bigtaskphp/bigtask.php");
            HttpURLConnection httpURLConnection = (HttpURLConnection)url1.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);

            return httpURLConnection;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }
}
