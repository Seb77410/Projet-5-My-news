package com.application.seb.projet5_mynews.Views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.seb.projet5_mynews.Model.MostPopularResponse;
import com.application.seb.projet5_mynews.R;
import com.bumptech.glide.RequestManager;

import java.util.List;

public class MostPopularAdapter extends RecyclerView.Adapter<MostPopularViewHolder> {

    // For data
    private List<MostPopularResponse.MostPopularResult> results;
    private RequestManager glide;

    // Constructor
    public MostPopularAdapter(List<MostPopularResponse.MostPopularResult> results, RequestManager glide){
        this.results = results;
        this.glide = glide;
    }

    @NonNull
    @Override
    public MostPopularViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_item, viewGroup, false);

        Log.d("Objet", results.toString());

        return new MostPopularViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MostPopularViewHolder newYorkTimesViewHolder, int i) {

        MostPopularResponse.MostPopularResult result = results.get(i);
        newYorkTimesViewHolder.updateWithMostPopularArticles(result, this.glide);
        Log.d("Objet", "results.size :" + results.size() + "/n" + results.toString());
    }

    @Override
    public int getItemCount() {

        if (results == null) {
            return 0;
        } else {
            return results.size();
        }
    }
}
