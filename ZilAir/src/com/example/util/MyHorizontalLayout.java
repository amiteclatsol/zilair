package com.example.util;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MyHorizontalLayout extends LinearLayout {

	Context myContext;
	ArrayList<Bitmap> itemList = new ArrayList<Bitmap>();

	ArrayList<Bitmap> main_images = new ArrayList<Bitmap>();

	//ImageView back_image;
	
	private CurlView mCurlView;

	public MyHorizontalLayout(Context context) {
		super(context);
		myContext = context;
	}

	public MyHorizontalLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		myContext = context;
	}

	public MyHorizontalLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		myContext = context;
	}

	public void add(Drawable path) {
		int newIdx = itemList.size();
		itemList.add(convertToBitmap(path, 100, 100));
		addView(getImageView(newIdx));
	}

	public Bitmap convertToBitmap(Drawable drawable, int widthPixels,
			int heightPixels) {
		Bitmap mutableBitmap = Bitmap.createBitmap(widthPixels, heightPixels,
				Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(mutableBitmap);
		drawable.setBounds(0, 0, widthPixels, heightPixels);
		drawable.draw(canvas);

		return mutableBitmap;
	}

	ImageView getImageView(final int i) {
		Bitmap bm = null;
		if (i < itemList.size()) {
			// bm = decodeSampledBitmapFromUri(itemList.get(i), 220, 220);
			bm = itemList.get(i);
		}

		ImageView imageView = new ImageView(myContext);
		imageView.setLayoutParams(new LayoutParams(100, 100));
		imageView.setPadding(5, 5, 5, 5);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setImageBitmap(bm);

		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				/*Toast.makeText(myContext, "Clicked - " + main_images.get(i),
						Toast.LENGTH_LONG).show();*/
				//back_image.setImageBitmap(main_images.get(i));
				mCurlView.setCurrentIndex(i);
			}
		});

		return imageView;
	}

	public void setCurlView(CurlView mCurlView) {
		// TODO Auto-generated method stub
		this.mCurlView = mCurlView;
	}

	/*public void setImageView(ImageView back_image) {
		// TODO Auto-generated method stub
		this.back_image = back_image;
	}

	public void setDefaultImage(Bitmap bitmap) {
		// TODO Auto-generated method stub
		back_image.setImageBitmap(bitmap);
	}*/

	/*public void addMain(Bitmap bitmap) {
		// TODO Auto-generated method stub
		main_images.add(bitmap);
	}*/

	/*
	 * public Bitmap decodeSampledBitmapFromUri(String path, int reqWidth, int
	 * reqHeight) { Bitmap bm = null;
	 * 
	 * // First decode with inJustDecodeBounds=true to check dimensions final
	 * BitmapFactory.Options options = new BitmapFactory.Options();
	 * options.inJustDecodeBounds = true; BitmapFactory.decodeFile(path,
	 * options);
	 * 
	 * // Calculate inSampleSize options.inSampleSize =
	 * calculateInSampleSize(options, reqWidth, reqHeight);
	 * 
	 * // Decode bitmap with inSampleSize set options.inJustDecodeBounds =
	 * false; bm = BitmapFactory.decodeFile(path, options);
	 * 
	 * return bm; }
	 * 
	 * public int calculateInSampleSize(
	 * 
	 * BitmapFactory.Options options, int reqWidth, int reqHeight) { // Raw
	 * height and width of image final int height = options.outHeight; final int
	 * width = options.outWidth; int inSampleSize = 1;
	 * 
	 * if (height > reqHeight || width > reqWidth) { if (width > height) {
	 * inSampleSize = Math.round((float) height / (float) reqHeight); } else {
	 * inSampleSize = Math.round((float) width / (float) reqWidth); } }
	 * 
	 * return inSampleSize; }
	 */

}