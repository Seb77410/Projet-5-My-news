package com.application.seb.projet5_mynews.Activities;

import android.content.Context;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.app.DatePickerDialog;
import android.graphics.drawable.Drawable;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.application.seb.projet5_mynews.Model.SearchResponse;
import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.FormatDate;
import com.application.seb.projet5_mynews.Utils.MyConstants;
import com.application.seb.projet5_mynews.Utils.NewYorkTimesStream;
import com.application.seb.projet5_mynews.Model.SearchRequestParameters;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SearchActivity extends AppCompatActivity {

    private TextView textViewBeginDate;
    private TextView textViewEndDate;
    private DatePickerDialog selector;
    private String beginDate;
    private String beginDateForSave;
    private String endDate;
    private String endDateForSave;
    private EditText editText;
    private CheckBox artCheckBox;
    private CheckBox businessCheckBox;
    private CheckBox entrepreneurCheckBox;
    private CheckBox politicCheckBox;
    private CheckBox sportCheckBox;
    private CheckBox travelCheckBox;
    private Context context = this;
    private Disposable disposable;
    private SearchRequestParameters searchRequestParameters = new SearchRequestParameters(context);


    //----------------------------------------------------------------------------------------------
    // ON CREATE
    //----------------------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //---------------------
        // Remove notifications contener from view
        //---------------------
        RelativeLayout relativeLayout = findViewById(R.id.activity_notifications_line_and_switch_contener);
        relativeLayout.setVisibility(View.INVISIBLE);

        //---------------------
        // Action bar
        //---------------------
        // Views
        Toolbar mToolBar = findViewById(R.id.toolbar_activity_search);
        setSupportActionBar(mToolBar);
        // Set back stack
        Drawable upArrow = ResourcesCompat.getDrawable(this.getResources(), R.drawable.ic_arrow_back_black_24dp, null);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(upArrow);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        //---------------------
        // Date picker dialog
        //---------------------
        // Set Begin and End Date EditText
        ImageButton imageBeginDate = findViewById(R.id.button_begin_date);
        ImageButton imageEndDate = findViewById(R.id.button_end_date);
        textViewBeginDate = findViewById(R.id.begin_date_for_spinner);
        textViewEndDate = findViewById(R.id.end_date_for_spinner);

        // Show date picker
        showDatePicker(imageBeginDate, textViewBeginDate);
        showDatePicker(imageEndDate,textViewEndDate);

        //---------------------
        // Get data for Http request
        //---------------------
        Button searchButton = findViewById(R.id.search_click_button);
        artCheckBox = findViewById(R.id.artsCheckBox);
        businessCheckBox = findViewById(R.id.businessCheckBox);
        entrepreneurCheckBox = findViewById(R.id.entreprenersCheckBox);
        politicCheckBox = findViewById(R.id.politicCheckBox);
        sportCheckBox = findViewById(R.id.sportCheckBox);
        travelCheckBox = findViewById(R.id.travelCheckBox);
        editText = findViewById(R.id.editText_search);

        // Start search
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // For data
                HashMap<String, String> optionsMap = searchRequestParameters.getOptionsMap();

                // Set searchRequestParameters checkBoxs
                searchRequestParameters.setArtCheckBox(artCheckBox);
                searchRequestParameters.setBusinessCheckBox(businessCheckBox);
                searchRequestParameters.setEntrepreneurCheckBox(entrepreneurCheckBox);
                searchRequestParameters.setPoliticCheckBox(politicCheckBox);
                searchRequestParameters.setSportCheckBox(sportCheckBox);
                searchRequestParameters.setTravelCheckBox(travelCheckBox);
                searchRequestParameters.setEditText(editText);

                // Save data to HashMap
                searchRequestParameters.saveFilterQuery();
                searchRequestParameters.saveQueryWords();
                saveDate(optionsMap);
                optionsMap.put(MyConstants.APIKEY, MyConstants.API_KEY);

                // If User set filters and query, we start the search.
                if (searchRequestParameters.getbFilterQuery() && searchRequestParameters.getbQuery()){executeSearchRequest(optionsMap);}
                // Else we show an AlertDialog
                else{searchRequestParameters.checkForAlertDialog();}
            }
        });

    }


    //----------------------------------------------------------------------------------------------
    // Date picker
    //----------------------------------------------------------------------------------------------

    /**
     * This method make appear a date picker dialog and show his selected date
     * @param imageButton click on this image and date picker appear
     * @param textView will show the date selected by user
     */
    private void showDatePicker(final ImageButton imageButton, final TextView textView) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Date dialogue de selection
                selector = new DatePickerDialog (SearchActivity.this, R.style.TimePickerTheme ,new DatePickerDialog.OnDateSetListener () {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // We change the visibility date for user by showing
                        String date = FormatDate.convertDateForSpinner(year,month+1,dayOfMonth);
                        String dateForSave = FormatDate.convertDateForSave(year,month+1,dayOfMonth);
                        textView.setText(date);
                        // We Save date for search data
                        if (textView == textViewBeginDate){
                            beginDate = date;
                            beginDateForSave = dateForSave;
                        }else{if(textView == textViewEndDate){
                            endDate = date;
                            endDateForSave = dateForSave;
                        }
                        }
                    }
                },
                        // Date dicker shows the current day at the opening
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                selector.show();
            }
        });
    }

    //----------------------------------------------------------------------------------------------
    // Get Date
    //----------------------------------------------------------------------------------------------

    /**
     * This method is going to add date wich was selected in the date picker into a HashMap
     * @param optionsMap is the HashMap wich correspond to search parameters
     */
    private void saveDate(HashMap<String, String> optionsMap){

        // If a date is selected, we save it
        if(beginDate != null){optionsMap.put(MyConstants.BEGIN_DATE, beginDateForSave);}
        if(endDate != null){optionsMap.put(MyConstants.END_DATE, endDateForSave);}

        Log.d("optionsMap saveDates", optionsMap.toString());
    }

    //----------------------------------------------------------------------------------------------
    // Http request
    //----------------------------------------------------------------------------------------------

    /**
     * This method execute an API request with some parameters and show the result by starting a new activity
     * @param optionsMap is a HashMap that correspond to parameters
     */
    private void executeSearchRequest(final HashMap<String, String> optionsMap) {
        Log.d("HashMap options", optionsMap.toString());
        this.disposable = NewYorkTimesStream.streamFetchQueryRequest(optionsMap)
                .subscribeWith(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {

                        Log.d("Result list :" ,searchResponse.getTheResponse().getDocs().toString());
                        if(searchResponse.getTheResponse().getDocs().size() >=1){

                            // Convert response to string for data
                            Gson gson = new Gson();
                            String stringResponse = gson.toJson(searchResponse);
                            //Log.d("Response convert string", stringResponse);

                            // Start New activity
                            Intent intent = new Intent(getApplicationContext(), SearchResultActivity.class);
                            intent.putExtra(MyConstants.STRING_RESPONSE , stringResponse);
                            startActivity(intent);}

                        else{searchRequestParameters.buildAndShowAlertDialog(getString(R.string.no_result_title),getString(R.string.no_result_message) ); }
                    }

                    @Override
                    public void onError(Throwable e) { Log.e("Request error", e.getMessage()); }

                    @Override
                    public void onComplete() {}
                });
    }


    //----------------------------------------------------------------------------------------------
    // On Destroy
    //----------------------------------------------------------------------------------------------

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

}

