package com.wise.vineetha.creol;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class User extends AppCompatActivity {
    Button getst, okay;
    EditText e;
    String email;
    int flag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        getst = (Button) findViewById(R.id.getstarted);
        okay = (Button) findViewById(R.id.ok);
        e = (EditText) findViewById(R.id.usermail);

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
                if (flag == 0)
                    startActivity(new Intent(User.this, Information.class));
                else {
                    startActivity(new Intent(User.this, MainActivity.class));
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
}
