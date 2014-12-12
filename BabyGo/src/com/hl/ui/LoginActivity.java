package com.hl.ui;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.hl.ui.R;
import com.hl.commons.GlobalVariate;
import com.hl.http.HttpDownload;
import com.hl.http.Storage;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class LoginActivity extends Activity 
{
    private EditText et_userName;
	private EditText et_password;
	private Button btn_login;
	private Button btn_register;
	protected String userName;
	protected String password;

	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		et_userName=(EditText)findViewById(R.id.username);
		et_password=(EditText)findViewById(R.id.password);
		btn_login=(Button)findViewById(R.id.btn_login);
		btn_login.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				userName=et_userName.getText().toString();
				password=et_password.getText().toString();
				textLogin();
			}	
		});
		btn_register=(Button)findViewById(R.id.btn_register);
		btn_register.setOnClickListener(new OnClickListener()
		{

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent();
				intent.setClass(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}	
		});

}
	protected void textLogin() {
		// TODO Auto-generated method stub
		Resources res=getResources();
		if(userName!=null && password!=null && !res.getString(R.string.et_account_hint).equals(userName)&&!res.getString(R.string.et_password_hint).equals(password));
		//设置HttpPost连接对象(HtpPost即为HttpRequest)
		HttpPost httpPost=new HttpPost(GlobalVariate.SERVERADDRESS+"/ClientUserAction!login.action");
		//使用NameValuePair来保存要传递的Post参数
		List<NameValuePair> params=new ArrayList<NameValuePair>();
		//设置传递参数
		params.add(new BasicNameValuePair("user.name",userName));
		params.add(new BasicNameValuePair("user.password",password));
		//设置字符集
		HttpEntity httpEntity;
		try {
			httpEntity = new UrlEncodedFormEntity(params,"utf-8");
		    httpPost.setEntity(httpEntity);
		    HttpClient httpClient=new DefaultHttpClient();//使用DefaultHttpClient为HttpClient
		    HttpResponse httpResponse=httpClient.execute(httpPost);
		    if(httpResponse.getStatusLine().getStatusCode()==HttpStatus.SC_OK)
		    {
		    	//取得返回的字符串
		    	//String result=EntityUtils.toString(httpResponse.getEntity());
		    	//System.out.println(result);
		    	String result="";
		    	if(httpResponse.getEntity()!=null)
		    	{
		    	   	InputStream is=httpResponse.getEntity().getContent();
		    	   	result=HttpDownload.convertStreamToString(is).trim();
		    	   	if(result!=null && result.startsWith("success"))
		    	   	{
		    	   		String uid[]=result.split("_");
						System.out.println(">>>>>>>userId<<<<<<"+uid[0].trim());
						System.out.println(">>>>>>>userId<<<<<<"+uid[1].trim());
		    	   		Storage.saveString(this, "userId", uid[1]);
		    	   		Storage.saveString(this, "userName", userName);
						Storage.saveString(this, "password", password);
						
						Intent intent=new Intent();
						intent.setClass(LoginActivity.this, MainTabActivity.class);
						startActivity(intent);
		    	   	}
		    	}
		    	Toast.makeText(LoginActivity.this, result, Toast.LENGTH_LONG).show();
		    }else{
		    	System.out.println("fail the request");
		    }
		    	}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
} 
	return ;
}
}
