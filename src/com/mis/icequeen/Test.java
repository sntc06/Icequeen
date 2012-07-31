package com.mis.icequeen;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
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
	private int correct=0,ans;
	private ArrayList<Integer> showlist;
	private ArrayList<Integer> idlist;
	private ArrayList<String> wronglist;
	private int[] cptrange;
	private Uri total;
	private Cursor test;
	private Long startTime;
	private Handler handler = new Handler();
	boolean check=true;
	String cpt=new String();
	int Qindex=0,temp=0;
	static int[] optionId=new int[4];
	String[] optionValue=new String[4];
	Random rand=new Random();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        Bundle extras = getIntent().getExtras();
        
      
        //取得目前時間
        startTime = System.currentTimeMillis();
        //設定定時要執行的方法
        handler.removeCallbacks(updateTimer);
        //設定Delay的時間
        handler.postDelayed(updateTimer, 1000);
        
        //showlist = new ArrayList<Integer>();
        idlist = new ArrayList<Integer>();
        wronglist = new ArrayList<String>();
        
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
        
        cptrange = extras.getIntArray("selected");
        showlist = extras.getIntegerArrayList("showlist");
        System.out.println("test vocs:"+showlist.size());
        
        if (extras.getBoolean("init")) {
        	startTime = System.currentTimeMillis();
        	testvocs=showlist.size();
        }
        
        
        
        for (int i = 0; i < cptrange.length; i++) {
    		if (cptrange[i] == 1) {
    			cpt+=(i+1)+",";
    		}
    	}
        
        //get all idlist
        getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getall"));
 		total = getIntent().getData();
    	test = managedQuery(total, null, null, null, null);
    	test.moveToFirst();
    	for(int i=0;i<test.getCount()-1;i++){
    		idlist.add(test.getInt(0));
    		if(!test.isLast())
    			test.moveToNext();
    	}
    	test.close();
        
        generateOption();
        
    }
    
  //timer
    private Runnable updateTimer = new Runnable() {
        public void run() {
        	tvTime = (TextView) findViewById(R.id.tvTime);
            Long spentTime = System.currentTimeMillis() - startTime;
            String h = String.valueOf((spentTime/1000)/3600);
            if(((spentTime/1000)/3600) <10)
            	h="0"+h;
            //計算目前已過分鐘數
            String minius = String.valueOf(((spentTime/1000)%3600)/60);
            if((((spentTime/1000)%3600)/60)<10)
            	minius="0"+minius;
            //計算目前已過秒數
            String seconds = String.valueOf((spentTime/1000) % 60);
            if(((spentTime/1000) % 60)<10)
            	seconds="0"+seconds;
            tvTime.setText(h+":"+minius+":"+seconds+"::"+(testvocs-showlist.size()));
            handler.postDelayed(this, 1000);
        }
    };
    
    /**
     * 產生隨機與正確的選項
     */
    private void generateOption() {
    	
    	rand.setSeed(System.currentTimeMillis());
    	Qindex=rand.nextInt(showlist.size());
    	System.out.println(Qindex); 
    	ans=showlist.get(Qindex);
    	optionId[0]=showlist.get(Qindex);
		showlist.remove(Qindex);
		for(int i=1;i<4;i++){
			check=true;
			temp=rand.nextInt(idlist.size());
				for(int j=0;j<i;j++){
					if(optionId[j]==idlist.get(temp)){
						check=false;
						j--;
						temp=rand.nextInt(idlist.size());
						continue;
					}else
						check=true;
					System.out.println(idlist.get(temp));
				}
			if(check)
				optionId[i]=idlist.get(temp);
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
    	temp=rand.nextInt(4);
    	for(int i=0;i<4;i++){
    		if(temp==0)
    			ANSWER=i+1;
    		System.out.println("now temp is:"+temp);
    		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+optionId[temp]));
    		total = getIntent().getData();
    		test = managedQuery(total, null, null, null, null);
    		test.moveToFirst();
    		if(test.getString(2).indexOf("[")!=-1)
    			optionValue[i]=test.getString(2).substring(0, test.getString(2).indexOf("["));
    		else
    			optionValue[i]=test.getString(2);
    		test.close();
    		//optionId[temp]=0;
    		temp=(temp+1)% 4;
    	}
		
    	//radioGroup.clearCheck();
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
    	if(selection==ANSWER){
    		correct++;
    	}else{
    		wronglist.add(tvVoc.getText().toString()+":"+optionValue[ANSWER-1]);
    		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/UpdateRating:3:"+ans));
    		total = getIntent().getData();
    		test = managedQuery(total, null, null, null, null);
    		test.moveToFirst();
    		System.out.println("wrong:"+ans);
    		test.close();
    	}
    	if(showlist.isEmpty()){
    		Intent it = new Intent();
    		it.setClass(Test.this, PostTest.class);
    		it.putExtra("Grade", (float) correct/testvocs * 100);
    		it.putExtra("Time",tvTime.getText().toString());
    		it.putExtra("cpt",cpt);
    		it.putExtra("wronglist",wronglist);
    		it.putExtra("BOOK", getIntent().getExtras().getInt("BOOK"));
    		finish();
    		startActivity(it);
    	}else
			generateOption();
			
    		
    	/*it.putExtra("init", true);
		it.putExtra("selected", cptrange);
		it.putIntegerArrayListExtra("showlist", showlist);
		*/
    		
    		
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
				Toast.makeText(v.getContext(), "請選擇一個選項！", Toast.LENGTH_SHORT).show();
				break;
			}
			Log.i("RADIO","SUBMITTED:"+selection);
			if (selection != 0) submit(selection);
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
