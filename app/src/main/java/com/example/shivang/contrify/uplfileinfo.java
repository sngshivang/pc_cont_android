package com.example.shivang.contrify;

import android.net.Uri;

public class uplfileinfo {
    Uri fluri;
    String size;
    String name;

    uplfileinfo(Uri in, String sz, String nm)
    {
        fluri = in;
        size = sz;
        name = nm;

    }
}
