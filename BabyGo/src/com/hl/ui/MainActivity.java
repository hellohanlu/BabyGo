package com.hl.ui;
import java.util.ArrayList;
import com.hl.ui.R;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
public class MainActivity extends Activity
{
   private ViewPager mTabPager;
   private ImageView mTabImg;   //����ͼƬ
   private ImageView mTab1,mTab2,mTab3;
   private int currIndex=0;  //��ǰҳ�����
   private int one;  //����ˮƽ����λ��
   private int two;  
   private int bmpW;
   private int offset=0;
   private TextView findpartner;
   public void onCreate(Bundle savedInstanceState)
   {
	   super.onCreate(savedInstanceState);
	   setContentView(R.layout.activity_main);
	   mTabPager=(ViewPager)findViewById(R.id.tabpager);
	   mTabPager.setOnPageChangeListener(new MyOnPageChangeListener());
	   mTabImg=(ImageView)findViewById(R.id.img_tab_now);
	   mTab1=(ImageView)findViewById(R.id.img_firstpage);
	   mTab2=(ImageView)findViewById(R.id.img_address);
	   mTab3=(ImageView)findViewById(R.id.img_settings);
	   mTab1.setOnClickListener(new MyOnClickListener(0));
	   mTab2.setOnClickListener(new MyOnClickListener(1));
	   mTab3.setOnClickListener(new MyOnClickListener(2));
	   bmpW=BitmapFactory.decodeResource(getResources(), R.drawable.tab_bg).getWidth();//��ȡͼƬ�Ŀ��
	   Display currDisplay=getWindowManager().getDefaultDisplay();//��ȡ��Ļ��ǰ�ֱ���
	   int displayWidth=currDisplay.getWidth();
	   offset=(displayWidth/3 - bmpW)/2;  //����ƫ����
	   one=offset*2+bmpW;    //ҳ��1��ҳ��2��ƫ����
	   two=one*2;              //ҳ��1��ҳ��3��ƫ����
	   //��ʼ��ҳ����������Ĭ����ʾ��һ��ҳ��
	   LayoutInflater mLi=LayoutInflater.from(this);
	   View view1=mLi.inflate(R.layout.home_page,null);
	   View view2=mLi.inflate(R.layout.home_page1,null);
	   View view3=mLi.inflate(R.layout.home_page2,null);
	   //ÿ��ҳ���view����
	   final ArrayList<View> views=new ArrayList<View>();
	   views.add(view1);
	   views.add(view2);
	   views.add(view3);
	   //���ViewPager��������������ʵ��ҳ����װ���ж��
	   PagerAdapter mPagerAdapter=new PagerAdapter()
	   {
        @Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0==arg1;
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return views.size();
		}
		 public void destroyItem(View container,int position,Object object)
		 {
			 ((ViewPager)container).removeView(views.get(position));
		 }
		 public Object instantiateItem(View container,int position)
		 {
			((ViewPager)container).addView(views.get(position));
			if(position==0)
			{
				 TextView ffbutton = (TextView)findViewById(R.id.find_parnter);
	          		ffbutton.setOnClickListener(new OnClickListener() {
	          			@Override
	          			public void onClick(View v) {
	          				// TODO Auto-generated method stub
	          				Intent intent		=new Intent();
	          				intent.setClass(MainActivity.this, LoginActivity.class);
	          				startActivity(intent);
	          			}
	          		}); 
	          	TextView mfbutton=(TextView)findViewById(R.id.travel_guide);
	          	mfbutton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent		=new Intent();
          				intent.setClass(MainActivity.this, TravelPageActivity.class);
          				startActivity(intent);
					}	
	          	});
	         	TextView mmbutton=(TextView)findViewById(R.id.children_education);
	          	mmbutton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent		=new Intent();
          				intent.setClass(MainActivity.this, WebmmActivity.class);
          				startActivity(intent);
					}	
	          	});
	          	TextView rebutton=(TextView)findViewById(R.id.record_growth);
	          	rebutton.setOnClickListener(new OnClickListener(){
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Intent intent		=new Intent();
          				intent.setClass(MainActivity.this, RecordActivity.class);
          				startActivity(intent);
					}	
	          	});
			}
			if(position==1)
			{
				 TextView textmessage= (TextView)findViewById(R.id.Text_message);
				 String text = "    ��Ӧ������ӵ������С���ĸ�ĸ�ǣ�" +
				 		          "��С�����Խ��������棬" +
				 		          "��С����ͬ�亢�ӻ���ѧϰ��" +
				 		          "ͬʱ���ҳ������ṩָ����" +
				 		          "ʹС�����Ը��ӿ��ֵĳɳ�\n";   
			      textmessage.setText(text);  
			}    
			 return views.get(position);
		 }
	   };
	   mTabPager.setAdapter(mPagerAdapter); 
   }
   
  /*ͷ��������*/
   public class MyOnClickListener implements View.OnClickListener
   {
       private int index=0;
       public MyOnClickListener(int i)
       {
    	   index=i;
       }
	@Override
	public void onClick(View v)
	{
		// TODO Auto-generated method stub
		mTabPager.setCurrentItem(index);	
	}
   };	
	/*ҳ���л�����*/
   public class MyOnPageChangeListener implements OnPageChangeListener {
	   
       @Override
       public void onPageSelected(int arg0) {
           Animation animation = null;
           switch (arg0) {
           case 0:
        	    mTab1.setImageDrawable(getResources().getDrawable(R.drawable.firstpage_pressed));
               if (currIndex == 1) {
                   animation = new TranslateAnimation(one, 0, 0, 0);
                   mTab2.setImageDrawable(getResources().getDrawable(R.drawable.message_normal));
               } else if (currIndex == 2) {
                   animation = new TranslateAnimation(two, 0, 0, 0);
                   mTab3.setImageDrawable(getResources().getDrawable(R.drawable.settings_normal));
               }
           		
               break;
           case 1:
        	   mTab2.setImageDrawable(getResources().getDrawable(R.drawable.message_pressed));
               if (currIndex == 0) {
                   animation = new TranslateAnimation(offset, one, 0, 0);
                   mTab1.setImageDrawable(getResources().getDrawable(R.drawable.firstpage_normal));
               } else if (currIndex == 2) {
                   animation = new TranslateAnimation(two, one, 0, 0);
                   mTab3.setImageDrawable(getResources().getDrawable(R.drawable.settings_normal));
               }
               break;
           case 2:
        	   mTab3.setImageDrawable(getResources().getDrawable(R.drawable.settings_pressed));
               if (currIndex == 0) {
                   animation = new TranslateAnimation(offset, two, 0, 0);
                   mTab1.setImageDrawable(getResources().getDrawable(R.drawable.firstpage_normal));
               } else if (currIndex == 1) {
                   animation = new TranslateAnimation(one, two, 0, 0);
                   mTab2.setImageDrawable(getResources().getDrawable(R.drawable.message_normal));
               }
               break;
           }
           //Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);//�������룬����Ҫ�ƶ�ͼ��Ļ��������滻����ġ�
           currIndex = arg0;
           animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
           animation.setDuration(150);
           mTabImg.startAnimation(animation);
       }

       @Override
       public void onPageScrolled(int arg0, float arg1, int arg2) {
       }
       @Override
       public void onPageScrollStateChanged(int arg0) {
       }
   }
   public boolean onKeyDown(int keyCode, KeyEvent event) 
	{ 
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) 
		{ 
			ExitDialog();  
			return true; 
		}  
		return true; 
	} 
	protected void ExitDialog() 
	{ 
		AlertDialog.Builder builder = new Builder(MainActivity.this);
		builder.setMessage("��ȷ���˳�Ӧ����?");  
		builder.setTitle("�˳�ȷ��");
		builder.setPositiveButton("ȷ��", new android.content.DialogInterface.OnClickListener() { @Override  
		    public void onClick(DialogInterface dialog, int which) { dialog.dismiss();
	        android.os.Process.killProcess(android.os.Process.myPid()); } });  
		
		builder.setNegativeButton("ȡ��", new android.content.DialogInterface.OnClickListener() { @Override  
		    public void onClick(DialogInterface dialog, int which) { dialog.dismiss(); } });  
		builder.create().show(); 
	}  
}

