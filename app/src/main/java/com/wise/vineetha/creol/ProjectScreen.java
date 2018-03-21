package com.wise.vineetha.creol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.sql.Statement;

public class ProjectScreen extends AppCompatActivity {

    private static final String TAG;
    TextView details;

    static {
        TAG = "MyActivity";
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_screen);
        Log.i(TAG, "onCreate: started");

        getIncomingIntent();;
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("project-name") && getIntent().hasExtra("project-description")) {
            String pname = getIntent().getStringExtra("project-name");
            String pdesc = getIntent().getStringExtra("project-description");

            setIntent(pname, pdesc);
        }
    }

    private void setIntent(String pname, String pdesc) {
        details = (TextView) findViewById(R.id.pdetails);
        details.setText(pname.toUpperCase());
        details.append("\n\nDescription :\n "+pdesc);
        try {
            java.sql.Connection con = DatabaseConnection.CONN();
            if (con == null) {
                Toast.makeText(this,"check internet connection",Toast.LENGTH_LONG).show();
            } else {
                String query = "select * from dbo.ProjectDetails where title='"+pname+"' and descriptn='"+pdesc+"';";
                Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    details.append("\n\nRequirements :\n "+rs.getString(4));
                    details.append("\n\nCategory :\n "+rs.getString(6));
                    details.append("\n\nDuration :\n "+rs.getString(5));
                    details.append("\n\nFor more details contact :\n "+rs.getString(1));
                }
            }
        } catch (Exception ex) {
            Log.e("ERROR", ex.getMessage());
            Toast.makeText(this,"data not found",Toast.LENGTH_LONG).show();

        }

    }
}
