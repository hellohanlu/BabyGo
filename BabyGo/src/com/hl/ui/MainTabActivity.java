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
		TabHost.TabSpec homePageSpec = tabHost.newTabSpec("homepage");//TabSpec��TabHost���ڲ��࣬TabHost�����newTabSpec()����һ��TabSpec����
		homePageSpec.setIndicator("��ҳ", res.getDrawable(R.drawable.tab_home_btn));//setIndicator()�˷����������ñ�ǩ��ͼ��
		homePageSpec.setContent(homePageIntent);//ָ������
		tabHost.addTab(homePageSpec);
		
		Intent writeMicroBlogIntent = new Intent();
		writeMicroBlogIntent.setClass(this, WriteMicroBlogActivity.class);
		TabHost.TabSpec writeMicroBlogSpec = tabHost.newTabSpec("write");
		writeMicroBlogSpec.setIndicator("����Ϣ", res.getDrawable(R.drawable.tab_message_btn));
		writeMicroBlogSpec.setContent(writeMicroBlogIntent);
		tabHost.addTab(writeMicroBlogSpec);
		
		Intent personInfoIntent = new Intent();
		personInfoIntent.setClass(this, PersonInfoActivity.class);
		TabHost.TabSpec personInfoSpec = tabHost.newTabSpec("personInfo");
		personInfoSpec.setIndicator("������Ϣ", res.getDrawable(R.drawable.tab_selfinfo_btn));
		personInfoSpec.setContent(personInfoIntent);
		tabHost.addTab(personInfoSpec);
		
		Intent collectionIntent = new Intent();
		collectionIntent.setClass(this, CollectionActivity.class);
		TabHost.TabSpec collectionSpec = tabHost.newTabSpec("collection");
		collectionSpec.setIndicator("�ղ�", res.getDrawable(R.drawable.tab_more_btn));
		collectionSpec.setContent(collectionIntent);
		tabHost.addTab(collectionSpec);
		
		Intent searchIntent = new Intent();
		searchIntent.setClass(this, SearchActivity.class);
		TabHost.TabSpec searchSpec = tabHost.newTabSpec("search");
		searchSpec.setIndicator("����", res.getDrawable(R.drawable.tab_square_btn));
		searchSpec.setContent(searchIntent);
		tabHost.addTab(searchSpec);	
	}
	
}
