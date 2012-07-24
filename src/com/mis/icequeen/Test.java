package com.mis.icequeen;

import android.app.Activity;
import android.content.Intent;
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
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        
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
    	
    }
    
    /**
     * 檢查答案正確 並且紀錄至資料庫中
     * 執行完成後載入下一題
     * @param selection 選項
     */
    private void submit(int selection) {
    	
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
