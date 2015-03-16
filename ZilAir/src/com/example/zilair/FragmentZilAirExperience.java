package com.example.zilair;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.util.CurlView;
import com.example.util.MyHorizontalLayout;

public class FragmentZilAirExperience extends Fragment {

	int i = 0;
	private CurlView mCurlView;
	// private Gallery gallery;
	int index = 0;
	LinearLayout imageView;
	MyHorizontalLayout myGallery;
	// private TimelineGallery mTimelineGallery;

	private int[] mBitmapIds = { R.drawable.zilairexp_1,
			R.drawable.zilairexp_7, R.drawable.zilairexp_8,
			R.drawable.zilairexp_9, R.drawable.zilairexp_10,
			R.drawable.zilairexp_11, R.drawable.zilairexp_12,
			R.drawable.zilairexp_13, R.drawable.zilairexp_14,
			R.drawable.zilairexp_15, R.drawable.zilairexp_16,
			R.drawable.zilairexp_17, R.drawable.zilairexp_18,
			R.drawable.zilairexp_19, R.drawable.zilairexp_20,
			R.drawable.zilairexp_21, R.drawable.zilairexp_22,
			R.drawable.zilairexp_23, R.drawable.zilairexp_24,
			R.drawable.zilairexp_25, R.drawable.zilairexp_26,
			R.drawable.zilairexp_27, };

	public static Fragment newInstance(Context context) {
		FragmentZilAirExperience f = new FragmentZilAirExperience();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.fragment_zil_air_exp, null);

		myGallery = (MyHorizontalLayout) root.findViewById(R.id.mygallery);

		// TouchImageView back_image = (TouchImageView)
		// root.findViewById(R.id.back_image);

		Handler handler = new Handler();
		Runnable r = new Runnable() {
			public void run() {

				for (int i = 0; i < mBitmapIds.length; i++) {

					myGallery.add(getActivity().getResources().getDrawable(
							mBitmapIds[i]));
					/*
					 * myGallery.addMain(BitmapFactory.decodeResource(
					 * getActivity().getResources(), mBitmapIds[i]));
					 */
				}

			}
		};
		handler.postDelayed(r, 1000);

		// ************** Code for Image View with horizontal ****//

		// myGallery.setImageView(back_image);
		/*
		 * Bitmap icon = BitmapFactory.decodeResource(
		 * getActivity().getResources(), mBitmapIds[0]);
		 * myGallery.setDefaultImage(icon);
		 */

		// ************** Code for Image View with horizontal ****//

		mCurlView = (CurlView) root.findViewById(R.id.curl);
		
		myGallery.setCurlView(mCurlView);

		/*
		 * gallery = (Gallery) root.findViewById(R.id.examplegallery);
		 * ImageAdapter adapter = new ImageAdapter(getActivity(), mBitmapIds);
		 * gallery.setAdapter(adapter); gallery.setSelection(4);
		 */
		// imageView = (LinearLayout) root.findViewById(R.id.ImageView01);

		// final TouchImageView touchImageView = new
		// TouchImageView(getActivity());

		/*
		 * touchImageView.setImageResource(mBitmapIds[0]); LayoutParams lp = new
		 * LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		 * 
		 * imageView.setGravity(Gravity.CENTER_HORIZONTAL |
		 * Gravity.CENTER_VERTICAL);
		 * 
		 * touchImageView.setLayoutParams(lp);
		 * 
		 * imageView.addView(touchImageView);
		 */

		// configure some values for the LOLZ

		// imageView.setBackgroundResource(mBitmapIds[0]);

		/*
		 * gallery.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> parent, View view,
		 * int position, long id) { // TODO Auto-generated method stub // index
		 * = position;
		 * 
		 * try {
		 * 
		 * imageView.removeAllViews();
		 * 
		 * } catch (Exception e) {
		 * 
		 * e.getMessage();
		 * 
		 * }
		 * 
		 * touchImageView.setImageResource(mBitmapIds[position]);
		 * 
		 * LayoutParams lp = new LayoutParams(LayoutParams.FILL_PARENT,
		 * LayoutParams.FILL_PARENT);
		 * 
		 * imageView.setGravity(Gravity.CENTER_HORIZONTAL |
		 * Gravity.CENTER_VERTICAL);
		 * 
		 * touchImageView.setLayoutParams(lp);
		 * 
		 * imageView.addView(touchImageView);
		 * 
		 * } });
		 */

		/*
		 * ViewGroup root = (ViewGroup) inflater.inflate(
		 * R.layout.fragment_zil_air_experience, null);
		 */

		/*
		 * if (getLastNonConfigurationInstance() != null) { index = (Integer)
		 * getLastNonConfigurationInstance(); }
		 */

		// ******************** Curl View Code **************//

		mCurlView.setBitmapProvider(new BitmapProvider());
		mCurlView.setSizeChangedObserver(new SizeChangedObserver());
		mCurlView.setCurrentIndex(index);
		mCurlView.setBackgroundColor(0xFF202830);

		// This is something somewhat experimental. Before uncommenting next
		// line, please see method comments in CurlView.
		mCurlView.setEnableTouchPressure(true);
		

		// CAGS: This is to allow 2 pages landscape mode, set to false for //
		// legacy mode
		mCurlView.set2PagesLandscape(false);
		if (getRetainInstance()) {
			index = (Integer) onRetainCustomNonConfigurationInstance();
		}

		// ******************** Curl View ******************//

		/*
		 * final AccelerateInterpolator accelerator = new
		 * AccelerateInterpolator(); final DecelerateInterpolator decelerator =
		 * new DecelerateInterpolator();
		 * 
		 * ViewGroup root = (ViewGroup) inflater.inflate(
		 * R.layout.fragment_zil_air_experience, null); final int[] book_left =
		 * { R.drawable.sp1, R.drawable.sp3, R.drawable.sp1, R.drawable.sp3 };
		 * final int[] book_right = { R.drawable.sp2, R.drawable.sp4,
		 * R.drawable.sp2, R.drawable.sp4 };
		 * 
		 * LinearLayout book = (LinearLayout) root.findViewById(R.id.book);
		 * final ImageView bookimage_left = (ImageView) root
		 * .findViewById(R.id.bookimage_left); final ImageView bookimage_right =
		 * (ImageView) root .findViewById(R.id.bookimage_right);
		 * bookimage_left.setBackgroundResource(R.drawable.homebackground);
		 * bookimage_right.setBackgroundResource(R.drawable.homebackground);
		 * 
		 * final int temp = i; book.setOnTouchListener(new
		 * OnSwipeTouchListener(getActivity()) {
		 * 
		 * @Override public void onSwipeLeft() {
		 * 
		 * if (i == 3) { bookimage_left.setBackgroundResource(book_left[3]);
		 * bookimage_right.setBackgroundResource(book_right[3]); } else {
		 * 
		 * ObjectAnimator visToInvis = ObjectAnimator.ofFloat( bookimage_right,
		 * "rotationY", 180f, 0f); visToInvis.setDuration(500);
		 * visToInvis.setInterpolator(accelerator); visToInvis.start();
		 * 
		 * visToInvis.addListener(new AnimatorListenerAdapter() {
		 * 
		 * @Override public void onAnimationEnd(Animator anim) { final
		 * ObjectAnimator invisToVis = ObjectAnimator .ofFloat(bookimage_left,
		 * "rotationY", 90f, 0f); invisToVis.setDuration(500);
		 * invisToVis.setInterpolator(decelerator); invisToVis.start(); i++;
		 * bookimage_left.setBackgroundResource(book_left[i]); bookimage_right
		 * .setBackgroundResource(book_right[i]); } });
		 * 
		 * }
		 * 
		 * }
		 * 
		 * @Override public void onSwipeRight() { // TODO Auto-generated method
		 * stub
		 * 
		 * if (i == 0) { bookimage_left.setBackgroundResource(book_left[0]);
		 * bookimage_right.setBackgroundResource(book_right[0]); } else {
		 * 
		 * ObjectAnimator visToInvis = ObjectAnimator.ofFloat( bookimage_left,
		 * "rotationY", -180f, 0f); visToInvis.setDuration(500);
		 * visToInvis.setInterpolator(accelerator); visToInvis.start();
		 * 
		 * visToInvis.addListener(new AnimatorListenerAdapter() {
		 * 
		 * @Override public void onAnimationEnd(Animator anim) { final
		 * ObjectAnimator invisToVis = ObjectAnimator .ofFloat(bookimage_right,
		 * "rotationY", -90f, 0f); invisToVis.setDuration(500);
		 * invisToVis.setInterpolator(decelerator); invisToVis.start(); i--;
		 * bookimage_left.setBackgroundResource(book_left[i]); bookimage_right
		 * .setBackgroundResource(book_right[i]); } });
		 * 
		 * } } });
		 */
		return root;
	}

	@Override
	public void onPause() {
		super.onPause();
		mCurlView.onPause();
	}

	@Override
	public void onResume() {
		super.onResume();
		mCurlView.onResume();
	}

	public Object onRetainCustomNonConfigurationInstance() {
		return mCurlView.getCurrentIndex();
	}

	private class BitmapProvider implements CurlView.BitmapProvider {

		@Override
		public Bitmap getBitmap(int width, int height, int index) {
			Bitmap b = Bitmap.createBitmap(width, height,
					Bitmap.Config.ARGB_8888);
			b.eraseColor(0xFFFFFFFF);
			Canvas c = new Canvas(b);
			Drawable d = getResources().getDrawable(mBitmapIds[index]);

			/*int margin = 7;
			int border = 3;*/

			int margin = 0;
			int border = 0;
			Rect r = new Rect(margin, margin, width - margin, height - margin);

			int imageWidth = r.width() - (border * 2);
			int imageHeight = imageWidth * d.getIntrinsicHeight()
					/ d.getIntrinsicWidth();
			if (imageHeight > r.height() - (border * 2)) {
				imageHeight = r.height() - (border * 2);
				imageWidth = imageHeight * d.getIntrinsicWidth()
						/ d.getIntrinsicHeight();
			}

			r.left += ((r.width() - imageWidth) / 2) - border;
			r.right = r.left + imageWidth + border + border;
			r.top += ((r.height() - imageHeight) / 2) - border;
			r.bottom = r.top + imageHeight + border + border;

			Paint p = new Paint();
			p.setColor(0xFFC0C0C0);
			c.drawRect(r, p);
			r.left += border;
			r.right -= border;
			r.top += border;
			r.bottom -= border;

			d.setBounds(r);
			d.draw(c);
			return b;
		}

		@Override
		public int getBitmapCount() {
			return mBitmapIds.length;
		}
	}

	// **
	// CurlView size changed observer.

	private class SizeChangedObserver implements CurlView.SizeChangedObserver {

		@Override
		public void onSizeChanged(int w, int h) {
			mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
			mCurlView.setMargins(0, 0, 0, 0);
			mCurlView.setAllowLastPageCurl(false);
/*
			if (w > h) {
				mCurlView.setViewMode(CurlView.SHOW_TWO_PAGES);
				mCurlView.setMargins(.1f, .05f, .1f, .05f);
			} else {
				mCurlView.setViewMode(CurlView.SHOW_ONE_PAGE);
				mCurlView.setMargins(.1f, .1f, .1f, .1f);
			}*/

		}
	}

}
