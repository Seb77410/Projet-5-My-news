package com.application.seb.projet5_mynews.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Views.MostPopularAdapter;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import com.application.seb.projet5_mynews.Model.MostPopularResponse;
import com.application.seb.projet5_mynews.Utils.MyConstants;
import com.application.seb.projet5_mynews.Utils.NewYorkTimesStream;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * A simple {@link Fragment} subclass.
 */
public class PageFragment extends Fragment {


    public PageFragment() {
        // Required empty public constructor
    }


    // Create keys and value for our Bundle
    private static final String KEY_POSITION = "position";
    private static int position = 0;

    // For data
    private MostPopularAdapter adapter;
    private Disposable disposable;
    private static List<MostPopularResponse.MostPopularResult> mostPopularResultList = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Get layout of PageFragment
        View pagerResult = inflater.inflate(R.layout.fragment_page, container, false);

        // Get widgets from layout and serialise it
        RecyclerView recyclerView = pagerResult.findViewById(R.id.recyclerView);
        SwipeRefreshLayout requestPullRefresh = pagerResult.findViewById(R.id.fragment_page_pull_refresh);

        this.executeMostPopularRequest(requestPullRefresh, recyclerView);
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_page, container, false);
    }




    /**
     * This method execute most popular http request
     * @param requestPullRefresh is recycler view pull refresh
     * @param recyclerView is PageFragment recycler view
     */
    private void executeMostPopularRequest(final SwipeRefreshLayout requestPullRefresh, final RecyclerView recyclerView) {

        // Execute the stream subscribing to Observable defined inside NewYorkTimesStream
        this.disposable = NewYorkTimesStream.streamFetchPeriodAndApiKey(1, MyConstants.API_KEY)
                .subscribeWith(new DisposableObserver<MostPopularResponse>() {
                    @Override
                    public void onNext(MostPopularResponse response) {
                        Log.d("MostPopular Response", response.toString());
                        requestPullRefresh.setRefreshing(false);
                        mostPopularResultList.clear();
                        configureRecyclerView(response, recyclerView);
                        mostPopularResultList.addAll(response.getResults());
                        adapter.notifyDataSetChanged();
                        //Log.d("MYData When update UI", mostPopularResponse.toString());
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.e("REQUEST ERROR", e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    /**
     * Configure RecyclerView, Adapter, LayoutManager & glue it together for most popular http request
     * @param mostPopularResponse is most popular http request response
     * @param recyclerView is pageFragment instance recycler view
     */
    private void configureRecyclerView(MostPopularResponse mostPopularResponse, RecyclerView recyclerView) {
        // Create adapter passing the list of articles
        adapter = new MostPopularAdapter(mostPopularResponse.getResults(), Glide.with(this));
        // Attach the adapter to the recycler view to populate items
        recyclerView.setAdapter(adapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

}
