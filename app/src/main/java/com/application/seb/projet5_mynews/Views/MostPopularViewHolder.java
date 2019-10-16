package com.application.seb.projet5_mynews.Views;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.application.seb.projet5_mynews.Model.MostPopularResponse;
import com.application.seb.projet5_mynews.R;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

class MostPopularViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private ImageView imageView;

    MostPopularViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.fragment_page_item0_title);
        textView2 = itemView.findViewById(R.id.fragment_page_item0_date);
        textView3 = itemView.findViewById(R.id.fragment_page_item0_section);
        imageView = itemView.findViewById(R.id.fragment_page_item0_image);
    }

    void updateWithMostPopularArticles(MostPopularResponse.MostPopularResult result, RequestManager glide) {
        Log.d("URL DE LA PHOTO", result.getMedia().get(0).getMediaMetadata().get(0).getUrl());
        this.textView.setText(result.getTitle());
        this.textView2.setText(result.getPublishedDate());
        this.textView3.setText(result.getSection());

        glide.load(result.getMedia().get(0).getMediaMetadata().get(0).getUrl())
                .apply(RequestOptions.centerCropTransform())
                .placeholder(R.drawable.no_image)
                .error(R.drawable.no_image)
                .into(imageView);

    }
}
