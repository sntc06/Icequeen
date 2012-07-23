/**
 * 
 */
package com.mis.icequeen;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LearningActivity extends Activity {
	private ArrayList<Integer> showlist;
	static int nowvocid =762;
    int[] cptrange;
    int from,to,totalstart,totalend,totalvoc,cptstart,cptend,nextcptstart,nextcptend;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.learning_activity);
		setupView();
		Uri total;
		Cursor test;
		int index;
		
		showlist = new ArrayList<Integer>();
		index = extras.getInt("index");
		
		System.out.println("received intent"+nowvocid);
		if(extras.getBoolean("init")){
			cptrange = extras.getIntArray("selected");
			for(int i=0;i<cptrange.length;i++){
				if(cptrange[i]==1){
					getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getVOCbyChapter:"+(i+1)));
					total = getIntent().getData();
					test =managedQuery(total, null, null, null, null);
					test.moveToFirst();
					for(int j=0;j<test.getCount();j++){
						showlist.add(test.getInt(0));
						if(!test.isLast())
							test.moveToNext();
					}
					System.out.println(showlist.size());
					test.close();
				}
			}
		 
		}else
			showlist=extras.getIntegerArrayList("showlist");
			
		nowvocid=showlist.get(index);
		
		
		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+nowvocid));
	    Uri uri = getIntent().getData();
	    
	    final TextView word = (TextView) findViewById(R.id.tvWord);
        TextView meaning = (TextView) findViewById(R.id.tvMeaning);
        TextView classes = (TextView) findViewById(R.id.tvClass);
        TextView sentence = (TextView) findViewById(R.id.tvSentence);
        TextView count = (TextView) findViewById(R.id.tvCount);
        Button btnPrev = (Button) findViewById(R.id.btnPrevious);
        Button btnNext = (Button) findViewById(R.id.btnNext);
        Button play = (Button) findViewById(R.id.pronounce);
        ImageView KK = (ImageView) findViewById(R.id.KKView1);
        //test
        
        
        Cursor set = managedQuery(uri, null, null, null, null);
        if(set.getCount()!=0)
        {
        	set.moveToFirst();
        	word.setText(set.getString(1));
        	meaning.setText(set.getString(2));
        	classes.setText(set.getString(3)+"    "+set.getString(4));
        	sentence.setText(set.getString(5)+"\n"+set.getString(6));
        	count.setText((index+1)+"/"+showlist.size());
        }else
        	System.out.println("error1");
        
        String vockk=word.getText().toString();
        
        int id=getResources().getIdentifier(getPackageName()+":drawable/"+vockk, null, null);
        
        KK.setImageResource(id);
        
        btnPrev.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		if(nowvocid!=showlist.get(0)){
        			Intent intent = new Intent(LearningActivity.this, LearningActivity.class);
        			intent.putExtra("init", false);
        			intent.putExtra("selected",cptrange);
        			intent.putExtra("index",showlist.indexOf(nowvocid)-1);
        			intent.putIntegerArrayListExtra("showlist", showlist);
        			finish();
        			startActivity(intent);
        		}else
        		{
        		 Toast.makeText(v.getContext(), "�w�g�b�̫e���F!", Toast.LENGTH_SHORT).show();
        		}
        	}
        	
        });
        btnNext.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		if(nowvocid!=showlist.get(showlist.size()-1)){
        			
            		Intent intent = new Intent(LearningActivity.this, LearningActivity.class);
            		intent.putExtra("init", false);
            		intent.putExtra("index",showlist.indexOf(nowvocid)+1);
            		intent.putIntegerArrayListExtra("showlist", showlist);
            		finish();
            		startActivity(intent);
            		}else
            		{
            		 Toast.makeText(v.getContext(), "�w�g�b�̫᭱�F!", Toast.LENGTH_SHORT).show();
            		}
        	}
        	
        });
        play.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		{
        		  String playfile=word.getText().toString();
        		  int id=getResources().getIdentifier(getPackageName()+":raw/"+playfile, null, null);
        		  
        		  MediaPlayer mPlayer = MediaPlayer.create(LearningActivity.this,id);
        		  
                  try {
                      mPlayer.prepare();
                  } catch (IllegalStateException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  } catch (IOException e) {
                      // TODO Auto-generated catch block
                      e.printStackTrace();
                  }

                  mPlayer.start();
        		}
        	}
        	
        });
	
	    
	}

	private void setupView() {
		// TODO Auto-generated method stub
		
	}

}
