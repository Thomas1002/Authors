package com.mirabellehegnet.authors;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.webkit.WebView;
import android.widget.Button;

public class JabberActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jabber);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    public void openWikipedia(View view)
    {
        String url = "http://en.wikipedia.org/wiki/Jabberwocky";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.jabber, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        MediaPlayer mediaPlayer;
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_jabber, container, false);
            final WebView webView = (WebView) rootView.findViewById(R.id.wikiWebView);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                    if ((keyCode == KeyEvent.KEYCODE_BACK) && webView.canGoBack()) {
                        webView.goBack();
                        return true;
                    }
                    return false;
                }
            });
            Button imageButton = (Button) rootView.findViewById(R.id.imageButton);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    webView.loadUrl("file:///android_asset/jabberwocky.jpg");
                }
            });

            try {
                webView.loadUrl("file:///android_asset/jabberwocky.html");
            } catch (Exception ex) {
                Log.e("Banana", ex.getMessage());
            }
            return rootView;
        }

        @Override
        public void onResume() {
            Log.e("Banana","onResume");
            mediaPlayer = MediaPlayer.create(getActivity(), R.raw.riot);
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            super.onResume();
        }

        @Override
        public void onPause() {
            Log.e("Banana","onPause!");
            mediaPlayer.stop();
            mediaPlayer.release();
            super.onPause();
        }
    }
}
