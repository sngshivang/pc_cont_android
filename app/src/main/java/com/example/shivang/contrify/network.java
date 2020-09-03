package com.example.shivang.contrify;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
/*
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
*/
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import javax.net.ssl.HttpsURLConnection;

public class network extends AsyncTask <String,Void,String>{

    private String name;
    loginnav ln;
    signup su;
    noclient nc;
    osinfo oi;
    commands cm;
    relaycmd rc;
    int fsel;
    filetransfer ft;
    procmanip pm;

    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String...arg0) {
        try{
            String pref = arg0[0];
            String link="https://tuximages.xyz/pc_cont/operator.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            ///conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String data=null;
            if (pref.equals("1"))
            {
                String un = arg0[1];
                String ps = arg0[2];
                String em = arg0[3];
                data = "typ=1&nm="+un+"&pwd="+ps+"&em="+em;
                fsel = 1;
            }
            else if (pref.equals("2"))
            {
                String un = arg0[1];
                data = "typ=2&nm="+un;
                fsel = 2;
            }
            else if  (pref.equals("3"))
            {
                String pr = arg0[2];
                String un = arg0[1];
                data = "typ=3&pref="+pr+"&nm="+un;
                fsel = 3;
            }
            else if (pref.equals("4"))
            {
                String un = arg0[1];
                String pw = arg0[2];
                data = "typ=4&nm="+un+"&pw="+pw;
                fsel = 4;
            }
            else if (pref.equals("9"))
            {
                String un = arg0[1];
                data = "typ=9&nm="+un;
                fsel = 9;
            }
            else if (pref.equals("10"))
            {
                String un = arg0[1];
                data = "typ=10&nm="+un;
                fsel = 10;
            }
            else if (pref.equals("11"))
            {
                String un = arg0[1];
                String com = arg0[2];
                data = "typ=11&nm="+un+"&com="+com;
                fsel = 11;
            }
            else if (pref.equals("13"))
            {
                String un = arg0[1];
                String ftrans = arg0[2];
                data = "typ=13&nm="+un+"&ftrans="+ftrans;
                fsel = 13;
            }
            else if (pref.equals("16"))
            {
                String un = arg0[1];
                data = "typ=16&nm="+un;
                fsel = 16;
            }
            //data  = URLEncoder.encode("Name", "UTF-8") + "=" +
                    //URLEncoder.encode("hello", "UTF-8");
            //data += "&" + URLEncoder.encode("password", "UTF-8") + "=" +
            //URLEncoder.encode(password, "UTF-8");
            if (pref.equals("12"))
            {
                Log.i("network","this area has been intentionally left blank");

            }
            else {
                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                wr.write(data);
                wr.flush();
            }
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(conn.getInputStream()));

            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null) {
                sb.append(line);
                break;
            }
            String out = sb.toString();
            Log.i("network", out);

            return out;
        } catch(Exception e){
            Log.e("networkerr",e.toString());
            return "ERR";
        }
    }
    protected void onPostExecute(String result) {
        Log.i("result",result);
        if (fsel == 1)
        su.retcall(result);
        else if (fsel == 2) {
            if (nc!=null)
            nc.retcall(result);
            if (oi!=null)
                oi.retcall(result);
        }
        else if (fsel == 3)
            cm.retcall(result);
        else if (fsel == 4)
            ln.login_ret(result);
        else if (fsel == 11)
            rc.retcall(result);
        else if (fsel == 13)
            ft.regretcall(result);
        else if (fsel == 16)
            pm.retcall(result);
    }

}
