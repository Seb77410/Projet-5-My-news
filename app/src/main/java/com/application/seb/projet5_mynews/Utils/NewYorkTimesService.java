package com.application.seb.projet5_mynews.Utils;

import com.application.seb.projet5_mynews.Model.MostPopularResponse;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewYorkTimesService {


    //----------------------------------------------------------------------------------------------
    // Most Popular request
    //----------------------------------------------------------------------------------------------
    @GET( MyConstants.MOST_POPULAR_BODY_REQUEST_1ST_PART + MyConstants.MOST_POPULAR_PATH + MyConstants.MOST_POPULAR_BODY_REQUEST_2ND_PART)
    Observable<MostPopularResponse> getPeriodAndApiKey(@Path(MyConstants.MOST_POPULAR_PATH) int period, @Query(MyConstants.REQUEST_API_KEY_QUERY) String apiKey);

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MyConstants.MOST_POPULAR_HEAD)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
