package com.job.practice;

import com.job.practice.broadcast.TimeTickReceiver;
import com.job.practice.broadcast.screens.ClockScreen;

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


public class PracticeActivity extends PreferenceActivity implements OnPreferenceClickListener {

	private final String CLOCK_BROADCAST_KEY = "clock_broadcast";
	
	IntentFilter intentFilter;
	TimeTickReceiver ttr;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.main_activity);
        
        Preference clockBroadCastPrefs  = (Preference) findPreference(CLOCK_BROADCAST_KEY);
        clockBroadCastPrefs.setOnPreferenceClickListener(this);
    }
    
    @Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    }
    
    @Override
    protected void onPause() {
    	// TODO Auto-generated method stub
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
	public boolean onPreferenceClick(Preference pref) 
	{
		String key = pref.getKey();
		
		if (key.equals(CLOCK_BROADCAST_KEY))
		{
			//Start Activity
			Intent activityIntent = new Intent(this, ClockScreen.class);
			this.startActivity(activityIntent);

			
			return true;
		}
		
		return false;
		
	}
}
