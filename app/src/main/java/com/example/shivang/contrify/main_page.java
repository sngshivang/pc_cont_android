package com.example.shivang.contrify;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class main_page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        setBackground();
    }

    public void setBackground()
    {
        //GridView grd = findViewById(R.id.lowergrid);
        //View vw = grd.getDecorView();
        //vw.setBackgroundColor(Color.RED);
    }
}
