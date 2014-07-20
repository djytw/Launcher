package tk.djytwplus.launcher;
import android.content.*;
import java.util.*;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.support.v4.widget.*;
import android.graphics.drawable.*;
import android.app.*;
import android.content.pm.*;
import android.content.res.*;

public class AppInfo
{
	private Intent it;
	static List<ResolveInfo> mApps;
	static PackageManager pm;
	static Context context;
	public void AppInfo(Context c){
		context=c;
		pm=c.getPackageManager();
		it = new Intent(Intent.ACTION_MAIN, null);  
		it.addCategory(Intent.CATEGORY_LAUNCHER);  
		mApps = pm.queryIntentActivities(it, 0);  
	}
	public int sum(){
		return mApps.size();
	}
	public Drawable getDrawable(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		Resources resources;
        try {
            resources = pm.getResourcesForApplication(
				info.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            resources = null;
        }
		Drawable d;
		try {
            d = resources.getDrawableForDensity(info.getIconResource(),150);
        } catch (Resources.NotFoundException e) {
            d = null;
        }
		
		return d;
	}
	public Drawable getHighDrawable(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		Resources resources;
        try {
            resources = pm.getResourcesForApplication(
				info.applicationInfo);
        } catch (PackageManager.NameNotFoundException e) {
            resources = null;
        }
		Drawable d;
		try {
            d = resources.getDrawableForDensity(info.getIconResource(),300);
        } catch (Resources.NotFoundException e) {
            d = null;
        }

		return d;
	}
	public String getLabel(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		return "\""+pm.getApplicationLabel(info.applicationInfo)+"\",\""+info.packageName+"\",\""+info.name+"\"";
	}
	public String getActivity(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		return info.name;
	}
	public String getPackage(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		return info.packageName;
	}
	public String getName(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		return (String)pm.getApplicationLabel(info.applicationInfo);
	}
	public void run(int count){
		ActivityInfo info;
		info = mApps.get(count).activityInfo;  
		Intent it=new Intent();
		it.setClassName(info.packageName,info.name);
		context.startActivity(it);
	}
}
