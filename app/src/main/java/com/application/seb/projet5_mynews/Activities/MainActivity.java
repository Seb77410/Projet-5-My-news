package com.application.seb.projet5_mynews.Activities;

import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.application.seb.projet5_mynews.Fragment.PageAdapter;
import com.application.seb.projet5_mynews.R;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

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
    // Show Navigation Drawer
    //-----------------------------------------------

    /**
     * This method Open/Close the menu drawer on UI
     */
    @Override
    public void onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    /**This method will allow to manage the clicks on the drawer
     * menu buttons.
     *
     * @param menuItem is the current clicked button  the Drawer menu
     * @return true to show ViewPager result
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();
        switch (id){
            // "Top stories" button ==> Show "top stories" page on the MainActivity ViewPager
            case R.id.activity_main_drawer_top_stories :
                pager.setCurrentItem(0);
                break;
            // "Most popular" button ==> Show "most popular" page on the MainActivity ViewPager
            case R.id.activity_main_drawer_most_popular:
                pager.setCurrentItem(1);
                break;
            // "Business" button ==> Show "Business" page on the MainActivity ViewPager
            case R.id.activity_main_drawer_business:
                pager.setCurrentItem(2);
                break;
            default:
                break;
        }
        this.drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



    //-----------------------------------------------
    // On Create
    //-----------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.configureToolBar();

        this.configureDrawerLayout();

        this.configureNavigationView();

        this.configureViewPagerAndTabs();
    }


    //-----------------------------------------------
    // Show View Pager
    //-----------------------------------------------

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
     * This method configure Drawer Layout
     */
    private void configureDrawerLayout(){
        // Glue drawerLayout to .xml file
        this.drawerLayout =  findViewById(R.id.activity_main_drawer_layout);
        // Glue drawer menu to MainActivity toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, mActionBarToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // Add listener to the menu drawer
        drawerLayout.addDrawerListener(toggle);
        // Add animation on drawer menu button when Open/close
        toggle.syncState();
    }

    /**
     * This method configure the Navigation view
     */
    private void configureNavigationView(){
        // Glue NavigationView to .xml file
        NavigationView navigationView = findViewById(R.id.activity_main_nav_view);
        // Allow user tu click on Menu drawer item button
        navigationView.setNavigationItemSelectedListener(this);
    }


}

