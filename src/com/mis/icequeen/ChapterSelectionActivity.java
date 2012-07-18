/**
 * 
 */
package com.mis.icequeen;

import java.util.ArrayList;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 收到選擇模式的 Intent 之後，選擇章節之後再啟動對應的 Activity
 */
public class ChapterSelectionActivity extends Activity {
	
	private ArrayList<String> data;

	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_selection);
        setupView();
        //test
    }

	/**
	 *  Generate chapters
	 */
	private void setupView() {
		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getAllChapter"));
        Uri uri = getIntent().getData();
        Cursor c = managedQuery(uri, null, null, null, null);
        c.moveToFirst();
		// Insert data
		data = new ArrayList<String>();
		for (int i=0; i<c.getCount(); i++) {
			System.out.println(c.getString(0));
			data.add(c.getString(0));
			if(c.getPosition()!=c.getCount()-1)
				c.moveToNext();
		}
		c.close();
		
		// TODO: Fill in test chapter data
		data.clear();
		for (int i=1; i<=10; i++) 
			data.add("Chapter "+i);
		Log.i("TEST", data.toString());
		
		LinearLayout chapterLinearLayoutMid = (LinearLayout) findViewById(R.id.chapterLinearLayoutMid);
		LinearLayout chapterLinearLayoutFinal = (LinearLayout) findViewById(R.id.chapterLinearLayoutFinal);
		CheckBox[] checkboxes = new CheckBox[data.size()];
		
		int half = data.size() / 2;
		
		for (int i=0; i<half; i++) {
			checkboxes[i] = new CheckBox(this);
			checkboxes[i].setText(data.get(i));
			chapterLinearLayoutMid.addView(checkboxes[i]);
		}
		
		for (int i=half; i<data.size(); i++) {
			checkboxes[i] = new CheckBox(this);
			checkboxes[i].setText(data.get(i));
			chapterLinearLayoutFinal.addView(checkboxes[i]);
		}
		
        
        // 點了確定按鈕
        Button btnConfirmChapter = (Button) findViewById(R.id.btnConfirmChapter);
        btnConfirmChapter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(ChapterSelectionActivity.this, LearningActivity.class);
				it.putExtra("ID",761);
				startActivity(it);
			}
        });
		
	}

}
