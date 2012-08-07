package com.mis.icequeen;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowWrong extends Activity {
	
	private TextView tvWrongList;
	private ArrayList<String> wronglist;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.show_wrong);
		
		wronglist = extras.getStringArrayList("wronglist");
		tvWrongList = (TextView) findViewById(R.id.tvWrongList);
		
		for (String wrong : wronglist) {
			tvWrongList.append(wrong+"\n");
		}
	}

}
