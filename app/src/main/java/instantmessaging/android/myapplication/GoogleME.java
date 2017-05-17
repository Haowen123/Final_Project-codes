package instantmessaging.android.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class GoogleME extends AppCompatActivity {
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // this.getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_google_me);
       // getWindow().setFeatureInt(Window.FEATURE_PROGRESS,Window.PROGRESS_VISIBILITY_ON);
        webView=(WebView)findViewById(R.id.google);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.google.ie");

    }
}
