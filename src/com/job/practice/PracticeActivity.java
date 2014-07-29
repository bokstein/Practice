package com.job.practice;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.job.practice.asynctask.AsyncTaskExample;
import com.job.practice.broadcast.TimeTickReceiver;
import com.job.practice.handler.HandlerExample;
import com.job.practice.service.ServiceExample;


public class PracticeActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener, OnPreferenceClickListener {

	private final String TIME_TICK_BROADCAST_KEY = "time_tick_broadcast";
	private final String BROADCAST_PRIORITY_KEY = "broadcast_priority";
	
	private final String HANDLER_MASSAGE_A_KEY = "handler_message_a";
	private final String HANDLER_MASSAGE_B_KEY = "handler_massage_b";
	private final String HANDLER_RUNNABLE_KEY  = "handler_runnable";
	
	private final String ASYNC_TASK_KEY = "async_task";

	private final String START_SERVICE_KEY  = "start_service";
	private final String STOP_SERVICE_KEY   = "stop_service";
	
	public final static String TIME_TICK_BROADCAST_ANR_KEY = "time_tick_broadcast_anr";
	public final static String MY_ACTION = "com.job.practice.intent.action.simple_action";
	public final static String STOP_SERVICE_ACTION = "com.job.practice.intent.action.stop_service";
	
	private final Context mContext = this;
	
	IntentFilter mIntentFilter;
	TimeTickReceiver mTtr;
	
	private Runnable mRunnable;
	
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
    	
    	Preference anrTimeTick     = (Preference) findPreference(BROADCAST_PRIORITY_KEY);
    	Preference handlerMsgA     = (Preference) findPreference(HANDLER_MASSAGE_A_KEY);
    	Preference handlerMsgB     = (Preference) findPreference(HANDLER_MASSAGE_B_KEY);
    	Preference handlerRunnable = (Preference) findPreference(HANDLER_RUNNABLE_KEY);
    	Preference asyncTask       = (Preference) findPreference(ASYNC_TASK_KEY);
    	Preference startService    = (Preference) findPreference(START_SERVICE_KEY);
    	Preference stopService     = (Preference) findPreference(STOP_SERVICE_KEY);
    	
    	anrTimeTick.setOnPreferenceClickListener(this);
    	handlerMsgA.setOnPreferenceClickListener(this);
    	handlerMsgB.setOnPreferenceClickListener(this);
    	handlerRunnable.setOnPreferenceClickListener(this);
    	asyncTask.setOnPreferenceClickListener(this);
    	startService.setOnPreferenceClickListener(this);
    	stopService.setOnPreferenceClickListener(this);

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
		else if (key.equals(HANDLER_MASSAGE_A_KEY))
		{
			HandlerExample handlerExample = new HandlerExample(this);
			//Wait for the thread to finish
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Handler handler = handlerExample.getHandler();

			handler.sendEmptyMessage(HandlerExample.MSG_A);
			
			return true;
		}
		else if (key.equals(HANDLER_MASSAGE_B_KEY))
		{
			HandlerExample handlerExample = new HandlerExample(this);
			//Wait for the thread to finish
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Handler handler = handlerExample.getHandler();

			handler.sendEmptyMessage(HandlerExample.MSG_B);
			
			return true;
		}
		else if (key.equals(HANDLER_RUNNABLE_KEY))
		{
			HandlerExample handlerExample = new HandlerExample(this);
			//Wait for the thread to finish
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Handler handler = handlerExample.getHandler();
			
			mRunnable = new Runnable()
			{
				public void run() 
				{
					//Toast parameters
					int duration = Toast.LENGTH_SHORT;
					CharSequence text = "Handler: Running runnable thread";
					
					Toast toast = Toast.makeText(mContext, text, duration);
					toast.show();
				}
			};

			handler.post(mRunnable);
			
			return true;
		}
		else if (key.equals(ASYNC_TASK_KEY))
		{
			AsyncTaskExample asyncTask = new AsyncTaskExample(this);
			asyncTask.execute(new String[0]);
		else if (key.equals(START_SERVICE_KEY))
		{
			Intent intent = new Intent(this, ServiceExample.class);
			startService(intent);
		}
		else if (key.equals(STOP_SERVICE_KEY))
		{
			Intent intent = new Intent(this, ServiceExample.class);
			intent.setAction(STOP_SERVICE_ACTION);
			startService(intent);
		}
		
		return false;
	}
}
