package com.mis.icequeen;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class Test extends Activity {
	
	private TextView tvVoc;
	private TextView tvClass;
	private TextView tvTime;
	private RadioGroup radioGroup; 
	private RadioButton radio1;
	private RadioButton radio2;
	private RadioButton radio3;
	private RadioButton radio4;
	private Button btnConfirm;
	private static int ANSWER;
	private int testvocs;
	private ArrayList<Integer> showlist;
	private int[] cptrange;
	private Uri total;
	private Cursor test;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Bundle extras = getIntent().getExtras();
        // 取出選擇的章節
        cptrange = extras.getIntArray("selected");
        showlist = new ArrayList<Integer>();
        showlist = extras.getIntegerArrayList("showlist");
        System.out.println("test vocs:"+showlist.size());
        
        if (extras.getBoolean("init")) {
        	testvocs=showlist.size();
        }else
        	testvocs=extras.getInt("Totalvocs");
        
        tvVoc = (TextView) findViewById(R.id.tvVoc);
        tvClass = (TextView) findViewById(R.id.tvClass);
        tvTime = (TextView) findViewById(R.id.tvTime);
        
        RadioListener radioListener = new RadioListener();
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioListener);
        radio1 = (RadioButton) findViewById(R.id.radio1);
        radio2 = (RadioButton) findViewById(R.id.radio2);
        radio3 = (RadioButton) findViewById(R.id.radio3);
        radio4 = (RadioButton) findViewById(R.id.radio4);
        
        ButtonListener buttonListener = new ButtonListener();
        btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(buttonListener);
        
        generateOption();
        
    }
    
    /**
     * 產生隨機與正確的選項
     */
    private void generateOption() {
    	boolean check=true;
    	int Qindex,temp;
    	int[] optionId=new int[4];
    	String[] optionValue=new String[4];
    	
    	Random rand=new Random();
    	rand.setSeed(System.currentTimeMillis());
    	Qindex=rand.nextInt(showlist.size())+1;
    	    	
    	getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getall"));
		total = getIntent().getData();
		test = managedQuery(total, null, null, null, null);
		test.moveToFirst();
		optionId[0]=showlist.get(Qindex);
		showlist.remove(Qindex);
		for(int i=1;i<4;i++){
			check=true;
			test.moveToPosition(rand.nextInt(test.getCount())+1);
				for(int j=0;j<i;j++){
					if(optionId[j]==test.getInt(0))
						check=false;
					System.out.println(test.getInt(0));
				}
			if(check)
				optionId[i]=test.getInt(0);
		}
		test.close();
		
    	System.out.println("[0]:"+optionId[0]+"[1]:"+optionId[1]+"[2]:"+optionId[2]+"[3]:"+optionId[3]);
    	
    	getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+optionId[0]));
		total = getIntent().getData();
		test = managedQuery(total, null, null, null, null);
		test.moveToFirst();
    	tvVoc.setText(test.getString(1));
    	tvClass.setText(test.getString(3)+" "+test.getString(4));
    	test.close();
    	
    	for(int i=0;i<4;i++){
    		temp=rand.nextInt(4);
    		while(optionId[temp]==0){
    			temp=rand.nextInt(4);
    		}
    		if(temp==0)
    			ANSWER=i+1;
    		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+optionId[temp]));
    		total = getIntent().getData();
    		test = managedQuery(total, null, null, null, null);
    		test.moveToFirst();
    		optionValue[i]=test.getString(2);
    		test.close();
    		optionId[temp]=0;
    		
    	}
		
		radio1.setText(optionValue[0]);
		radio2.setText(optionValue[1]);
		radio3.setText(optionValue[2]);
		radio4.setText(optionValue[3]);
		System.out.println(ANSWER);
    }
    
    /**
     * 檢查答案正確 並且紀錄至資料庫中
     * 執行完成後載入下一題
     * @param selection 選項
     */
    
    
    
    private void submit(int selection) {
    	
    	Intent it = new Intent();
		it.setClass(Test.this, Test.class);
		it.putExtra("init", true);
		it.putExtra("selected", cptrange);
		it.putIntegerArrayListExtra("showlist", showlist);
		finish();
		startActivity(it);
    		
    	
    		
    		
    }
    
    /**
     * Button handler
     * 檢查選項後送出
     */
    private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			int checkedId = radioGroup.getCheckedRadioButtonId();
			int selection = 0;
			switch (checkedId) {
			case R.id.radio1:
				selection = 1;
				break;
			case R.id.radio2:
				selection = 2;
				break;
			case R.id.radio3:
				selection = 3;
				break;
			case R.id.radio4:
				selection = 4;
				break;
			default:
				break;
			}
			Log.i("RADIO","SUBMITTED:"+selection);
			submit(selection);
		}
    }
    /**
     * RadioButton Listener & 動態變更選擇高亮
     */
    private class RadioListener implements OnCheckedChangeListener {
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			switch (checkedId) {
			case R.id.radio1:
				break;
			case R.id.radio2:
				break;
			case R.id.radio3:
				break;
			case R.id.radio4:
				break;
			default:
				break;
			}
		}
    	
    }

}
