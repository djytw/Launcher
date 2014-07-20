package tk.djytwplus.launcher.view;
import android.view.*;
import android.view.View.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.graphics.drawable.*;
import tk.djytwplus.launcher.*;
import android.content.res.*;
import android.widget.*;
public class metro extends View
{
	private AssetManager assetManager;
	private Context context;
	private Paint paint;
	private Paint textIconSmall,textIconBig,textBig,textHeader,textSmall;
	private float scroll=0,tscroll=0,speed=0;
	private boolean pressed=false;
	Drawable d=getResources().getDrawable(R.drawable.download);
	Bitmap download=((BitmapDrawable)d).getBitmap();
//	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.screenshot);
	public metro(Context c)
	{
		super(c);
		assetManager = c.getAssets();
		context=c;
		init();
	}
	public metro(Context c, AttributeSet attr)
	{
		super(c, attr);
		assetManager = c.getAssets();
		context=c;
		init();
	}
	public metro(Context c, AttributeSet attr, int defStyle)
	{
		super(c, attr, defStyle);
		assetManager = c.getAssets();
		context=c;
		init();
	}
	void init()
	{
		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(0xff000000);
		paint.setStrokeWidth(2);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		textIconBig = new Paint(Paint.ANTI_ALIAS_FLAG);
		textIconBig.setColor(0xffffffff);
		textIconBig.setStrokeWidth(1);
		textIconBig.setTextSize(150);
		textIconBig.setTypeface(Typeface.createFromAsset(assetManager, "iconFont.ttf"));
		textIconBig.setStyle(Paint.Style.FILL);
		textIconSmall = new Paint(Paint.ANTI_ALIAS_FLAG);
		textIconSmall.setColor(0xffffffff);
		textIconSmall.setStrokeWidth(1);
		textIconSmall.setTextSize(70);
		textIconSmall.setTypeface(Typeface.createFromAsset(assetManager, "iconFont.ttf"));
		textIconSmall.setStyle(Paint.Style.FILL);
		textBig = new Paint(Paint.ANTI_ALIAS_FLAG);
		textBig.setColor(0xffffffff);
		textBig.setStrokeWidth(1);
		textBig.setTextSize(32);
		textBig.setTypeface(Typeface.createFromAsset(assetManager, "SegoeWP.ttf"));
		textBig.setStyle(Paint.Style.FILL);
		textHeader= new Paint(Paint.ANTI_ALIAS_FLAG);
		textHeader.setColor(0xffffffff);
		textHeader.setStrokeWidth(1);
		textHeader.setTextSize(45);
		textHeader.setTypeface(Typeface.createFromAsset(assetManager, "SegoeWPLight.ttf"));
		textHeader.setStyle(Paint.Style.FILL);
		textSmall= new Paint(Paint.ANTI_ALIAS_FLAG);
		textSmall.setColor(0xffffffff);
		textSmall.setStrokeWidth(1);
		textSmall.setTextSize(30);
		textSmall.setTypeface(Typeface.createFromAsset(assetManager, "SegoeWPLight.ttf"));
		textSmall.setStyle(Paint.Style.FILL);
	}
	@Override
	public void onDraw(Canvas canvas)
	{
		//if(scroll>20)scroll=20;
		canvas.translate(0, scroll);
//		canvas.drawBitmap(bitmap, 0, 0, paint); 
		//vertical
		canvas.drawRect(0, 0, 40, 20000, paint);
		canvas.drawRect(340, 500, 350, 20000, paint);
		canvas.drawRect(650, 0, 1000, 20000, paint);
		//horiz
		canvas.drawRect(0, -20000, 1000, 200, paint);
		canvas.drawRect(0, 500, 1000, 510, paint);
		canvas.drawRect(0, 810, 1000, 820, paint);
		canvas.drawRect(0, 1120, 1000, 1130, paint);
		canvas.drawRect(0, 1430, 1000, 1440, paint);
		canvas.drawRect(0, 1740, 1000, 1750, paint);
		canvas.drawRect(0, 2050, 1000, 2060, paint);
		canvas.drawRect(0, 2360, 1000, 20000, paint);
		//1
	//	canvas.drawBitmap(download,100,300,paint);
		canvas.drawText("\ue01a",60,485,textIconSmall);
		canvas.drawText("\ue005",110,715,textIconBig);
		canvas.drawText("\ue0f6",420,715,textIconBig);
		canvas.drawText("\ue0d4",420,1035,textIconBig);
		canvas.drawText("\ue0c0",110,1345,textIconBig);
		canvas.drawText("\ue0c5",110,1655,textIconBig);
		//2
		canvas.drawText("djytw@live.com",70,265,textHeader);
		canvas.drawText("RE:shenme?shenme!sjdkxjdj...",70,315,textSmall);
		canvas.drawText("我了个大去。。。",70,345,textSmall);
		canvas.drawText("3",605,480,textBig);
		canvas.drawText("相机",50,795,textBig);
		canvas.drawText("Opera",360,795,textBig);
		canvas.drawText("亚马逊",360,1105,textBig);
		//	canvas.drawText("Start", 10, 100, textHeader);
/*		if (!pressed)
		{
			if (speed > 0)
			{
				scroll += speed;
				speed -= 20;
				if (speed < 0)speed = 0;
				invalidate();
			}
			else if (speed < 0)
			{
				scroll += speed;
				speed += 20;
				if (speed > 0)speed = 0;
				invalidate();
			}
		}*/
	}
	@Override
	public boolean onTouchEvent(MotionEvent e)
	{
		int x,y;
		switch (e.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				x=(int)e.getX();
				y=(int)e.getY();
				if(x>40&&x<340&&y>510&&y<810){
					//启动相机
					Intent it=new Intent();
					it.setClassName("com.android.camera","com.android.camera.Camera");
					context.startActivity(it);
				}else if(x>40&&x<650&&y>200&&y<500){
					//邮件
					Intent it=new Intent();
					it.setClassName("com.android.email","com.android.email.activity.Welcome");
					context.startActivity(it);
				}else if(x>350&&x<650&&y>510&&y<810){
					//启动opera
					Intent it=new Intent();
					it.setClassName("com.opera.browser","com.opera.Opera");
					context.startActivity(it);
				}
				break;
		}
		return true;
	}
}
