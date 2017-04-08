package com.example.ankhiya.findsimilarimages.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.ankhiya.findsimilarimages.R;
import com.example.ankhiya.findsimilarimages.utils.SearchMode;

/**
 * Created by ankhiya on 4/8/17.
 */

public class MainActivity extends AppCompatActivity {

    private static final String KEY_SEARCH_MODE = "searchMode";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button imagesButton = (Button) findViewById(R.id.btn_find_images);
        Button audioButton = (Button) findViewById(R.id.btn_find_audios);

        imagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =  new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra(KEY_SEARCH_MODE, SearchMode.IMAGE);
                startActivity(intent);
            }
        });

        audioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                intent.putExtra(KEY_SEARCH_MODE,SearchMode.AUDIO);
                startActivity(intent);
            }
        });
    }
}
