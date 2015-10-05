package com.example.hockey;

import android.app.Activity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MyActivity  extends Activity  {

    Button button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the view from activity_main.xml
        setContentView(R.layout.main);

        // Locate the button in activity_main.xml
        button = (Button) findViewById(R.id.buttonid1);

        // Capture button clicks
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                // Start NewActivity.class
                Intent myIntent = new Intent(MyActivity.this,
                        MyActivitySuivi.class);
                startActivity(myIntent);
            }
        });
    }


}



