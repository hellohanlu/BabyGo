package com.hl.ui;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.hl.ui.R;
import com.hl.commons.Base64;
import com.hl.commons.GlobalVariate;
import com.hl.http.HttpDownload;
public class RegisterActivity extends Activity{
  
	private Resources res;
	private TextView tv_note;
	private EditText et_userName;
	private EditText et_password;
	private EditText et_confirm;
	private EditText et_trueName;
	private EditText et_age;
	private ImageView iv_headPhoto;
	private Button btn_takePhoto;
	private Button btn_register;
	private Button btn_back;
    private File mCurrentPhotoFile;  //照相机拍照得到的照片
	//拍照的照片存储位置,此处为SD卡路径
	private static final File PHOTO_DIR=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera");
	//用来标识请求照相功能的activity
	private static final int CAMERA_WITH_DATA=3023;
	//用来标识请求gallery的activity
	private static final int PHOTO_PICKED_WITH_DATA=3021;
	private byte[] img;  //头像数据
	private String photoName=GlobalVariate.defaultHeadPhotoName;
	protected  void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		res=getResources();
		tv_note=(TextView)findViewById(R.id.reg_note);
	    et_userName=(EditText)findViewById(R.id.reg_username);
	    et_password=(EditText)findViewById(R.id.reg_password);
	    et_confirm=(EditText)findViewById(R.id.reg_confirm);
	    et_trueName=(EditText)findViewById(R.id.reg_nickname);
	    et_age=(EditText)findViewById(R.id.reg_age);
	    iv_headPhoto=(ImageView)findViewById(R.id.reg_headPhoto);
	    btn_takePhoto=(Button)findViewById(R.id.reg_btn_takePhoto);
	    btn_takePhoto.setOnClickListener(new ChangePhotoListener());
	    btn_register=(Button)findViewById(R.id.reg_btn_register);
	    btn_register.setOnClickListener(new RegisterListener());
	    btn_back=(Button)findViewById(R.id.reg_btn_login);
		btn_back.setOnClickListener(new BackLoginListener());
	}
	class BackLoginListener implements OnClickListener
		{
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
				RegisterActivity.this.finish();
			}
		}
	//弹出提示信息
	public void showToast(String msg)
	{
		   Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
	private class ChangePhotoListener implements OnClickListener	
	 {

		@Override
		public void onClick(View v) 
		{
			// TODO Auto-generated method stub
			Context context=RegisterActivity.this;
			final Context dialogContext=new ContextThemeWrapper(context,android.R.style.Theme_Light);
			String cancel="返回";
			String[] choices;
			choices=new String[2];
		    choices[0]=getString(R.string.take_photo);
		    choices[1]=getString(R.string.pick_photo);
		    final ListAdapter adapter=new ArrayAdapter<String>(dialogContext,android.R.layout.simple_list_item_1,choices);
		    final AlertDialog.Builder builder=new AlertDialog.Builder(dialogContext);
		    builder.setTitle(R.string.attachToContact);
		    builder.setSingleChoiceItems(adapter, -1,
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
							switch (which) {
							case 0:{
								String status=Environment.getExternalStorageState();
								if(status.equals(Environment.MEDIA_MOUNTED)){//判断是否有SD卡
									doTakePhoto();// 用户点击了从照相机获取
								}
								else{
									showToast("没有SD卡");
								}
								break;
								
							}
							case 1:
								doPickPhotoFromGallery();// 从相册中去获取
								break;
							}
						}
					});
			builder.setNegativeButton(cancel, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
				
			});
			builder.create().show();
		}
	}
	/*拍照获取照片*/	
	protected void doTakePhoto() {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();// 创建照片的存储目录
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// 给新照的照片文件命名
			final Intent intent = getTakePickIntent(mCurrentPhotoFile);
			startActivityForResult(intent, CAMERA_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.photoPickerNotFoundText,
					Toast.LENGTH_LONG).show();
		}
	}
	

	public static Intent getTakePickIntent(File f) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE, null);
		intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
		return intent;
	}
	
	/**
	 * 用当前时间给取得的图片命名
	 * 
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyyMMddhhmmss");
		return dateFormat.format(date) + ".png";
	}
	// 请求Gallery程序
	protected void doPickPhotoFromGallery() {
		try {
			// Launch picker to choose photo for selected contact
			final Intent intent = getPhotoPickIntent();
			startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
		} catch (ActivityNotFoundException e) {
			Toast.makeText(this, R.string.photoPickerNotFoundText1,
					Toast.LENGTH_LONG).show();
		}
	}
	//封装请求Gallery的intent
	public static Intent getPhotoPickIntent() {
		Intent intent = new Intent(Intent.ACTION_GET_CONTENT, null);
		intent.setType("image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 80);
		intent.putExtra("outputY", 80);
		intent.putExtra("return-data", true);
		return intent;
	}
   //因为调用了Camera和Gally所以要判断他们各自的返回情况，他们启动时是startActivityForResult
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: {// 调用Gallery返回的
			final Bitmap photo = data.getParcelableExtra("data");
			// 下面就是显示照片了
			System.out.println(photo);
			//缓存用户选择的图片
			img=getBitmapByte(photo);
			Log.v("RegisterActivity", "new photo set!");
			iv_headPhoto.setImageBitmap(photo);
			System.out.println("set new photo");
			photoName = getPhotoFileName();
			break;
		}
		case CAMERA_WITH_DATA: {// 照相机程序返回的,再次调用图片剪辑程序去修剪图片
			doCropPhoto(mCurrentPhotoFile);
			break;
		}
		}
	}
	//将字节数组转换为bitmap
	public Bitmap getBitmapFromByte(byte[] temp)
	{
		if(temp!=null)
		{
			Bitmap bitmap=BitmapFactory.decodeByteArray(temp, 0, temp.length);
			return bitmap;
		}else{
			return null;
		}
	}
	//将头像转换成byte[]以便能将图片上传到服务器
	public byte[] getBitmapByte(Bitmap bitmap)
 {
		ByteArrayOutputStream out=new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG,100, out);
		return out.toByteArray();	
  }
	protected void doCropPhoto(File f)
	{
		final Intent intent=getCropImageIntent(Uri.fromFile(f));
		startActivityForResult(intent,PHOTO_PICKED_WITH_DATA);
		Toast.makeText(this, R.string.photoPickerNotFoundText, Toast.LENGTH_LONG).show();	
	}
   /*调用图片剪辑程序*/
   public static Intent getCropImageIntent(Uri photoUri)
   {
	Intent intent=new Intent("com.android.camera.action.CROP");//获取任意类型的图片
	intent.setDataAndType(photoUri, "image/*");
	intent.putExtra("crop", "true");//以滑动形式选中图片区域
	intent.putExtra("aspectX",1);
	intent.putExtra("aspectY",1);//设置裁剪框比例为1:1
	intent.putExtra("outputX", 80);
	intent.putExtra("outputY", 80);//裁剪后的输出图片大小为80*80
	intent.putExtra("return-data",true);
	return intent;
  }
  class RegisterListener implements OnClickListener
  {
   	public void onClick(View v)
   	{
   		String userName;
   		String password;
   		String confirm;
   		String trueName;
   		String age;
   		userName=et_userName.getText().toString();
   		password=et_password.getText().toString();
   		confirm=et_confirm.getText().toString();
   		trueName=et_trueName.getText().toString();
   		age=et_age.getText().toString();
   		if(userName!=null && "".equals(userName))
   		{
   			tv_note.setText(res.getString(R.string.reg_error_userNameEmpty));
   			et_userName.setFocusable(true);
   			et_userName.requestFocus(); //获取输入焦点
   			return;
   		}
   		if(password!=null &&"".equals(password))
   		{
   			tv_note.setText(res.getString(R.string.reg_error_passwordEmpty));
   			et_password.setFocusable(true);
   			et_password.requestFocus();
   			return;
   		}
   		if(confirm!=null && "".equals(confirm))
   		{
   			tv_note.setText(res.getString(R.string.reg_error_confirmEmpty));
			et_confirm.setFocusable(true);
			et_confirm.requestFocus();
			return;
   		}
   		if(!confirm.equals(password))
   		{
   			tv_note.setText(res.getString(R.string.reg_error_confirmError));
   			et_password.setText("");
   			et_password.setText("");
   			et_password.setFocusable(true);
   			et_password.requestFocus();
   			return;
   		}
   		//判断是否重名
   		String url="/ClientUserAction!isNameConflict.action";
   		List<NameValuePair> params=new ArrayList<NameValuePair>();
   		params.add(new BasicNameValuePair("user.name",userName));  //创建一个自定义请求头的字段
   		String result=HttpDownload.sendPostHttpRequest(url, params);
   		System.out.println("NameConflict: "+result);
   		if("true".equals(result.trim()))
   		{
   			tv_note.setText(res.getString(R.string.reg_error_userNameConflict));
   			et_userName.setText("");
   			et_userName.setFocusable(true);
   			et_userName.requestFocus();
   			Toast.makeText(RegisterActivity.this, res.getString(R.string.reg_error_userNameConflict), Toast.LENGTH_LONG).show();
   			return; 		
   		}
   		//上传照片
		if(photoName!= null && !photoName.equals(GlobalVariate.defaultHeadPhotoName)){
			url = "/UploadImageAction!upload.action";
			System.out.println("========img=====  "+img.toString());
			params.add(new BasicNameValuePair("filename", photoName));
			params.add(new BasicNameValuePair("filebody", Base64.encodeBytes(img)));
			result = HttpDownload.sendPostHttpRequest(url, params);
			if(!"success".equals(result.trim())){
				showToast("头像上传失败，将使用系统默认头像");
				photoName = GlobalVariate.defaultHeadPhotoName;
			}
		}else if(photoName == null){
			photoName = GlobalVariate.defaultHeadPhotoName;
		}
		//发送注册请求
		url = "/ClientUserAction!register.action";
		params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("user.name", userName));
		params.add(new BasicNameValuePair("user.password", password));
		params.add(new BasicNameValuePair("user.trueName", trueName));
		params.add(new BasicNameValuePair("user.age",age));
		params.add(new BasicNameValuePair("user.picture", photoName));
		result = HttpDownload.sendPostHttpRequest(url, params);
		try {
			JSONObject root = new JSONObject(result);
			Integer status = root.getInt("status");
			String showNote = root.getString("showNote");
			if(status == -1){
				tv_note.setText(showNote);
				et_userName.setText("");
				et_userName.setFocusable(true);
				et_userName.requestFocus();
				Toast.makeText(RegisterActivity.this, showNote, Toast.LENGTH_SHORT).show();
				return;
			}else if(status == 0){
				tv_note.setText(showNote);
				Toast.makeText(RegisterActivity.this, showNote, Toast.LENGTH_SHORT).show();
				btn_register.setClickable(false);
			}else{
				Dialog dialog = new AlertDialog.Builder(RegisterActivity.this)
				.setTitle("注册")
				.setMessage(showNote+"\n点击确定后返回登录")
				.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.dismiss();
								Intent intent = new Intent();
								intent.setClass(RegisterActivity.this, LoginActivity.class);
								RegisterActivity.this.startActivity(intent);
								RegisterActivity.this.finish();
							}
						}
				)
				.create();
				dialog.show();
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
}
