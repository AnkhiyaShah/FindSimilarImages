package com.example.ankhiya.findsimilarimages.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ankhiya.findsimilarimages.R;


/**
 * this activity is main activity to run app.
 */

public class HomeActivity extends AppCompatActivity {

    private Button mClickButton;
    private DirectoryChooserFragment mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mClickButton = (Button) findViewById(R.id.button);
        mClickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDialog = new DirectoryChooserFragment.DirectoryChooserConfig().build();
                mDialog.show(getSupportFragmentManager(), null);
            }
        });

    }
}
