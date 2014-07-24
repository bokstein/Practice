package com.job.practice.broadcast;

import com.job.practice.PracticeActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BroadcastReceiverA extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String action = intent.getAction();
		
		if (action.equals(PracticeActivity.MY_ACTION))
		{
			System.out.println(this.getClass().getSimpleName() + " Has been called!");
		}
	}

}
