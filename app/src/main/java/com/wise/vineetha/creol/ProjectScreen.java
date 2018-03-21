package com.wise.vineetha.creol;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

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
        details.setText(pname);
        details.append("\n");
        details.append(pdesc);
    }
}
