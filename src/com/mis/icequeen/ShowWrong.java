package com.mis.icequeen;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ShowWrong extends Activity {
	
	private int[] cptrange;
	private TextView tvWrongList;
	private Button btnGoToReview;
	private ArrayList<String> wronglist;

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		cptrange = extras.getIntArray("selected");
		setContentView(R.layout.show_wrong);
		
		wronglist = extras.getStringArrayList("wronglist");
		tvWrongList = (TextView) findViewById(R.id.tvWrongList);
		
		for (String wrong : wronglist) {
			tvWrongList.append(wrong+"\n");
		}
		
		btnGoToReview = (Button) findViewById(R.id.btnGoToReview);
		btnGoToReview.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent it = new Intent();
				it.setClass(ShowWrong.this, PreReview.class);
				it.putExtra("selected", cptrange);
				//it.putExtra("BOOK", getIntent().getExtras().getInt("BOOK"));
				startActivity(it);
			}
		});
	}

}
