package com.job.practice;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.job.practice.broadcast.TimeTickReceiver;


public class PracticeActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {

	private final String TIME_TICK_BROADCAST_KEY = "time_tick_broadcast";
	private final String BROADCAST_PRIORITY_KEY = "broadcast_priority";
	
	public final static String TIME_TICK_BROADCAST_ANR_KEY = "time_tick_broadcast_anr";
	public final static String MY_ACTION = "com.job.practice.intent.action.simple_action";
	

	
	
	
	IntentFilter mIntentFilter;
	TimeTickReceiver mTtr;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.main_activity);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	
    	SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
    	
    	Preference broadcastPriority = (Preference) findPreference(TIME_TICK_BROADCAST_ANR_KEY);;
    	
    	sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    	
    	Preference anrTimeTick  = (Preference) findPreference(BROADCAST_PRIORITY_KEY);
    	anrTimeTick.setOnPreferenceClickListener(this);

    	boolean startTimeTick = sharedPreferences.getBoolean(TIME_TICK_BROADCAST_KEY, false);
    	
    	if (startTimeTick)
    	{
    		
			if (mTtr != null)
			{
				unregisterReceiver(mTtr);
				mTtr = null;
			}
    		
			//Register broadcast
			mTtr = new TimeTickReceiver();
			mIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
			registerReceiver(mTtr, mIntentFilter);
			
    		anrTimeTick.setEnabled(true);
    	}
    	else
    	{
			if (mTtr != null)
			{
				unregisterReceiver(mTtr);
				mTtr = null;
			}
			
    		anrTimeTick.setEnabled(false);
    	}

    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
    	
		if (mTtr != null)
		{
			unregisterReceiver(mTtr);
			mTtr = null;
		}
		
    	super.onPause();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.practice, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) 
	{
		if (key.equals(TIME_TICK_BROADCAST_KEY))
		{
			boolean startTimeTick = sharedPreferences.getBoolean(TIME_TICK_BROADCAST_KEY, false);
		
	        Preference anrTimeTick  = (Preference) findPreference(TIME_TICK_BROADCAST_ANR_KEY);

			if (startTimeTick)
			{
				//Register broadcast
				mTtr = new TimeTickReceiver();
				mIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
				registerReceiver(mTtr, mIntentFilter);
				
				anrTimeTick.setEnabled(true);
			}
			else
			{
				if (mTtr != null)
				{
					unregisterReceiver(mTtr);
					mTtr = null;
				}
				
				anrTimeTick.setEnabled(false);
			}
		}
	}

	@Override
	public boolean onPreferenceClick(Preference pref) 
	{
		String key = pref.getKey();
		
		if (key.equals(BROADCAST_PRIORITY_KEY))
		{
			Intent intent = new Intent(MY_ACTION);
			sendBroadcast(intent);
			
			return true;
		}
		
		return false;
	}
}
