package com.application.seb.projet5_mynews.Activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.application.seb.projet5_mynews.R;
import com.application.seb.projet5_mynews.Utils.MyConstants;

import java.util.Objects;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        // Reference
        WebView webView = findViewById(R.id.webView);
        // New webView client
        webView.setWebViewClient(new WebViewClient());
        // Get url
        String url = Objects.requireNonNull(getIntent().getExtras()).getString(MyConstants.WEB_VIEW_URL);
        // Show url
        webView.loadUrl(url);
    }
}
