package com.example.zilair;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FragmentAboutUs extends Fragment implements OnClickListener{
	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		ViewGroup root = (ViewGroup) inflater.inflate(R.layout.fragment_about_us,
				null);
		
		TextView contact = (TextView)root.findViewById(R.id.contact);
		TextView emergency_contact1 = (TextView)root.findViewById(R.id.emergency_contact1);
		TextView emergency_contact2 = (TextView)root.findViewById(R.id.emergency_contact2);
		TextView mail_reservations = (TextView)root.findViewById(R.id.mail_reservations);
		TextView mail_operations = (TextView)root.findViewById(R.id.mail_operations);
		TextView mail_corporate = (TextView)root.findViewById(R.id.mail_corporate);
		LinearLayout linear_map =(LinearLayout)root.findViewById(R.id.linear_map);
		contact.setOnClickListener(this);
		emergency_contact1.setOnClickListener(this);
		emergency_contact2.setOnClickListener(this);
		mail_reservations.setOnClickListener(this);
		mail_operations.setOnClickListener(this);
		mail_corporate.setOnClickListener(this);
		linear_map.setOnClickListener(this);
		return root;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.contact:
			makeACall("2484375100");
			break;
			
		case R.id.emergency_contact1:
			makeACall("2482528800");
			break;
			
		case R.id.emergency_contact2:
			makeACall("2482514940");
			break;
			
		case R.id.mail_reservations:
			sendEmail("book@zilair.com");
			break;
		case R.id.mail_operations:
			sendEmail("operations@zilair.com");
			break;
		case R.id.mail_corporate:
			sendEmail("corporate@zilair.com");
			break;
		case R.id.linear_map:
			showFleetImage(3);
			break;
		default:
			break;
		}
		
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

	private void makeACall(String phone) {
		// TODO Auto-generated method stub
		try {
			Intent callIntent = new Intent(Intent.ACTION_CALL);
			callIntent.setData(Uri.parse(phone));
			startActivity(callIntent);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendEmail(String email) {
		// TODO Auto-generated method stub
		try {
			Intent intent = new Intent(Intent.ACTION_SENDTO);
			intent.setData(Uri.parse("mailto:"));
			intent.putExtra(Intent.EXTRA_EMAIL  , new String[] { email });
			intent.putExtra(Intent.EXTRA_SUBJECT, "");

			startActivity(Intent.createChooser(intent, "Email via..."));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
