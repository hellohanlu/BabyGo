package com.hl.ui;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.hl.entity.MicroBlogHP;
import com.hl.http.HttpDownload;
import com.hl.http.Storage;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MicroBlogDetailActivity extends Activity {
	
	private TextView tv_name;
	private EditText et_content;
	private TextView tv_time;
	private Button btn_transmit;
	//private Button btn_comment;
	private Button btn_collection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.microblogdetail);
		
		final MicroBlogHP hp = (MicroBlogHP)getIntent().getSerializableExtra("mbDetail");
		
		tv_name = (TextView)findViewById(R.id.mb_detail_username);
		et_content = (EditText)findViewById(R.id.mb_detail_content);
		tv_time =(TextView)findViewById(R.id.mb_detail_time);
		
		tv_name.setText(hp.getUserName());
		et_content.setText(hp.getContent());
		tv_time.setText(hp.getTime());

		btn_transmit = (Button)findViewById(R.id.mb_detail_transmit);
		btn_transmit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater factory = LayoutInflater.from(MicroBlogDetailActivity.this);
				final View dialogView = factory.inflate(R.layout.dialog_microblog_detail_operate, null);
				AlertDialog dlg = new AlertDialog.Builder(MicroBlogDetailActivity.this)
					.setTitle("微博转发")
					.setView(dialogView)
					.setPositiveButton("转发",new DialogInterface.OnClickListener() {
						private EditText mbd_tm_content;
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							mbd_tm_content = (EditText)dialogView.findViewById(R.id.dialog_content);
							String url = "/ClientMicroBlogAction!microBlogTransmit.action";
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("transmit.microBlog.id", hp.getMicroblogId().toString()));
							params.add(new BasicNameValuePair("transmit.user.id", Storage.getString(MicroBlogDetailActivity.this, "userId")));
							String content = mbd_tm_content.getText().toString();
							params.add(new BasicNameValuePair("transmit.content", content));
							
							String result = HttpDownload.sendPostHttpRequest(url, params);
							if("success".equals(result)){
								Toast.makeText(MicroBlogDetailActivity.this, "转发成功", Toast.LENGTH_LONG).show();
								MicroBlogDetailActivity.this.finish();
							}else{
								Toast.makeText(MicroBlogDetailActivity.this, result, Toast.LENGTH_LONG).show();
							}
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					})
					.create();
				dlg.show();
			}
		});
//		btn_comment = (Button)findViewById(R.id.mb_detail_comment);
//		btn_comment.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				LayoutInflater factory = LayoutInflater.from(MicroBlogDetailActivity.this);
//				final View dialogView = factory.inflate(R.layout.dialog_microblog_detail_operate, null);
//				AlertDialog dlg = new AlertDialog.Builder(MicroBlogDetailActivity.this)
//					.setTitle("发表评论")
//					.setView(dialogView)
//					.setPositiveButton("评论",new DialogInterface.OnClickListener() {
//						private EditText mbd_tm_content;
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							mbd_tm_content = (EditText)dialogView.findViewById(R.id.dialog_content);
//							String url = "/ClientMicroBlogAction!microBlogComment.action";
//							List<NameValuePair> params = new ArrayList<NameValuePair>();
//							params.add(new BasicNameValuePair("comment.microBlog.id", hp.getMicroblogId().toString()));
//							params.add(new BasicNameValuePair("comment.user.id", Storage.getString(MicroBlogDetailActivity.this, "userId")));
//							String content = mbd_tm_content.getText().toString();
//							params.add(new BasicNameValuePair("comment.content", content));
//							
//							String result = HttpDownload.sendPostHttpRequest(url, params);
//							if("success".equals(result)){
//								Toast.makeText(MicroBlogDetailActivity.this, "评论成功", Toast.LENGTH_LONG).show();
//								MicroBlogDetailActivity.this.finish();
//							}else{
//								Toast.makeText(MicroBlogDetailActivity.this, result, Toast.LENGTH_LONG).show();
//							}
//						}
//					})
//					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
//						
//						@Override
//						public void onClick(DialogInterface dialog, int which) {
//							// TODO Auto-generated method stub
//							
//						}
//					})
//					.create();
//				dlg.show();
//			}
//		});
		
		
		btn_collection = (Button)findViewById(R.id.mb_detail_collection);
		btn_collection.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AlertDialog dlg = new AlertDialog.Builder(MicroBlogDetailActivity.this)
					.setTitle("微博收藏")
					.setPositiveButton("收藏",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String url = "/ClientMicroBlogAction!microBlogCollection.action";
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("collection.microBlog.id", hp.getMicroblogId().toString()));
							params.add(new BasicNameValuePair("collection.user.id", Storage.getString(MicroBlogDetailActivity.this, "userId")));
							
							String result = HttpDownload.sendPostHttpRequest(url, params);
							if("success".equals(result)){
								Toast.makeText(MicroBlogDetailActivity.this, "收藏成功", Toast.LENGTH_LONG).show();
								MicroBlogDetailActivity.this.finish();
							}else{
								Toast.makeText(MicroBlogDetailActivity.this, result, Toast.LENGTH_LONG).show();
							}
						}
					})
					.setNegativeButton("取消", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					})
					.create();
				dlg.show();
			}
		});
	}

}