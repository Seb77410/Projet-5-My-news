package com.application.seb.projet5_mynews.Views;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.application.seb.projet5_mynews.Model.SearchResponse;
import com.application.seb.projet5_mynews.R;
import com.bumptech.glide.RequestManager;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchViewHolder> {


    // For data
    private List<SearchResponse.Doc> results;
    private RequestManager glide;

    // Constructor
    public SearchAdapter(List<SearchResponse.Doc> results, RequestManager glide){
        this.results = results;
        this.glide = glide;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_page_item, viewGroup, false);

        Log.d("Objet", results.toString());

        return new SearchViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder businessViewHolder, int i) {
        SearchResponse.Doc result = results.get(i);
        businessViewHolder.updateWithBusinessSearchArticles(result, this.glide);
        Log.d("Objet", "results.size :" + results.size() + "/n" + results.toString());
    }

    @Override
    public int getItemCount() {
        return results.size();
    }
}
