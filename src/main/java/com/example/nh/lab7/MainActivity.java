package com.example.nh.lab7;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;
    private TextView mUrlDisplayTextView;
    private TextView mSearchResultTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);

        this.mSearchBoxEditText = (EditText)findViewById(R.id.et_search_box);
        this.mUrlDisplayTextView = (TextView)findViewById(R.id.tv_url_display);
        this.mSearchResultTextView =(TextView)findViewById(R.id.tv_github_search_result_json);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuapp, menu);
        return  true;
    }



}
