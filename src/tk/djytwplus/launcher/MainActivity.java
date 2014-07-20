package tk.djytwplus.launcher;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.net.wifi.*;
import android.os.*;
import android.provider.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import android.util.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.client.*;
import org.apache.http.client.entity.*;
import org.apache.http.client.methods.*;
import org.apache.http.impl.client.*;
import org.apache.http.protocol.*;
import org.apache.http.util.*;
import tk.djytwplus.launcher.swipe.*;
import android.graphics.drawable.*;
import tk.djytwplus.launcher.view.*;
import org.apache.http.impl.io.*;
public class MainActivity extends FragmentActivity
{
	//swipe
	private ViewPager viewPager;
	private TabsPagerAdapter mAdapter;
	//sms
	private Uri SMS_INBOX = Uri.parse("content://sms/");
	private SmsObserver smsObserver;
	public Handler smsHandler = new Handler() {};
	Context context;
	//appinfo
	AppInfo ai=new AppInfo();
	//toggle
	boolean menuOpened=false,wifienabled=false,nightmode=false;
	//layout
	static LinearLayout metrocontrol, metroroot,listcontrol;
	static RelativeLayout control;
	//scale
	static int scale;
	//database
	SQLiteDatabase database;
	//typeface
	final TypefaceManager typefaceManager=new TypefaceManager();
	//renderer
	public String[][] rec=new String[200][2];
	//listevent
	int listpresscounter=0;
	boolean listreleased=false;
	Runnable listlongpress;
	boolean listlongpressed=false;
	View listlast=null;
	//metroevent
	int metropresscounter=0;
	boolean metroreleased=false;
	Runnable metrolongpress;
	boolean metrolongpressed=false;
	View metrolast=null;
	OnTouchListener metrotouchlistener;
	//static
	static final String[][] pre={
		{"Github","com.github.mobile","com.github.mobile.ui.user.HomeActivity","\uf001"},
		{"淘宝","闹不清","闹不清","\uf002"},
		{"微博","com.sina.weibo","com.sina.weibo.SplashActivity","\uf003"},
		{"腾讯微博","也是闹不清","也是闹不清","\uf004"},
		{"facebook","com.facebook.katana","com.facebook.katana.LoginActivity","\uf005"},
		{"Twitter","com.twitter.android","com.twitter.android.StartActivity","\uf006"},
		{"chrome","com.android.chrome","com.google.android.apps.chrome.Main","\uf007"},
		{"opera","com.opera.browser","com.opera.Opera","\uf008"},
		{"opera mini","com.opera.mini.android","com.opera.mini.android.Browser","\uf008"},
		{"天天动听","com.sds.android.ttpod","com.sds.android.ttpod.EntryActivity","\uf009"},
		{"音乐","com.miui.player","com.miui.player.ui.MusicBrowserActivity","\uf009"},
		{"ZArchiver","ru.zdevs.zarchiver","ru.zdevs.zarchiver.ZArchiver","\uf00c"},
		{"日历","com.android.calendar","com.android.calendar.AllInOneActivity","\uf00e"},
		{"Adobe Reader","com.adobe.reader","com.adobe.reader.AdobeReader","\uf00f"},
		{"VLC","org.videolan.vlc.betav7neon","org.videolan.vlc.betav7neon.gui.MainActivity","\uf010"},
		{"地图","真的闹不清","闹不清","\uf011"},
		{"信息","com.android.mms","com.android.mms.ui.MmsTabActivity","\uf012"},
		{"新闻","不骗你闹不清","闹不清","\uf013"},
		{"安卓市场","com.hiapk.marketpho","com.hiapk.marketpho.SplashFrame","\uf014"},
		{"应用商店","com.xiaomi.market","com.xiaomi.market.ui.MarketTabActivity","\uf014"},
		{"图库","com.miui.gallery","com.miui.gallery.app.Gallery","\uf015"},
		{"迅雷","com.xunlei.downloadprovider","com.xunlei.downloadprovider.loading.LoadingActivity","\uf016"},
		{"视频","com.miui.video","com.miui.video.HomeActivity","\uf017"},
		{"MX播放器","com.mxtech.videoplayer.ad","com.mxtech.videoplayer.ad.ActivityMediaList","\uf017"},
		{"系统设置","com.android.settings","com.android.settings.MiuiSettings","\uf018"},
		{"密码保护","com.android.settings","com.android.settings.MiuiPasswordGuardActivity","\uf018"},
		{"安全中心","com.android.settings","com.miui.securitycenter.Main","\uf018"},
		{"照相机","com.android.camera","com.android.camera.Camera","\uf019"},
		{"照相机","com.google.android.GoogleCamera","com.android.camera.CameraLauncher","\uf019"},
		{"Office","cn.wps.moffice_eng","cn.wps.moffice.documentmanager.PreStartActivity","\uf01a"},
		{"邮件","com.android.email","com.android.email.activity.Welcome","\uf01b"},
		{"QQ邮箱","com.tencent.androidqqmail","com.tencent.qqmail.LaucherActivity","\uf01b"},
		{"微信","com.tencent.mm","com.tencent.mm.ui.LauncherUI","\uf01c"},
		{"高德地图","com.autonavi.minimap","com.autonavi.minimap.Splashy","\uf01d"},
		{"百度","naobuqing","闹不清","\uf01e"},
		{"QQ","com.tencent.mobileqq","com.tencent.mobileqq.activity.SplashActivity","\uf01f"},
		{"终端模拟器","jackpal.androidterm","jackpal.androidterm.Term","\uf020"},
		{"Busybox Installer","com.jrummy.busybox.installer","com.jrummy.busybox.installer.BusyboxActivity","\uf020"},
		{"DroidEdit","com.aor.droidedit.pro","com.aor.droidedit.DroidEditProActivity","\uf021"},
		{"计算器","com.android.calculator2","com.android.calculator2.Calculator","\uf022"},
		{"Graph 89","com.Bisha.TI89EmuDonation","com.graph89.emulationcore.EmulatorActivity","\uf022"},
		{"AndroPHP","com.ayansoft.androphp","com.ayansoft.androphp.MainActivity","\uf023"},
		{"浏览器","com.android.browser","com.android.browser.BrowserActivity","\uf024"},
		{"wi-fi","wifi","","\uf025"},
		{"WiFi万能钥匙","com.snda.wifilocating","com.snda.wifilocating.ui.activity.WelcomeActivity","\uf025"},
		{"C4droid","com.n0n3m4.droidc","com.n0n3m4.droidc.CCompilerMain","\uf026"},
		{"Root Explorer","com.speedsoftware.rootexplorer","com.speedsoftware.rootexplorer.RootExplorer","\uf027"},
		{"文件管理","com.android.fileexplorer","com.android.fileexplorer.FileExplorerTabActivity","\uf027"},
		{"ES文件浏览器","com.estrongs.android.pop","com.estrongs.android.pop.view.FileExplorerActivity","\uf027"},
		{"计算器","又是计算器?","","\uf028"},
		{"Threes","vo.threes.exclaimeeia","com.unity3d.player.UnityPlayerNativeActivity","\uf029"},
		{"SWF 播放器","com.issess.flashplayer","com.issess.flashplayer.activity.MainListActivity","\uf02a"},
		{"Adobe Flash Player","com.adobe.flashplayer","com.adobe.flashplayer.SettingsManager","\uf02a"},
		{"手电筒","com.android.systemui","com.android.systemui.TorchActivity","\uf02b"},
		{"百度云","com.baidu.netdisk","com.baidu.netdisk.ui.Navigate","\uf02c"},
		{"MathStudio","com.PomegranateSoftware.MathStudio","com.PomegranateSoftware.MathStudio.MathStudioActivity","\uf02d"},
		{"十六进制编辑器","tuba.tools","tuba.tools.HexSplash","\uf02e"},
		{"EveryCircuit","com.everycircuit","com.everycircuit.HomeLauncher","\uf02f"},
		{"网易有道词典","com.youdao.dict","com.youdao.dict.activity.DictSplashActivity","\uf030"},
		{"通讯录","com.android.contacts","com.android.contacts.activities.PeopleActivity","\uf031"},
		{"黄历","com.android.contacts","com.android.contacts.activities.YellowPageNavigation","\uf031"},
		{"拨号","com.android.contacts","com.android.contacts.activities.TwelveKeyDialer","\uf032"},
		{"时钟","com.android.deskclock","com.android.deskclock.DeskClockTabActivity","\uf033"},
		{"主题风格","com.android.thememanager","com.android.thememanager.ThemeResourceTabActivity","\uf034"},
		{"GPS状态","com.eclipsim.gpsstatus2","com.eclipsim.gpsstatus2.GPSStatus","\uf035"},
		{"备份","com.miui.backup","com.miui.backup.BackupActivity","\uf036"},
		{"钛备份","com.keramidas.TitaniumBackup","com.keramidas.TitaniumBackup.MainActivity","\uf036"},
		{"用户反馈","com.miui.bugreport","com.miui.bugreport.ui.TypeSelectionActivity","\uf037"},
		{"下载管理","com.android.providers.downloads.ui","com.android.providers.downloads.ui.DownloadList","\uf038"},
		{"收音机","com.miui.fmradio","com.miui.fmradio.FmRadioActivity","\uf039"},
		{"指南针","com.miui.compass","com.miui.compass.CompassActivity","\uf03a"},
		{"便签","com.miui.notes","com.miui.notes.ui.NotesListActivity","\uf03b"},
		{"支付服务","com.xiaomi.payment","com.xiaomi.payment.MiliCenterEntryActivity","\uf03c"},
		{"搜索","com.android.quicksearchbox","com.android.quicksearchbox.SearchActivity","\uf03d"},
		{"录音机","com.android.soundrecorder","com.android.soundrecorder.SoundRecorder","\uf03e"},
		{"语音助手","com.miui.voiceassist","com.miui.voiceassist.MiuiVoiceAssistActivity","\uf03e"},
		{"SIM卡1","com.android.stk","com.android.stk.StkLauncherActivity","\uf03f"},
		{"SIM卡2","com.android.stk","com.android.stk.StkLauncherActivityII","\uf03f"},
		{"快传","com.miui.transfer","com.miui.transfer.activity.ReceiveActivity","\uf040"},
		{"玩机手册","com.miui.userbook","com.miui.userbook.GuideListActivity","\uf041"},
		{"小米服务","com.xiaomi.xmsf","com.xiaomi.xmsf.account.ui.MiCloudSettingsActivity","\uf041"},
		{"天气","com.miui.weather2","com.miui.weather2.ActivityWeatherCycle","\uf042"},
		{"游戏中心","com.xiaomi.gamecenter","com.xiaomi.gamecenter.ui.MainTabActivity","\uf043"},
		{"ONS模拟器","cn.natdon.onscripterhd","cn.natdon.onscripterhd.start","\uf043"},
		{"NDS模拟器","com.dsemu.drasticcn","com.dsemu.drasticcn.DraSticActivity","\uf044"},
		{"PrinterShare","com.dynamixsoftware.printershare","com.dynamixsoftware.printershare.ActivityMain","\uf045"},
		{"文本编辑器","com.jecelyin.editor","com.jecelyin.editor.JecEditor","\uf046"},
		{"录屏","com.iwobanas.screenrecorder.pro","com.iwobanas.screenrecorder.RecorderActivity","\uf047"},
		{"QuickMark","tw.com.quickmark","tw.com.quickmark.Splash","\uf048"},
		{"蓝牙FTP","it.medieval.blueftp","it.medieval.blueftp.AMain","\uf049"},
		{"VPN","fq.router2","fq.router2.MainActivity","\uf04a"},
		{"中望CAD","com.ZWSoft.ZWCAD","com.ZWSoft.ZWCAD.Activity.ZWSplashActivity","\uf04b"},
		{"QQ空间","com.qzone","com.tencent.sc.activity.SplashActivity","\uf04c"},
		{"CHM 阅读器","com.pdagate.chmreader","com.pdagate.chmreader.CHMReader","\uf04d"},
		{"NTFS&HFS+ 挂载","com.paragon.mounter","com.paragon.mounter.Mounter","\uf04e"},
		{"叔叔工具箱","com.mobileuncle.toolbox","com.mobileuncle.toolbox.SplashActivity","\uf04f"},
		{"iCircuit","com.kruegersystems.circuitdroid","circuit.droid.HomeActivity","\uf050"},
		{"运营商名称修改","com.miui.SpnConfig","com.miui.SpnConfig.MiuiSpnConfigActivity","\uf051"},
		{"搜狗输入法","com.sohu.inputmethod.sogou","com.sohu.inputmethod.sogou.SogouIMELauncher","\uf052"},
		{"系统更新","com.android.updater","com.android.updater.MainActivity","\uf051"},
		{"Adhoc Server","org.cv.adhocserver","org.cv.adhocserver.MainActivity","\uf052"},
		{"a","24","","\uf00a"},
		{"b","25","","\uf00b"},
		{"c","26","","\uf00c"},
		{"d","27","","\uf00d"},
	};



	////////////////////////////////////////////////////
	//////////////////Overrides
	/////////

    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		//屏幕
     	requestWindowFeature(Window.FEATURE_NO_TITLE); 
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.mainframe);
		//初始化
		database = new Database(this).getWritableDatabase();
		scale = (int)(getResources().getDisplayMetrics().density + 0.5);
		mAdapter = new TabsPagerAdapter(getSupportFragmentManager());
		viewPager = (ViewPager) findViewById(R.id.pager);
		viewPager.setAdapter(mAdapter);
		ai.AppInfo(this);
		smsObserver = new SmsObserver(this, smsHandler);
		getContentResolver().registerContentObserver(SMS_INBOX, true, smsObserver);
		context = this;
		Cursor cur=database.query("data", new String[]{"id","size","app"}, null, null, null, null, "id");
		if (cur == null || cur.getCount() < 1)
		{initDatabase();}
		new Handler().postDelayed(new Runnable(){   
				public void run()
				{   
					Drawable draw;
					try
					{
						Exception e=new Exception();
						draw = Drawable.createFromPath("/sdcard/djytw/launcher/background.png");
						if (draw == null)throw e;
					}
					catch (Exception e)
					{
						draw = getResources().getDrawable(R.drawable.screenshot);
					}
					((RelativeLayout)findViewById(R.id.tab_one_Root)).setBackground(draw);
					//初始化layouts
					LinearLayout splash=(LinearLayout)findViewById(R.id.Splash);
					listcontrol = (LinearLayout)findViewById(R.id.listcontrol);
					metrocontrol = (LinearLayout)findViewById(R.id.metrocontrol);
					control = (RelativeLayout)findViewById(R.id.Control);
					metroroot = (LinearLayout)findViewById(R.id.metroroot);
					((TextView)splash.getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(context, "msyhl"));
					TextView applistback=(TextView)findViewById(R.id.ApplistBack);
					applistback.setTypeface(typefaceManager.getTypefaceByFontName(context, "djytw"));
					applistback.setText("\uf00d");
					listlongpress = new Runnable() {  
						@Override  
						public void run()
						{  
							listpresscounter--; 
							if (listpresscounter > 0 || listreleased) return;  
							listcontrol.setVisibility(LinearLayout.VISIBLE);
							listlongpressed = true;
						}  
					};  

					OnTouchListener listtouchlistener=new OnTouchListener(){
						@Override
						public boolean onTouch(View v, MotionEvent e)
						{
							listlast = v;
							switch (e.getAction())
							{
								case MotionEvent.ACTION_DOWN:
									v.setBackgroundColor(0xff0088cc);
									listpresscounter++;  
									listreleased = false;  
									listlongpressed = false;
									new Handler().postDelayed(listlongpress, ViewConfiguration.getLongPressTimeout()); 
									break;
								case MotionEvent.ACTION_UP:
									v.setBackgroundColor(0xff000000);
									listreleased = true;
									if (!listlongpressed)applistclick(v);
									break;
								case MotionEvent.ACTION_CANCEL:
									listreleased = true;
									v.setBackgroundColor(0xff000000);
									break;
							}
							return true;
						}
					};



					int i,j;
					//初始化应用列表
					LinearLayout applist=(LinearLayout)findViewById(R.id.AppList);
					String activity;
					for (i = 0;i < ai.sum();i++)
					{

						LinearLayout v = (LinearLayout)View.inflate(context, R.layout.applist, null);  
						((TextView)v.getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(context, "djytw"));
						((TextView)v.getChildAt(1)).setTypeface(typefaceManager.getTypefaceByFontName(context, "msyhl"));
						activity = ai.getActivity(i);
						for (j = 0;j < pre.length;j++)
						{
							if (activity.compareTo(pre[j][2]) == 0)
							{
								((TextView)v.getChildAt(0)).setText(pre[j][3]);
								((TextView)v.getChildAt(0)).setBackgroundColor(0xff0088cc);
								((TextView)v.getChildAt(1)).setText(pre[j][0]);
								break;
							}
						}
						if (j == pre.length)
						{
							((TextView)v.getChildAt(0)).setBackground(ai.getDrawable(i));
							((TextView)v.getChildAt(1)).setText(ai.getName(i));
						}
						v.setId(0x7f050300 + i);
						v.setOnTouchListener(listtouchlistener);
						applist.addView(v);
					}
				}   
			}, 200);   
		new Handler().postDelayed(new Runnable(){   
				public void run()
				{   
					LinearLayout splash=(LinearLayout)findViewById(R.id.Splash);
					splash.setVisibility(LinearLayout.GONE);
					render();
				}   
			}, 500);   
    }
	@Override
	public void onBackPressed()
	{
		viewPager.setCurrentItem(0);
		clearalert(null);
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		clearalert(null);
		if (!menuOpened)control.setVisibility(LinearLayout.VISIBLE);
		else control.setVisibility(LinearLayout.GONE);
		menuOpened = !menuOpened;
		return false;
	}

	@Override
	protected void onDestroy()
	{
		// TODO: Implement this method
		
		super.onDestroy();
		Intent it=new Intent();
		it.setClassName("tk.djytwplus.launcher", "tk.djytwplus.launcher.MainActivity");
		startActivity(it);
	}
	public void clearalert(View v)
	{
		listcontrol.setVisibility(LinearLayout.GONE);
		metrocontrol.setVisibility(LinearLayout.GONE);
		control.setVisibility(LinearLayout.GONE);
	}



	public void metrolongclick(int id)
	{
		metrocontrol.setVisibility(LinearLayout.VISIBLE);
	}
	public void metroclick(View v)
	{
		try
		{
			if (v.getId() == 0x7f050099)
			{
				Intent it=new Intent();
				it.setClassName("com.android.mms", "com.android.mms.ui.MmsTabActivity");
				startActivity(it);
			}
			else if (v.getId() == 0x7f050098)
			{
				TextView t=((TextView)((LinearLayout)v).getChildAt(0));
				if (!wifienabled)
					t.setTextColor(0xffcc8800);
				else 
					t.setTextColor(0xffffffff);
				toggleWiFi();

			}
			else if (v.getId() == -1)return;
			else
			{
				String pack=rec[v.getId() - 0x7f050100][0];
				String act=rec[v.getId() - 0x7f050100][1];
				Intent it=new Intent();
				it.setClassName(pack, act);
				startActivity(it);
			}
		}
		catch (Exception e)
		{
			Toast.makeText(this, "启动应用程序失败", Toast.LENGTH_SHORT).show();

		}
	}



	class SmsObserver extends ContentObserver
	{
		Context c;
		public SmsObserver(Context context, Handler handler)
		{super(handler);c = context;}
		@Override
		public void onChange(boolean selfChange)
		{
			super.onChange(selfChange);
			getSmsFromPhone(c);
		}
	}
	public void metroforward(View v){
		int id=metrolast.getId();
		Cursor cur;
		if(id==0x7f050100){return;}
		else if(id==0x7f050099){
			cur = database.query("data", new String[]{"id"}, "app='com.android.mms'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
			if(id==1)return;
		}
		else if(id==0x7f050098){
			cur = database.query("data", new String[]{"id"}, "app='wifi'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
			if(id==1)return;
		}
		else{
			id-=0x7f0500ff;
		}
		database.execSQL("update data set id=-1 where id="+id);
		database.execSQL("update data set id=id+1 where id="+(id-1));
		database.execSQL("update data set id="+(id-1)+" where id=-1");
		onBackPressed();
		render();
	}
	public void metrobackward(View v){
		int id=metrolast.getId();
		Cursor cur;
		cur = database.query("data", new String[]{"id"},null, null, null, null, null);
		int count=cur.getCount();
		if(id==0x7f0500ff+count){return;}
		else if(id==0x7f050099){
			cur = database.query("data", new String[]{"id"}, "app='com.android.mms'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
			if(id==count)return;
		}
		else if(id==0x7f050098){
			cur = database.query("data", new String[]{"id"}, "app='wifi'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
			if(id==count)return;
		}
		else{
			id-=0x7f0500ff;
		}
		database.execSQL("update data set id=-1 where id="+id);
		database.execSQL("update data set id=id-1 where id="+(id+1));
		database.execSQL("update data set id="+(id+1)+" where id=-1");
		onBackPressed();
		render();
	}
	public void metroin(View v){
		int id=metrolast.getId();
		Cursor cur;
		if(id==0x7f050098){return;}
		else{
			id-=0x7f0500ff;
			cur = database.query("data", new String[]{"size"}, "id="+id, null, null, null, null);
			cur.moveToFirst();
			if(cur.getInt(cur.getColumnIndex("size"))==2)return;
		}
		database.execSQL("update data set size=size+1 where id="+id);
		onBackPressed();
		render();
	}
	public void metroout(View v){
		int id=metrolast.getId();
		Cursor cur;
		if(id==0x7f050099){
			cur = database.query("data", new String[]{"id"}, "app='com.android.mms'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
		}
		else if(id==0x7f050098){return;}
		else{
			id-=0x7f0500ff;
			cur = database.query("data", new String[]{"size"}, "id="+id, null, null, null, null);
			cur.moveToFirst();
			if(cur.getInt(cur.getColumnIndex("size"))==0)return;
		}
		database.execSQL("update data set size=size-1 where id="+id);
		onBackPressed();
		render();
	}
	public void metrodelete(View v)
	{
		int id=metrolast.getId();
		Cursor cur;
		if (id == 0x7f050098)
		{
			cur = database.query("data", new String[]{"id"}, "app='wifi'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
		}
		else if (id == 0x7f050099)
		{
			cur = database.query("data", new String[]{"id"}, "app='com.android.mms'", null, null, null, null);
			cur.moveToFirst();
			id = cur.getInt(cur.getColumnIndex("id"));
		}
		else
		{
			id -= 0x7f0500ff;
		}
		database.execSQL("delete from data where id=" + id);
		database.execSQL("update data set id=id-1 where id>" + id);
		onBackPressed();
		render();
	}
	public void render()
	{
		metrolongpress = new Runnable() {  
			@Override  
			public void run()
			{  
				metropresscounter--; 
				if (metropresscounter > 0 || metroreleased) return;  
				metrocontrol.setVisibility(LinearLayout.VISIBLE);
				metrolongpressed = true;
		//		Toast.makeText(context, "id=" + metrolast.getId() + "; ", Toast.LENGTH_SHORT).show();
			}  
		};  

		metrotouchlistener = new OnTouchListener(){
			@Override
			public boolean onTouch(View v, MotionEvent e)
			{
				metrolast = v;
				int a=5 * scale;
				switch (e.getAction())
				{
					case MotionEvent.ACTION_DOWN:
						v.setBackgroundColor(0xff0088cc);
						metropresscounter++;  
						metroreleased = false;  
						metrolongpressed = false;
						new Handler().postDelayed(metrolongpress, ViewConfiguration.getLongPressTimeout()); 
						break;
					case MotionEvent.ACTION_UP:
						v.setBackground(getResources().getDrawable(R.drawable.background));
						metroreleased = true;
						v.setPadding(a, a, a, a);
						if (!metrolongpressed)metroclick(v);
						break;
					case MotionEvent.ACTION_CANCEL:
						metroreleased = true;
						v.setBackground(getResources().getDrawable(R.drawable.background));
						v.setPadding(a, a, a, a);
						break;
				}
				return true;
			}
		};
		metroroot.removeAllViews();
		//先读数据
		Cursor cur=database.query("data", new String[]{"id","size","app"}, null, null, null, null, "id");
		int i;
		LinearLayout last=null,last2=null;
		int last2c=0;
		int id,size;String app;
		for (i = 0;i < cur.getCount();i++)
		{
			cur.moveToNext();
			//goes here
			id = cur.getInt(cur.getColumnIndex("id"));	
			size = cur.getInt(cur.getColumnIndex("size"));
			app = cur.getString(cur.getColumnIndex("app"));
		//	Log.e("renderer","id:"+id+";size:"+size+";app="+app);
			if (size == 2)
			{
				//初始化
				if (last2c != 0)
				{
					if (last2c == 1)
						((LinearLayout)last2.getChildAt(0)).getChildAt(1).setBackgroundColor(0xff000000);
					if (last2c <= 2)
						((LinearLayout)last2.getChildAt(1)).getChildAt(0).setBackgroundColor(0xff000000);
					if (last2c <= 3)
						((LinearLayout)last2.getChildAt(1)).getChildAt(1).setBackgroundColor(0xff000000);
					last2c = 0;
					if (last != null)
					{
						last.addView(last2);
						metroroot.addView(last);
						last = null;
					}
					else
					{
						last = new LinearLayout(this);
						last.setOrientation(LinearLayout.HORIZONTAL);
						last.addView(last2);
					}
					last2 = null;
				}
				if (last != null)
				{ 
					LinearLayout t=new LinearLayout(this);
					t.setBackgroundColor(0xff000000);
					last.addView(t, 150 * scale, 150 * scale);
					metroroot.addView(last);
					last = null;
				}
				//开始添加
				if (app.compareTo("com.android.mms") != 0)
				{
					View v = View.inflate(this, R.layout.metro_b0, null);  
					v.setId(0x7f050100 + i);
					v.setOnTouchListener(metrotouchlistener);
					setFont((RelativeLayout)v);
					int j;
					for (j = 0;j < pre.length;j++)
						if (pre[j][1].compareTo(app) == 0)break;
					if (j < pre.length)
					{
						//name
						((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(0)).getChildAt(0)).setText(pre[j][0]);
						//图
						((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(1)).getChildAt(0)).setText(pre[j][3]);
						//记录
						rec[i][0] = pre[j][1];
						rec[i][1] = pre[j][2];
					}
					else
					{
						for (j = 0;j < ai.sum();j++)
						{
							//找到
							if (app.compareTo(ai.getPackage(j)) == 0)
							{
								//name
								((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(0)).getChildAt(0)).setText(ai.getName(j));
								//图
								((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(1)).getChildAt(0)).setBackground(ai.getHighDrawable(j));
								//记录
								rec[i][0] = ai.getPackage(j);
								rec[i][1] = ai.getActivity(j);
							}
						}
					}
					LinearLayout t=new LinearLayout(this);
					t.addView(v, 300 * scale, 150 * scale);
					metroroot.addView(t, 300 * scale, 150 * scale);
				}
				else
				{
					View v = View.inflate(this, R.layout.metro_b0, null);  
					v.setId(0x7f050099);
					LinearLayout t=new LinearLayout(this);
					t.addView(v, 300 * scale, 150 * scale);
					metroroot.addView(t, 300 * scale, 150 * scale);
					rec[i][0] = "com.android.mms";
					rec[i][1] = "com.android.mms.ui.MmsTabActivity";
					getSmsFromPhone(this);
				}
			}
			else if (size == 1)
			{
				//初始化
				if (last2c != 0)
				{
					if (last2c == 1)
						((LinearLayout)last2.getChildAt(0)).getChildAt(1).setBackgroundColor(0xff000000);
					if (last2c <= 2)
						((LinearLayout)last2.getChildAt(1)).getChildAt(0).setBackgroundColor(0xff000000);
					if (last2c <= 3)
						((LinearLayout)last2.getChildAt(1)).getChildAt(1).setBackgroundColor(0xff000000);
					last2c = 0;
					if (last != null)
					{
						last.addView(last2);
						metroroot.addView(last);
						last = null;
					}
					else
					{
						last = new LinearLayout(this);
						last.addView(last2);
					}
					last2 = null;
				}
				//添加
				View v = View.inflate(this, R.layout.metro_m, null);  
				v.setId(0x7f050100 + i);
				v.setOnTouchListener(metrotouchlistener);
				setFont((RelativeLayout)v);
				int j;
				for (j = 0;j < pre.length;j++)
					if (pre[j][1].compareTo(app) == 0)break;
				if (j < pre.length)
				{
					//name
					((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(0)).getChildAt(0)).setText(pre[j][0]);
					//图
					((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(1)).getChildAt(0)).setText(pre[j][3]);
					rec[i][0] = pre[j][1];
					rec[i][1] = pre[j][2];
				}
				else
				{
					for (j = 0;j < ai.sum();j++)
					{
						//找到
						if (app.compareTo(ai.getPackage(j)) == 0)
						{
							//name
							((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(0)).getChildAt(0)).setText(ai.getName(j));
							//图
							((TextView)((LinearLayout)((RelativeLayout)v).getChildAt(1)).getChildAt(0)).setBackground(ai.getHighDrawable(j));
							rec[i][0] = ai.getPackage(j);
							rec[i][1] = ai.getActivity(j);

						}
					}
				}
				if (last != null)
				{ 
					last.addView(v, 150 * scale, 150 * scale);
					metroroot.addView(last);
					last = null;
				}
				else
				{
					last = new LinearLayout(this);
					last.setOrientation(LinearLayout.HORIZONTAL);
					last.addView(v, 150 * scale, 150 * scale);
				}
			}
			else
			{
				LinearLayout t=null;
				if (last2c != 0)
				{
					switch (last2c)
					{
						case 1:
							t = (LinearLayout)((LinearLayout)last2.getChildAt(0)).getChildAt(1);
							break;
						case 2:
							t = (LinearLayout)((LinearLayout)last2.getChildAt(1)).getChildAt(0);
							break;
						case 3:
							t = (LinearLayout)((LinearLayout)last2.getChildAt(1)).getChildAt(1);
							break;
					}
					last2c++;
					if (app.compareTo("wifi") == 0)
					{
						t.setId(0x7f050098);
						t.setOnTouchListener(metrotouchlistener);
						((TextView)t.getChildAt(0)).setText("\uf025");
						setFont(t);
					}
					else
					{
						t.setOnTouchListener(metrotouchlistener);
						t.setId(0x7f050100 + i);
						setFont(t);
						int j;
						for (j = 0;j < pre.length;j++)
							if (pre[j][1].compareTo(app) == 0)break;
						if (j < pre.length)
						{
							((TextView)t.getChildAt(0)).setText(pre[j][3]);		
							rec[i][0] = pre[j][1];
							rec[i][1] = pre[j][2];

						}
						else
						{
							for (j = 0;j < ai.sum();j++)
							{
								//找到
								if (app.compareTo(ai.getPackage(j)) == 0)
								{
									((TextView)t.getChildAt(0)).setBackground(ai.getHighDrawable(j));
									rec[i][0] = ai.getPackage(j);
									rec[i][1] = ai.getActivity(j);

								}
							}
						}
					}
					if (last2c == 4)
					{
						//满了
						last2c = 0;
						if (last != null)
						{
							last.addView(last2);
							metroroot.addView(last);
							last = null;
						}
						else
						{
							last = new LinearLayout(this);
							last.setOrientation(LinearLayout.HORIZONTAL);
							last.addView(last2);
						}
						last2 = null;
					}
				}
				else
				{
					//第一个小磁贴
					last2 = (LinearLayout)View.inflate(this, R.layout.metro_s, null);
					last2c = 1;
					t = (LinearLayout)((LinearLayout)last2.getChildAt(0)).getChildAt(0);
					if (app.compareTo("wifi") == 0)
					{
						t.setId(0x7f050098);
						t.setOnTouchListener(metrotouchlistener);
						((TextView)t.getChildAt(0)).setText("\uf025");
						setFont(t);
					}
					else
					{
						t.setId(0x7f050100 + i);
						t.setOnTouchListener(metrotouchlistener);
						setFont(t);
						int j;
						for (j = 0;j < pre.length;j++)
							if (pre[j][1].compareTo(app) == 0)break;
						if (j < pre.length)
						{
							((TextView)t.getChildAt(0)).setText(pre[j][3]);		
							rec[i][0] = pre[j][1];
							rec[i][1] = pre[j][2];

						}
						else
						{
							for (j = 0;j < ai.sum();j++)
							{
								//找到
								if (app.compareTo(ai.getPackage(j)) == 0)
								{
									((TextView)t.getChildAt(0)).setBackground(ai.getHighDrawable(j));
									rec[i][0] = ai.getPackage(j);
									rec[i][1] = ai.getActivity(j);

								}
							}
						}
					}
				}
			}
		}
		//处理未满磁贴
		if (last2c != 0)
		{
			if (last2c == 1)
				((LinearLayout)last2.getChildAt(0)).getChildAt(1).setBackgroundColor(0xff000000);
			if (last2c <= 2)
				((LinearLayout)last2.getChildAt(1)).getChildAt(0).setBackgroundColor(0xff000000);
			if (last2c <= 3)
				((LinearLayout)last2.getChildAt(1)).getChildAt(1).setBackgroundColor(0xff000000);
			last2c = 0;
			if (last != null)
			{
				last.addView(last2);
				metroroot.addView(last);
				last = null;
			}
			else
			{
				last = new LinearLayout(this);
				last.setOrientation(LinearLayout.HORIZONTAL);
				last.addView(last2);
			}
			last2 = null;
		}
		if (last != null)
		{ 
			LinearLayout t=new LinearLayout(this);
			t.setBackgroundColor(0xff000000);
			last.addView(t, 150 * scale, 150 * scale);
			metroroot.addView(last);
			last = null;
		}
		cur.close();
	}


	public void getSmsFromPhone(Context c)
	{
		ContentResolver cr = c.getContentResolver();
		String[] projection = new String[] { "body" ,"address","person"};//"_id", "address", "person",, "date", "type
		String where = "type='1' and read = '0' ";
		Cursor cur = cr.query(SMS_INBOX, projection, where, null, "date desc");
		LinearLayout par;
		try
		{
			par = (LinearLayout)(findViewById(0x7f050099)).getParent();
		}
		catch (Exception e)
		{
			return;
		}
		if (par == null)return;

		par.removeViewAt(0);
		if (null == cur || cur.getCount() == 0)
		{
			RelativeLayout v = (RelativeLayout)View.inflate(c, R.layout.metro_b0, null);  
			v.setId(0x7f050099);
			v.setOnTouchListener(metrotouchlistener);
			setFont(v);
			((TextView)((LinearLayout)(v).getChildAt(0)).getChildAt(0)).setText("信息");
			((TextView)((LinearLayout)(v).getChildAt(1)).getChildAt(0)).setText("\uF012");	
			par.addView(v);
		}

		if (cur.moveToNext())
		{
			String p=cur.getString(cur.getColumnIndex("address"));
			String q=getContactNameFromPhoneBook(p);
			if (q != "" && q != null)p = q + "(" + p + ")";
			String body = cur.getString(cur.getColumnIndex("body"));
			RelativeLayout rl=(RelativeLayout)View.inflate(c, R.layout.metro_b, null);  
			rl.setId(0x7f050099);
			rl.setOnTouchListener(metrotouchlistener);
			setFont(rl);
			//数量
			TextView smscount=(TextView)((LinearLayout)rl.getChildAt(0)).getChildAt(0);
			smscount.setText("" + cur.getCount());
			//图标
			((TextView)((LinearLayout)rl.getChildAt(1)).getChildAt(0)).setText("\uf012");
			//标题
			TextView smshead=(TextView)((LinearLayout)rl.getChildAt(2)).getChildAt(0);
			smshead.setText(p);
			//内容
			TextView smsbody=(TextView)((LinearLayout)rl.getChildAt(2)).getChildAt(1);
			smsbody.setText(body);
			par.addView(rl);
		}
	}
	Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg)
		{
			super.handleMessage(msg);
			Bundle data = msg.getData();
			String val = data.getString("value");
			Log.e("mylog", "请求结果-->" + val);
			Toast.makeText(context, val, Toast.LENGTH_SHORT).show();
		}
	};

	Runnable runnable = new Runnable(){
		@Override
		public void run()
		{
			//
			// TODO: http request.
			//
			String a="";
			Log.e("djytw", "start");
			List<NameValuePair> b=new ArrayList<NameValuePair>();
			try
			{
				a = doPost(b, "http://weatherapi.market.xiaomi.com/wtr-v2/weather?cityId=101100101");
			}
			catch (NetworkOnMainThreadException e)
			{
				Log.e("网络", e.getMessage() + ";");
			}
			catch (Exception e)
			{
				Log.e("网络", "错误");
			}
			//Toast.makeText(this., a, Toast.LENGTH_SHORT).show();
			Log.e("网络", "结果:" + a);

			Message msg = new Message();
			Bundle data = new Bundle();
			data.putString("value", a);
			msg.setData(data);
			handler.sendMessage(msg);
		}
	};
	//
	public void Test(View v)
	{
		new Thread(runnable).start();
	}
	public static String doPost(List<NameValuePair> params, String url)
	throws Exception
	{
		String result = null;
// 获取HttpClient对象
		HttpClient httpClient = new DefaultHttpClient();
// 新建HttpPost对象
		HttpPost httpPost = new HttpPost(url);
		if (params != null)
		{
// 设置字符集
			HttpEntity entity = new UrlEncodedFormEntity(params, HTTP.UTF_8);
// 设置参数实体
			httpPost.setEntity(entity);
		}
		/*// 连接超时
		 httpClient.getParams().setParameter(
		 CoreConnectionPNames.CONNECTION_TIMEOUT, 3000);
		 // 请求超时
		 httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT,
		 3000);*/
// 获取HttpResponse实例
		HttpResponse httpResp = httpClient.execute(httpPost);
// 判断是够请求成功
		if (httpResp.getStatusLine().getStatusCode() == 200)
		{
// 获取返回的数据
			result = EntityUtils.toString(httpResp.getEntity(), "UTF-8");
		}
		else
		{
			Log.i("HttpPost", "HttpPost方式请求失败");
		}
		return result;
	}

	//无按键处理
	public void backclick(View v)
	{onBackPressed();}

	public void menuclick(View v)
	{onCreateOptionsMenu(null);}
	//
	public void addToMetro(View v)
	{
		int id=listlast.getId() - 0x7f050300;
		//	Toast.makeText(this, ai.getLabel(id),Toast.LENGTH_SHORT).show();
		Cursor cur=database.query("data", new String[]{"id","size","app"}, null, null, null, null, "id");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (" + (cur.getCount()+1) + ",1,'" + ai.getPackage(id) + "')");
		onBackPressed();
		render();
	}
	public void dummy(View v)
	{}
	//封装好
	public void editDatabase(View v)
	{EditText e=(EditText)findViewById(R.id.control_edit);TextView t=(TextView)findViewById(R.id.control_console);try
		{database.execSQL(e.getText().toString());t.setText("操作成功结束");}
		catch (Exception ex)
		{t.setText(ex.getMessage());}render();}

	private void setFont(LinearLayout rl)
	{
	//	Log.e("layout",""+rl.getId());
		switch (rl.getChildCount())
		{
			case 3:
				((TextView)((LinearLayout)rl.getChildAt(0)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "SegoeWPBold"));
				((TextView)((LinearLayout)rl.getChildAt(1)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "djytw"));
				((TextView)((LinearLayout)rl.getChildAt(2)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "msyhl"));
				((TextView)((LinearLayout)rl.getChildAt(2)).getChildAt(1)).setTypeface(typefaceManager.getTypefaceByFontName(this, "msyhl"));
			break;
			case 2:
				((TextView)((LinearLayout)rl.getChildAt(0)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "msyhl"));
				((TextView)((LinearLayout)rl.getChildAt(1)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "djytw"));
			break;
			case 1:
				((TextView)rl.getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "djytw"));
		}
	}

	private void setFont(RelativeLayout rl)
	{switch (rl.getChildCount())
		{case 3:((TextView)((LinearLayout)rl.getChildAt(0)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "SegoeWPBold"));((TextView)((LinearLayout)rl.getChildAt(1)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "djytw"));((TextView)((LinearLayout)rl.getChildAt(2)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "msyhl"));((TextView)((LinearLayout)rl.getChildAt(2)).getChildAt(1)).setTypeface(typefaceManager.getTypefaceByFontName(this, "msyhl"));break;case 2:((TextView)((LinearLayout)rl.getChildAt(0)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "msyhl"));((TextView)((LinearLayout)rl.getChildAt(1)).getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "djytw"));break;case 1:((TextView)rl.getChildAt(0)).setTypeface(typefaceManager.getTypefaceByFontName(this, "djytw"));}}

	public String getContactNameFromPhoneBook(String phoneNum)
	{String contactName = "";ContentResolver cr = getContentResolver();Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.NUMBER + " = ?", new String[] { phoneNum }, null);if (pCur.moveToFirst())
		{contactName = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));pCur.close();}return contactName;}

	private void toggleWiFi()
	{WifiManager wm = (WifiManager)getSystemService(WIFI_SERVICE);wifienabled = !wifienabled;wm.setWifiEnabled(wifienabled);}

	public void toggleNight(View v)
	{
		LinearLayout night=(LinearLayout)findViewById(R.id.Night);
		nightmode = !nightmode;
		if (nightmode)night.setVisibility(LinearLayout.VISIBLE);
		else night.setVisibility(LinearLayout.GONE);
	}
	public void applistclick(View v)
	{ai.run(v.getId() - 0x7f050300);}

	public void initDatabase()
	{
		Database d=new Database(this);d.CreateTable();
		database.execSQL("INSERT INTO data (id,size,app) VALUES (1,2,'com.tencent.mobileqq')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (2,1,'tk.djytwplus.launcher')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (3,1,'com.android.camera')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (4,2,'com.android.mms')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (5,1,'com.facebook.katana')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (6,1,'com.github.mobile')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (7,1,'com.twitter.android')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (8,1,'com.android.calendar')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (9,2,'com.android.chrome')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (10,1,'com.sds.android.ttpod')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (11,1,'com.youdao.dict')");
		database.execSQL("INSERT INTO data (id,size,app) VALUES (12,2,'com.adobe.reader')");
	}
	//备用
	//	Toast.makeText(this, "",Toast.LENGTH_SHORT).show();
}
