package com.ase.ase12345;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.*;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);
        setContentView(R.layout.activity_webview);

        String token = getIntent().getStringExtra("TOKEN");

        WebView w = findViewById(R.id.webview);
        w.getSettings().setJavaScriptEnabled(true);
        w.getSettings().setDomStorageEnabled(true);
        w.setWebChromeClient(new WebChromeClient());

        w.addJavascriptInterface(new Object() {

            @JavascriptInterface
            public String getToken() {
                return token;
            }

            @JavascriptInterface
            public void logout() {
                FirebaseAuth.getInstance().signOut();
                startActivity(
                    new Intent(WebViewActivity.this,
                        LoginActivity.class));
                finish();
            }
        }, "Android");

        w.loadUrl("https://yourwebsite.com");
    }
}
