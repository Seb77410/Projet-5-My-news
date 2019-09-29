package com.application.seb.projet5_mynews.Utils;

import java.util.concurrent.TimeUnit;

import com.application.seb.projet5_mynews.Model.MostPopularResponse;
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
}
