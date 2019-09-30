package com.application.seb.projet5_mynews.Model;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.MyConstants;

import java.util.ArrayList;
import java.util.HashMap;

public class SearchRequestParameters {

    //----------------------------------------------------------------------------------------------
    // Values
    //----------------------------------------------------------------------------------------------

    // Notifications Able / disable
    private boolean switchCompat;
    // Context
    private Context context;
    // Query words
    private Boolean bQuery;
    private transient EditText editText;
    private String query;
    private String queryFilter;
    // For save date
    private HashMap<String, String> optionsMap = new HashMap<>();
    // Total checkBox checked
    private int checkedBoxNumber = 0;
    //CheckBox
    private Boolean bFilterQuery;
    private CheckBox artCheckBox;
    private CheckBox businessCheckBox;
    private CheckBox entrepreneurCheckBox;
    private CheckBox politicCheckBox;
    private CheckBox sportCheckBox;
    private CheckBox travelCheckBox;
    // CheckBox list
    private ArrayList<CheckBox> checkBoxArrayList = new ArrayList<>();

    //----------------------------------------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------------------------------------

    public SearchRequestParameters(Context context) {
        this.context = context;
    }

    //----------------------------------------------------------------------------------------------
    // Getters / Setters
    //----------------------------------------------------------------------------------------------


    public String getQueryFilter() {
        return queryFilter;
    }

    public HashMap<String, String> getOptionsMap() {
        return optionsMap;
    }

    public void setSwitchCompat(boolean switchCompat) {
        this.switchCompat = switchCompat;
    }

    public Boolean getbQuery() {
        return bQuery;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }

    public EditText getEditText() {
        return editText;
    }

    public String getQuery() {
        return query;
    }

    public Boolean getbFilterQuery() {
        return bFilterQuery;
    }


    public void setArtCheckBox(CheckBox artCheckBox) {
        this.artCheckBox = artCheckBox;
    }

    public void setBusinessCheckBox(CheckBox businessCheckBox) {
        this.businessCheckBox = businessCheckBox;
    }

    public void setEntrepreneurCheckBox(CheckBox entrepreneurCheckBox) {
        this.entrepreneurCheckBox = entrepreneurCheckBox;
    }

    public void setPoliticCheckBox(CheckBox politicCheckBox) {
        this.politicCheckBox = politicCheckBox;
    }

    public void setSportCheckBox(CheckBox sportCheckBox) {
        this.sportCheckBox = sportCheckBox;
    }

    public void setTravelCheckBox(CheckBox travelCheckBox) {
        this.travelCheckBox = travelCheckBox;
    }

    //----------------------------------------------------------------------------------------------
    // Save data
    //----------------------------------------------------------------------------------------------

    /**
     * This method save EditText query word into HashMap and set bQuery boolean value to "true"
     */
    public void saveQueryWords(){

        // Reinitialise Query
        bQuery = false;

        // Get user query
        query = editText.getText().toString();

        // If at least one character is enter on the editText
        if (!query.isEmpty()) {
            // We save it as query
            optionsMap.put(MyConstants.QUERY, query);
            // And pass query boolean TRUE
            this.bQuery = true; }

        Log.d("optionsMap query saved ", optionsMap.toString());
    }

    /**
     * This method save CheckBox filter into HashMap and set bQueryFilters boolean value to "true"
     */
    public void saveFilterQuery(){

        // Reinitialise filters
        bFilterQuery = false;
        String sFilters = "";

        // Get filters by verify if checkbox are check
        if (this.artCheckBox.isChecked()){
            sFilters =  sFilters + "\"" + context.getString(R.string.Arts)+"\""; }
        if (this.businessCheckBox.isChecked()){
            sFilters = sFilters + "\"" + context.getString(R.string.Business)+"\"";}
        if (this.entrepreneurCheckBox.isChecked()){
            sFilters = sFilters + "\"" + context.getString(R.string.Entrepreneurs)+"\"";}
        if (this.politicCheckBox.isChecked()){
            sFilters = sFilters + "\"" + context.getString(R.string.Politics) + "\"";}
        if(this.sportCheckBox.isChecked()){
            sFilters = sFilters + "\"" + context.getString(R.string.Sports)+"\"";}
        if(this.travelCheckBox.isChecked()){
            sFilters = sFilters + "\"" + context.getString(R.string.Travel)+"\"";}

        // If at least one filter (or checkbox) is select
        if(!sFilters.isEmpty()){
            // We save filters
            queryFilter = MyConstants.NEWS_DESK+"("+ sFilters +")";
            optionsMap.put(MyConstants.QUERY_FILTERS, queryFilter);
            // And pas boolean query filter TRUE
            this.bFilterQuery = true;
            Log.d("optionsMap savedFilters", optionsMap.toString());}
    }

    //----------------------------------------------------------------------------------------------
    // Alert Dialog
    //----------------------------------------------------------------------------------------------

    /**
     * This method check if an AlertDialog have to appear on UI
     */
    public void checkForAlertDialog() {

        if (!getbFilterQuery() && getbQuery()){
            buildAndShowAlertDialog(context.getString(R.string.no_category_title), context.getString(R.string.no_category_message)); }
        if (!getbQuery() && getbFilterQuery()){
            buildAndShowAlertDialog( context.getString(R.string.no_query_title), context.getString(R.string.no_query_message));}
        if (!getbQuery() && !getbFilterQuery()){
            buildAndShowAlertDialog( context.getString(R.string.no_query_no_category_title), context.getString(R.string.no_query_no_category_message));}

    }

    /**
     * This method create AlertDialog for SearchActivity and NotificationActivity
     * @param alertTitle is tha AlertDialog title
     * @param alertMessage is the AlertDialog message
     */
    public void buildAndShowAlertDialog(CharSequence alertTitle, CharSequence alertMessage) {

        new AlertDialog.Builder(this.context)
                .setTitle(alertTitle)
                .setMessage(alertMessage)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    //----------------------------------------------------------------------------------------------
    // CheckBox listener for notifications
    //----------------------------------------------------------------------------------------------

    /**
     *This method check if user can uncheck CheckBox for NotificationActivity
     */
    public void onCheckedListener(){
        checkedBoxNumber = 0;
        checkBoxArrayList.add(artCheckBox);
        checkBoxArrayList.add(businessCheckBox);
        checkBoxArrayList.add(entrepreneurCheckBox);
        checkBoxArrayList.add(politicCheckBox);
        checkBoxArrayList.add(sportCheckBox);
        checkBoxArrayList.add(travelCheckBox);

        for (int x = 0; x < checkBoxArrayList.size(); x++) {
            CheckBox checkBox = checkBoxArrayList.get(x);
            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    whenCheckBoxIsChecked(buttonView);
                    whenCheckBoxIsUnchecked(buttonView);
                    Log.d("CheckBoxNumber", String.valueOf(checkedBoxNumber));
                }
            });
        }
    }

    /**
     * This method add 1 on total checked CheckBox and save filter query when notifications
     * are allowed if a CheckBox is check
     * @param buttonView is the checked that is check or uncheck
     */
    private void whenCheckBoxIsChecked(CompoundButton buttonView){
        if (buttonView.isChecked()) {
            //  We add 1 on total CheckBox checked
            checkedBoxNumber++;
            Log.d("CheckBoxNumber", String.valueOf(checkedBoxNumber));
            // And if notifications are allowed, we save parameters
            if (switchCompat){saveFilterQuery();}
        }

    }

    /**
     * This method verify if notifications are allowed by user when he uncheck a CheckBox.
     * If it is, we subtract 1 on total CheckBox checked
     * Else, we verify if total checked CheckBox is under 2.
     * If it is, we show an AlertDialog. Else we subtract 1 on total CheckBox checked
     * @param buttonView is the checked that is check or uncheck
     */
    private void whenCheckBoxIsUnchecked(CompoundButton buttonView){
        if (!buttonView.isChecked()) {
            // If notifications are allowed
            if (!switchCompat) {
                // We subtract 1 on total CheckBox checked
                checkedBoxNumber--;}
            // If notifications are not allowed
            else{
                // And if total CheckBox checked is under 2
                if (checkedBoxNumber < 2) {
                    // We notify user, with pop-op, he have to let at least 1 CheckBox checked
                    buildAndShowAlertDialog(context.getString(R.string.checkbox_not_uncheckable_title), context.getString(R.string.checkbox_not_uncheckable_message));
                    // And we don't uncheck the CheckBox
                    buttonView.setChecked(true);
                }
                // Else we subtract 1 on total CheckBox checked
                else{checkedBoxNumber--;}
            }
        }
    }


}


