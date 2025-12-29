package com.ase.aseapp;

import android.net.Uri;
import android.webkit.WebResourceRequest;

import java.util.Map;

public class WrappedRequest implements WebResourceRequest {

    private final WebResourceRequest original;
    private final Map<String, String> headers;

    public WrappedRequest(WebResourceRequest original, Map<String, String> headers) {
        this.original = original;
        this.headers = headers;
    }

    @Override
    public Uri getUrl() {
        return original.getUrl();
    }

    @Override
    public boolean isForMainFrame() {
        return original.isForMainFrame();
    }

    @Override
    public boolean isRedirect() {
        return original.isRedirect();
    }

    @Override
    public boolean hasGesture() {
        return original.hasGesture();
    }

    @Override
    public String getMethod() {
        return original.getMethod();
    }

    @Override
    public Map<String, String> getRequestHeaders() {
        return headers;
    }
}
