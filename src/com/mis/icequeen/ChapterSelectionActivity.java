/**
 * 
 */
package com.mis.icequeen;

import java.util.ArrayList;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;

/**
 * 收到選擇模式的 Intent 之後，選擇章節之後再啟動對應的 Activity
 */
public class ChapterSelectionActivity extends BaseActivity {

	private ArrayList<String> data;
	private static String MODE;
	private static int BOOK;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.chapter_selection);

		Intent intent = getIntent();
		MODE = intent.getExtras().getString("MODE");
		BOOK = intent.getExtras().getInt("BOOK");
		
		// selected db
		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/BookSet:"+BOOK));
		Uri uri = getIntent().getData();
		Cursor c = managedQuery(uri, null, null, null, null);
		
		// setting Title
		
		if (MODE.equals("LEARN")) setTitle(getResources().getString(R.string.mode_learn));
		else if (MODE.equals("REVIEW")) setTitle(getResources().getString(R.string.mode_review));
		else if (MODE.equals("TEST")) setTitle(getResources().getString(R.string.mode_test));
		
		
		Log.i("INT", "received intent MODE=" + MODE + ";BOOK=" + BOOK);

		setupView();
		// test
	}

	/**
	 * Generate chapters
	 */
	private void setupView() {

		data = new ArrayList<String>();

		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getAllChapter"));
		Uri uri = getIntent().getData();
		Cursor c = managedQuery(uri, null, null, null, null);
		c.moveToFirst();
		// Insert data
		for (int i = 0; i < c.getCount(); i++) {
			System.out.println(c.getString(0));
			data.add(c.getString(0));
			if (c.getPosition() != c.getCount() - 1)
				c.moveToNext();
		}
		c.close();

		/*
		 * data.clear(); for (int i=1; i<=11; i++) data.add("Chapter "+i);
		 * Log.i("FILL_CH", data.toString());
		 */
		LinearLayout chapterLinearLayoutMid = (LinearLayout) findViewById(R.id.chapterLinearLayoutMid);
		LinearLayout chapterLinearLayoutFinal = (LinearLayout) findViewById(R.id.chapterLinearLayoutFinal);
		final CheckBox[] checkboxes = new CheckBox[data.size()];
		final TextView[] tvScores = new TextView[data.size()];
		

		final int half = data.size() / 2;
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		lp.setMargins(0, 0, 20, 0);

		for (int i = 0; i < half; i++) {
			checkboxes[i] = new CheckBox(this);
			checkboxes[i].setText(data.get(i));
			checkboxes[i].setButtonDrawable(this.getResources().getDrawable(R.drawable.checkbox));
			checkboxes[i].setLayoutParams(lp);
			tvScores[i] = new TextView(this);
			getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getLastTest:"+(i+1)));
			uri = getIntent().getData();
			c = managedQuery(uri, null, null, null, null);
			if(c.getCount()!=0){
				c.moveToFirst();
				tvScores[i].setText(c.getString(0));
			}
			else
				tvScores[i].setText("尚未測驗!");
			c.close();
			System.out.println("SCORE:");
			if(!tvScores[i].getText().equals("尚未測驗!") && Integer.valueOf(tvScores[i].getText().toString().substring(0,2))<70)
				tvScores[i].setTextColor(Color.RED);
			else tvScores[i].setTextColor(Color.BLACK);
			LinearLayout tempLayout = new LinearLayout(this);
			TextView label = new TextView(this);
			label.setText("正確率: ");
			tempLayout.addView(checkboxes[i]);
			tempLayout.addView(label);
			tempLayout.addView(tvScores[i]);
			chapterLinearLayoutMid.addView(tempLayout);
		}

		for (int i = half; i < data.size(); i++) {
			checkboxes[i] = new CheckBox(this);
			checkboxes[i].setText(data.get(i));
			checkboxes[i].setButtonDrawable(this.getResources().getDrawable(R.drawable.checkbox));
			checkboxes[i].setLayoutParams(lp);
			tvScores[i] = new TextView(this);
			getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getLastTest:"+(i+1)));
			uri = getIntent().getData();
			c = managedQuery(uri, null, null, null, null);
			if(c.getCount()!=0){
				c.moveToFirst();
				tvScores[i].setText(c.getString(0));}
			else
				tvScores[i].setText("尚未測驗!");
			c.close();
			System.out.println("SCORE:");
			if(!tvScores[i].getText().equals("尚未測驗!") && Integer.valueOf(tvScores[i].getText().toString().substring(0,2))<70)
				tvScores[i].setTextColor(Color.RED);
			else tvScores[i].setTextColor(Color.BLACK);
			LinearLayout tempLayout = new LinearLayout(this);
			TextView label = new TextView(this);
			label.setText("正確率: ");
			
			tempLayout.addView(checkboxes[i]);
			tempLayout.addView(label);
			tempLayout.addView(tvScores[i]);
			chapterLinearLayoutFinal.addView(tempLayout);
		}

		CheckBox checkBoxMid = (CheckBox) findViewById(R.id.checkBoxMid);
		CheckBox checkBoxFinal = (CheckBox) findViewById(R.id.checkBoxFinal);
		checkBoxMid.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				for (int i = 0; i < half; i++) {
					checkboxes[i].setChecked(isChecked);
				}

			}
		});
		checkBoxFinal.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				for (int i = half; i < data.size(); i++) {
					checkboxes[i].setChecked(isChecked);
				}

			}
		});

		// 點了確定按鈕
		// Confirm chapter selection
		Button btnConfirmChapter = (Button) findViewById(R.id.btnConfirmChapter);
		btnConfirmChapter.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				int cptrange[] = new int[data.size()];
				//Intent it = new Intent(ChapterSelectionActivity.this, LearningActivity.class);
				Intent it = new Intent();
				boolean checkempty=true;
				if (MODE.equals("LEARN")) it.setClass(ChapterSelectionActivity.this, LearningActivity.class);
				else if (MODE.equals("REVIEW")) it.setClass(ChapterSelectionActivity.this, PreReview.class);
				else if (MODE.equals("TEST")) it.setClass(ChapterSelectionActivity.this, PreTest.class);

				for (int i = 0; i < data.size(); i++) {
					if (checkboxes[i].isChecked()){
						cptrange[i] = 1;
						checkempty=false;
					}
					else
						cptrange[i] = 0;
				}
				it.putExtra("init", true);
				it.putExtra("index", 0);
				it.putExtra("selected", cptrange);
				it.putExtra("side", "right");
				
				it.putExtra("MODE", MODE);
				it.putExtra("BOOK", BOOK);
				Log.i("INT", "received intent MODE=" + MODE + ";BOOK=" + BOOK);
				if(checkempty)
					Toast.makeText(arg0.getContext(), "請至少選擇一章節", Toast.LENGTH_SHORT).show();
				else{
					finish();
					startActivity(it);
				}
			}
		});

	}

}
