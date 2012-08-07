package com.mis.icequeen;

import java.util.ArrayList;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class PreReview extends BaseActivity {
	
	private ArrayList<Integer> showlist;
	private int[] cptrange;
	private TextView tvPendingVoc;
	private RatingBar ratingBar;
	private Button btnConfirmReview;
	private Uri total;
	private Cursor test;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_review);
        Bundle extras = getIntent().getExtras();
        // 取出選擇的章節
        cptrange = extras.getIntArray("selected");
       
        showlist = new ArrayList<Integer>();
        RatingBarListener ratingBarListener = new RatingBarListener(); 
    	ratingBar = (RatingBar) findViewById(R.id.ratingBar);
    	ratingBar.setOnRatingBarChangeListener(ratingBarListener);
    	
    	ButtonListener buttonListener = new ButtonListener();
    	btnConfirmReview = (Button) findViewById(R.id.btnConfirmReview);
    	btnConfirmReview.setOnClickListener(buttonListener);
    	tvPendingVoc = (TextView) findViewById(R.id.tvPendingVoc); 
    	
        refreshPendingVoc( (int) ratingBar.getRating() );
        
    }
    
    
    /**
     * 依照星等重新整理待複習單字
     * @param rating
     */
    private void refreshPendingVoc(int rating) {
    	
    	switch (rating) {
    	case 0:
    		tvPendingVoc.setText("請選擇星星數量！");
    		break;
    	case 1:
    		tvPendingVoc.setText("一星等的單字：\n");
    		break;
    	case 2:
    		tvPendingVoc.setText("二星等的單字：\n");
    		break;
    	case 3:
    		tvPendingVoc.setText("三星等的單字：\n");
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
    					tvPendingVoc.append("\n第 "+(i+1)+" 章\n");
    					for (int j = 0; j < test.getCount(); j++) {
    						showlist.add(test.getInt(0));
    						tvPendingVoc.append("\t\t"+test.getString(1)+"\n");
    						if (!test.isLast())
    							test.moveToNext();
    					}
    					
    					System.out.println("showsize: "+showlist.size());
    					test.close();
    				}
    			}
    		 
    		 if (showlist.size() == 0)
    			 tvPendingVoc.setText("查無單字！");
    		
		}
    }
    
    /**
     * 確認送出
     */
    private class ButtonListener implements OnClickListener {
		public void onClick(View arg0) {
			if(showlist.size()==0)
			{
				Toast.makeText(arg0.getContext(), "查無單字", Toast.LENGTH_SHORT).show();
			}else{
			Intent it = new Intent();
			Intent its = new Intent();
			its.setClass(PreReview.this, PreReview.class);
			it.setClass(PreReview.this, Review.class);
			it.putExtra("init", true);
			it.putExtra("index", 0);
			it.putExtra("selected", cptrange);
			its.putExtra("selected", cptrange);
			it.putIntegerArrayListExtra("showlist", showlist);
			it.putExtra("Rate",(int) ratingBar.getRating());
			it.putExtra("side", "right");
			finish();
			//startActivity(its);
			startActivity(it);
			
			}
			
			
			
		}
    }

}
