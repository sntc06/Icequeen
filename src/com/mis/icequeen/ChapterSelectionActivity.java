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
	 * 
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
		
        
        // 點了確定按鈕
        Button btnConfirmChapter = (Button) findViewById(R.id.btnConfirmChapter);
        btnConfirmChapter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent it = new Intent(ChapterSelectionActivity.this, LearningActivity.class);
				startActivity(it);
			}
        });
		
	}

}
