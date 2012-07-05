package com.mis.icequeen;

import pl.polidea.coverflow.*;
import android.app.Activity;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private TextView textView;
	
	// CoverFlow 顯示的圖片 
	private final int[] RESOURCE_IMAGES = {
			R.drawable.image01,
			R.drawable.image02,
			R.drawable.image03,
			R.drawable.image04,
			R.drawable.image05,
			R.drawable.image06
	};
	private final int COVERFLOW_INIT_POSITION = 0;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setupView();
        
        getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider"));
        Uri uri_test = getIntent().getData();
        Cursor c = managedQuery(uri_test, null, null, null, null);
        if(c.getCount()!=0)
        {	c.moveToFirst();
        System.out.println(c.getString(0));
        }else 
        	System.out.println("FAIL");
        c.close();
    }

	/**
	 *  建立畫面 
	 */
	private void setupView() {
		// TODO Auto-generated method stub
		textView = (TextView) findViewById(R.id.tvSelectedChapter);
        final CoverFlow coverFlow1 = (CoverFlow) findViewById(this.getResources().getIdentifier("coverflow", "id",
                "com.mis.icequeen"));
        setupCoverFlow(coverFlow1, false);
        //Reflection
        //final CoverFlow reflectingCoverFlow = (CoverFlow) findViewById(this.getResources().getIdentifier(
        //        "coverflowReflect", "id", "pl.polidea.coverflow"));
        //setupCoverFlow(reflectingCoverFlow, true);
		
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
            coverImageAdapter = new ReflectingImageAdapter(new ResourceImageAdapter(this));
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
            @Override
            public void onItemClick(final AdapterView< ? > parent, final View view, final int position, final long id) {
                textView.setText("Item clicked! : " + id);
            }

        });
        mCoverFlow.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(final AdapterView< ? > parent, final View view, final int position, final long id) {
                textView.setText("Item selected! : " + id);
            }

            @Override
            public void onNothingSelected(final AdapterView< ? > parent) {
                textView.setText("Nothing clicked!");
            }
        });
    }
}