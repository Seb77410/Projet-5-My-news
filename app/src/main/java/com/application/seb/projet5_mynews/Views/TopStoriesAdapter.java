package com.application.seb.projet5_mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.seb.projet5_mynews.Model.TopStoriesResponse;
import com.application.seb.projet5_mynews.R;
import com.bumptech.glide.RequestManager;

import java.util.List;

public class TopStoriesAdapter extends RecyclerView.Adapter<TopStoriesViewHolder> {




    // For data
    private List<TopStoriesResponse.TopStoriesResult> results;
    private RequestManager glide;

    // For return a Result object
    public TopStoriesResponse.TopStoriesResult getTopStoriesResult(int position){
        return this.results.get(position);
    }

    // Constructor
    public TopStoriesAdapter(List<TopStoriesResponse.TopStoriesResult> results, RequestManager glide){
        this.results = results;
        this.glide = glide;
    }

    @NonNull
    @Override
    public TopStoriesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_item, viewGroup, false);

        Log.d("Objet", results.toString());

        return new TopStoriesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopStoriesViewHolder topStoriesViewHolder, int i) {
        TopStoriesResponse.TopStoriesResult result = results.get(i);
        topStoriesViewHolder.updateWithTopStoriesrArticles(result, this.glide);
        Log.d("Objet", "results.size :" + results.size() + "/n" + results.toString());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
