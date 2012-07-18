/**
 * 
 */
package com.mis.icequeen;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class LearningActivity extends Activity {
	static int nowvocid =762;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.learning_activity);
		setupView();
			
		nowvocid = extras.getInt("ID");
		System.out.println("received intent"+nowvocid);
		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+nowvocid));
	    Uri uri = getIntent().getData();
	    
	    TextView word = (TextView) findViewById(R.id.tvWord);
        TextView meaning = (TextView) findViewById(R.id.tvMeaning);
        TextView classes = (TextView) findViewById(R.id.tvClass);
        TextView sentence = (TextView) findViewById(R.id.tvSentence);
        TextView count = (TextView) findViewById(R.id.tvCount);
        Button btnPrev = (Button) findViewById(R.id.btnPrevious);
        Button btnNext = (Button) findViewById(R.id.btnNext);
        
        Cursor set = managedQuery(uri, null, null, null, null);
        if(set.getCount()!=0)
        {
        	set.moveToFirst();
        	word.setText(set.getString(1));
        	meaning.setText(set.getString(2));
        	classes.setText(set.getString(3)+"  "+set.getString(4));
        	sentence.setText(set.getString(5)+"\n"+set.getString(6));
        	count.setText(nowvocid+"/963");
        }else
        	System.out.println("error1");
        
        btnPrev.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		if(nowvocid!=761){
        		Intent intent = new Intent(LearningActivity.this, LearningActivity.class);
        		intent.putExtra("ID",nowvocid-1);
        		finish();
        		startActivity(intent);
        		}else
        		{
        		 Toast.makeText(v.getContext(), "已經在最前面了!", Toast.LENGTH_SHORT).show();
        		}
        	}
        	
        });
        btnNext.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		if(nowvocid!=963){
            		Intent intent = new Intent(LearningActivity.this, LearningActivity.class);
            		intent.putExtra("ID",nowvocid+1);
            		finish();
            		startActivity(intent);
            		}else
            		{
            		 Toast.makeText(v.getContext(), "已經在最後面了!", Toast.LENGTH_SHORT).show();
            		}
        	}
        	
        });
	
	    
	}

	/**
	 * 
	 */
	private void setupView() {
		// TODO Auto-generated method stub
		
	}

}
