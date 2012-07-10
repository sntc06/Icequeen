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
 * �����ܼҦ��� Intent ����A��ܳ��`����A�Ұʹ����� Activity
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
			data.add("��� "+(i+1));
		}
		
		// ��R ListView
		list = (ListView)findViewById(R.id.chapterSelectionList);    
        ChapterListAdapter adapter=new ChapterListAdapter(this, data);    
        list.setAdapter(adapter);    
        list.setItemsCanFocus(false);    
        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);   
        
        // ���չw���Ŀ�d�� 
        /*
        for (int i=0; i<10; i++) {
        	adapter.setClick(i, true);
        }
        adapter.notifyDataSetChanged();*/
    
        list.setOnItemClickListener(new OnItemClickListener(){    
            @Override    
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {    
                ViewHolder vHollder = (ViewHolder) view.getTag();
                //�C���I�� item �ɱN������ checkbox ���A���ܡA�P�ɭק� map ����
                vHollder.chapterCB.toggle();    
                ChapterListAdapter.isSelected.put(position, vHollder.chapterCB.isChecked());    
            }

        }); 
        
        // �I�F�T�w���s
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
