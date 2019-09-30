package com.application.seb.projet5_mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.seb.projet5_mynews.Model.SearchResponse;
import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.FormatDate;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

class SearchViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private ImageView imageView;

    SearchViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.fragment_page_item0_title);
        textView2 = itemView.findViewById(R.id.fragment_page_item0_date);
        textView3 = itemView.findViewById(R.id.fragment_page_item0_section);
        imageView = itemView.findViewById(R.id.fragment_page_item0_image);
    }

    void updateWithBusinessSearchArticles(SearchResponse.Doc result, RequestManager glide) {
        Log.d("Business title", result.getSnippet());
        textView.setText(result.getSnippet());
        textView2.setText(FormatDate.convertBusinessDate(result.getPubDate()));
        textView3.setText(result.getSectionName());

        // Load image and set default image
        String url= null;
        if (result.getMultimedia().size()>= 1){
            Log.d("BUSINESS PHOTO URL", "https://static01.nyt.com/" + result.getMultimedia().get(0).getUrl());
            url = "https://static01.nyt.com/" + result.getMultimedia().get(0).getUrl();}
        glide.load(url)
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.no_mage)
                .error(R.drawable.no_mage)
                .into(imageView);

    }

}
