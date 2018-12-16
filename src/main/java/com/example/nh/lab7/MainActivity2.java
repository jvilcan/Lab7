package com.example.nh.lab7;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.nh.lab7.utilities.NetworkUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity2 extends AppCompatActivity {

    private EditText mSearchBoxEditText;

    private TextView mUrlDisplayTextView;

    private ListView mSearchResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        mSearchBoxEditText = (EditText) findViewById(R.id.et_search_box);
        mUrlDisplayTextView = (TextView) findViewById(R.id.tv_url_display);
        mSearchResultTextView = (ListView) findViewById(R.id.tv_github_search_result_json);
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
                //mSearchResultTextView.setText(githubSearchResults);
                processJSON(githubSearchResults);
            }
        }

    }

    private void processJSON(String githubSearchResults)
    {
        List<String> repos = new ArrayList<String>();
        try
        {
            JSONObject json = new JSONObject(githubSearchResults);
            JSONArray items = json.getJSONArray("items");
            for(int i=0; i<items.length(); i++)
            {
                JSONObject object = items.getJSONObject(i);
                repos.add(object.getString("html_url"));
            }

            // Llenar datos a lista por medio de adaptador.
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, repos);
            if(mSearchResultTextView != null)
            {
                mSearchResultTextView.setAdapter(adapter);

            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

}
