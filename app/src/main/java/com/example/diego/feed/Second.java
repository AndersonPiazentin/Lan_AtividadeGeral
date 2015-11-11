package com.example.diego.feed;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;


public class Second extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);
        WebView w1=(WebView)findViewById(R.id.webView);
        w1.loadUrl("http://www.ragazzid.com.br/rss.xml");
    }
}
