package com.hl.ui;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.view.WindowManager;
import android.widget.TextView;
import android.content.Context;
import android.content.Intent;

public class TravelDetailActivity extends Activity {

	public class Info
	{ 
		public String sToShow;  
		public int nIdPic;
	}
	
	protected void onCreate(Bundle savedInstanceState) 
	{	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jingdianxinxi);		
		
		Info retInfo = ParseIntent();
		
		WindowManager wm = (WindowManager)this.getSystemService(Context.WINDOW_SERVICE);
    	int nWidth = wm.getDefaultDisplay().getWidth();
    	
		SpannableString spanText1 = new SpannableString("  ");
		Drawable d1 = getResources().getDrawable(retInfo.nIdPic);
		d1.setBounds(0, 0, nWidth*9/10, nWidth*9/10);			//设置成正方形
		spanText1.setSpan(new ImageSpan(d1), 0, 1, Spannable.SPAN_INCLUSIVE_EXCLUSIVE);		
		TextView text_item1 = (TextView) findViewById(R.id.picXinxi);
		text_item1.setText( spanText1);
		
		SpannableString spanText2 = new SpannableString(retInfo.sToShow);
		TextView text_item2 = (TextView) findViewById(R.id.textXinxi);
		text_item2.setText( spanText2);
	}
		
	public Info ParseIntent()
	{
		Info info = new Info();
		
		Intent intent = this.getIntent();   
		Bundle bundle = intent.getExtras();
		int nIdJingdian = bundle.getInt("idJingdian"); 
		switch ( nIdJingdian )
		{	
		case 1:
			return ShowFulushan();
		case 2:
			return ShowGuoliangcun();
		case 3:
			return ShowJimingdao();
		case 4:
			return ShowXuexiang();	
		case 5:
			return ShowShamo();
		case 6:
			return ShowPuzhehei();
		default:
			break;
		}
		
		return info;
	}
	
	public Info ShowFulushan()
	{
		Info info = new Info();		
		info.nIdPic = R.drawable.fulushan;
		info.sToShow = "【简介】：　福寿山位于湖南省平江县南部思村乡境内。目前为国家旅游局批准的国家级风景名胜区。" +
				" 与浏阳市社港镇毗邻，最高峰海拔高度1572.3米，总面积达近100平方公里，森林覆盖率96%，年均气温12.0℃，极端最高温30.5℃，是国务院国发[1988]51号文件公布的“岳阳楼洞庭湖风景名胜区”所包括的九个景区之一。" +
				"福寿山风景区集山秀、水美、林幽、石奇于一体，含自然景观、人文景观和福寿文化在一起，是建设生态旅游、避暑度假、休闲疗养、登山探险、水上游乐、民俗文化、红色教育等于一体的综合型旅游景区的理想之地。" +
				"福寿山风景区包括福寿山森林公园、百福洞峡谷瀑布群、白水湖水上游乐中心、福寿山矿泉水疗养区、福寿避暑度假村、芦洞民俗文化村、福寿山生态农业观光园、红色教育基地等八大景区100余个景点，是一个大容量、多功能、高品位的旅游区。"+
				"\n\n【地址】： 湖南省平江县南部福寿山镇" ;
		
		return info;			
	}
	
	public Info ShowGuoliangcun()
	{	
		Info info = new Info();		
		info.nIdPic = R.drawable.guoliangcun;
		info.sToShow = "【简介】：郭亮位于河南省新乡市辉县西北部，太行山腹地的万仙山风景区，" +
				"是一个以奇特壮观的悬崖绝壁和人工开凿的绝壁长廊而为人所知的村庄，" +
				"被称作“挂在绝壁上村庄”，还是国家AAA旅游景区。"+
				"\n\n【门票】：成人 80元，学生 45元" +
				"\n\n【地址】： 河南省新乡市辉县沙窑乡郭亮村" ;
		return info;			
	}
	
	public Info ShowJimingdao()
	{
		Info info = new Info();
		info.nIdPic = R.drawable.jimingdao;				
		info.sToShow = "【简介】：鸡鸣岛位于荣成市港西镇虎头角西北约1公里的海域中，海岛形状很象雄鸡。" +
				"面积约0.31平方公里，海岛上有居民66户，近200人，主要从事渔业生产。" +
				"岛上林木葱郁，草碧花香，瓦舍井然，环境优雅。养殖、加工、织网、修船业已渐居规模。" +
				"美丽的鸡鸣岛，在勤劳的岛上居民的建设中，变得越来起富裕、越清新幽雅。" +
				"\n\n【地址】：威海下属的荣成市港西镇虎头角西北约1公里的海域" ;
		return info;
	}	

	public Info ShowXuexiang()
	{
		Info info = new Info();
		info.nIdPic = R.drawable.xuexiang;
		info.sToShow = "【简介】：雪乡拥着层层叠叠的积雪，百余户的居民区犹如一座相连的“雪屋”，房舍随物具形的积雪在风力的作用下可达1米厚，" +
				"其状好似奔马、卧兔、神龟、巨蘑……千姿百态，仿佛是天上的朵朵白白云飘落，雪乡从初冬冰花乍放的清晰到早春雾淞涓流的婉约，无时无刻不散发着雪的神韵，因此得名-中国雪乡。" +
				"景色秀丽民风淳朴气候独特的“中国雪乡”双峰景区是大海林风景区的重要组成部分，积雪期长达七个月，积雪深达2米，皑皑白雪在风力的作用下随物具形，千姿百态。" +
				"雪乡的夜景尤为美丽，淳朴的雪乡人在自家挂起大红灯笼，洁白如玉的白雪在大红灯笼的照耀下，宛如天上的朵朵白云飘落人间，幻化无穷。" +
				"\n\n【地址】： 牡丹江西南部海林市大海林林业局" ;
		return info;
	}
		
	public Info ShowShamo()
	{
		Info info = new Info();
		info.nIdPic = R.drawable.shamo;		
		info.sToShow = "【简介】：白天滑沙，探险，乘坐羊皮筏漂流;夜晚赏月，篝火，欣赏沙漠焰火。" +
				"这一幕人与自然的梦幻交响曲场景，出现在时下最火爆的亲子节目《爸爸去哪儿》中，并产生连锁效应。在第二站宁夏中卫市沙坡头播出后不久，便吸引了大批游客游玩。" +
				"中卫沙坡头位于宁夏中卫市城区以西20公里腾格里沙漠东南边缘处，是国家第一批AAAA级旅游景区、第一个国家级沙漠生态自然保护区、中国三大鸣沙“沙坡鸣钟”所在地。" +
				"于1984年建立，面积1.3万余公顷，这里即具西北风光之雄奇，又兼江南景色之秀美，被旅游界专家誉为世界垄断性旅游资源。" +
				"\n\n【地址】：宁夏中卫市城区以西20公里" ;
		return info;
	}

	public Info ShowPuzhehei()
	{
		Info info = new Info();
		info.nIdPic = R.drawable.puzhehei;		
		info.sToShow = "【简介】：普者黑位于云南省文山壮族苗族自治州丘北县城西北。彝语为“鱼虾多的池塘”。" +
				"随处可见一座座散落的孤峰间，延绵环绕着40里水路、万亩野生荷花、一望无际的桃园和世界最大的岩溶湿地，" +
				"被誉为“世间罕见、中国独一无二的喀斯特山水田园风光”。"+
				"\n\n【地址】：云南省文山壮族苗族自治州丘北县城西北" ;
		return info;
	}
}