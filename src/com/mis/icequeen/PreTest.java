package com.mis.icequeen;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class PreTest extends Activity {
	
	private Button btnConfirmTest;
	private LinearLayout pendingVocLinearLayout;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pre_test);
        
        ButtonListener buttonListener = new ButtonListener();
        btnConfirmTest = (Button) findViewById(R.id.btnConfirmTest);
        btnConfirmTest.setOnClickListener(buttonListener);
        pendingVocLinearLayout = (LinearLayout) findViewById(R.id.pendingVocLinearLayout);
        
    }
    
    private class ButtonListener implements OnClickListener {
		public void onClick(View v) {
			Intent it = new Intent();
			it.setClass(PreTest.this, Test.class);
			startActivity(it);
		}
    }

}
