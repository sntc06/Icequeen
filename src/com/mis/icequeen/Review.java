/**
 * 
 */
package com.mis.icequeen;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RatingBar.OnRatingBarChangeListener;

public class Review extends BaseActivity implements OnInitListener {
	private ArrayList<Integer> showlist;
	private final int KK_IMAGE_DESNITY = 240; // 圖片解析度，數值越小圖片越大
	static int nowvocid = 762;
	int[] cptrange;
	int deforate;
	private Uri total;
	private Cursor test;
	private final Locale locale = Locale.UK;
	private TextToSpeech tts;
	private TextView word, meaning, classes, sentence, count;
	private ImageButton btnPrev, btnNext;
	private LinearLayout layout;
	private Button play,btnRemoveStar;
	private ImageView KK;
	private RatingBar ratingBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.review);
		layout = (LinearLayout) findViewById(R.id.reviewshow);
		layout.setFocusable(true);
		
		int index;
		if(!extras.getString("side").equals("left"))
			overridePendingTransition(R.anim.push_left_in,R.anim.push_left_out);
		else
			overridePendingTransition(R.anim.push_right_in,R.anim.push_right_out);
		
		showlist = new ArrayList<Integer>();
		index = extras.getInt("index");
		deforate = extras.getInt("Rate");
		System.out.println("received intent" + nowvocid);
		cptrange = extras.getIntArray("selected");
		
			showlist = extras.getIntegerArrayList("showlist");
			
		nowvocid = showlist.get(index);

		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+nowvocid));
		Uri uri = getIntent().getData();

		word = (TextView) findViewById(R.id.tvWord);
		meaning = (TextView) findViewById(R.id.tvMeaning);
		classes = (TextView) findViewById(R.id.tvClass);
		sentence = (TextView) findViewById(R.id.tvSentence);
		count = (TextView) findViewById(R.id.tvCount);
		btnPrev = (ImageButton) findViewById(R.id.btnPrev);
		btnNext = (ImageButton) findViewById(R.id.btnNext);
		play = (Button) findViewById(R.id.pronounce);
		btnRemoveStar = (Button) findViewById(R.id.btnRemoveStar);
		KK = (ImageView) findViewById(R.id.KKView1);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		ratingBar.setRating(deforate);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		RatingBarListener ratingBarListener = new RatingBarListener(); 
    	ratingBar.setOnRatingBarChangeListener(ratingBarListener);
    	
		Cursor set = managedQuery(uri, null, null, null, null);
		if (set.getCount() != 0) {
			set.moveToFirst();
			word.setText(set.getString(1));
			//meaning.setText(set.getString(2));
			//classes.setText(set.getString(3) + "    " + set.getString(4));
			sentence.setText(set.getString(5) + "\n" + set.getString(6));
			ratingBar.setRating(set.getFloat(7));
			count.setText((index + 1) + "/" + showlist.size());
		} else
			System.out.println("error1");
		set.close();
		// 顯示 KK 音標 from assets
		String vockk = word.getText().toString();
		try {
			Options opts = new BitmapFactory.Options();
			opts.inDensity = KK_IMAGE_DESNITY;
			AssetManager am = getAssets();
			BufferedInputStream buf = new BufferedInputStream(am.open(vockk
					+ ".jpg"));
			Bitmap bitmap = BitmapFactory.decodeStream(buf, new Rect(), opts);
			KK.setImageBitmap(bitmap);
			buf.close();
			Log.v("ASSET_IMAGE", "read from assets:" + vockk);
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		// 上一個按鈕
		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (nowvocid != showlist.get(0)) {
					Intent intent = new Intent(Review.this, Review.class);
					intent.putExtra("init", false);
					intent.putExtra("selected", cptrange);
					intent.putExtra("index", showlist.indexOf(nowvocid) - 1);
					intent.putIntegerArrayListExtra("showlist", showlist);
					intent.putExtra("Rate",deforate);
					intent.putExtra("side", "left");
					finish();
					startActivity(intent);
				} else {
					Toast.makeText(v.getContext(), "已經在最前面了!", Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		// 下一個按鈕
		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (nowvocid != showlist.get(showlist.size() - 1)) {

					Intent intent = new Intent(Review.this, Review.class);
					intent.putExtra("init", false);
					intent.putExtra("index", showlist.indexOf(nowvocid) + 1);
					intent.putIntegerArrayListExtra("showlist", showlist);
					intent.putExtra("Rate",deforate);
					intent.putExtra("side", "right");
					finish();
					startActivity(intent);
				} else {
					Toast.makeText(v.getContext(), "已經在最後面了!",Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		// 播放按鈕
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// destroy duplicate service
				onDestroy();
				tts = new TextToSpeech(Review.this, Review.this);
				Log.v("TTS","tts service created.");
			}

		});
		
		btnRemoveStar.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// destroy duplicate service
				
				getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/UpdateRating:0:"+nowvocid));
	    		total = getIntent().getData();
	    		test = managedQuery(total, null, null, null, null);
	    		test.close();
	    		
	    		Intent intent;
	    		if(showlist.indexOf(nowvocid)==0 && showlist.size()==1)
	    			intent = new Intent(Review.this, PreReview.class);
	    		else
	    			intent = new Intent(Review.this, Review.class);
				intent.putExtra("init", false);
				intent.putExtra("selected", cptrange);
				if (nowvocid == showlist.get(showlist.size() - 1))
					intent.putExtra("index", showlist.indexOf(nowvocid)-1);
				else
					intent.putExtra("index", showlist.indexOf(nowvocid));
				
				showlist.remove(showlist.indexOf(nowvocid));
				intent.putIntegerArrayListExtra("showlist", showlist);
				intent.putExtra("Rate",deforate);
				intent.putExtra("side", "left");
				finish();
				startActivity(intent);
			}

		});
		
		// 自動發音
		SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
	    if (preferences.getBoolean("autospeak_review", true) == true) {
	    	// destroy duplicate service
			onDestroy();
			tts = new TextToSpeech(Review.this, Review.this);
			Log.v("TTS","tts service created.");
	    }
		

	}
	public void onClick (View view) {
		getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/getsetbyid:"+nowvocid));
		Uri uri = getIntent().getData();
		Cursor set = managedQuery(uri, null, null, null, null);
		if (set.getCount() != 0) {
			set.moveToFirst();
			//word.setText(set.getString(1));
			meaning.setText(set.getString(2));
			classes.setText(set.getString(3) + "    " + set.getString(4));
			//sentence.setText(set.getString(5) + "\n" + set.getString(6));
			//ratingBar.setRating(set.getFloat(7));
			//count.setText((index + 1) + "/" + showlist.size());
		} else
			System.out.println("error1");
		set.close();
		layout.setVisibility(0);
		System.out.println("CLICKKKKKKKKKKKKKKKKED");
    }
	
	private class RatingBarListener implements OnRatingBarChangeListener {
    	// 在星等改變的時候呼叫 refreshPendingVoc
		public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
			
			getIntent().setData(Uri.parse("content://com.mis.icequeen.testprovider/UpdateRating:"+(int) rating+":"+nowvocid));
			System.out.println("content://com.mis.icequeen.testprovider/UpdateRating:"+(int) rating+":"+nowvocid);
    		total = getIntent().getData();
    		test = managedQuery(total, null, null, null, null);
    		test.moveToFirst();
    		System.out.println("voc rating change:"+test.getString(7));
    		test.close();
    		
		}
    }
	
	/**
	 * TTS service 被建立之後開始說話
	 * */
	public void onInit(int status) {
	      if (status == TextToSpeech.SUCCESS) {
	    	  
	            int result = tts.setLanguage(locale);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.i("TTS", "This Language is not supported");
	            } else {
	            	// 利用 tts 說出單字
	                speakOut();
	                Log.v("TTS","tts service init");
	            }
	 
	        } else {
	            Log.e("TTS", "Initilization Failed!");
	        }
		
	}
	
	/**
	 * 利用 TTS 念出單字
	 */
    private void speakOut() {
        String text2 = word.getText().toString();
        tts.speak(text2, TextToSpeech.QUEUE_FLUSH, null);
    }
    
    @Override
    public void onDestroy() {
        // Don't forget to shutdown!
        if (tts != null) {
            tts.stop();
            tts.shutdown();
            Log.v("TTS","tts service destroyed.");
        }
        super.onDestroy();
    }
}