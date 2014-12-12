package com.hl.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.hl.http.HttpDownload;
import com.hl.http.Storage;

import android.R.integer;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class WriteMicroBlogActivity extends Activity {
	
	private EditText et_mb_content;
	private TextView tv_mb_contentSize;
	private Button btn_send;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.writemb);
		
		et_mb_content = (EditText)findViewById(R.id.tab_wmb_Content);
		tv_mb_contentSize = (TextView)findViewById(R.id.tab_wmb_contentSize);
//		tv_mb_contentSize.setText("test");
		btn_send = (Button)findViewById(R.id.tab_wmb_sendmb);
		
		et_mb_content.addTextChangedListener(new TextWatcher() {
			private CharSequence temp;
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				temp = s;
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				int size = 140 - temp.length();
				tv_mb_contentSize.setText(size+"");
			}
		});
		
		btn_send.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String url = "/ClientMicroBlogAction!saveMicroBlog.action";
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				System.out.println(et_mb_content.getText().toString());
				String content = et_mb_content.getText().toString();
				params.add(new BasicNameValuePair("microBlog.content", content));
				params.add(new BasicNameValuePair("userId", Storage.getString(WriteMicroBlogActivity.this, "userId")));
				String result = HttpDownload.sendPostHttpRequest(url, params);
				if("success".equals(result)){
					Toast.makeText(WriteMicroBlogActivity.this, "发布成功", Toast.LENGTH_LONG);
					Intent intent = new Intent();
					intent.setClass(WriteMicroBlogActivity.this, HomePageActivity.class);
					WriteMicroBlogActivity.this.startActivity(intent);
				}else{
					Toast.makeText(WriteMicroBlogActivity.this, "发布失败", Toast.LENGTH_LONG);
				}
				
			}
		});
		
		
	}
}
