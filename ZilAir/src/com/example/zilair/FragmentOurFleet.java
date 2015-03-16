package com.example.zilair;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class FragmentOurFleet extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.fragment_our_fleet, null);
		Button button1 = (Button) root.findViewById(R.id.button1);
		button1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				callDetailsFragment(0);

			}
		});
		Button button2 = (Button) root.findViewById(R.id.button2);
		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				callDetailsFragment(1);

			}
		});

		ImageView image1 = (ImageView) root.findViewById(R.id.image1);
		image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFleetImage(0);
			}
		});

		ImageView image2 = (ImageView) root.findViewById(R.id.image2);
		image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFleetImage(1);
			}
		});

		return root;
	}

	protected void showFleetImage(int fleet_number) {
		// TODO Auto-generated method stub
		Fragment newFragment = new FragmentImageFullScreen(); //

		Bundle args = new Bundle();
		args.putInt("fleet_number", fleet_number);
		newFragment.setArguments(args);
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		// Replace whatever is in the fragment_container view with this
		// fragment, and add the transaction to the back stack
		transaction.replace(R.id.content_frame, newFragment);
		transaction.addToBackStack("FragmentImageFullScreen");

		// Commit the transaction
		transaction.commit();
	}

	protected void callDetailsFragment(int fleet_no) {
		// TODO Auto-generated method stub
		Fragment newFragment = new FragmentFleetDetails();
		// consider using Java coding conventions (upper first char class
		// names!!!)
		FragmentTransaction transaction = getFragmentManager()
				.beginTransaction();

		Bundle args = new Bundle();
		args.putInt("fleet_number", fleet_no);
		newFragment.setArguments(args);

		// Replace whatever is in the fragment_container view with this
		// fragment,
		// and add the transaction to the back stack
		transaction.replace(R.id.content_frame, newFragment);
		transaction.addToBackStack("FragmentFleetDetails");

		// Commit the transaction
		transaction.commit();
	}

}
