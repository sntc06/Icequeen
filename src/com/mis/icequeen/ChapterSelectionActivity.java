/**
 * 
 */
package com.mis.icequeen;

import java.util.ArrayList;

import com.mis.icequeen.ChapterListAdapter.ViewHolder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;

/**
 * 收到選擇模式的 Intent 之後，選擇章節之後再啟動對應的 Activity
 */
public class ChapterSelectionActivity extends Activity {
	
	private ArrayList<String> data;
	
	private ListView list;
	
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
		
		// Insert data
		data = new ArrayList<String>();
		for (int i=0; i<50; i++) {
			data.add("資料 "+(i+1));
		}
		
		// 填充 ListView
		list = (ListView)findViewById(R.id.chapterSelectionList);    
        ChapterListAdapter adapter=new ChapterListAdapter(this, data);    
        list.setAdapter(adapter);    
        list.setItemsCanFocus(false);    
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);   
        
        // 測試預先勾選範圍 
        /*
        for (int i=0; i<10; i++) {
        	adapter.setClick(i, true);
        }
        adapter.notifyDataSetChanged();*/
    
        list.setOnItemClickListener(new OnItemClickListener(){    
            @Override    
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {    
                ViewHolder vHollder = (ViewHolder) view.getTag();
                //每次點擊 item 時將對應的 checkbox 狀態改變，同時修改 map 的值
                vHollder.chapterCB.toggle();    
                ChapterListAdapter.isSelected.put(position, vHollder.chapterCB.isChecked());    
            }

        }); 
        
        // 點了確定按鈕
        Button btnConfirmChapter = (Button) findViewById(R.id.btnConfirmChapter);
        btnConfirmChapter.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				for(int i=0; i<list.getCount(); i++) {    
	                if(ChapterListAdapter.isSelected.get(i)) {
	                	Log.i("ChapterSelected","i="+(i+1));	                	
	                }
	            }
				Intent it = new Intent(ChapterSelectionActivity.this, LearningActivity.class);
				startActivity(it);
				
				
			}
        	
        });
		
	}

}
