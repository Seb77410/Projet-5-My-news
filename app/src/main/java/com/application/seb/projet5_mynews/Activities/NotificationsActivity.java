package com.application.seb.projet5_mynews.Activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.application.seb.projet5_mynews.Utils.AlarmNotificationReceiver;
import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Model.SearchRequestParameters;
import com.application.seb.projet5_mynews.Utils.MyConstants;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;

public class NotificationsActivity extends AppCompatActivity {

    // For data
    private Toolbar mToolBar;
    private Button button;
    private RelativeLayout endDateLayout;
    private RelativeLayout beginDateLayout;
    private SwitchCompat allowNotificationsSwitch;
    private Context context = this;
    private SearchRequestParameters searchRequestParameters = new SearchRequestParameters(context);
    private HashMap<String, String> optionsMap = new HashMap<>();

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // References
        CheckBox artCheckBox = findViewById(R.id.artsCheckBox);
        CheckBox businessCheckBox = findViewById(R.id.businessCheckBox);
        CheckBox entrepreneurCheckBox = findViewById(R.id.entreprenersCheckBox);
        CheckBox politicCheckBox = findViewById(R.id.politicCheckBox);
        CheckBox sportCheckBox = findViewById(R.id.sportCheckBox);
        CheckBox travelCheckBox = findViewById(R.id.travelCheckBox);
        EditText editText = findViewById(R.id.editText_search);
        allowNotificationsSwitch = findViewById(R.id.activity_search_switch);
        mToolBar = findViewById(R.id.toolbar_activity_search);
        beginDateLayout = findViewById(R.id.begin_date_contener);
        endDateLayout = findViewById(R.id.end_date_contener);
        button = findViewById(R.id.search_click_button);

        // Action bar
        configureActivityToolbar();
        // Display the part of the view for SearchActivity
        configureActivityLayout();

        //-----------------
        // Save data
        //-----------------

        // And glue it to my SearchRequestParameters object
        optionsMap = searchRequestParameters.getOptionsMap();
        searchRequestParameters.setArtCheckBox(artCheckBox);
        searchRequestParameters.setBusinessCheckBox(businessCheckBox);
        searchRequestParameters.setEntrepreneurCheckBox(entrepreneurCheckBox);
        searchRequestParameters.setPoliticCheckBox(politicCheckBox);
        searchRequestParameters.setSportCheckBox(sportCheckBox);
        searchRequestParameters.setTravelCheckBox(travelCheckBox);
        searchRequestParameters.setEditText(editText);

        searchRequestParameters.onCheckedListener();

        // User use the switch for able/enable notifications
        allowNotificationsSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    // Save data to HashMap
                    searchRequestParameters.saveFilterQuery();
                    searchRequestParameters.saveQueryWords();
                    optionsMap.put(MyConstants.APIKEY, MyConstants.API_KEY);

                    // If User set filters and query, we start the search.
                    if (searchRequestParameters.getbFilterQuery() && searchRequestParameters.getbQuery()) {
                        // Notifications button stay ON
                        searchRequestParameters.setSwitchCompat(true);
                    }
                    // Else we show an AlertDialog and enable notifications
                    else {
                        searchRequestParameters.checkForAlertDialog();
                        // Notifications button stay OFF
                        allowNotificationsSwitch.setChecked(false);
                        searchRequestParameters.setSwitchCompat(false);
                    }
                }else{
                    allowNotificationsSwitch.setChecked(false);
                    searchRequestParameters.setSwitchCompat(false);
                }
            }
        });
    }


    //----------------------------------------------------------------------------------------------
    // Configure NotificationsActivity layout
    //----------------------------------------------------------------------------------------------
    public void configureActivityLayout(){

        // Set dates layout invisible
        button.setVisibility(View.INVISIBLE);
        beginDateLayout.setVisibility(View.INVISIBLE);
        endDateLayout.setVisibility(View.INVISIBLE);

        // And set checkBox layout constraint
        ConstraintLayout constraintLayout = findViewById(R.id.activity_search_constraintLayout);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constraintLayout);
        constraintSet.connect(R.id.activity_search_checkboxs_contener,ConstraintSet.TOP,R.id.editText_search,ConstraintSet.BOTTOM,20);
        constraintSet.applyTo(constraintLayout);
    }

    //----------------------------------------------------------------------------------------------
    // Configure NotificationsActivity toolbar
    //----------------------------------------------------------------------------------------------

    public void configureActivityToolbar(){
        // Set action bar
        mToolBar.setTitle(getString(R.string.notifications_activity_title));
        setSupportActionBar(mToolBar);
        //Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_black_24dp, null);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Alarm manager
    //----------------------------------------------------------------------------------------------

    /** This method will start an alarm everyday at 21h
     *
     * @param context is the context
     */
    private void startAlarm(Context context) {
        Log.d("Start alarm ", "Open startAlarm method in NotificationsActivity ");

        // Alarm config
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        calendar.set(Calendar.HOUR_OF_DAY, 20);
        calendar.set(Calendar.MINUTE, 7);
        calendar.set(Calendar.SECOND, 0);

        // Alarm references
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmNotificationReceiver.class);
        // Pass the searchRequestParameters as intent arguments
        intent.putExtra(MyConstants.SEARCH_REQUEST_QUERY, searchRequestParameters.getQuery() );
        intent.putExtra(MyConstants.SEARCH_REQUEST_QUERY_FILTERS, searchRequestParameters.getQueryFilter());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // The alarm will start everyday
        if (alarmManager != null) {
            Log.d("programming alarm", " alarmManager is not null");
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

        }
    }



    //----------------------------------------------------------------------------------------------
    // On pause save notifications parameters
    //----------------------------------------------------------------------------------------------


    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences arrayPreferences = context.getSharedPreferences(MyConstants.SHARED_PREFERENCES_NAME, MODE_PRIVATE);
        SharedPreferences.Editor arrayPrefEditor = arrayPreferences.edit();
        arrayPrefEditor.clear();

        // If notifications are allowed
        if(allowNotificationsSwitch.isChecked()){
            // We pass notifications parameters to sting
            Gson gson = new Gson();
            String query = gson.toJson(searchRequestParameters.getQuery());
            String queryFilters = gson.toJson(searchRequestParameters.getbFilterQuery());
            // We save string parameters in a shared preference
            arrayPrefEditor.putString(MyConstants.SEARCH_REQUEST_QUERY, query).apply();
            arrayPrefEditor.putString(MyConstants.SEARCH_REQUEST_QUERY_FILTERS, queryFilters).apply();
            arrayPrefEditor.putBoolean(MyConstants.NOTIFICATIONS_ON, true );
            arrayPrefEditor.commit();
            Log.d("SharedPreferences", "into onPause, Notifications_ON  : " + arrayPreferences.getBoolean(MyConstants.NOTIFICATIONS_ON, false));
            // We start the alarm
            startAlarm(context);
            // And we notify saving success to user with a toast
            Toast.makeText(getApplicationContext(), "Notifications parameters saved", Toast.LENGTH_LONG).show();
        }
        // Else we save that notifications are not allowed
        else{arrayPrefEditor.putBoolean(MyConstants.NOTIFICATIONS_ON, false ).commit();}
    }

}
