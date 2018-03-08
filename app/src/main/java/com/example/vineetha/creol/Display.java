package com.example.vineetha.creol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class Display extends AppCompatActivity {
    EditText ddes,ddur,dreq,dcat,dtit,deml;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        ddes=(EditText)findViewById(R.id.ddescription);
        ddur=(EditText)findViewById(R.id.dduration);
        dreq=(EditText)findViewById(R.id.drequirements);
        dcat=(EditText)findViewById(R.id.dcategory);
        dtit=(EditText)findViewById(R.id.dtitle);
        deml=(EditText)findViewById(R.id.demail);
        String ptitle=getIntent().getStringExtra("title");
        String pdes=getIntent().getStringExtra("desc");
        try {
            java.sql.Connection con = DatabaseConnection.CONN();
            if (con == null) {
                Toast.makeText(Display.this,"Error in connection with SQL server",Toast.LENGTH_LONG).show();
            } else {
                String query = "select * from dbo.ProjectDetails where title='"+ptitle+"' and descriptn='"+pdes+"';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    deml.setText(rs.getString(1));
                    dreq.setText(rs.getString(4));
                    ddur.setText(rs.getString(5));
                    dcat.setText(rs.getString(6));
                }

            }
        }catch (Exception ex) {
            Toast.makeText(Display.this,"Error in request",Toast.LENGTH_LONG).show();
        }
        ddes.setText(pdes);
        dtit.setText(ptitle);

    }
}
