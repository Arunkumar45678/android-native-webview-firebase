package com.ase.aseapp;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;
    private ProgressBar spinner;
    private SwipeRefreshLayout swipeRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);

        webView = findViewById(R.id.webView);
        spinner = findViewById(R.id.loadingSpinner);
        swipeRefresh = findViewById(R.id.swipeRefresh);

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);

        webView.setWebViewClient(new AuthWebViewClient());

        loadPage();

        swipeRefresh.setOnRefreshListener(() -> {
            loadPage();
            swipeRefresh.setRefreshing(false);
        });
    }

    private void loadPage() {
        if (!isOnline()) {
            webView.loadUrl("file:///android_asset/offline.html");
            return;
        }
        webView.loadUrl("https://testase.gt.tc");
    }

    private boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnected();
    }

    private class AuthWebViewClient extends WebViewClient {

        @Override
        public void onPageStarted(WebView view, String url, android.graphics.Bitmap favicon) {
            spinner.setVisibility(View.VISIBLE);
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            spinner.setVisibility(View.GONE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            Map<String, String> headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + TokenStore.getIdToken());
            view.loadUrl(request.getUrl().toString(), headers);
            return true;
        }
    }
}
