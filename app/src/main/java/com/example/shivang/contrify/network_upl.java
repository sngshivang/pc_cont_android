package com.example.shivang.contrify;

import android.app.Activity;
import android.content.ContentResolver;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.util.Log;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class network_upl extends AsyncTask<String, Integer, String> {
    Activity db;
    filetransfer ft;
    private String pref = null;
    uplfileinfo fl;
    protected void onPreExecute(){
    }

    @Override
    protected String doInBackground(String...arg0) {
        try {
            pref = arg0[0];
            String link = "https://tuximages.xyz/pc_cont/fileupl.php";
            URL url = new URL(link);
            URLConnection conn = url.openConnection();
            ///conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            String data = null;
            if (pref.equals("12")) {
                byte[] buffer = new byte[8192];
                String lineEnd = "\r\n";
                String twoHyphens = "--";
                String boundary = "*****";
                Uri ur = fl.fluri;
                Log.i("uploaduri", ur.getPath());
                String size = fl.size;
                String flnme = fl.name;
                ContentResolver ct = db.getContentResolver();
                InputStream is = ct.openInputStream(ur);
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setRequestProperty("uploaded_file", flnme);
                int count;
                float fsize = Float.parseFloat(size);
                float tot = 0,calc;
                DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + flnme + "\"" + lineEnd);

                dos.writeBytes(lineEnd);
                while ((count = is.read(buffer)) > 0) {
                    tot += count;
                    calc = (tot/fsize)*100;
                    publishProgress((int) calc);
                    dos.write(buffer, 0, count);
                    dos.flush();
                }
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                dos.close();
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
        }
        catch (Exception e)
        {
            Log.e("network_upl",e.toString());
            return "ERR";
        }
    }
    @Override
    public void onProgressUpdate(Integer... prog)
    {
        ft.progcall(prog[0]);


    }
    @Override
    protected void onPostExecute(String result) {
        if (pref.equals("12"))
            ft.retcall(result);
    }


}
