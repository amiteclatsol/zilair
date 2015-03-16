package com.example.util;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.adapter.LeftMenuAdapterNew;
import com.example.zilair.HomeActivity;
import com.example.zilair.R;
import com.example.zilair.R.drawable;

public class ServerConstant {

	public static final String SERVER_URL = "http://10.0.2.2/gcm_server_php/register.php";

	// Google project id
	public static final String SENDER_ID = "779956390545";
	public static final String DISPLAY_MESSAGE_ACTION = "com.androidhive.pushnotifications.DISPLAY_MESSAGE";

	public static final String EXTRA_MESSAGE = "message";

	public static void displayMessage(Context context, String message) {
		Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
		intent.putExtra(EXTRA_MESSAGE, message);
		context.sendBroadcast(intent);
	}

	public static String[] values_string = new String[] {
			"Seychelles Int. Airport/Zil Air Base", "Aride",
			"Banyan Tree Resort", "Bird Island", "Cap Lazare",
			"Cert Island Resort", "Chateau De Feuilles", "Cousine Island",
			"Cousin Island", "Denis Island", "Ephelia Resort",
			"Felicite Island", "Fregate Island", "La Digue Island",
			"Lemuria Resort", "Maia Resort", "North Island Resort",
			"Kempinski Seychelles Resort", "Praslin Airport", "Raffles Resort",
			"Round Island", "Silhouette (Labriz)", "Silhouette (Grand Barbe)",
			"Ste Anne Resort" };

	/*
	 * public static final String[] home_menu_fragments = {
	 * "com.example.zilair.FragmentZilAirExperience",
	 * "com.example.zilair.FragmentBookAFlight",
	 * "com.example.zilair.FragmentEmptyLegs",
	 * "com.example.zilair.FragmentSettings" };
	 */

	/*
	 * public static int[] zilair_exp = new int[] { R.drawable.zilairexp_1,
	 * R.drawable.zilairexp_7, R.drawable.zilairexp_8, R.drawable.zilairexp_4,
	 * R.drawable.zilairexp_5, R.drawable.zilairexp_6, R.drawable.zilairexp_7,
	 * R.drawable.zilairexp_8, R.drawable.zilairexp_9, R.drawable.zilairexp_10,
	 * R.drawable.zilairexp_11, R.drawable.zilairexp_12,
	 * R.drawable.zilairexp_13, R.drawable.zilairexp_14,
	 * R.drawable.zilairexp_15, R.drawable.zilairexp_16,
	 * R.drawable.zilairexp_17, R.drawable.zilairexp_18,
	 * R.drawable.zilairexp_19, R.drawable.zilairexp_20,
	 * R.drawable.zilairexp_21, R.drawable.zilairexp_22,
	 * R.drawable.zilairexp_23, R.drawable.zilairexp_24,
	 * R.drawable.zilairexp_25, R.drawable.zilairexp_26,
	 * R.drawable.zilairexp_27, R.drawable.zilairexp_28
	 * 
	 * };
	 */

	public static LeftMenuAdapterNew setMenuAdapter(Context context,
			Resources resources) {

		ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
		/*
		 * Bitmap logo = BitmapFactory.decodeResource(resources,
		 * R.drawable.zilair_logo);
		 */

		Bitmap icon0 = BitmapFactory.decodeResource(resources,
				R.drawable.home_menu);
		Bitmap icon1 = BitmapFactory.decodeResource(resources,
				R.drawable.about_us_menu);
		Bitmap icon2 = BitmapFactory.decodeResource(resources,
				R.drawable.our_fleet_menu);
		Bitmap icon3 = BitmapFactory.decodeResource(resources,
				R.drawable.experience_menu);
		Bitmap icon4 = BitmapFactory.decodeResource(resources,
				R.drawable.booking_menu);
		Bitmap icon5 = BitmapFactory.decodeResource(resources,
				R.drawable.empty_legs_menu);
		// bitmap.add(logo);
		bitmap.add(icon0);
		bitmap.add(icon1);
		bitmap.add(icon2);
		bitmap.add(icon3);
		bitmap.add(icon4);
		bitmap.add(icon5);

		LeftMenuAdapterNew adapter = new LeftMenuAdapterNew(context, bitmap,
				false);

		return adapter;
	}

}
