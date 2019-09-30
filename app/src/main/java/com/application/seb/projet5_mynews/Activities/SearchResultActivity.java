package com.application.seb.projet5_mynews.Activities;

import android.content.Intent;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.application.seb.projet5_mynews.Fragment.PageFragment;
import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.MyConstants;


public class SearchResultActivity extends AppCompatActivity {


    //----------------------------------------------------------------------------------------------
    // ON CREATE
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        configureToolBar();
        configureAndShowFragment();
    }

    //----------------------------------------------------------------------------------------------
    // TOOLBAR
    //----------------------------------------------------------------------------------------------

    /**
     * This method configure activity toolbar and create her back stack
     */
    private void configureToolBar() {
        // Set action bar
        Toolbar mToolBar = findViewById(R.id.activity_search_result_toolbar);
        setSupportActionBar(mToolBar);
        //Set back stack
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_black_24dp, null));
            actionBar.setDisplayHomeAsUpEnabled(true);

        }
    }

    //----------------------------------------------------------------------------------------------
    // FRAGMENT
    //----------------------------------------------------------------------------------------------

    /**
     * This method configure the fragment and show the search result
     */
    private void configureAndShowFragment() {
        // Get the response from Intent arguments
        Intent intent = getIntent();
        String response = intent.getStringExtra(MyConstants.STRING_RESPONSE);
        Log.d("SearchResultActivity :", "String response from intent : " + response);

        // Pass this response on PageFragment instance argument
        Bundle bundle = new Bundle();
        bundle.putString(MyConstants.STRING_RESPONSE, response);
        Log.d("SearchActivityResult", "bundle before send to new fragment instance : " + bundle);
        PageFragment frag = PageFragment.newInstance(-1, bundle);

        // And show the response
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_search_result_fragment, frag)
                .commit();
    }


}

