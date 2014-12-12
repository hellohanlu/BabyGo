package com.hl.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;

public class WebmmActivity extends Activity{
	private WebView webview;

	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		webview=new WebView(this);
		webview.loadUrl("http://www.bamaol.com/Article/Articlecate.html?cid=39");
		setContentView(webview);
	}
}