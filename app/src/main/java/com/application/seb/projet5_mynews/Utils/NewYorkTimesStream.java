package com.application.seb.projet5_mynews.Utils;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.application.seb.projet5_mynews.Model.MostPopularResponse;
import com.application.seb.projet5_mynews.Model.SearchResponse;
import com.application.seb.projet5_mynews.Model.TopStoriesResponse;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NewYorkTimesStream {


    //----------------------------------------------------------------------------------------------
    // Observable for Most Popular api request
    //----------------------------------------------------------------------------------------------

    public static Observable<MostPopularResponse> streamFetchPeriodAndApiKey(int period, String apiKey){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit.create(NewYorkTimesService.class);
        return newYorkTimesService.getPeriodAndApiKey(period, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    //----------------------------------------------------------------------------------------------
    // Observable for Top Stories api request
    //----------------------------------------------------------------------------------------------

    public static Observable<TopStoriesResponse> streamFetchApiKey(String apiKey){
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit2.create(NewYorkTimesService.class);
        return newYorkTimesService.getApiKey(apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    //----------------------------------------------------------------------------------------------
    // Observable for Business article search api request
    //----------------------------------------------------------------------------------------------

    public static Observable<SearchResponse> streamFetchBusinessRequest(HashMap<String, String> optionsMap) {
        //Log.e("SECTION IN STREAM : ", options.toString() );
        NewYorkTimesService newYorkTimesService = NewYorkTimesService.retrofit3.create(NewYorkTimesService.class);
        return newYorkTimesService.getSearchBusinessParams(optionsMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}