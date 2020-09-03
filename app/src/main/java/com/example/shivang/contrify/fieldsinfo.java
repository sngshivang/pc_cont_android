package com.example.shivang.contrify;

public class fieldsinfo {
    public int pos;
    public String name;
    public String dig;
    public String inti;
    public boolean chkbx;

    public fieldsinfo(String name, String digi, String inti) {
        this.name = name;
        this.dig = digi;
        this.inti = inti;
    }
    public fieldsinfo(String name, String digi, String inti, boolean chk) {
        this.name = name;
        this.dig = digi;
        this.inti = inti;
        this.chkbx = chk;
    }
    public fieldsinfo(int setpos) {
        this.pos = setpos;
    }
    public fieldsinfo(String name)
    {
        this.name = name;
    }
}
