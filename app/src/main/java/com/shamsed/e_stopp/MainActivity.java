package com.shamsed.e_stopp;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private WebView myWebView;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //variable  in new website
        myWebView = (WebView) findViewById(R.id.webView);
        WebSettings webSettings=myWebView.getSettings();

        //enable javascript in webpage
        webSettings.setJavaScriptEnabled(true);

        //improve WebView performance
        myWebView.clearCache(true);
        myWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        myWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        myWebView.getSettings().setAppCacheEnabled(true);
        myWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSavePassword(true);
        webSettings.setSaveFormData(true);
        webSettings.setEnableSmoothTransition(true);

        //Connect to UL

        myWebView.loadUrl("http://e-stopp.org");



        // do the output in android layer
        myWebView.setWebViewClient(new WebViewClient() );

        //Add  progress bar whenever any click happen
        myWebView.setWebViewClient(new WebViewClient(){

            ProgressDialog pd = null;

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){

                pd=new ProgressDialog(MainActivity.this);
                pd.setTitle("Please Wait...");
                pd.setMessage("Starting......");
                pd.show();
                super.onPageStarted(view,url,favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url){
                pd.dismiss();
                super.onPageFinished(view,url);

            }
        });

        myWebView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.i("MainActivity", "view id: "+v.getId() +", event action: "+event.getAction());
                return false;
            }
        });

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    // adding back button functionality

    @Override
    public void onBackPressed() {

        if (myWebView.canGoBack()) {
            myWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.i("MainActivity", "view id: "+v.getId() +", event action: "+event.getAction());
        return false;
    }
}
