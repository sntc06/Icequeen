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
import android.content.res.AssetManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class Review extends Activity implements OnInitListener {
	private ArrayList<Integer> showlist;
	private final int KK_IMAGE_DESNITY = 240; // �Ϥ��ѪR�סA�ƭȶV�p�Ϥ��V�j
	static int nowvocid = 762;
	int[] cptrange;
	int deforate;
	
	private final Locale locale = Locale.UK;
	private TextToSpeech tts;
	private TextView word, meaning, classes, sentence, count;
	private ImageButton btnPrev, btnNext;
	private Button play;
	private ImageView KK;
	private RatingBar ratingBar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		setContentView(R.layout.review);

		Uri total;
		Cursor test;
		int index;

		showlist = new ArrayList<Integer>();
		index = extras.getInt("index");
		deforate = extras.getInt("Rate");
		System.out.println("received intent" + nowvocid);
		
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
		KK = (ImageView) findViewById(R.id.KKView1);
		ratingBar = (RatingBar) findViewById(R.id.ratingBar);
		ratingBar.setRating(deforate);
		Cursor set = managedQuery(uri, null, null, null, null);
		if (set.getCount() != 0) {
			set.moveToFirst();
			word.setText(set.getString(1));
			meaning.setText(set.getString(2));
			classes.setText(set.getString(3) + "    " + set.getString(4));
			sentence.setText(set.getString(5) + "\n" + set.getString(6));
			count.setText((index + 1) + "/" + showlist.size());
		} else
			System.out.println("error1");

		// ��� KK ���� from assets
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

		// �W�@�ӫ��s
		btnPrev.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (nowvocid != showlist.get(0)) {
					Intent intent = new Intent(Review.this, Review.class);
					intent.putExtra("init", false);
					intent.putExtra("selected", cptrange);
					intent.putExtra("index", showlist.indexOf(nowvocid) - 1);
					intent.putIntegerArrayListExtra("showlist", showlist);
					intent.putExtra("Rate",deforate);
					finish();
					startActivity(intent);
				} else {
					Toast.makeText(v.getContext(), "�w�g�b�̫e���F!", Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		// �U�@�ӫ��s
		btnNext.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (nowvocid != showlist.get(showlist.size() - 1)) {

					Intent intent = new Intent(Review.this, Review.class);
					intent.putExtra("init", false);
					intent.putExtra("index", showlist.indexOf(nowvocid) + 1);
					intent.putIntegerArrayListExtra("showlist", showlist);
					intent.putExtra("Rate",deforate);
					finish();
					startActivity(intent);
				} else {
					Toast.makeText(v.getContext(), "�w�g�b�̫᭱�F!",Toast.LENGTH_SHORT).show();
				}
			}

		});
		
		// ������s
		play.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// destroy duplicate service
				onDestroy();
				tts = new TextToSpeech(Review.this, Review.this);
				Log.v("TTS","tts service created.");
			}

		});

	}
	
	/**
	 * TTS service �Q�إߤ���}�l����
	 * */
	public void onInit(int status) {
	      if (status == TextToSpeech.SUCCESS) {
	    	  
	            int result = tts.setLanguage(locale);
	 
	            if (result == TextToSpeech.LANG_MISSING_DATA
	                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
	                Log.i("TTS", "This Language is not supported");
	            } else {
	            	// �Q�� tts ���X��r
	                speakOut();
	                Log.v("TTS","tts service init");
	            }
	 
	        } else {
	            Log.e("TTS", "Initilization Failed!");
	        }
		
	}
	
	/**
	 * �Q�� TTS ���X��r
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