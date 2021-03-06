package com.mis.icequeen;

import pl.polidea.coverflow.*;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class MainActivity extends BaseActivity {

	// 預設選擇的課本
	private static long selectedBookId = 0;
	public static String BookName[] = new String[]{"Economics","Marketing","null","null","null","null"};
	// CoverFlow 顯示的圖片 
	private final int[] RESOURCE_IMAGES = {
			R.drawable.book_1
	};
	// CoverFlow 初始位置
	public final int COVERFLOW_INIT_POSITION = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupView();
    }

	/**
	 *  建立畫面 
	 */
	private void setupView() {

        //CoverFlow Reflection
        final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(this.getResources().getIdentifier(
                "coverflowReflection", "id", "com.mis.icequeen"));
        setupCoverFlow(reflectingCoverFlow, true);
        setTitle(getResources().getString(R.string.app_name));
        Button btnLearn = (Button) findViewById(R.id.btnLearn);
        Button btnReview = (Button) findViewById(R.id.btnReview);
        Button btnTest = (Button) findViewById(R.id.btnTest);
        ButtonListener buttonListener = new ButtonListener();
        btnLearn.setOnClickListener(buttonListener);
        btnReview.setOnClickListener(buttonListener);
        btnTest.setOnClickListener(buttonListener);
        
		ImageButton ic_gear_button = (ImageButton) findViewById(R.id.ic_gear_button);
		ic_gear_button.setOnClickListener(new OnClickListener(){
			public void onClick(View arg0) {
				Intent it = new Intent();
				it.setClass(MainActivity.this, Settings.class);
		        startActivity(it);
			}
		});
	}

	/**
     * Setup cover flow.
     * 
     * @param mCoverFlow
     *            the m cover flow
     * @param reflect
     *            the reflect
     */
    private void setupCoverFlow(final CoverFlow mCoverFlow, final boolean reflect) {
        BaseAdapter coverImageAdapter;
        
        if (reflect) {
        	ResourceImageAdapter resourceImageAdapter = new ResourceImageAdapter(this);
            coverImageAdapter = new ReflectingImageAdapter(resourceImageAdapter);
            resourceImageAdapter.setResources(RESOURCE_IMAGES);
        } else {
            coverImageAdapter = new ResourceImageAdapter(this);
            ((ResourceImageAdapter) coverImageAdapter).setResources(RESOURCE_IMAGES);
        }
       
        mCoverFlow.setAdapter(coverImageAdapter);
        mCoverFlow.setSelection(COVERFLOW_INIT_POSITION, true);
        setupListeners(mCoverFlow);
    }

    /**
     * Sets the up listeners.
     * 
     * @param mCoverFlow
     *            the new up listeners
     */
    private void setupListeners(final CoverFlow mCoverFlow) {
        mCoverFlow.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(final AdapterView< ? > parent, final View view, final int position, final long id) {
            	selectedBookId = id;
            	Log.i("COVERFLOW","clicked:"+id);
            }

        });
        mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(final AdapterView< ? > parent, final View view, final int position, final long id) {
            	selectedBookId = id;
            	Log.i("COVERFLOW","selected:"+id);
            }
            public void onNothingSelected(final AdapterView< ? > parent) { /*Do nothing */ }
        });
    }
    
    private class ButtonListener implements OnClickListener {

		public void onClick(View v) {
			
			Intent it = new Intent();
			String mode = "";
			
			switch (v.getId()) {
			case R.id.btnLearn:
				mode = "LEARN";
				break;
			case R.id.btnReview:
				//review
				mode = "REVIEW";
				break;
			case R.id.btnTest:
				//test
				mode = "TEST";
				break;
			default:
				break;
			}
			
			// chosen book id
			it.putExtra("MODE", mode);
			it.putExtra("BOOK", (int)selectedBookId);
			it.setClass(MainActivity.this, ChapterSelectionActivity.class);
			if(BookName[(int)selectedBookId].equals("null"))
        		Toast.makeText(v.getContext(), "未擁有此課本", Toast.LENGTH_LONG).show();
			else
				startActivity(it);

		}
    	
    }
    
}