package com.application.seb.projet5_mynews.Utils;

import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.util.Log;

import com.application.seb.projet5_mynews.Model.SearchResponse;
import com.application.seb.projet5_mynews.R;

import java.util.Calendar;
import java.util.HashMap;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;


public class AlarmNotificationReceiver extends BroadcastReceiver {

    private NotificationManagerCompat notificationManager;
    HashMap<String, String> optionsMap = new HashMap<>();
    //List<SearchResponse.Doc> searchResults = new ArrayList<>();
    private SearchResponse mSearchResponse;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationManager = NotificationManagerCompat.from(context);
        Log.d("ALARME RECUE", "ALARME RECUE !");

        getIntentArgumentsToHashMap(intent);
        addCurrentDateToHashMap();

        // Execute Http request
        if(optionsMap.get(MyConstants.QUERY) != null && optionsMap.get(MyConstants.QUERY_FILTERS) != null) {
            executeSearchRequest(optionsMap, context);
        }
    }

    //----------------------------------------------------------------------------------------------
    // Get Http request parameters
    //----------------------------------------------------------------------------------------------

    private void getIntentArgumentsToHashMap(Intent intent){
        //Get arguments
        String searchRequestQuery = intent.getStringExtra(MyConstants.SEARCH_REQUEST_QUERY);
        String searchRequestFilterQuery = intent.getStringExtra(MyConstants.SEARCH_REQUEST_QUERY_FILTERS);
        Log.d("Alarm notifications","receiver arguments : " + searchRequestFilterQuery + "," + searchRequestQuery);
        // Put arguments to HashMap
        optionsMap.put(MyConstants.QUERY,searchRequestQuery);
        optionsMap.put(MyConstants.QUERY_FILTERS, searchRequestFilterQuery);
        optionsMap.put(MyConstants.APIKEY, MyConstants.API_KEY);
    }

    private void addCurrentDateToHashMap(){
        //Get current date
        Calendar cldr = Calendar.getInstance ();
        int day = cldr.get (Calendar. DAY_OF_MONTH );
        int month = cldr.get (Calendar.MONTH ) + 1 ;
        int year = cldr.get (Calendar.YEAR );
        String beginDate = FormatDate.convertDateForSave(year,month,day);
        Log.d("Alarm notifications","receiver current date : " + beginDate);
        // Put current date
        optionsMap.put(MyConstants.BEGIN_DATE, beginDate);

    }

    //----------------------------------------------------------------------------------------------
    // Http request
    //----------------------------------------------------------------------------------------------

    private void executeSearchRequest(final HashMap<String, String> optionsMap, final Context context) {
        Log.d("HashMap options", optionsMap.toString());
        Disposable disposable = NewYorkTimesStream.streamFetchQueryRequest(optionsMap)
                .subscribeWith(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {


                        Log.d("Result list size:", searchResponse.getTheResponse().getDocs().toString());
                        mSearchResponse = searchResponse;
                        //searchResults = searchResponse.getTheResponse().getDocs();
                        if (searchResponse.getTheResponse().getDocs().size() >= 1) {
                            sendOnChannel1(context);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("REQUEST ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    //----------------------------------------------------------------------------------------------
    // Create and send notification
    //----------------------------------------------------------------------------------------------

    /**
     * Create and send notification
     * @param context is the context
     */
    private void sendOnChannel1(Context context){

        Notification notification = new NotificationCompat.Builder(context, MyConstants.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.alarm)
                .setContentTitle(setNotificationTitle(context))
                .setContentText(setNotificationContentText(context))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1,notification);
    }

    /**
     * This method modify the notification title
     * @param context is the context
     * @return the notification title with String value
     */
    private String setNotificationTitle(Context context){
        String title;
        if (mSearchResponse.getTheResponse().getDocs().size()>1){
            title = context.getString(R.string.new_articles_title);
        }else {
            title = context.getString(R.string.new_article_title);
        }
        return title;
    }

    /**
     * This method modify the notification message
     * @param context is the context
     * @return the notification message with String value
     */
    private String setNotificationContentText(Context context){
        String contentText;
        if (mSearchResponse.getTheResponse().getDocs().size()>1){
            contentText = context.getString(R.string.new_article_message_1st_part) +" "+ (mSearchResponse.getTheResponse().getDocs().size()) +" "+context.getString(R.string.new_article_message_2nd_part);
        }else {
            contentText = context.getString(R.string.new_articles_message);
        }
        return contentText;
    }
}