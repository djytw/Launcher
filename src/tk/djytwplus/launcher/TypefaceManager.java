package tk.djytwplus.launcher;

import android.content.*;
import android.graphics.*;
import java.util.*;
import android.util.*;

public class TypefaceManager
{
	public static HashMap<String, Typeface> TypefaceMap = new HashMap<String, Typeface>();
	public static Typeface getTypefaceByFontName(Context context,String name){
		if (TypefaceMap.containsKey(name)) {
			//Log.i("djytw桌面 TypefaceManager","字体:"+name+" get");
			return TypefaceMap.get(name);
		} else {
			Typeface tf;
			if(name=="msyhl"){
				String file="/sdcard/djytw/launcher/msyhl.ttf";
				try{
					tf=Typeface.createFromFile(file);
				}catch (Exception e) {
					tf=getTypefaceByFontName(context,"SegoeWPLight");
				}
				if(tf==null){tf=getTypefaceByFontName(context,"SegoeWPLight");}
			}else{
				tf = Typeface.createFromAsset(context.getResources().getAssets(), name+".ttf");
			}
			TypefaceMap.put(name, tf);
			return tf;
		}
	}
	
}
