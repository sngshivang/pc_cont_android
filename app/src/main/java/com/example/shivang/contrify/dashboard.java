package com.example.shivang.contrify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.NavGraph;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;

public class dashboard extends AppCompatActivity implements osinfo.OnFragmentInteractionListener, commands.OnFragmentInteractionListener, relaycmd.OnFragmentInteractionListener, filetransfer.OnFragmentInteractionListener, procmanip.OnFragmentInteractionListener, about.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        dl = findViewById(R.id.drawlay);
        //navController = Navigation.findNavController(this, R.id.pager);
        //ng = navController.getGraph();
        //apbr = new AppBarConfiguration.Builder(ng).setDrawerLayout(dl).build();
        mytoolbar = findViewById(R.id.my_toolbar);
        mytoolbar.inflateMenu(R.menu.menu_main);
        mytoolbar.setTitle("PC Controller");
        setSupportActionBar(mytoolbar);
        //final ActionBar ab = getSupportActionBar();
        navView = findViewById(R.id.nav_view);
        //NavigationUI.setupWithNavController(navView, navController);
        //NavigationUI.setupWithNavController(mytoolbar, navController, apbr);
        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);
        adapter = new TabAdapter(getSupportFragmentManager());
        adapter.addFragment(new osinfo(), "Client Info");
        adapter.addFragment(new commands(), "Command");
        adapter.addFragment(new relaycmd(), "Relay CMD");
        adapter.addFragment(new filetransfer(), "File Transfer");
        adapter.addFragment(new procmanip(), "Process list");
        adapter.addFragment(new about(), "About");
        navdrawer(navView);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }
    DrawerLayout dl;
    NavController navController;
    NavGraph ng;
    AppBarConfiguration apbr;
    Toolbar mytoolbar;
    NavigationView navView;
    private TabAdapter adapter;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    private void navdrawer(NavigationView ngv)
    {
        ngv.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {

                        dl.closeDrawers();
                        switch (menuItem.getItemId())
                        {
                            case R.id.clinfo:
                                tabLayout.getTabAt(0).select();
                                break;
                            case R.id.sendcom:
                                tabLayout.getTabAt(2).select();
                                break;
                            case R.id.filup:
                                tabLayout.getTabAt(3).select();
                                break;
                            case R.id.proclist:
                                tabLayout.getTabAt(4).select();
                                break;
                            case R.id.acpicom:
                                tabLayout.getTabAt(1).select();
                                break;
                            case R.id.about:
                                tabLayout.getTabAt(5).select();
                                break;
                        }
                        return true;
                    }
                });
    }


}
