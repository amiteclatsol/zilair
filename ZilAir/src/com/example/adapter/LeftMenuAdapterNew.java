package com.example.adapter;

import java.util.ArrayList;

import com.example.zilair.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class LeftMenuAdapterNew extends BaseAdapter {
	private Context context;
	private ArrayList<Bitmap> bitmapImage;
	private Boolean ishome;

	public int getCount() {
		return bitmapImage.size();
	}

	public Object getItem(int position) {
		return bitmapImage.get(position);
	}

	public long getItemId(int position) {
		return 0;
	}

	public LeftMenuAdapterNew(Context c, ArrayList<Bitmap> bitmap, Boolean ishome) {
		context = c;
		bitmapImage = bitmap;
		this.ishome = ishome;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		/*ImageView imageView;
		if (convertView == null) {
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(5, 5, 5, 5);
		} else {
			imageView = (ImageView) convertView;
		}
		imageView.setImageBitmap(bitmapImage[position]);*/
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = null;
		if(ishome)
		{
			rowView = inflater.inflate(R.layout.homemenu_row, null, true);
		}
		else
		{
			rowView = inflater.inflate(R.layout.menuleftlist_row, null, true);
		}
			
				
		ImageView imageView = (ImageView) rowView.findViewById(R.id.item);
		imageView.setImageBitmap(bitmapImage.get(position));		
		return rowView;
	}

}