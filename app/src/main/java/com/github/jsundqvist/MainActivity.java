package com.github.jsundqvist;

import android.app.*;
import android.net.*;
import android.os.*;
import android.view.*;
import android.webkit.*;
import android.content.pm.*;

public class MainActivity extends Activity {
    
	private WebView myWebView;
	boolean watching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		myWebView = (WebView) findViewById(R.id.webview);
		myWebView.setWebViewClient(new MyWebViewClient());
		WebSettings webSettings = myWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
		myWebView.loadUrl("https://www.google.com/video");
    }

	private class MyWebViewClient extends WebViewClient
	{
		
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			return false;
		}
		
		@Override
		public void onPageFinished(WebView view, String url) {
			super.onPageFinished(view, url);
			if(!url.contains("google.")) {
				setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
				watching = true;
			}
			hide("div._muc");
			hide("div#koya_elem_0_17");
			setFullSize("div#player");
			//TODO after play event
			setFullSize("video");
			removeControls("video");
		}
		
		private void hide(String selector) {
			myWebView.evaluateJavascript("document.querySelector('"+selector+"').style.display='none';", null);
		}
		
		private void setFullSize(String selector) {
			myWebView.evaluateJavascript("document.querySelector('"+selector+"').style.top='0';", null);
			myWebView.evaluateJavascript("document.querySelector('"+selector+"').style.height='100%';", null);
		}
		
		private void removeControls(String selector) {
			myWebView.evaluateJavascript("document.querySelector('"+selector+"').removeAttribute('controls');", null);
		}
		
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (!isWatching())
			myWebView.goBack();
		return true;
	}
	
	public boolean isWatching() {
		return false; //TODO synchronous
	}
	
}
