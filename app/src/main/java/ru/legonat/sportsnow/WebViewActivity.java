package ru.legonat.sportsnow;

/**
 * Created by Zenbook on 04.05.2015.
 */

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class WebViewActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_layout);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.webView, new WebViewUrlFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            switch (item.getItemId()) {
                // Respond to the action bar's Up/Home button
                case android.R.id.home:
                    onBackPressed();
                    return true;
            }
            return super.onOptionsItemSelected(item);
        }



    public static class WebViewUrlFragment extends Fragment {
        public WebViewUrlFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater,
                                 ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_web_view, container, false);

            Intent intent = getActivity().getIntent();
            String myURL = intent.getStringExtra("myURL");
            WebView wv = (WebView) rootView.findViewById(R.id.mwebView);
            wv.clearHistory();
            wv.clearFormData();

            wv.getSettings().setBuiltInZoomControls(true);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.getSettings().setLoadWithOverviewMode(true);
            wv.getSettings().setUseWideViewPort(true);
            wv.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
            wv.setScrollbarFadingEnabled(false);

            wv.loadUrl(myURL);
            wv.setWebViewClient(new WebViewClient());

            return rootView;
        }

    }
}
