package com.hl.ui;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
public class TravelPageActivity extends Activity {
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.jingdianliebiao);
		Button button_liebiao = (Button) findViewById(R.id.Button_liebiao1);
		button_liebiao.setEnabled(false);		
		AddAllViewSpot();
		Button button_gailan = (Button) findViewById(R.id.Button_gailan1);
		button_gailan.setOnClickListener(new Button.OnClickListener(){
		public void onClick(View v) 
			{   
				Intent intent=new Intent();
	            intent.setClass(TravelPageActivity.this, JingDianGaiLan.class);
	            TravelPageActivity.this.startActivity(intent);	
			}
		});			
	}
	public void AddAllViewSpot()
	{	
		//在最后添加
		AddOneViewSpot(" 福禄山", R.drawable.fulushan, R.id.Button_item1, 1);
		AddOneViewSpot(" 郭亮村", R.drawable.guoliangcun, R.id.Button_item2, 2);
		AddOneViewSpot(" 鸡鸣岛", R.drawable.jimingdao, R.id.Button_item3, 3);
		AddOneViewSpot(" 雪乡",   R.drawable.xuexiang, R.id.Button_item4, 4);
		AddOneViewSpot(" 中卫沙坡头",   R.drawable.shamo, R.id.Button_item5, 5);
		AddOneViewSpot(" 普者黑", R.drawable.puzhehei, R.id.Button_item6, 6);
	}
	public void AddOneViewSpot(String sName, int nPicValue, int nButtonId, final int nViewSpotId)
	{	
		WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
    	int nHeight = wm.getDefaultDisplay().getHeight();
		SpannableString spanText1 = new SpannableString(sName);
		Drawable d1 = getResources().getDrawable(nPicValue);
		d1.setBounds(0, 0, nHeight/8, nHeight/8);
		spanText1.setSpan(new ImageSpan(d1), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);		
		Button button_item1 = (Button) findViewById(nButtonId);
		button_item1.setText( spanText1);
		button_item1.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) 
			{   
				Intent intent=new Intent();
	            intent.setClass(TravelPageActivity.this, TravelDetailActivity.class);
	            Bundle bundle = new Bundle();   
	            bundle.putInt("idJingdian", nViewSpotId);
	            intent.putExtras(bundle);
	            TravelPageActivity.this.startActivity(intent);	
			}
		});		
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
