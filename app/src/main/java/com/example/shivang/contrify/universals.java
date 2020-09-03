package com.example.shivang.contrify;

import android.app.AlertDialog;
import android.content.Context;

public class universals {
    public static String uname;
    public static void alertcntrl(String tit, String msg, Context ct)
    {
        AlertDialog.Builder ad = new AlertDialog.Builder(ct);
        ad.setTitle(tit);
        ad.setMessage(msg);
        ad.create();
        ad.show();
    }
}
