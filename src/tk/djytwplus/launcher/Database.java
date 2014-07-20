package tk.djytwplus.launcher;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.util.*;
public class Database extends SQLiteOpenHelper
{
	private static final String DATABASE_NAME = "data.db";
	private static final int DATABASE_VERSION = 1;
	Database(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String sql = "CREATE TABLE data ( id integer(20), size integer(20),app longtext ) ;";
		db.execSQL(sql);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{}
	public void CreateTable()
	{
		SQLiteDatabase db = getWritableDatabase();
		Cursor cur=db.query("data",new String[]{"app"},null,null,null,null,null);
		if(cur==null){
			String sql = "CREATE TABLE data ( id integer(20), size integer(20),app longtext)";
			db.execSQL(sql);
		}
	}
}
