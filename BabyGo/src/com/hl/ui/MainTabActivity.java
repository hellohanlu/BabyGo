package com.hl.ui;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_tab_layout);
		
		TabHost tabHost = getTabHost();
		
		Resources res = getResources();
		
		Intent homePageIntent = new Intent();
		homePageIntent.setClass(this, HomePageActivity.class);
		TabHost.TabSpec homePageSpec = tabHost.newTabSpec("homepage");//TabSpec是TabHost的内部类，TabHost对象的newTabSpec()返回一个TabSpec对象
		homePageSpec.setIndicator("首页", res.getDrawable(R.drawable.tab_home_btn));//setIndicator()此方法用来设置标签和图表
		homePageSpec.setContent(homePageIntent);//指定内容
		tabHost.addTab(homePageSpec);
		
		Intent writeMicroBlogIntent = new Intent();
		writeMicroBlogIntent.setClass(this, WriteMicroBlogActivity.class);
		TabHost.TabSpec writeMicroBlogSpec = tabHost.newTabSpec("write");
		writeMicroBlogSpec.setIndicator("发信息", res.getDrawable(R.drawable.tab_message_btn));
		writeMicroBlogSpec.setContent(writeMicroBlogIntent);
		tabHost.addTab(writeMicroBlogSpec);
		
		Intent personInfoIntent = new Intent();
		personInfoIntent.setClass(this, PersonInfoActivity.class);
		TabHost.TabSpec personInfoSpec = tabHost.newTabSpec("personInfo");
		personInfoSpec.setIndicator("个人信息", res.getDrawable(R.drawable.tab_selfinfo_btn));
		personInfoSpec.setContent(personInfoIntent);
		tabHost.addTab(personInfoSpec);
		
		Intent collectionIntent = new Intent();
		collectionIntent.setClass(this, CollectionActivity.class);
		TabHost.TabSpec collectionSpec = tabHost.newTabSpec("collection");
		collectionSpec.setIndicator("收藏", res.getDrawable(R.drawable.tab_more_btn));
		collectionSpec.setContent(collectionIntent);
		tabHost.addTab(collectionSpec);
		
		Intent searchIntent = new Intent();
		searchIntent.setClass(this, SearchActivity.class);
		TabHost.TabSpec searchSpec = tabHost.newTabSpec("search");
		searchSpec.setIndicator("查找", res.getDrawable(R.drawable.tab_square_btn));
		searchSpec.setContent(searchIntent);
		tabHost.addTab(searchSpec);	
	}
	
}
