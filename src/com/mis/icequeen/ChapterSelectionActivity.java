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
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * 收到選擇模式的 Intent 之後，選擇章節之後再啟動對應的 Activity
 */
public class ChapterSelectionActivity extends Activity {
	
	private static ArrayList<String> data;
	CheckBox[] checkboxes;

	
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
		
		data = new ArrayList<String>();
		
		//TODO: FIX get all chapter
		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getAllChapter"));
        Uri uri = getIntent().getData();
        Cursor c = managedQuery(uri, null, null, null, null);
        c.moveToFirst();
		// Insert data
		
		for (int i=0; i<c.getCount(); i++) {
			System.out.println(c.getString(0));
			data.add(c.getString(0));
			if(c.getPosition()!=c.getCount()-1)
				c.moveToNext();
		}
		c.close();
		
		// TODO: Fill in test chapter data
		/*data.clear();
		for (int i=1; i<=11; i++) 
			data.add("Chapter "+i);
		Log.i("FILL_CH", data.toString());
		*/
		LinearLayout chapterLinearLayoutMid = (LinearLayout) findViewById(R.id.chapterLinearLayoutMid);
		LinearLayout chapterLinearLayoutFinal = (LinearLayout) findViewById(R.id.chapterLinearLayoutFinal);
		checkboxes = new CheckBox[data.size()];
		
		//dataSize = data.size();
		//dataHalf = data.size() / 2;
		
		for (int i=0; i < data.size()/2; i++) {
			checkboxes[i] = new CheckBox(this);
			checkboxes[i].setText(data.get(i));
			chapterLinearLayoutMid.addView(checkboxes[i]);
		}
		
		for (int i= data.size()/2 ; i<data.size(); i++) {
			checkboxes[i] = new CheckBox(this);
			checkboxes[i].setText(data.get(i));
			chapterLinearLayoutFinal.addView(checkboxes[i]);
		}
		
		// Mid & Final button
		CheckBox checkBoxMid = (CheckBox) findViewById(R.id.checkBoxMid);
		CheckBox checkBoxFinal = (CheckBox) findViewById(R.id.checkBoxFinal);
		checkBoxMid.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if ( isChecked )
		        {
		    		for (int i=0; i< data.size()/2 ; i++) {
		    			checkboxes[i].setChecked(true);
		    		}
		        }
		        else {
		    		for (int i=0; i< data.size()/2 ; i++) {
		    			checkboxes[i].setChecked(false);
		    		}
		        }

		    }
		});
		
		checkBoxFinal.setOnCheckedChangeListener(new OnCheckedChangeListener()
		{
		    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
		    {
		        if ( isChecked )
		        {
		    		for (int i= data.size()/2 ; i< data.size() ; i++) {
		    			checkboxes[i].setChecked(true);
		    		}
		        }
		        else {
		    		for (int i= data.size()/2 ; i< data.size() ; i++) {
		    			checkboxes[i].setChecked(false);
		    		}
		        }

		    }
		});
		
        
        // 點了確定按鈕
		// TODO: Confirm chapter selection
        Button btnConfirmChapter = (Button) findViewById(R.id.btnConfirmChapter);
        btnConfirmChapter.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent it = new Intent(ChapterSelectionActivity.this, LearningActivity.class);
				it.putExtra("ID",761);
				startActivity(it);
			}
        });
		
	}

}
