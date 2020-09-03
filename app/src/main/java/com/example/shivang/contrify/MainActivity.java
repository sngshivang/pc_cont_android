package com.example.shivang.contrify;

import android.content.Intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements loginnav.OnFragmentInteractionListener, noclient.OnFragmentInteractionListener, signup.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dl = findViewById(R.id.drawlay);
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        ng = navController.getGraph();
        apbr = new AppBarConfiguration.Builder(ng).setDrawerLayout(dl).build();
        mytoolbar = findViewById(R.id.my_toolbar);
        mytoolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(mytoolbar);
        final ActionBar ab = getSupportActionBar();
        navView = findViewById(R.id.nav_view);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(mytoolbar, navController, apbr);
    }
    String pun;
    DrawerLayout dl;
    NavController navController;
    NavGraph ng;
    AppBarConfiguration apbr;
    Toolbar mytoolbar;
    NavigationView navView;
    @Override
    public void onFragmentInteraction(Uri uri) {
    }
}
