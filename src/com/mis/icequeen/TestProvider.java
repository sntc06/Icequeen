package com.mis.icequeen;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.content.ContentProvider;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class TestProvider extends ContentProvider {
	private final String DATABASE_PATH = android.os.Environment.getExternalStorageDirectory().getAbsolutePath()	+ "/phonebook";
	public static final String PATH = "/phonebook";
	private final String DATABASE_FILENAME = "vls.db";
	SQLiteDatabase db ;
	//SQLiteOpenHelper-建立資料庫PhoneContentDB和Table:Users
	private static class DatabaseHelper extends SQLiteOpenHelper {
		private static final String DATABASE_NAME = "vls.db";
		private static final int DATABASE_VERSION = 1;
		//建立PhoneContentDB資料庫
		private DatabaseHelper(Context ctx) {
			super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
		}
		//建立Users表單
		@Override
		public void onCreate(SQLiteDatabase db) {
			String sql = "CREATE TABLE " + UserSchema.TABLE_NAME + " (" 
			+ UserSchema.ID  + " INTEGER primary key , " 
			+ UserSchema.USER_NAME + " text not null "+ ");";
			//Log.i("haiyang:createDB=", sql);
			db.execSQL(sql);
		}
		//更新新版本
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS vls");
            onCreate(db);
		}
	}
	//定義DatabaseHelper類別變數 databaseHelper
    static DatabaseHelper databaseHelper;
    //實作Content Providers的onCreate()
    @Override
    public boolean onCreate() {
    	db=openDatabase(getContext());
    	databaseHelper = new DatabaseHelper(getContext());
        return true;
    }
    public interface UserSchema {
		String TABLE_NAME = "WORD";          //Table Name
		String ID = "w_id";                    //ID
		String USER_NAME = "s_word";       //User Name
		}
    //實作Content Providers的insert()
    @Override
    public Uri insert(Uri uri, ContentValues values) {
    	//SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.insert(UserSchema.TABLE_NAME, null, values);
		db.close();
		return null;
	}
    //實作Content Providers的query()
    @Override
    public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
    	//SQLiteDatabase db = databaseHelper.getWritableDatabase();
		db.update(UserSchema.TABLE_NAME, values, selection ,null);
		db.close();
		return 0;
	}
    //實作Content Providers的delete()
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
    	//SQLiteDatabase db = databaseHelper.getWritableDatabase(); 
		db.delete(UserSchema.TABLE_NAME, selection ,null);
		db.close();
		return 0;
	}
   
    //實作Content Providers的update()
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        //SQLiteDatabase db = databaseHelper.getReadableDatabase();
        //SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        //qb.setTables(UserSchema.TABLE_NAME);
        //Log.i("ICEQUEEN", db.isReadOnly()+"");
        Cursor c = db.query("WORD", projection, selection, selectionArgs, null, null, null);
        return c;
    }
    //實作Content Providers的getType()
    @Override
    public String getType(Uri uri) {
        // TODO Auto-generated method stub
        return null;
    }
    
	private SQLiteDatabase openDatabase(Context context) {
    	try {
			File myDataPath = new File(DATABASE_PATH+PATH);
			System.out.println(DATABASE_PATH+PATH);
			String databaseFilename = myDataPath+ "/" + DATABASE_FILENAME;
			if (!myDataPath.exists()) {
				myDataPath.mkdirs();	
			}
			if (!(new File(databaseFilename)).exists())
			{				
				InputStream is = context.getResources().openRawResource(R.raw.vls);
				FileOutputStream fos = new FileOutputStream(databaseFilename);
				byte[] buffer = new byte[8192];
				int count = 0;
				while ((count = is.read(buffer)) > 0)
				{
					fos.write(buffer, 0, count);
				}
				fos.close();
				is.close();
			}
			SQLiteDatabase database = SQLiteDatabase.openDatabase(databaseFilename, null, -1);
			return database;
		}
		catch (Exception e)
		{
			Log.i("DB","DB_DIR_Exception: ");
			e.printStackTrace();
		}
		return null;
    }
}
