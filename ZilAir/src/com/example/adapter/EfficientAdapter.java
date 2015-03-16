package com.example.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Color;
import android.provider.MediaStore.Audio.Radio;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.zilair.R;

public class EfficientAdapter extends BaseAdapter {

	private LayoutInflater mInflater;

	private ArrayList<String> mStrings;
	
	String get;

	private int mViewResourceId;

	public EfficientAdapter(Context ctx, int viewResourceId, ArrayList<String> strings) {
		super();
		mInflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		mStrings = strings;

		mViewResourceId = viewResourceId;
	}

	@Override
	public int getCount() {
		return mStrings.size();
	}

	@Override
	public String getItem(int position) {
		return mStrings.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		convertView = mInflater.inflate(mViewResourceId, null);
		TextView tv = (TextView) convertView.findViewById(R.id.emptylegs_notification_name);
		tv.setText(mStrings.get(position));	
		
		/*final CheckBox emptylegs_notification_check = (CheckBox)convertView.findViewById(R.id.emptylegs_notification_check);
		emptylegs_notification_check.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(emptylegs_notification_check.isChecked())
				{
					get=mStrings[position];
				}
			}
		});
*/
		return convertView;
	}
	
	/*public String getElement()
	{
		return get;
	}*/
}
