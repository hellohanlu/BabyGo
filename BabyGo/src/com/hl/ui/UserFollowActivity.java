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
import com.hl.ui.HomePageActivity.ListAdapter;
import com.hl.ui.HomePageActivity.ViewHolder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;

public class UserFollowActivity extends Activity {
	private static String TAG="UserFollowActivity";
	public static String json;
	private List<PersonInfo> list = null;
	private ListAdapter adapter;
	private ListView listView;
	
	private String followType;
	
	//��ҳ����
	private int pageNow = 1;
	private int pageCount = 0;
	private int lastItem = 0;
	private int itemCount = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homepage_list);
		

		Intent intent = getIntent();
		followType = intent.getStringExtra("follow");
		
		list = new ArrayList<PersonInfo>();
		shareJSON();
		adapter = new ListAdapter(this, list);
		listView = (ListView) findViewById(R.id.tab_homepage_list);
		listView.setAdapter(adapter);
		
		if("idol".equals(followType)){
			listView.setOnItemLongClickListener(new OnItemLongClickListener() {
				
				@Override
				public boolean onItemLongClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					final PersonInfo pi = list.get(position);
					final int tmp = position;
					AlertDialog dlg = new AlertDialog.Builder(UserFollowActivity.this)
					.setTitle("ȡ������?")
					.setPositiveButton("ȷ��",new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							String url = "/ClientMicroBlogAction!deleteUserIdol.action";
							List<NameValuePair> params = new ArrayList<NameValuePair>();
							params.add(new BasicNameValuePair("follow.idol.id", pi.getUserId().toString()));
							params.add(new BasicNameValuePair("follow.fans.id", Storage.getString(UserFollowActivity.this, "userId")));
							
							String result = HttpDownload.sendPostHttpRequest(url, params);
							if("success".equals(result)){
								list.remove(tmp);
								adapter.notifyDataSetChanged();
								UserFollowActivity.this.onResume();
								Toast.makeText(UserFollowActivity.this, "ȡ���ɹ�", Toast.LENGTH_LONG).show();
							}else{
								Toast.makeText(UserFollowActivity.this, result, Toast.LENGTH_LONG).show();
							}
						}
					})
					.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}
					})
					.create();
					dlg.show();
					
					
					return false;
				}
				
			});
		}
		
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
		String url = "";
		if("idol".equals(followType)){
			url ="/ClientMicroBlogAction!getUserIdols.action";
		}else{
			url = "/ClientMicroBlogAction!getUserFanses.action";
		}
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("pageNow", pageNow+""));
		params.add(new BasicNameValuePair("userId", Storage.getString(UserFollowActivity.this, "userId")));
		
		json = HttpDownload.sendPostHttpRequest(url, params);
		
//		private Integer microblogId;
//		private Integer userId;
//		private String userName;
//		private String userPhoto;
//		private String Content;
//		private String Time;
		
		try {
			// ��ȡ���ڵ�
			JSONObject root = new JSONObject(json.toString());
			pageCount = root.getInt("pageCount");
			JSONArray items = root.getJSONArray("results");
			for (int i = 0; i < items.length(); i++) {
				PersonInfo pInfo = new PersonInfo();
				pInfo.setUserId(items.getJSONObject(i).getInt("userId"));
				pInfo.setUserName(items.getJSONObject(i).getString("userName"));
				pInfo.setUserAge(items.getJSONObject(i).getString("userAge"));
				pInfo.setUserPicture(GlobalVariate.SERVERADDRESS+"/headPhoto/"+items.getJSONObject(i).getString("userPicture"));
				pInfo.setLastLoginTime(items.getJSONObject(i).getString("lastLoginTime"));
				pInfo.setMbCount(items.getJSONObject(i).getInt("mbCount"));
				pInfo.setIdolCount(items.getJSONObject(i).getInt("idolCount"));
				pInfo.setFansCount(items.getJSONObject(i).getInt("fansCount"));
				list.add(pInfo);
			}

		} catch (JSONException e) {

			e.printStackTrace();
		}
		
	}
	public static class ViewHolder {
		ImageView imageView;
		TextView userName;
		TextView userAge;
		TextView lastLoginTime;
		TextView mbCount;
		TextView idolCount;
		TextView fansCount;
		ImageView userPicture;
	}
	
	class ListAdapter extends BaseAdapter{

		private LayoutInflater inflater;
		private List<PersonInfo> list;
		
		public ListAdapter(Context context, List<PersonInfo> list) {
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
				convertView = inflater.inflate(R.layout.userfollow_item, null);
				viewHolder = new ViewHolder();
				viewHolder.imageView = (ImageView)convertView.findViewById(R.id.uf_userPhoto);
				viewHolder.userName =(TextView)convertView.findViewById(R.id.uf_username);
				viewHolder.userAge =(TextView)convertView.findViewById(R.id.uf_userage);
				viewHolder.lastLoginTime =(TextView)convertView.findViewById(R.id.uf_lastlogintime);
				viewHolder.mbCount =(TextView)convertView.findViewById(R.id.uf_mbcount);
				viewHolder.idolCount =(TextView)convertView.findViewById(R.id.uf_idolcount);
				viewHolder.fansCount =(TextView)convertView.findViewById(R.id.uf_fanscount);
				
				convertView.setTag(viewHolder);
			}else {
				viewHolder = (ViewHolder)convertView.getTag();
			}
			viewHolder.imageView.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.user_headphoto2));
			String url = list.get(position).getUserPicture();
			Bitmap bm = GetBitmap.getBitmap(url);
			viewHolder.imageView.setImageBitmap(bm);
			viewHolder.userName.setText(list.get(position).getUserName());
			viewHolder.userAge.setText(list.get(position).getUserAge());
			viewHolder.lastLoginTime.setText(list.get(position).getLastLoginTime());
			viewHolder.mbCount.setText("΢��("+list.get(position).getMbCount()+")");
			viewHolder.idolCount.setText("��ע("+list.get(position).getIdolCount()+")");
			viewHolder.fansCount.setText("��˿("+list.get(position).getFansCount()+")");
			
			return convertView;
		}
		
	}
	
}
