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
		String sText = "  近日，湖南卫视亲子秀节目《爸爸去哪儿2》又开始了，《爸爸去哪儿1》可谓是大红大紫。" +
				"除了节目本身，一切与《爸爸去哪儿》有关的话题都成为网友讨论的热点。" +
				"更有意思的是，拍摄节目的景点也火了，一跃成为网友们向往的旅游胜地。" +
				"现为各位网友整理出了爸爸1节目去到的部分景点，希望能给小孩们提供旅行参考。\n" +
				"  《爸爸去哪儿》第一季路线图为：北京灵水村——宁夏沙坡头——云南普者黑——山东鸡鸣岛——湖南福寿山——黑龙江雪乡\n\n";
		SpannableString spanText = new SpannableString(sText);
		TextView text_item = (TextView) findViewById(R.id.Text_jingdiangailan);
		text_item.setText( spanText);		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK || keyCode == KeyEvent.KEYCODE_HOME){
        	Intent intent=new Intent();
            intent.setClass(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);   //在跳转到MainActivity的时候，会将栈顶的Activity都给清理掉。 
            this.startActivity(intent);
        }
        return super.onKeyDown(keyCode, event);
    }
}