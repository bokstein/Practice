package com.job.practice.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TimeTickReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String action = intent.getAction();
		
		if (action.equals(Intent.ACTION_TIME_TICK))
		{
			System.out.println("paz");
		}
	}

}
