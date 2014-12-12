package com.hl.ui;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.hl.commons.GlobalVariate;
import com.hl.entity.MicroBlogHP;
import com.hl.entity.PersonInfo;
import com.hl.http.GetBitmap;
import com.hl.http.HttpDownload;
import com.hl.http.Storage;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoActivity extends Activity {
	
	private ImageView iv_picture;
	private TextView tv_name;
	private TextView tv_age;
	private TextView tv_lastLoginTime;
	
	private TextView tv_mbCount;
	private TextView tv_idolCount;
	private TextView tv_fansCount;
	
	private Button btn_follow;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.personinfo);
		
		iv_picture = (ImageView)findViewById(R.id.pi_Picture);
		tv_name = (TextView)findViewById(R.id.pi_userName);
		tv_age = (TextView)findViewById(R.id.pi_age);
		tv_lastLoginTime = (TextView)findViewById(R.id.pi_lastLoginTime);
		
		tv_mbCount = (TextView)findViewById(R.id.pi_microBlogCount);
		//tv_idolCount = (TextView)findViewById(R.id.pi_idolCount);
		//tv_fansCount = (TextView)findViewById(R.id.pi_fansCount);
		
		btn_follow = (Button)findViewById(R.id.pi_follow);
		tv_mbCount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				Toast.makeText(PersonInfoActivity.this, "Good", Toast.LENGTH_LONG).show();
				
				Intent intent = new Intent();
				intent.setClass(PersonInfoActivity.this, UserMicroBlogActivity.class);
				PersonInfoActivity.this.startActivity(intent);
				
			}
		});
		
//		tv_idolCount.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(PersonInfoActivity.this, UserFollowActivity.class);
//				intent.putExtra("follow", "idol");
//				PersonInfoActivity.this.startActivity(intent);
//			}
//		});
//		
//		tv_fansCount.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				Intent intent = new Intent();
//				intent.setClass(PersonInfoActivity.this, UserFollowActivity.class);
//				intent.putExtra("follow", "fans");
//				PersonInfoActivity.this.startActivity(intent);
//			}
//		});
		
		this.show();
	}
	
	public void show(){
		String url = "/ClientMicroBlogAction!getUserInfo.action";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userId", Storage.getString(PersonInfoActivity.this, "userId")));
		
		String result = HttpDownload.sendPostHttpRequest(url, params);
		try {
			// 获取根节点
			JSONObject root = new JSONObject(result.toString());
			JSONArray items = root.getJSONArray("results");
			
			PersonInfo pInfo = new PersonInfo();
			pInfo.setUserId(items.getJSONObject(0).getInt("userId"));
			pInfo.setUserName(items.getJSONObject(0).getString("userName"));
			pInfo.setUserAge(items.getJSONObject(0).getString("userAge"));
			pInfo.setUserPicture(GlobalVariate.SERVERADDRESS+"/headPhoto/"+items.getJSONObject(0).getString("userPicture"));
			pInfo.setLastLoginTime(items.getJSONObject(0).getString("lastLoginTime"));
			pInfo.setMbCount(items.getJSONObject(0).getInt("mbCount"));
			//pInfo.setIdolCount(items.getJSONObject(0).getInt("idolCount"));
			//pInfo.setFansCount(items.getJSONObject(0).getInt("fansCount"));
			
			String pictureUrl = pInfo.getUserPicture();
			Bitmap bm = GetBitmap.getBitmap(pictureUrl);
			iv_picture.setImageBitmap(bm);
			tv_name.setText("昵称："+pInfo.getUserName());
			tv_age.setText("年龄："+pInfo.getUserAge());
			tv_lastLoginTime.setText(pInfo.getLastLoginTime());
			tv_mbCount.setText("微博("+pInfo.getMbCount()+")");
			//tv_idolCount.setText("关注("+pInfo.getIdolCount()+")");
			//tv_fansCount.setText("粉丝("+pInfo.getFansCount()+")");
				
//				MicroBlogHP share = new MicroBlogHP();
//				share.setMicroblogId(Integer.parseInt(items.getJSONObject(i).getString("microBlogId")));
//				share.setUserId(Integer.parseInt(items.getJSONObject(i).getString("userId")));
//				share.setUserName(items.getJSONObject(i).getString("userName"));
//				share.setUserPicture(GlobalVariate.SERVERADDRESS+"/headPhoto/"+items.getJSONObject(i).getString("userPhoto"));
//				share.setContent(items.getJSONObject(i).getString("content"));
//				share.setTime(items.getJSONObject(i).getString("time"));
//				list.add(share);

		} catch (JSONException e) {

			e.printStackTrace();
		}
		
	}
}