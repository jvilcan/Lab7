package com.example.nh.lab7;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nh.lab7.utilities.NetworkUtils;

import java.io.IOException;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private TextView mSearchResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultTextView = (TextView) findViewById(R.id.tv_github_search_result_json);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuapp, menu);

        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int itemClickedId = item.getItemId();

        if (itemClickedId == R.id.action_search) {

            //Context context = MainActivity.this;
            //Toast.makeText(context, "opción de búsqueda", Toast.LENGTH_SHORT).show();

            makeGitHubSearchQuery();

        }

        return true;
    }


    private void makeGitHubSearchQuery() {

        String githubQuery = mSearchBoxEditText.getText().toString();
        URL githubSearchURL = NetworkUtils.buildUrl(githubQuery);
        mUrlDisplayTextView.setText(githubSearchURL.toString());

        /*
        String githubSearchResults = null;

        try {

            githubSearchResults = NetworkUtils.getResponseFromHttpUrl(githubSearchURL);
            mSearchResultTextView.setText(githubSearchResults);


        } catch(IOException e) {
            e.printStackTrace();
        }
        */

        new GithubQueryTask().execute(githubSearchURL);
    }

    public class GithubQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {

            URL searchUrl = params[0];
            String githubSearchResults = null;

            try {
                githubSearchResults = NetworkUtils.getResponseFromHttpUrl(searchUrl);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return githubSearchResults;

        }

        @Override
        protected void onPostExecute(String githubSearchResults) {
            if (githubSearchResults != null && !githubSearchResults.equals("")) {
                mSearchResultTextView.setText(githubSearchResults);
            }


        }

    }











}
