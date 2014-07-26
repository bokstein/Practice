package com.job.practice.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.Toast;

public class HandlerExample {
	
	public static final int MSG_A = 0;
	public static final int MSG_B = 1;
	
	private Handler mHandler;
	
	private Context mContext;
	
	public HandlerExample(Context context)
	{
		mContext = context;
		
		new Thread()
		{
			public void run() 
			{
				Looper.prepare();
				
				mHandler = new Handler()
				{
					@Override
					public void handleMessage(Message msg) 
					{
						super.handleMessage(msg);
						
						//Toast parameters
						int duration = Toast.LENGTH_SHORT;
						CharSequence text;
						
						switch (msg.what) {
						case MSG_A:
							text = "Handler: MSG A";
							break;
							
						case MSG_B:
							text = "Handler: MSG B";
							break;

						default:
							text = "Handler: NO MSG";
							break;
						}
						
						Toast toast = Toast.makeText(mContext, text, duration);
						toast.show();	
					}			
				};
				
				Looper.loop();
			}
		}.start();
	}
	
	
	public Handler getHandler()
	{
		return mHandler;
		
	}
	
}
