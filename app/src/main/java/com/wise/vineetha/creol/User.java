package com.wise.vineetha.creol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User extends AppCompatActivity {
    BootstrapButton getst, okay;
    BootstrapEditText e;
    String email;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getst = (BootstrapButton) findViewById(R.id.getstarted);
        okay = (BootstrapButton) findViewById(R.id.ok);
        e = (BootstrapEditText) findViewById(R.id.useremail);

        flag = 0;

        okay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = e.getText().toString().trim();
                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    if (con == null) {
                        Toast.makeText(User.this, "check internet connection", Toast.LENGTH_LONG).show();
                    } else {
                        String query = "select * from dbo.Information where email='"+email+"';";
                        Statement stmt = con.createStatement();
                        ResultSet rs = stmt.executeQuery(query);
                        if (rs.next()) {
                            SharedPreferences settings = getSharedPreferences(Information.PREFS_NAME, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor=settings.edit();
                            editor.putString("email",email);
                            editor.commit();
                            flag=1;
                        }
                    }
                } catch (Exception ex) {
                    Toast.makeText(User.this, "data not found", Toast.LENGTH_LONG).show();
                }
                if (flag == 0) {

                    startActivity(new Intent(User.this, Information.class));
                    finish();
                }
                else {

                    startActivity(new Intent(User.this, MainActivity.class));
                    finish();
                }
            }
        });
        getst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(User.this,Information.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}
