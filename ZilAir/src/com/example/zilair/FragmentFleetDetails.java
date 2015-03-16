package com.example.zilair;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class FragmentFleetDetails extends Fragment {

	int fleet_number;

	public FragmentFleetDetails() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Bundle bundle = getArguments();
		fleet_number = bundle.getInt("fleet_number");
		ViewGroup root = null;
		root = (ViewGroup) inflater.inflate(
				R.layout.fragment_fleet_details_bc120, null);
		ImageView fleet_details = (ImageView) root
				.findViewById(R.id.fleet_details);
		Bitmap icon = null;
		if (fleet_number == 0) {
			icon = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.zilairexp_3);
			fleet_details.setImageBitmap(icon);
		} else if (fleet_number == 1) {
			icon = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.zilairexp_5);
			fleet_details.setImageBitmap(icon);
		} else if (fleet_number == 2) {
			icon = BitmapFactory.decodeResource(this.getResources(),
					R.drawable.zilairexp_3);
			fleet_details.setImageBitmap(icon);
		}

		/*
		 * LinearLayout image_fullscreen =
		 * (LinearLayout)root.findViewById(R.id.image_fullscreen);
		 * image_fullscreen.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub Fragment newFragment = new FragmentImageFullScreen(); //
		 * consider using Java coding conventions (upper first char class
		 * names!!!)
		 * 
		 * Bundle args = new Bundle(); args.putInt("fleet_number",fleet_number);
		 * newFragment.setArguments(args); FragmentTransaction transaction =
		 * getFragmentManager().beginTransaction();
		 * 
		 * // Replace whatever is in the fragment_container view with this
		 * fragment, // and add the transaction to the back stack
		 * transaction.replace(R.id.content_frame, newFragment);
		 * transaction.addToBackStack(null);
		 * 
		 * // Commit the transaction transaction.commit(); } });
		 */
		return root;
	}
}
