package tk.djytwplus.launcher.receiver;
import android.content.*;
import android.os.*;
import android.telephony.*;
import java.text.*;
import java.util.*;
import android.widget.*;
import android.util.*;

public class SmsReciever extends BroadcastReceiver 
{
	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		SmsMessage msg = null;
		if (null != bundle) {
			Object[] smsObj = (Object[]) bundle.get("pdus");
			for (Object object : smsObj) {
				msg = SmsMessage.createFromPdu((byte[]) object);
				Date date = new Date(msg.getTimestampMillis());//时间
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String receiveTime = format.format(date);
				String a=("djytw短信接收:  number:" + msg.getOriginatingAddress()
								   + "   body:" + msg.getDisplayMessageBody() + "  time:"
								   + msg.getTimestampMillis());

				//在这里写自己的逻辑
			/*	if (msg.getOriginatingAddress().equals("10086")) {
					//TODO

				}*/

			}
		}
	}
}
