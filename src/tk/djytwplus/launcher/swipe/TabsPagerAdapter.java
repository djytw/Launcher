package tk.djytwplus.launcher.swipe;

//import info.androidhive.tabsswipe.GamesFragment;
//import info.androidhive.tabsswipe.MoviesFragment;
//import info.androidhive.tabsswipe.TopRatedFragment;

import tk.djytwplus.launcher.swipe.*;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int arg0) {
		switch (arg0) {
		case 0:
			return new LayoutMetro();
			
		case 1:
			return new LayoutAppList();

		}
		return null;
	}

	@Override
	public int getCount() {

		return 2;
	}

}
