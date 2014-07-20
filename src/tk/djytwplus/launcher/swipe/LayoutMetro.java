package tk.djytwplus.launcher.swipe;

import android.os.*;
import android.support.v4.app.*;
import android.view.*;
import tk.djytwplus.launcher.*;

public class LayoutMetro extends Fragment
{
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState)
	{
		View rootView = inflater.inflate(R.layout.tab_one, container, false);
		return rootView;
	}
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
}
