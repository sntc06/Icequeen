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
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PreReview extends Activity {
	
	private ArrayList<Integer> showlist;
	private TextView tvSelectedChapter;
	private RatingBar ratingBar;
	private LinearLayout pendingVocLinearLayout;
	private Button btnConfirmReview;
	private Uri total;
	TextView tempTV;
	int[] cptrange;
	Cursor test;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_review);
        Bundle extras = getIntent().getExtras();
        // 取出選擇的章節
        Intent it = getIntent();
        
        cptrange = extras.getIntArray("selected");
       
        showlist = new ArrayList<Integer>();
        tvSelectedChapter = (TextView) findViewById(R.id.tvSelectedChapter);
        tvSelectedChapter.setText("Chosen chapter: <CHANGE_ME>");
        RatingBarListener ratingBarListener = new RatingBarListener(); 
    	ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    	ratingBar.setOnRatingBarChangeListener(ratingBarListener);
    	pendingVocLinearLayout = (LinearLayout) findViewById(R.id.pendingVocLinearLayout);
    	
    	ButtonListener buttonListener = new ButtonListener();
    	btnConfirmReview = (Button) findViewById(R.id.btnConfirmReview);
    	btnConfirmReview.setOnClickListener(buttonListener);
    	
        refreshPendingVoc( (int) ratingBar.getRating() );
        
    }
    
    /**
     * 依照星等重新整理待複習單字
     * @param rating
     */
    private void refreshPendingVoc(int rating) {
    	
    	switch (rating) {
    	case 0:
    		tempTV = new TextView(this);
    		tempTV.setText("請選擇星星數量！");
    		pendingVocLinearLayout.removeAllViews();
    		pendingVocLinearLayout.addView(tempTV);
    		break;
    	case 1:
    		tempTV = new TextView(this);
    		tempTV.setText("STAR 1");
    		pendingVocLinearLayout.removeAllViews();
    		pendingVocLinearLayout.addView(tempTV);
    		break;
    	case 2:
    		tempTV = new TextView(this);
    		tempTV.setText("STAR 2");
    		pendingVocLinearLayout.removeAllViews();
    		pendingVocLinearLayout.addView(tempTV);
    		break;
    	case 3:
    		tempTV = new TextView(this);
    		tempTV.setText("STAR 3");
    		pendingVocLinearLayout.removeAllViews();
    		pendingVocLinearLayout.addView(tempTV);
    		break;
    	default:
    		break;
    	}
    	
    }
    
    /**
     * RatingBar 監聽 class
     */
    private class RatingBarListener implements OnRatingBarChangeListener {
    	// 在星等改變的時候呼叫 refreshPendingVoc
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			refreshPendingVoc( (int) rating );
			
    		//query by R&C
			showlist.clear();
    		 for (int i = 0; i < cptrange.length; i++) {
    				if (cptrange[i] == 1) {
    					getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getVOCbyRC:"+(int) rating+":"+(i+1)));
    					total = getIntent().getData();
    					test = managedQuery(total, null, null, null, null);
    					System.out.println("voc count: "+test.getCount());
    					test.moveToFirst();
    					for (int j = 0; j < test.getCount(); j++) {
    						showlist.add(test.getInt(0));
    						tempTV.append("\n"+test.getString(1));
    						if (!test.isLast())
    							test.moveToNext();
    					}
    					System.out.println(showlist.size());
    					test.close();
    				}
    			}
    		
		}
    }
    
    /**
     * 確認送出
     */
    private class ButtonListener implements OnClickListener {
		public void onClick(View arg0) {
			
			Intent it = new Intent();
			it.setClass(PreReview.this, Review.class);
			it.putExtra("init", true);
			it.putExtra("index", 0);
			it.putExtra("selected", cptrange);
			it.putIntegerArrayListExtra("showlist", showlist);
			it.putExtra("Rate",(int) ratingBar.getRating());
			startActivity(it);
			
		}
    }

}
