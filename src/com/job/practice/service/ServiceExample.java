package com.job.practice.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.job.practice.PracticeActivity;
import com.job.practice.R;

public class ServiceExample extends Service {
	
	private final int NOTIFICATION_ID = 0;

	private NotificationManager mNM;
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() 
	{
		super.onCreate();
		
        mNM = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);

        // Display a notification about us starting.  We put an icon in the status bar.
        showNotification();
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) 
	{
		String action = intent.getAction();
		
		if (action != null && action.equals(PracticeActivity.STOP_SERVICE_ACTION))
		{
			stopSelf();
		}
		
		return START_STICKY;
	}
	
    /**
     * Show a notification while this service is running.
     */
    private void showNotification() 
    {
        // In this sample, we'll use the same text for the ticker and the expanded notification
        CharSequence text = "Service is running";

        // Set the icon, scrolling text and timestamp
        Notification notification = new Notification(R.drawable.ic_launcher, "Service has started",
                System.currentTimeMillis());

        // The PendingIntent to launch our activity if the user selects this notification
//        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
//                new Intent(this, LocalServiceActivities.Controller.class), 0);

        // Set the info for the views that show in the notification panel.
        notification.setLatestEventInfo(this, "Service",
                       text, null);

        // Send the notification.
        mNM.notify(NOTIFICATION_ID, notification);
    }
    
    @Override
    public void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();

    	// Cancel the persistent notification.
    	mNM.cancel(NOTIFICATION_ID);
    }

}
