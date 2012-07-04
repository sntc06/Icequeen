/**
 * 
 */
package com.mis.icequeen;

import android.app.Activity;
import android.os.Bundle;

/**
 * 收到選擇模式的 Intent 之後，選擇章節之後再啟動對應的 Activity
 */
public class ChapterSelectionActivity extends Activity {
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter_selection);
        setupView();
    }

	/**
	 * 
	 */
	private void setupView() {
		// TODO Auto-generated method stub
		
	}

}
