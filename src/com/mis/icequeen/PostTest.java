package com.mis.icequeen;

import java.util.ArrayList;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class PostTest extends BaseActivity {
	private TextView tvTime;
	private TextView tvScore;
	private Uri total;
	private int[] cptrange;
	private String[] cpt;
	private Cursor test;
	private Button btnReselect;
	private ArrayList<String> wronglist;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_test);
        
        Bundle extras = getIntent().getExtras();
        cptrange = extras.getIntArray("selected");
        wronglist=extras.getStringArrayList("wronglist");
        tvScore = (TextView) findViewById(R.id.tvScore);
        tvTime = (TextView) findViewById(R.id.tvTime);
        tvScore.setText(extras.getFloat("Grade")+"%");
        tvTime.setText(extras.getString("Time"));
        
        cpt=extras.getString("cpt").toString().split(",");
        for(int i=0;i<cpt.length;i++){
        	getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/Newtest:"+(i+1)+":"+tvScore.getText().toString()));
        	total = getIntent().getData();
        	test = managedQuery(total, null, null, null, null);
        	test.moveToLast();
        	System.out.println("insert:"+test.getString(0)+test.getString(1));
        }
		btnReselect = (Button) findViewById(R.id.btnReselect);
		btnReselect.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent it = new Intent();
				String mode = "TEST";
				it.putExtra("MODE", mode);
				it.putExtra("BOOK", getIntent().getExtras().getInt("BOOK"));
				it.setClass(PostTest.this, ChapterSelectionActivity.class);
				finish();
				startActivity(it);
				
			}
		});
		
		CharSequence[] list = new CharSequence[wronglist.size()];
		for (int i = 0; i < list.length; i++) {
			list[i] = wronglist.get(i);
		}
		
		final Dialog ViewWrongDialog = new Dialog(PostTest.this);
		ViewWrongDialog.setTitle("¿ù»~³æ¦r");
		ViewWrongDialog.setCancelable(true);
		ViewWrongDialog.setContentView(R.layout.viewwrong);
		
		ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(PostTest.this, android.R.layout.simple_list_item_1,list);
		ListView listView = (ListView)ViewWrongDialog.findViewById(R.id.listView1);
		listView.setAdapter(adapter);
		listView.setTextFilterEnabled(true);
		listView.setOnItemClickListener(new OnItemClickListener(){

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Toast.makeText(arg1.getContext(), "YA", Toast.LENGTH_SHORT).show();
				
			}
		
		});
		
		Button btnRetry= (Button) findViewById(R.id.btnRetry);
		btnRetry.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent it = new Intent();
				it.setClass(PostTest.this,PreTest.class);
				it.putExtra("selected", cptrange);
				finish();
				startActivity(it);
			}
		});

		Button btnViewError= (Button) findViewById(R.id.btnViewError);
		btnViewError.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				Intent it = new Intent();
				it.setClass(PostTest.this,ShowWrong.class);
				Bundle extras = new Bundle();
				extras.putStringArrayList("wronglist", wronglist);
				it.putExtras(extras);
				startActivity(it);
			}
		});
    }

}
