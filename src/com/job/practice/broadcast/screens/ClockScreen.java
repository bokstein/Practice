package com.job.practice.broadcast.screens;

import com.job.practice.R;
import com.job.practice.broadcast.TimeTickReceiver;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class ClockScreen extends Activity {
	
	IntentFilter mIntentFilter;
	TimeTickReceiver mTtr;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clock_screen);
	}
	
	@Override
	protected void onResume() 
	{
		super.onResume();
		
		//Register broadcast
		mTtr = new TimeTickReceiver();
		mIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
		registerReceiver(mTtr, mIntentFilter);
	}
	
	@Override
	protected void onPause() 
	{
		if (mTtr != null)
		{
			unregisterReceiver(mTtr);
			mTtr = null;
		}
		
		super.onPause();
	}

}
