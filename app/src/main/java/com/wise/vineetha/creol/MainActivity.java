package com.wise.vineetha.creol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AddProject.OnFragmentInteractionListener,ProjectFeed.OnFragmentInteractionListener,Profile.OnFragmentInteractionListener {
    Toolbar toolbar;
    private static long back_pressed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Fragment fragment=new ProjectFeed();
        FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_main,fragment);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setTitle("Project Feed");
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if (back_pressed + 2000 > System.currentTimeMillis()) finishAffinity();
            else Toast.makeText(getBaseContext(), "Press once again to exit!", Toast.LENGTH_SHORT).show();
            {
                back_pressed = System.currentTimeMillis();
                //super.onBackPressed();
            }
            //finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    public void display(int id){
        Fragment fragment=null;
        switch(id){
            case R.id.nav_add_proj:
                fragment=new AddProject();
                toolbar.setTitle("Add Project");
                break;
            case R.id.nav_proj_feed:
                fragment=new ProjectFeed();
                toolbar.setTitle("Project feed");
                break;

            case R.id.nav_profile:
                fragment=new Profile();
                toolbar.setTitle("Profile");
                break;
            case R.id.nav_your_proj:
                fragment=ProjectFeed.newInstance(1);
                toolbar.setTitle("Your Projects");
                break;
            case R.id.nav_logout:
                SharedPreferences  settings = getSharedPreferences(Information.PREFS_NAME, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor=settings.edit();
                editor.putString("email",null);
                editor.commit();
                Intent intent=new Intent(MainActivity.this,Home.class);
                //finish();
                startActivity(intent);
                break;

        }
        if(fragment!=null){
            FragmentTransaction ft=getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_main,fragment);
            ft.commit();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        display(id);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}


