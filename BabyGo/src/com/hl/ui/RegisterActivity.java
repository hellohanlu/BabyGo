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
    private File mCurrentPhotoFile;  //��������յõ�����Ƭ
	//���յ���Ƭ�洢λ��,�˴�ΪSD��·��
	private static final File PHOTO_DIR=new File(Environment.getExternalStorageDirectory()+"/DCIM/Camera");
	//������ʶ�������๦�ܵ�activity
	private static final int CAMERA_WITH_DATA=3023;
	//������ʶ����gallery��activity
	private static final int PHOTO_PICKED_WITH_DATA=3021;
	private byte[] img;  //ͷ������
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
	//������ʾ��Ϣ
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
			String cancel="����";
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
								if(status.equals(Environment.MEDIA_MOUNTED)){//�ж��Ƿ���SD��
									doTakePhoto();// �û�����˴��������ȡ
								}
								else{
									showToast("û��SD��");
								}
								break;
								
							}
							case 1:
								doPickPhotoFromGallery();// �������ȥ��ȡ
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
	/*���ջ�ȡ��Ƭ*/	
	protected void doTakePhoto() {
		try {
			// Launch camera to take photo for selected contact
			PHOTO_DIR.mkdirs();// ������Ƭ�Ĵ洢Ŀ¼
			mCurrentPhotoFile = new File(PHOTO_DIR, getPhotoFileName());// �����յ���Ƭ�ļ�����
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
	 * �õ�ǰʱ���ȡ�õ�ͼƬ����
	 * 
	 */
	private String getPhotoFileName() {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyyMMddhhmmss");
		return dateFormat.format(date) + ".png";
	}
	// ����Gallery����
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
	//��װ����Gallery��intent
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
   //��Ϊ������Camera��Gally����Ҫ�ж����Ǹ��Եķ����������������ʱ��startActivityForResult
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK)
			return;
		switch (requestCode) {
		case PHOTO_PICKED_WITH_DATA: {// ����Gallery���ص�
			final Bitmap photo = data.getParcelableExtra("data");
			// ���������ʾ��Ƭ��
			System.out.println(photo);
			//�����û�ѡ���ͼƬ
			img=getBitmapByte(photo);
			Log.v("RegisterActivity", "new photo set!");
			iv_headPhoto.setImageBitmap(photo);
			System.out.println("set new photo");
			photoName = getPhotoFileName();
			break;
		}
		case CAMERA_WITH_DATA: {// ��������򷵻ص�,�ٴε���ͼƬ��������ȥ�޼�ͼƬ
			doCropPhoto(mCurrentPhotoFile);
			break;
		}
		}
	}
	//���ֽ�����ת��Ϊbitmap
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
	//��ͷ��ת����byte[]�Ա��ܽ�ͼƬ�ϴ���������
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
   /*����ͼƬ��������*/
   public static Intent getCropImageIntent(Uri photoUri)
   {
	Intent intent=new Intent("com.android.camera.action.CROP");//��ȡ�������͵�ͼƬ
	intent.setDataAndType(photoUri, "image/*");
	intent.putExtra("crop", "true");//�Ի�����ʽѡ��ͼƬ����
	intent.putExtra("aspectX",1);
	intent.putExtra("aspectY",1);//���òü������Ϊ1:1
	intent.putExtra("outputX", 80);
	intent.putExtra("outputY", 80);//�ü�������ͼƬ��СΪ80*80
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
   			et_userName.requestFocus(); //��ȡ���뽹��
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
   		//�ж��Ƿ�����
   		String url="/ClientUserAction!isNameConflict.action";
   		List<NameValuePair> params=new ArrayList<NameValuePair>();
   		params.add(new BasicNameValuePair("user.name",userName));  //����һ���Զ�������ͷ���ֶ�
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
   		//�ϴ���Ƭ
		if(photoName!= null && !photoName.equals(GlobalVariate.defaultHeadPhotoName)){
			url = "/UploadImageAction!upload.action";
			System.out.println("========img=====  "+img.toString());
			params.add(new BasicNameValuePair("filename", photoName));
			params.add(new BasicNameValuePair("filebody", Base64.encodeBytes(img)));
			result = HttpDownload.sendPostHttpRequest(url, params);
			if(!"success".equals(result.trim())){
				showToast("ͷ���ϴ�ʧ�ܣ���ʹ��ϵͳĬ��ͷ��");
				photoName = GlobalVariate.defaultHeadPhotoName;
			}
		}else if(photoName == null){
			photoName = GlobalVariate.defaultHeadPhotoName;
		}
		//����ע������
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
				.setTitle("ע��")
				.setMessage(showNote+"\n���ȷ���󷵻ص�¼")
				.setPositiveButton("ȷ��",
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
