package com.application.seb.projet5_mynews.Views;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.seb.projet5_mynews.Model.TopStoriesResponse;
import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.FormatDate;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;


class TopStoriesViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private ImageView imageView;

    TopStoriesViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.fragment_page_item0_title);
        textView2 = itemView.findViewById(R.id.fragment_page_item0_date);
        textView3 = itemView.findViewById(R.id.fragment_page_item0_section);
        imageView = itemView.findViewById(R.id.fragment_page_item0_image);
    }

    void updateWithTopStoriesrArticles(TopStoriesResponse.TopStoriesResult result, RequestManager glide) {
        Log.d("MULTIMEDIA", result.getMultimedia().toString());
        if (!result.getSubsection().isEmpty()){
            this.textView3.setText(result.getSection() + " > " + result.getSubsection());
        } else {
            this.textView3.setText(result.getSection());
        }
        this.textView.setText(result.getTitle());
        this.textView2.setText(FormatDate.convertTopStoriesDate(result.getPublishedDate()));

        if(!result.getMultimedia().isEmpty()) {
            Log.d("URL DE LA PHOTO", result.getMultimedia().get(0).getUrl());
            glide.load(result.getMultimedia().get(0).getUrl())
                    .apply(RequestOptions.centerCropTransform())
                    .placeholder(R.drawable.no_mage)
                    .error(R.drawable.no_mage)
                    .into(imageView);
        }else {imageView.setImageResource(R.drawable.no_mage);}

    }

}

