/*
 * @Ref: http://mobile.51cto.com/android-254823.htm
 */

package com.mis.icequeen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

public class ChapterListAdapter extends BaseAdapter {    
    private LayoutInflater mInflater;    
    private List<Map<String, Object>> mData;    
    public static Map<Integer, Boolean> isSelected;    
    
    public ChapterListAdapter(Context context, ArrayList<String> data) {    
        mInflater = LayoutInflater.from(context);
        
        mData=new ArrayList<Map<String, Object>>();  
        for (int i=0; i<data.size(); i++) {
        	Map<String, Object> map = new HashMap<String, Object>();
            //map.put("img", R.drawable.icon);
            map.put("title", "data:" + data.get(i));
            mData.add(map);
        }
        
        //紀錄 isSelected 此 map 終 listitem 的狀態，預設皆為 false    
        isSelected = new HashMap<Integer, Boolean>();    
        for (int i = 0; i < mData.size(); i++) {    
            isSelected.put(i, false);    
        }
 
    }    
 
    public void setClick(int index, boolean clicked) {
    	isSelected.put(index, clicked);
    }
    
    public void cleanClick() {
        for (int i = 0; i < mData.size(); i++) {    
            isSelected.put(i, false);    
        }
    }
    
    @Override    
    public int getCount() {    
        return mData.size();    
    }    
    
    @Override    
    public Object getItem(int position) {    
        return null;    
    }    
    
    @Override    
    public long getItemId(int position) {    
        return 0;    
    }    
    
    @Override    
    public View getView(int position, View convertView, ViewGroup parent) {    
        ViewHolder holder = null;    
        //convertView為null時初始化convertView。    
        if (convertView == null) {    
            holder = new ViewHolder();    
            convertView = mInflater.inflate(R.layout.chapter_list, null);    
            //holder.img = (ImageView) convertView.findViewById(R.id.img);    
            holder.chapterTitleTV = (TextView) convertView.findViewById(R.id.chapterTitleTV);    
            holder.chapterCB = (CheckBox) convertView.findViewById(R.id.chapterCB);    
            convertView.setTag(holder);    
        } else {    
            holder = (ViewHolder) convertView.getTag();    
        }    
        //holder.img.setBackgroundResource((Integer) mData.get(position).get("img"));    
        holder.chapterTitleTV.setText(mData.get(position).get("title").toString());    
        holder.chapterCB.setChecked(isSelected.get(position));    
        return convertView;    
    }    
    
    public final class ViewHolder {       
        public TextView chapterTitleTV;    
        public CheckBox chapterCB;    
    }
   
}    