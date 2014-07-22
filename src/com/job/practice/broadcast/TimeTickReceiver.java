package com.job.practice.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.job.practice.PracticeActivity;

public class TimeTickReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) 
	{
		String action = intent.getAction();
		
		if (action.equals(Intent.ACTION_TIME_TICK))
		{
			SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
			boolean anrEnabled = sharedPrefs.getBoolean(PracticeActivity.TIME_TICK_BROADCAST_ANR_KEY, false);
			
			if (anrEnabled)
			{
				int x = 0;
				while (true)
				{
					x++;
					System.out.println("Second: " + x);
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			else
			{
				//Show toast
				CharSequence text = "Time Tick!";
				int duration = Toast.LENGTH_SHORT;

				Toast toast = Toast.makeText(context, text, duration);
				toast.show();				
			}

		}
	}

}
