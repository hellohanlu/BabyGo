package com.hl.ui;

import android.os.Bundle;  
import android.os.Handler;  
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.WindowManager;
import android.widget.TextView;
import android.app.Activity;  
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
  
public class Welcome extends Activity  
{    
    @Override  
    protected void onCreate(Bundle savedInstanceState)  
    {  
        super.onCreate(savedInstanceState);  
        setContentView(R.layout.welcome);        
        ShowPicture();
        
        new Handler().postDelayed(new Runnable()  
        {    
            @Override  
            public void run()  
            {  
                // TODO Auto-generated method stub   
                Intent intent=new Intent(Welcome.this,MainActivity.class);  
                startActivity(intent);  
                Welcome.this.finish();  
            }  
        }, 1000);           //—”≥Ÿ1√Î
    }  
    
    public void ShowPicture()
    {
    	WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
    	int nWidth = wm.getDefaultDisplay().getWidth();
    	int nHeight = wm.getDefaultDisplay().getHeight();
    	
        SpannableString spanText = new SpannableString(" ");
		Drawable d1 = getResources().getDrawable(R.drawable.first);
		d1.setBounds(0, 0, nWidth, nHeight);
		spanText.setSpan(new ImageSpan(d1), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);		
		TextView textview = (TextView) findViewById(R.id.Text_Welcome);
		textview.setText(spanText);		
    }
}  