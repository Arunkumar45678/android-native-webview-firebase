package com.ase.aseapp;

import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

public class WebViewActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebViewClient(new AuthWebViewClient());
        setContentView(webView);

        webView.loadUrl("https://testase.gt.tc/xx");
    }

    private class AuthWebViewClient extends WebViewClient {

        @Override
        public WebResourceResponse shouldInterceptRequest(
                WebView view, WebResourceRequest request) {

            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            if (user == null) return super.shouldInterceptRequest(view, request);

            try {
                String token = user.getIdToken(false).getResult().getToken();

                Map<String, String> headers = new HashMap<>();
                headers.put("Authorization", "Bearer " + token);

                return super.shouldInterceptRequest(
                        view,
                        new WrappedRequest(request, headers)
                );

            } catch (Exception e) {
                return super.shouldInterceptRequest(view, request);
            }
        }
    }
}
