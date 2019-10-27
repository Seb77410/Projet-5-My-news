package com.application.seb.projet5_mynews.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.seb.projet5_mynews.Activities.WebViewActivity;
import com.application.seb.projet5_mynews.Model.SearchResponse;
import com.application.seb.projet5_mynews.Model.MostPopularResponse;
import com.application.seb.projet5_mynews.Model.TopStoriesResponse;
import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.ItemClickSupport;
import com.application.seb.projet5_mynews.Utils.MyConstants;
import com.application.seb.projet5_mynews.Utils.NewYorkTimesStream;
import com.application.seb.projet5_mynews.Views.SearchAdapter;
import com.application.seb.projet5_mynews.Views.MostPopularAdapter;
import com.application.seb.projet5_mynews.Views.TopStoriesAdapter;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class PageFragment extends Fragment {

    // Create keys and value for our Bundle
    private static final String KEY_POSITION = "position";
    private static int position = 0;

    // For data
    private MostPopularAdapter adapter;
    private TopStoriesAdapter topStoriesAdapter;
    private SearchAdapter searchAdapter;
    private Disposable disposable;
    private Disposable disposable2;
    private Disposable disposable3;
    private static List<MostPopularResponse.MostPopularResult> mostPopularResultList = new ArrayList<>();
    private static List<TopStoriesResponse.TopStoriesResult> topStoriesResults = new ArrayList<>();
    private static List<SearchResponse.Doc> searchResults = new ArrayList<>();
    private static List<SearchResponse.Doc> businessResults = new ArrayList<>();


    //----------------------------------------------------------------------------------------------
    // Constructor
    //----------------------------------------------------------------------------------------------

    // Constructor
    public PageFragment() {}

    /**
     * Method that will create a new instance of PageFragment, and add data to its bundle.
     * @param position - is fragment position in view pager (value could 0, 1 or 2)
     *                 - for position = -1, that create a new instance for the SearchResultActivity
     * @param bundle - for position 0,1 or 2 : must be a new Bundle
     *               - for position -1 : bundle have to contains search request response string value
     * @return new PageFragment instance
     */
    public static PageFragment newInstance(int position, Bundle bundle) {
        Log.d("PageFragment", "New instance create");
        // 2.1 Create new fragment
        PageFragment frag = new PageFragment();
        // 2.2 Create bundle and add it some data
        bundle.putInt(KEY_POSITION, position);
        Log.d("PageFragmentNewInstance", "bundle after send to new fragment instance : " + bundle);
        frag.setArguments(bundle);
        return (frag);
    }

    //----------------------------------------------------------------------------------------------
    // On Create
    //----------------------------------------------------------------------------------------------

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Get layout of PageFragment
        View pagerResult = inflater.inflate(R.layout.fragment_page, container, false);
        // Get widgets from layout and serialise it
        RecyclerView recyclerView = pagerResult.findViewById(R.id.recyclerView);
        SwipeRefreshLayout requestPullRefresh = pagerResult.findViewById(R.id.fragment_page_pull_refresh);

        // Configure the view refresh
        this.configureSwipeRefreshLayout(recyclerView, requestPullRefresh);
        // Get arguments to bundle
        final Bundle bundle = getArguments();
        //Execute request  and show result according position argument
        this.executeHttpRequest(recyclerView,requestPullRefresh,getActivity(), bundle);
        Log.d("Business search result","into onCreateView after http request : " + businessResults);
        this.configureOnClickRecyclerView(recyclerView);

        return pagerResult;
    }

    // ---------------------------------------------------------------------------------------------
    // CONFIGURATION
    // ---------------------------------------------------------------------------------------------

    /**
     * This method configure the recycler view pull refresh
     * @param recyclerView we want to add pull refresh
     * @param requestPullRefresh to glue to the recycler view
     */
    private void configureSwipeRefreshLayout(final RecyclerView recyclerView, final SwipeRefreshLayout requestPullRefresh) {
        requestPullRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                executeHttpRequest(recyclerView, requestPullRefresh,getActivity(), getArguments());
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
    /**
     * Configure RecyclerView, Adapter, LayoutManager & glue it together for top stories http request
     * @param topStoriesResponse is top stories http request response
     * @param recyclerView is pageFragment instance recycler view
     */
    private void configureRecyclerView(TopStoriesResponse topStoriesResponse, RecyclerView recyclerView) {
        // Create adapter passing the list of articles
        topStoriesAdapter = new TopStoriesAdapter(topStoriesResponse.getTopStoriesResults(), Glide.with(this));
        // Attach the adapter to the recycler view to populate items
        recyclerView.setAdapter(topStoriesAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    /**
     * Configure RecyclerView, Adapter, LayoutManager & glue it together for business search http request
     * @param searchResponse is business search http request response
     * @param recyclerView is pageFragment instance recycler view
     */
    private void configureRecyclerView(SearchResponse searchResponse, RecyclerView recyclerView, Activity activity) {
        // Create adapter passing the list of articles
        searchAdapter = new SearchAdapter(searchResponse.getTheResponse().getDocs(), Glide.with(activity));
        // Attach the adapter to the recycler view to populate items
        recyclerView.setAdapter(searchAdapter);
        // Set layout manager to position the items
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    /**
     *Configure item click on RecyclerView
     * @param recyclerView is item recycler view
     */
    private void configureOnClickRecyclerView(RecyclerView recyclerView) {
        ItemClickSupport.addTo(recyclerView, R.layout.fragment_page_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int mPosition, View v) {

                        if (getArguments() != null) {position = getArguments().getInt(KEY_POSITION);}
                        Log.d("On click recycler view", "Fragment position into view pager (current Item) : " + position);
                        Log.d("On click recycler view", "Item position into recycler view : " + mPosition);

                        String url = null;
                        if (position == 0 && topStoriesResults.get(mPosition).getUrl() != null) {
                            url = topStoriesResults.get(mPosition).getUrl();
                        }
                        if (position == 1 && mostPopularResultList.get(mPosition).getUrl() != null) {
                            url = mostPopularResultList.get(mPosition).getUrl();
                        }
                        if (position == 2 && businessResults.get(mPosition).getWebUrl() != null) {
                            url = businessResults.get(mPosition).getWebUrl();
                        }
                        if (position == -1 && searchResults.get(mPosition).getWebUrl() != null) {
                            url = searchResults.get(mPosition).getWebUrl();
                        }

                        Intent intent = new Intent(getActivity(), WebViewActivity.class);
                        Log.d("Web View Url : ", url);
                        intent.putExtra(MyConstants.WEB_VIEW_URL, url);
                        startActivity(intent);
                    }
                });
    }


    //----------------------------------------------------------------------------------------------
    // HTTP Request with RxJava and Retrofit
    //----------------------------------------------------------------------------------------------

    /**
     * This method will define fragment behavior according position value
     * For position = 0, 1 or 2 :  Execute the request according position argument
     * Fro position = -1 : Get and show response from argument
     * @param recyclerView is view pager recycler view
     * @param requestPullRefresh is recycler view pull refresh
     * @param activity is the current activity
     * @param bundle is fragment argument
     */
    private void executeHttpRequest(RecyclerView recyclerView, SwipeRefreshLayout requestPullRefresh, Activity activity, Bundle bundle) {

        // We get the view pager fragment position
        if (getArguments() != null) {position = getArguments().getInt(KEY_POSITION);}
        Log.d("PageFragment position :", String.valueOf(position));

        // Do something according position
        if (position == 0) {executeTopStoriesRequest(requestPullRefresh, recyclerView);}
        if (position == 1) {executeMostPopularRequest(requestPullRefresh, recyclerView);}
        if (position == 2) {
            // Make a quest HasMap for http request query
            HashMap<String, String> optionsMap = new HashMap<>();
            optionsMap.put(MyConstants.QUERY, "");
            optionsMap.put(MyConstants.APIKEY, MyConstants.API_KEY);
            optionsMap.put(MyConstants.QUERY_FILTERS, MyConstants.NEWS_DESK+"(" + MyConstants.BUSINESS_SECTION +")");
            // And execute the associate Http request
            executeBusinessRequest(optionsMap, requestPullRefresh, recyclerView, activity);}
        if (position == -1) {
            // Get the string response from arguments passing by search activity
            SearchResponse searchResponse = getStringResponseToResponseObject(bundle);
            requestPullRefresh.setRefreshing(false);
            // And show the result
            if (searchResponse != null) {
                searchResults.clear();
                searchResults.addAll(searchResponse.getTheResponse().getDocs());
                recyclerView.setLayoutManager(new LinearLayoutManager(activity));
                configureRecyclerView(searchResponse, recyclerView, activity);
                searchAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     *The method transform String response value to SearchResponse object
     *
     * @param bundle is PageFragment argument that contains the string response value
     * @return SearchResponse object
     */
    private SearchResponse getStringResponseToResponseObject(Bundle bundle){
        String response = bundle.getString(MyConstants.STRING_RESPONSE);
        Log.d("PageFragment.","get string response for fragment position -1 : " + response);
        // Convert string response to SearchResponse Object
        Gson gson = new Gson();
        Type type = new TypeToken<SearchResponse>() {}.getType();
        return gson.fromJson(response, type);
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
     * This method execute TopStories http request
     * @param requestPullRefresh is recycler view pull refresh
     * @param recyclerView is PageFragment recycler view
     */
    private void executeTopStoriesRequest(final SwipeRefreshLayout requestPullRefresh, final RecyclerView recyclerView) {
        this.disposable2 = NewYorkTimesStream.streamFetchApiKey(MyConstants.API_KEY)
                .subscribeWith(new DisposableObserver<TopStoriesResponse>() {
                    @Override
                    public void onNext(TopStoriesResponse response) {
                        Log.d("TopStories Response", response.toString());
                        configureRecyclerView(response, recyclerView);
                        requestPullRefresh.setRefreshing(false);
                        topStoriesResults.clear();
                        topStoriesResults.addAll(response.getTopStoriesResults());
                        topStoriesAdapter.notifyDataSetChanged();
                        //Log.d("MYData When update UI", topStoriesResponse.getTopStoriesResults().toString());
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
     * This method execute Business search http request
     * @param requestPullRefresh is recycler view pull refresh
     * @param recyclerView is PageFragment recycler view
     */
    private void executeBusinessRequest(final HashMap<String, String> optionsMap, final SwipeRefreshLayout requestPullRefresh, final RecyclerView recyclerView, final Activity activity) {
        Log.d("HashMap Options", optionsMap.toString());
        this.disposable3 = NewYorkTimesStream.streamFetchBusinessRequest(optionsMap)
                .subscribeWith(new DisposableObserver<SearchResponse>() {
                    @Override
                    public void onNext(SearchResponse searchResponse) {
                        Log.d("Business Response", searchResponse.toString());
                        requestPullRefresh.setRefreshing(false);
                        businessResults.clear();
                        configureRecyclerView(searchResponse, recyclerView, activity);
                        businessResults.addAll(searchResponse.getTheResponse().getDocs());
                        //Log.d("Business result", businessResults);
                        searchAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
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
        if (this.disposable2 != null && !this.disposable2.isDisposed()) this.disposable2.dispose();
        if (this.disposable3 != null && !this.disposable3.isDisposed()) this.disposable3.dispose();
    }

}