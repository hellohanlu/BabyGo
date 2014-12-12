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
import com.hl.http.GetBitmap;
import com.hl.http.HttpDownload;
import com.hl.http.Storage;
import com.hl.ui.HomePageActivity.ListAdapter;
import com.hl.ui.HomePageActivity.ViewHolder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class UserMicroBlogActivity extends Activity{

	private static String TAG="UserMicroBlogActivity";
	public static String json;
	private List<MicroBlogHP> list = null;
	private ListAdapter adapter;
	private ListView listView;
	
	//分页变量
	private int pageNow = 1;
	private int pageCount = 0;
	private int lastItem = 0;
	private int itemCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_list);

		list = new ArrayList<MicroBlogHP>();
		shareJSON();
		adapter = new ListAdapter(this, list);
		listView = (ListView) findViewById(R.id.tab_homepage_list);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.i(TAG, "OnClick >>> "+list.get(position).getContent());
//				MicroBlogHP hp = list.get(position);
//				Intent intent = new Intent();
//				intent.putExtra("mbDetail", hp);
//				intent.setClass(UserMicroBlogActivity.this, MicroBlogDetailActivity.class);
//				UserMicroBlogActivity.this.startActivity(intent);
			}
		});
		
		listView.setOnScrollListener(new OnScrollListener() {
			
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				Log.i(TAG , "Scroll>>>first: " + firstVisibleItem + ", visible: " + visibleItemCount + ", total: " + totalItemCount);
				
			}
		});
		
	}
	
	private void shareJSON(){
		String url = "/ClientMicroBlogAction!getUserMicroBlog.action";
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNow", pageNow+""));
		params.add(new BasicNameValuePair("userId", Storage.getString(UserMicroBlogActivity.this, "userId")));
		
		
		json = HttpDownload.sendPostHttpRequest(url, params);
		try {
			// 获取根节点
			JSONObject root = new JSONObject(json.toString());
			pageCount = root.getInt("pageCount");
			JSONArray items = root.getJSONArray("results");
			for (int i = 0; i < items.length(); i++) {
				MicroBlogHP share = new MicroBlogHP();
				share.setMicroblogId(Integer.parseInt(items.getJSONObject(i).getString("microBlogId")));
				share.setUserId(Integer.parseInt(items.getJSONObject(i).getString("userId")));
				share.setUserName(items.getJSONObject(i).getString("userName"));
				share.setUserPicture(GlobalVariate.SERVERADDRESS+"/headPhoto/"+items.getJSONObject(i).getString("userPhoto"));
				share.setContent(items.getJSONObject(i).getString("content"));
				share.setTime(items.getJSONObject(i).getString("time"));
				list.add(share);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}	
	}
	public static class ViewHolder {
		ImageView imageView;
		TextView userName;
		TextView microBlogTime;
		TextView microBlogContent;
		ImageView userPicture;
	}
	class ListAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<MicroBlogHP> list;
		
		public ListAdapter(Context context, List<MicroBlogHP> list) {
			// TODO Auto-generated constructor stub
			inflater = LayoutInflater.from(context);
			this.list = list;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder viewHolder = null;
			if(convertView == null){
				convertView = inflater.inflate(R.layout.homepage_item, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView)convertView.findViewById(R.id.hp_userPhoto);
				viewHolder.userName =(TextView)convertView.findViewById(R.id.hp_username);
				viewHolder.microBlogTime = (TextView)convertView.findViewById(R.id.hp_microBlogTime);
				viewHolder.microBlogContent = (TextView)convertView.findViewById(R.id.hp_microBlogContent);
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.user_headphoto2));
			String url = list.get(position).getUserPicture();
			Bitmap bm = GetBitmap.getBitmap(url);
			viewHolder.imageView.setImageBitmap(bm);
			viewHolder.userName.setText(list.get(position).getUserName());
			viewHolder.microBlogTime.setText(list.get(position).getTime());
			viewHolder.microBlogContent.setText(list.get(position).getContent());
			return convertView;
		}
		
	}

}
