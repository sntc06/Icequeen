package com.mis.icequeen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;

public class PreReview extends Activity {
	
	private TextView tvSelectedChapter;
	private RatingBar ratingBar;
	private LinearLayout pendingVocLinearLayout;
	private Button btnConfirmReview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_review);
        
        // 取出選擇的章節
        Intent it = getIntent();
        
        tvSelectedChapter = (TextView) findViewById(R.id.tvSelectedChapter);
        RatingBarListener ratingBarListener = new RatingBarListener(); 
    	ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    	ratingBar.setOnRatingBarChangeListener(ratingBarListener);
    	pendingVocLinearLayout = (LinearLayout) findViewById(R.id.pendingVocLinearLayout);
    	btnConfirmReview = (Button) findViewById(R.id.btnConfirmReview);
    	
        refreshPendingVoc( (int) ratingBar.getRating() );
        
    }
    
    /**
     * 依照星等重新整理待複習單字
     * @param rating
     */
    private void refreshPendingVoc(int rating) {
    	
    	TextView tempTV;
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
		}
    	
    }

}
