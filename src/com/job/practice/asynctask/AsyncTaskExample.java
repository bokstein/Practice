package com.job.practice.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class AsyncTaskExample extends AsyncTask<String, Integer, String> {

	private Context mContext;
	
	public AsyncTaskExample(Context context)
	{
		mContext = context;
	}
	
	@Override
	protected String doInBackground(String... params) 
	{
		int x = 0;
		
		for (int i=0; i<4; i++)
		{
			x+=25;
			publishProgress(x);
			
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
		return "Done";
	}
	
	@Override
	protected void onProgressUpdate(Integer... progress) 
	{
		int duration = Toast.LENGTH_SHORT;
		CharSequence text = progress[0].toString();
		
		Toast toast = Toast.makeText(mContext, text, duration);
		toast.show();
	}
	
	@Override
	protected void onPostExecute(String result) 
	{
		int duration = Toast.LENGTH_SHORT;
		
		Toast toast = Toast.makeText(mContext, result, duration);
		toast.show();
	}

}
