package com.example.zilair;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.adapter.LeftMenuAdapterNew;
import com.example.util.ServerUtilities;
import com.example.util.WakeLocker;
import com.google.android.gcm.GCMRegistrar;

import static com.example.util.ServerConstant.DISPLAY_MESSAGE_ACTION;
import static com.example.util.ServerConstant.EXTRA_MESSAGE;
import static com.example.util.ServerConstant.SENDER_ID;

public class HomeActivity extends FragmentActivity {

	ListView list_main_menu;
	public static String name = "abc";
	public static String email = "abc@abc.com";

	String lblMessage;

	// Asyntask
	AsyncTask<Void, Void, Void> mRegisterTask;

	/*
	 * private static int[] images= {R.drawable.settings, R.drawable.aircraft,
	 * R.drawable.settings, R.drawable.settings}; private static String[]
	 * values= {"E-BROCHURE", "BOOK A FLIGHT", "EMPTY LEGS", "SETTINGS"};
	 */

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_home);
		init();
		setListMenuAdapterListener();

		registerGCM();
	}

	private void registerGCM() {
		// TODO Auto-generated method stub
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		lblMessage = "";

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		// Check if regid already presents
		if (regId.equals("")) {
			// Registration is not present, register now with GCM
			GCMRegistrar.register(this, SENDER_ID);
		} else {
			// Device is already registered on GCM
			if (GCMRegistrar.isRegisteredOnServer(this)) {
				// Skips registration.
				Toast.makeText(getApplicationContext(),
						"Already registered with GCM", Toast.LENGTH_LONG)
						.show();
			} else {
				// Try to register again, but not in the UI thread.
				// It's also necessary to cancel the thread onDestroy(),
				// hence the use of AsyncTask instead of a raw thread.
				final Context context = this;
				mRegisterTask = new AsyncTask<Void, Void, Void>() {

					@Override
					protected Void doInBackground(Void... params) {
						// Register on our server
						// On server creates a new user
						ServerUtilities.register(context, name, email, regId);
						return null;
					}

					@Override
					protected void onPostExecute(Void result) {
						mRegisterTask = null;
					}

				};
				mRegisterTask.execute(null, null, null);
			}
		}
	}

	/**
	 * Receiving push messages
	 * */
	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			WakeLocker.acquire(getApplicationContext());

			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			lblMessage.concat(newMessage + "\n");
			Toast.makeText(getApplicationContext(),
					"New Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			WakeLocker.release();
		}
	};

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	private void setListMenuAdapterListener() {
		// TODO Auto-generated method stub
		/* MainMenuAdapter adapter = new MainMenuAdapter(this, values, images); */

		ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();

		/*
		 * Bitmap icon0 = BitmapFactory.decodeResource(getResources(),
		 * R.drawable.zilair_logo);
		 */
		Bitmap icon1 = BitmapFactory.decodeResource(getResources(),
				R.drawable.about_us_menu);
		Bitmap icon2 = BitmapFactory.decodeResource(getResources(),
				R.drawable.our_fleet_menu);
		Bitmap icon3 = BitmapFactory.decodeResource(getResources(),
				R.drawable.experience_menu);
		Bitmap icon4 = BitmapFactory.decodeResource(getResources(),
				R.drawable.booking_menu);
		Bitmap icon5 = BitmapFactory.decodeResource(getResources(),
				R.drawable.empty_legs_menu);

		/* bitmap.add(icon0); */bitmap.add(icon1);
		bitmap.add(icon2);
		bitmap.add(icon3);
		bitmap.add(icon4);
		bitmap.add(icon5);

		Boolean ishome = true;

		LeftMenuAdapterNew adapter = new LeftMenuAdapterNew(HomeActivity.this,
				bitmap, ishome);
		list_main_menu.setAdapter(adapter);

		/*
		 * list_main_menu.setAdapter(ServerConstant.setMenuAdapter(
		 * NewHomeActivity.this, this.getResources()));
		 */

		list_main_menu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				finish();
				if (position == 0) {
					Intent intent = new Intent(HomeActivity.this,
							MenuActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("position", 1);
					startActivity(intent);
				} else if (position == 1) {
					Intent intent = new Intent(HomeActivity.this,
							MenuActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("position", 2);
					startActivity(intent);
				} else if (position == 2) {
					Intent intent = new Intent(HomeActivity.this,
							MenuActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("position", 3);
					startActivity(intent);
				} else if (position == 3) {
					Intent intent = new Intent(HomeActivity.this,
							MenuActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("position", 4);
					startActivity(intent);
				} else if (position == 4) {
					Intent intent = new Intent(HomeActivity.this,
							MenuActivity.class);
					intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
					intent.putExtra("position", 5);
					startActivity(intent);
				}
				/*
				 * android.support.v4.app.FragmentTransaction tx =
				 * getSupportFragmentManager() .beginTransaction();
				 * tx.replace(R.id.main,
				 * Fragment.instantiate(NewHomeActivity.this,
				 * ServerConstant.home_menu_fragments[position])); tx.commit();
				 */
			}
		});
	}

	private void init() {
		// TODO Auto-generated method stub
		list_main_menu = (ListView) findViewById(R.id.list_main_menu);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
