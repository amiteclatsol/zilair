package com.example.zilair;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.FragmentManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.zilair.dataclass.EmptyLegs;
import com.sun.mail.imap.protocol.FLAGS;

public class FragmentEmptyLegs extends Fragment {

	TextView select_filter_date;
	int pyear, pmonth, pday;
	int position;

	NotificationManager NM;

	LinearLayout empty_legs_row_parent;

	public static Fragment newInstance(Context context) {
		FragmentEmptyLegs f = new FragmentEmptyLegs();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.fragment_empty_legs, null);

		empty_legs_row_parent = (LinearLayout) root
				.findViewById(R.id.empty_legs_row_parent);
		empty_legs_row_parent.removeAllViews();

		final ArrayList<EmptyLegs> listEmptyLegs = new ArrayList<EmptyLegs>();
		EmptyLegs emptyLegs = new EmptyLegs("helicopter", "12/12/12", "11:12",
				"ZilBase");
		listEmptyLegs.add(emptyLegs);
		listEmptyLegs.add(emptyLegs);
		listEmptyLegs.add(emptyLegs);
		listEmptyLegs.add(emptyLegs);

		int hour = 8;
		int minutes = 30;
		Alarm alarm = new Alarm();
		alarm.setNotifiation(hour, minutes);

		for (int i = 0; i < listEmptyLegs.size(); i++) {
			position = i;
			View view_row = inflater.inflate(R.layout.inflate_empty_legs_row,
					empty_legs_row_parent, false);
			Button book_emptyfleet = (Button) view_row
					.findViewById(R.id.book_emptyfleet);

			book_emptyfleet.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Bundle bundle = new Bundle();
					((MenuActivity) getActivity()).setIsfromemptylegs(true);
					Fragment newFragment = new FragmentBookAFlight();
					// consider using Java coding conventions (upper first char
					// class names!!!)
					FragmentTransaction transaction = getFragmentManager()
							.beginTransaction();

					// Replace whatever is in the fragment_container view with
					// this fragment,
					// and add the transaction to the back stack
					transaction.replace(R.id.content_frame, newFragment);
					transaction.addToBackStack(null);
					bundle.putString("source", "Base");
					bundle.putString("destination", "Somewhere");
					bundle.putString("sourcedate", listEmptyLegs.get(position)
							.getDate());
					bundle.putString("sourcetime", listEmptyLegs.get(position)
							.getTime());
					bundle.putBoolean("isreturnflight", false);
					bundle.putString("aircraft", listEmptyLegs.get(position)
							.getAircraft_type());
					newFragment.setArguments(bundle);
					// Commit the transaction
					transaction.commit();
				}
			});
			empty_legs_row_parent.addView(view_row);
		}

		RelativeLayout relative_filter = (RelativeLayout) root
				.findViewById(R.id.relative_filter);

		ImageView image_filter = (ImageView) root
				.findViewById(R.id.image_filter);

		Button button_filter = (Button) root.findViewById(R.id.button_filter);

		relative_filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFilterDialog();
			}
		});

		image_filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFilterDialog();
			}
		});

		button_filter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showFilterDialog();
			}
		});

		// ((MenuActivity) getActivity()).newFlight();
		/* ((MenuActivity) getActivity()).rightMenuListener(); */
		/*
		 * ((MenuActivity) getActivity()).inflateBookingView(null, null, null,
		 * null, false);
		 */
		return root;
	}

	protected void showFilterDialog() {
		// TODO Auto-generated method stub
		AlertDialog ad = new AlertDialog.Builder(getActivity()).create();

		ad.setTitle("Filter Empty Legs");

		LayoutInflater layoutInflater = (LayoutInflater) getActivity()
				.getApplicationContext().getSystemService(
						Context.LAYOUT_INFLATER_SERVICE);
		View view = layoutInflater.inflate(R.layout.inflate_filter, null);

		Spinner spinner_filter = (Spinner) view
				.findViewById(R.id.spinner_filter);

		ImageView filter_date = (ImageView) view.findViewById(R.id.filter_date);
		select_filter_date = (TextView) view
				.findViewById(R.id.select_filter_date);

		filter_date.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showDatePicker();
			}
		});

		ad.setView(view);
		ad.show();
	}

	private void showDatePicker() {
		DatePickerFragment date = new DatePickerFragment();

		FragmentManager fm = getActivity().getFragmentManager();
		android.app.FragmentTransaction ft = fm.beginTransaction();

		/**
		 * Set Up Current Date Into dialog
		 */
		Calendar calender = Calendar.getInstance();
		Bundle args = new Bundle();
		args.putInt("year", calender.get(Calendar.YEAR));
		args.putInt("month", calender.get(Calendar.MONTH));
		args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
		date.setArguments(args);
		/**
		 * Set Call back to capture selected date
		 */
		date.setCallBack(ondate);
		date.show(ft, "Date Picker");
	}

	OnDateSetListener ondate = new OnDateSetListener() {
		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {

			pyear = year;
			pmonth = monthOfYear + 1;
			pday = dayOfMonth;

			select_filter_date.setText("" + pday + "/" + pmonth + "/" + pyear);
		}
	};

	public class Alarm {
		Timer _timer;

		public Alarm() {

		}

		public void setNotifiation(int hour, int minutes) {
			// Create a Date corresponding to 10:30:00 AM today.
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.HOUR_OF_DAY, hour);
			calendar.set(Calendar.MINUTE, minutes);
			calendar.set(Calendar.SECOND, 0);

			Date alarmTime = calendar.getTime();

			_timer = new Timer();
			_timer.schedule(new AlarmTask(), alarmTime);

		}

		class AlarmTask extends TimerTask {
			/**
			 * Called on a background thread by Timer
			 */
			public void run() {
				// Do your work here; it's 10:30 AM!
				notifyFlight();

				// If you don't want the alarm to go off again
				// tomorrow (etc), cancel the timer
				_timer.cancel();
			}
		}
	}

	@SuppressWarnings("deprecation")
	public void notifyFlight() {
		Intent intent = new Intent(getActivity(), MenuActivity.class);
		
		PendingIntent pIntent = PendingIntent.getActivity(getActivity()
				.getApplicationContext(), 0, intent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		Notification noti = new Notification.Builder(getActivity())
				.setContentTitle("New mail from " + "test@gmail.com")
				.setContentText("Subject").setSmallIcon(R.drawable.ic_launcher)
				.setContentIntent(pIntent)
				.addAction(R.drawable.ic_launcher, "Book", pIntent).build();
		NotificationManager notificationManager = (NotificationManager) getActivity()
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// hide the notification after its selected
		noti.flags |= Notification.FLAG_AUTO_CANCEL;

		notificationManager.notify(0, noti);

		/*
		 * NM = (NotificationManager) getActivity().getSystemService(
		 * Context.NOTIFICATION_SERVICE); Notification notify = new
		 * Notification( android.R.drawable.stat_notify_more, "title",
		 * System.currentTimeMillis()); PendingIntent pending =
		 * PendingIntent.getActivity(getActivity() .getApplicationContext(), 0,
		 * new Intent(), 0);
		 * notify.setLatestEventInfo(getActivity().getApplicationContext(),
		 * "Subject", "This is body", pending);
		 * 
		 * NM.notify(0, notify);
		 */
	}

}
