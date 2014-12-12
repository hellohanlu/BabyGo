package com.hl.ui;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.text.SpannableString;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class JingDianGaiLan extends Activity {

	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.jingdiangailan);
		
		ShowText();
		Button button_gailan = (Button) findViewById(R.id.Button_gailan2);
		button_gailan.setEnabled(false);
		
		Button button_liebiao = (Button) findViewById(R.id.Button_liebiao2);
		button_liebiao.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) 
			{   
				Intent intent=new Intent();
	            intent.setClass(JingDianGaiLan.this, TravelPageActivity.class);
	            JingDianGaiLan.this.startActivity(intent);	
			}
		}); 
	}
	
	public void ShowText()
	{
		String sText = "  ���գ����������������Ŀ���ְ�ȥ�Ķ�2���ֿ�ʼ�ˣ����ְ�ȥ�Ķ�1����ν�Ǵ����ϡ�" +
				"���˽�Ŀ����һ���롶�ְ�ȥ�Ķ����йصĻ��ⶼ��Ϊ�������۵��ȵ㡣" +
				"������˼���ǣ������Ŀ�ľ���Ҳ���ˣ�һԾ��Ϊ����������������ʤ�ء�" +
				"��Ϊ��λ����������˰ְ�1��Ŀȥ���Ĳ��־��㣬ϣ���ܸ�С�����ṩ���вο���\n" +
				"  ���ְ�ȥ�Ķ�����һ��·��ͼΪ��������ˮ�塪������ɳ��ͷ�����������ߺڡ���ɽ���������������ϸ���ɽ����������ѩ��\n\n";
		SpannableString spanText = new SpannableString(sText);
		TextView text_item = (TextView) findViewById(R.id.Text_jingdiangailan);
		text_item.setText( spanText);		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
        	Intent intent=new Intent();
            intent.setClass(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   //����ת��MainActivity��ʱ�򣬻Ὣջ����Activity����������� 
            this.startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}