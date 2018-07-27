package com.wise.vineetha.creol;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Statement;
import android.content.SharedPreferences;
import android.content.Context;
import android.content.SharedPreferences.Editor;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;

public class Information extends AppCompatActivity {
    BootstrapButton save;
    BootstrapEditText email,emaild,name,college,skills,works;
    String uname,clg,sk,wk;
    String eml,eml2;
    public static final String PREFS_NAME = "EMAIL";
    SharedPreferences s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        save=(BootstrapButton)findViewById(R.id.saveInfo);
        email=(BootstrapEditText)findViewById(R.id.Email);
        emaild=(BootstrapEditText)findViewById(R.id.Emaild);
        name=(BootstrapEditText)findViewById(R.id.Name);
        college=(BootstrapEditText)findViewById(R.id.College);
        skills=(BootstrapEditText)findViewById(R.id.Skills);
        works=(BootstrapEditText)findViewById(R.id.Work);

        /*s=getSharedPreferences(Information.PREFS_NAME,MODE_PRIVATE);
        eml=s.getString("email",null);
        email.setText(eml);*/
        //email.setEnabled(false);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eml=email.getText().toString().trim();
                eml2=emaild.getText().toString().trim();
                if(eml.equals(eml2)) {
                    InsertInfo id = new InsertInfo();
                    id.execute("");
                }
                else
                    Toast.makeText(Information.this,"check email fields",Toast.LENGTH_LONG).show();
            }
        });
    }
    public class InsertInfo extends AsyncTask<String,Void,String>
    {
        String z = "";
        Boolean isSuccess = false;

        @Override
        protected void onPreExecute() {

            uname = name.getText().toString().trim();
            clg = college.getText().toString().trim();
            sk= skills.getText().toString().trim();
            wk = works.getText().toString().trim();
            SharedPreferences  settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
            Editor editor=settings.edit();
            editor.putString("email",eml);
            editor.commit();

        }

        @Override
        protected void onPostExecute(String r) {
            if(isSuccess) {
                SharedPreferences  settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                Editor editor=settings.edit();
                editor.putString("reg",eml);
                editor.commit();
                Intent i=new Intent(Information.this,MainActivity.class);
                startActivity(i);
                finish();
            }
            else {
                Toast.makeText(Information.this, z, Toast.LENGTH_LONG).show();
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
                Editor editor = settings.edit();
                editor.putString("reg", null);
                editor.commit();
            }

        }

        @Override
        protected String doInBackground(String... params) {
            if(eml.equals("")|| uname.equals("") || clg.equals("") || sk.equals("") || wk.equals("")){
                z = "Please fill all the fields";
                Log.e("Error",z);
            }else{
                try {
                    java.sql.Connection con = DatabaseConnection.CONN();
                    if (con == null) {
                        z = "Error in connection with SQL server";
                    }else{
                        Statement stmt = con.createStatement();
                        int flag = stmt.executeUpdate("insert into dbo.Information values('"+eml+"','"+uname+"','"+clg+"','"+sk+"','"+wk+"');");
                        z = "successfull";
                        //Toast.makeText(InsertData.this,z,Toast.LENGTH_LONG).show();
                        isSuccess=true;
                    }
                }catch (Exception ex)
                {
                    isSuccess = false;
                    z = "Exceptions";
                    Log.e("ERROR", ex.getMessage());

                }
            }

            return z;
        }
    }
}
