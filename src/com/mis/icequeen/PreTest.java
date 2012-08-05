package com.mis.icequeen;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class PreTest extends BaseActivity {
	private Button btnConfirmTest;
	private ArrayList<Integer> showlist;
	private int[] cptrange;
	private Uri total;
	private Cursor test;
	private LinearLayout pendingVocLinearLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_test);
        Bundle extras = getIntent().getExtras();
        // 取出選擇的章節
        cptrange = extras.getIntArray("selected");
        showlist = new ArrayList<Integer>();
        for (int i = 0; i < cptrange.length; i++) {
			if (cptrange[i] == 1) {
				getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getVOCbyChapter:"+(i+1)));
				total = getIntent().getData();
				test = managedQuery(total, null, null, null, null);
				System.out.println("voc count: "+test.getCount());
				test.moveToFirst();
				for (int j = 0; j < test.getCount(); j++) {
					showlist.add(test.getInt(0));
					if (!test.isLast())
						test.moveToNext();
				}
				System.out.println(showlist.size());
				test.close();
			}
		}
        
        
        ButtonListener buttonListener = new ButtonListener();
        btnConfirmTest = (Button) findViewById(R.id.btnConfirmTest);
        btnConfirmTest.setOnClickListener(buttonListener);
        pendingVocLinearLayout = (LinearLayout) findViewById(R.id.pendingVocLinearLayout);
        TextView word = (TextView) findViewById(R.id.textView2);
        word.setText("");
        for (int i = 0; i < cptrange.length; i++) {
    		if (cptrange[i] == 1) {
    			word.append(" Chapter "+(i+1)+"\n");
    		}
    	}
        word.setText(word.getText().toString().substring(0, word.getText().length()-1));
        
    }
    
    private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			Intent it = new Intent();
			it.setClass(PreTest.this, Test.class);
			it.putExtra("init", true);
			it.putExtra("selected", cptrange);
			it.putExtra("BOOK", getIntent().getExtras().getInt("BOOK"));
			it.putIntegerArrayListExtra("showlist", showlist);
			startActivity(it);
		}
    }

}
