package com.mis.icequeen;

import android.util.Log;
import android.widget.Toast;
import net.emome.hamiapps.sdk.ForwardActivity;

public class CheckHami extends ForwardActivity
{
	@SuppressWarnings({ "rawtypes" })
	@Override
	public Class getTargetActivity() 
	{
		Log.v("HAMI","check passed");
		return MainActivity.class;
	}
	
	@Override
	public boolean passOnUnavailableDataNetwork()
	{
		//Toast.makeText(CheckHami.this, "DATA Network not available", Toast.LENGTH_SHORT).show();
		return true;
	}
}