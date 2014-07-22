package com.job.practice;

import com.job.practice.broadcast.TimeTickReceiver;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;


public class PracticeActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {

	private final String CLOCK_BROADCAST_KEY = "time_tick_broadcast";
	
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
    	getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
    	getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
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
		if (key.equals(CLOCK_BROADCAST_KEY))
		{
			boolean startTimeTick = sharedPreferences.getBoolean(CLOCK_BROADCAST_KEY, false);
			
			if (startTimeTick)
			{
				//Register broadcast
				mTtr = new TimeTickReceiver();
				mIntentFilter = new IntentFilter(Intent.ACTION_TIME_TICK);
				registerReceiver(mTtr, mIntentFilter);
			}
			else
			{
				if (mTtr != null)
				{
					unregisterReceiver(mTtr);
					mTtr = null;
				}
			}
		}
	}
}
