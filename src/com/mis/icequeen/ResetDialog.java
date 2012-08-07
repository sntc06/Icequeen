package com.mis.icequeen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.preference.DialogPreference;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ResetDialog extends DialogPreference {
	private Context context;
    private LinearLayout layout;
    
	public final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()	+ "/icequeen";
	public static final String PATH = "/db";
	public final String DATABASE_FILENAME = "iceqdb.db";
         
    public ResetDialog (Context context, AttributeSet attrs)
    {
        super(context, attrs);
        this.context = context;
        setDialogMessage("�O�_�n���]��Ʈw�H�Ҧ��ǲ߬������|�򥢡I\n\n���]��������A���ε{���|�۰������A�Э��s�Ұ����ε{���C");
        setPositiveButtonText(android.R.string.ok);
        setNegativeButtonText(android.R.string.cancel);
      }
 
    @Override
    protected void onPrepareDialogBuilder(Builder builder)
    {
        super.onPrepareDialogBuilder(builder);
        //layout���w�q
        layout=new LinearLayout(context);
        layout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        layout.setMinimumWidth(400);
        layout.setPadding(20, 20, 20, 20);
        layout.setOrientation(1);  // 1 is vertical
         
        builder.setView(layout);
    }
    
    public void onClick (DialogInterface dialog, int which) {
    	Log.i("DIALOG",""+which);
    	switch (which) {
    	case -1: 
    		//�T�w
    		resetDB();
    	case -2:
    		return;
    	default:
    		return;
    	}
    }

	/**
	 * �R����Ʈw
	 */
	private void resetDB() {
    	try {
			File myDataPath = new File(TestProvider.DATABASE_PATH+TestProvider.PATH);
			System.out.println(TestProvider.DATABASE_PATH+TestProvider.PATH);
			if (myDataPath.exists()) {	
				if (myDataPath.isDirectory()) {
			        String[] children = myDataPath.list();
			        for (int i = 0; i < children.length; i++) {
			            new File(myDataPath, children[i]).delete();
			        }
			        Toast.makeText(context, "��Ʈw�w�g���]", Toast.LENGTH_LONG).show();
			        // �����{��
			        android.os.Process.killProcess(android.os.Process.myPid());
			    }
			}
		}
		catch (Exception e)
		{
			Log.i("DB","DB_DIR_Exception: ");
			e.printStackTrace();
		}
		
	}
}