package com.application.seb.projet5_mynews.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.application.seb.projet5_mynews.R;

import com.application.seb.projet5_mynews.Fragment.PageAdapter;

public class MainActivity extends AppCompatActivity {

    //-----------------------------------------------
    // References
    //-----------------------------------------------

    private Toolbar mActionBarToolbar;
    private DrawerLayout drawerLayout;
    public ViewPager pager;
    public Context context = this;

    //-----------------------------------------------
    // Show menu on toolbar of UI
    //-----------------------------------------------

    /**We create a menu by inflate an .xml
     *
     * @param menu is a the new menu we will glue to our layout
     * @return true to show menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    /**This method will allow to manage the clicks on the menu
     * buttons.
     *
     * @param item is the selected button in the menu
     * @return true to able on click item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            // Search button ==> Start SearchActivity
            case R.id.search_menu:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivity(intent);
                return true;
            // "Notifications button" ==> Start NotificationActivity
            case R.id.parameters_notifications:
                Intent intent1 = new Intent(getApplicationContext(), NotificationsActivity.class);
                startActivity(intent1);
                return true;
            // "About" button ==> Nothing
            case R.id.parameters_about:
                return true;
            // "Help" button ==> Nothing
            case R.id.parameters_help:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    //-----------------------------------------------
    // On Create
    //-----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolBar();

        this.configureViewPagerAndTabs();

    }

    //-----------------------------------------------
    // Configure methods
    //-----------------------------------------------

    /**
     *This method configure the MainActivity Toolbar
     */
    private void configureToolBar(){
        // Glue toolbar to .xml file
        this.mActionBarToolbar =  findViewById(R.id.toolbar_activity_main);
        // Show the toolbar
        setSupportActionBar(mActionBarToolbar);
    }

    /**
     *This method configure the ViewPager and his TabLayout
     */
    private void configureViewPagerAndTabs(){
        //Get ViewPager from layout
        pager = findViewById(R.id.activity_main_viewpager);
        // Set total page number to 3
        pager.setOffscreenPageLimit(3);
        //Set Adapter PageAdapter and glue it together
        pager.setAdapter(new PageAdapter(getSupportFragmentManager(), context));
        // Get TabLayout from layout
        TabLayout tabs= findViewById(R.id.activity_main_tabs);
        //Glue TabLayout and ViewPager together
        tabs.setupWithViewPager(pager);
        // Design purpose. Tabs have the same width
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }
}
