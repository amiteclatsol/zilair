package com.example.zilair;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.json.JSONArray;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proxy.ProxyInterface;
import com.example.util.ServerConstant;
import com.example.util.Utils;
import com.example.util.Validations;
import com.example.zilair.dataclass.BookAFlight;
import com.example.zilair.dataclass.EmptyLegsNotification;
import com.example.zilair.dataclass.Passengers;

public class MenuActivity extends FragmentActivity implements ProxyInterface {

	private Button menu_left, menu_right;
	// title_image;
	private DrawerLayout mDrawerLayout;
	private LinearLayout mDrawerList_left;
	private ListView mDrawerListChild;
	private ActionBarDrawerToggle mDrawerToggle;
	private static boolean menuright_visible = false;
	private LinearLayout menu_right_list;
	String set_destination = "none";
	boolean isreturnflight = false;
	Intent intent;
	int position = 0;
	TextView title_text;
	boolean isfromemptylegs = false;

	/*
	 * public static final String[] itemname = { "", "", "", "", "" }; public
	 * static final Integer[] imgid = { R.drawable.zilair_logo, R.drawable.sp3,
	 * R.drawable.sp4, R.drawable.sp1, R.drawable.sp2 };
	 */

	// private static final String home_fragment =
	// "com.example.zilair.FragmentHome";
	private static final String[] fragments = { "",
			"com.example.zilair.FragmentAboutUs",
			"com.example.zilair.FragmentOurFleet",
			"com.example.zilair.FragmentZilAirExperience",
			"com.example.zilair.FragmentBookAFlight",
			"com.example.zilair.FragmentEmptyLegs" };

	static final int DATE_DIALOG_ID = 999;
	static final int DATE_RETURN_DIALOG_ID = 9999;
	/*
	 * int pmonth, pday, pyear; int pmonth_return, pday_return, pyear_return;
	 * boolean issource_date;
	 */
	TextView airplane_date_return, airplane_date;

	ArrayList<String> source, destination;

	Utils utils;
	Validations validations;
	RelativeLayout linear_updatesourcedate, linear_updatedestinationdate;
	int aircraft = -1;
	BookAFlight bookAFlight;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		init();

		intent = getIntent();
		try {
			if (intent != null) {
				position = intent.getExtras().getInt("position");
				selectItem(position);
			}
		} catch (NullPointerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * ArrayList<Bitmap> bitmap = new ArrayList<Bitmap>();
		 * 
		 * Bitmap icon0 = BitmapFactory.decodeResource(this.getResources(),
		 * R.drawable.zilair_logo); Bitmap icon1 =
		 * BitmapFactory.decodeResource(this.getResources(), R.drawable.sp1);
		 * Bitmap icon2 = BitmapFactory.decodeResource(this.getResources(),
		 * R.drawable.sp1); Bitmap icon3 =
		 * BitmapFactory.decodeResource(this.getResources(), R.drawable.sp1);
		 * Bitmap icon4 = BitmapFactory.decodeResource(this.getResources(),
		 * R.drawable.sp2); Bitmap icon5 =
		 * BitmapFactory.decodeResource(this.getResources(), R.drawable.sp2);
		 * bitmap.add(icon0); bitmap.add(icon1); bitmap.add(icon2);
		 * bitmap.add(icon3); bitmap.add(icon5);
		 * 
		 * LeftMenuAdapterNew adapter = new
		 * LeftMenuAdapterNew(MenuActivity.this, bitmap);
		 */
		/*
		 * LeftMenuAdapter adapter = new LeftMenuAdapter(MenuActivity.this,
		 * itemname, imgid);
		 */
		/*
		 * mDrawerList_left.setAdapter(ServerConstant.setMenuAdapter(
		 * MenuActivity.this, this.getResources()));
		 * mDrawerList_left.setOnItemClickListener(new
		 * DrawerItemClickListener());
		 */

		mDrawerListChild.setAdapter(ServerConstant.setMenuAdapter(
				MenuActivity.this, this.getResources()));
		mDrawerListChild.setOnItemClickListener(new DrawerItemClickListener());

		// startHomeFragment();

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.menu, R.string.title_activity_first,
				R.string.title_activity_first) {
			public void onDrawerSlide(View drawerView, float slideOffset) {
				getLeftDrawerState();
			}
		};

		mDrawerLayout.setDrawerListener(mDrawerToggle);

		menu_left.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getLeftDrawerState();
			}
		});

		menu_right.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				rightMenuListener();
			}
		});

		/*
		 * title_image.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub goHome(); } });
		 */

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		newFlight();
	}

	public void rightMenuListener() {

		if (menuright_visible) {
			closeRightMenu();

		} else {
			openRightMenu();
		}

	}

	public void closeRightMenu() {
		// TODO Auto-generated method stub
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.slide_in_right);
		animation.setDuration(500);
		menu_right_list.setAnimation(animation);
		menu_right_list.animate();
		animation.start();
		menu_right_list.setVisibility(View.GONE);
		menuright_visible = false;
		menu_right.setBackgroundResource(R.drawable.settings);
		isreturnflight = false;
	}

	protected void openRightMenu() {
		// TODO Auto-generated method stub
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			closeLeftDrawer();
		}
		Animation animation = AnimationUtils.loadAnimation(this,
				R.anim.slide_out_left);
		animation.setDuration(500);
		menu_right_list.setAnimation(animation);
		menu_right_list.animate();
		animation.start();

		menu_right_list.setVisibility(View.VISIBLE);
		menuright_visible = true;
		menu_right.setBackgroundResource(R.drawable.close);
	}

	private void goHome() {
		// TODO Auto-generated method stub
		finish();
		Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
		startActivity(intent);
		/*
		 * android.support.v4.app.FragmentTransaction tx =
		 * getSupportFragmentManager() .beginTransaction();
		 * tx.replace(R.id.content_frame,
		 * Fragment.instantiate(MenuActivity.this, home_fragment)); tx.commit();
		 */

	}

	private void init() {
		// TODO Auto-generated method stub
		source = new ArrayList<String>();
		destination = new ArrayList<String>();

		for (int i = 0; i < ServerConstant.values_string.length; i++) {
			source.add(ServerConstant.values_string[i]);
		}
		menu_left = (Button) findViewById(R.id.menu_left);
		menu_right = (Button) findViewById(R.id.menu_right);
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList_left = (LinearLayout) findViewById(R.id.left_drawer);
		mDrawerListChild = (ListView) findViewById(R.id.left_drawer_child);
		title_text = (TextView) findViewById(R.id.title_text);
		// title_image = (Button) findViewById(R.id.title_image);

		utils = new Utils();
		validations = new Validations();

		newFlight();
		menu_right_list = (LinearLayout) findViewById(R.id.menu_right_list);

	}

	public void newFlight() {
		// TODO Auto-generated method stub
		bookAFlight = null;
		bookAFlight = new BookAFlight();

	}

	public BookAFlight getBookAFlightObject() {
		return bookAFlight;
	}

	private void getLeftDrawerState() {
		// TODO Auto-generated method stub
		if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
			// drawer is open
			closeLeftDrawer();
		} else {

			if (menuright_visible) {
				closeRightMenu();
			}
			openLeftDrawer();
		}

	}

	private void openLeftDrawer() {
		// TODO Auto-generated method stub
		mDrawerLayout.openDrawer(Gravity.LEFT);
		menu_left.setBackgroundResource(R.drawable.close);
	}

	private void closeLeftDrawer() {
		// TODO Auto-generated method stub
		mDrawerLayout.closeDrawer(mDrawerList_left);
		menu_left.setBackgroundResource(R.drawable.menu);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			selectItem(position);
		}
	}

	private void selectItem(int position) {
		// update the main content by replacing fragments
		closeLeftDrawer();
		menu_right_list.removeAllViews();
		FragmentTransaction tx = getSupportFragmentManager().beginTransaction();
		tx.setCustomAnimations(0, 0);

		// tx.addToBackStack(null);
		switch (position) {
		case 0:
			// finish();
			goHome();
			break;
		case 1:
			// aboutus
			title_text.setText("ABOUT US");
			/*
			 * getSupportFragmentManager().popBackStack(null,
			 * FragmentManager.POP_BACK_STACK_INCLUSIVE);
			 */
			tx.addToBackStack(fragments[position]);
			tx.replace(R.id.content_frame, Fragment.instantiate(
					MenuActivity.this, fragments[position]));

			break;
		case 2:
			// our fleet
			title_text.setText("OUR FLEET");
			tx.addToBackStack(fragments[position]);
			tx.replace(R.id.content_frame, Fragment.instantiate(
					MenuActivity.this, fragments[position]));

			break;
		case 3:
			// zil air exp
			title_text.setText("ZIL AIR EXPERIENCE");
			getSupportFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			tx.replace(R.id.content_frame, Fragment.instantiate(
					MenuActivity.this, fragments[position]));
			inflateEmptyLegsView();
			break;
		case 4:
			// book a flight
			title_text.setText("BOOK A FLIGHT");
			setIsfromemptylegs(false);
			inflateSettingView();
			getSupportFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			tx.replace(R.id.content_frame, Fragment.instantiate(
					MenuActivity.this, fragments[position]));

			break;
		case 5:
			// empty legs
			title_text.setText("EMPTY LEGS");
			inflateEmptyLegsView();
			getSupportFragmentManager().popBackStack(null,
					FragmentManager.POP_BACK_STACK_INCLUSIVE);
			tx.replace(R.id.content_frame, Fragment.instantiate(
					MenuActivity.this, fragments[position]));

			break;

		default:
			break;
		}

		tx.commit();

	}

	private void inflateEmptyLegsView() {
		// TODO Auto-generated method stub
		EmptyLegsNotification emptyLegs = new EmptyLegsNotification();
		View view = getLayoutInflater().inflate(R.layout.inflate_emptylegs,
				menu_right_list, false);
		final String[] names = { "Aride", "Bird Island", "Cap Lazare",
				"Cert Island Resort", "Chataeu De Feullis", "Cousine Island",
				"Cousin Island", "Denis Land", "Aride", "Bird Island",
				"Cap Lazare", "Cert Island Resort", "Chataeu De Feullis",
				"Cousine Island", "Cousin Island", "Denis Land" };
		final ArrayList<String> names_array = new ArrayList<String>();
		LinearLayout emptylegs_row = (LinearLayout) view
				.findViewById(R.id.emptylegs_row);
		emptylegs_row.removeAllViews();
		View view_row = null;
		for (int i = 0; i < names.length; i++) {
			final int temp = i;
			view_row = getLayoutInflater().inflate(R.layout.emptylegs_row,
					emptylegs_row, false);
			TextView emptylegs_notification_name = (TextView) view_row
					.findViewById(R.id.emptylegs_notification_name);
			emptylegs_notification_name.setText(names[temp]);
			final CheckBox emptylegs_notification_check = (CheckBox) view_row
					.findViewById(R.id.emptylegs_notification_check);

			emptylegs_notification_check
					.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							if (emptylegs_notification_check.isChecked()) {
								names_array.add(names[temp]);
							}
						}
					});
			emptylegs_row.addView(view_row);
		}

		emptyLegs.setNotification(names_array);
		Toast.makeText(getApplicationContext(),
				"" + emptyLegs.getNotification(), 1000).show();
		menu_right_list.addView(view);
	}

	protected void callBookAFlightAPi(BookAFlight bookAFlight) {
		// TODO Auto-generated method stub

		/*
		 * if (validations.isBookAFlightApiValid(bookAFlight)) {
		 * Toast.makeText(getApplicationContext(), "Api Valid",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * } else { Toast.makeText(getApplicationContext(), "Api Invalid",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * }
		 */
		/*
		 * utils.printFlightDetails(flight);
		 * utils.printUserDetails(flight.getUserDetails());
		 * utils.printPassengerDetails(flight.getPassengers());
		 */

		// new PostNGetResponce(this, (ProxyInterface)this, "",
		// flight).execute();
		// newFlight();

		/*ArrayList<NameValuePair> postParameters;
		JSONObject jsonObject = new JSONObject();*/
		
		String jsonObject="";

		jsonObject = jsonObject.concat("First Name: "+ bookAFlight.getUserDetails()
				.getFirstname() +"\n");
		
		
		
		jsonObject = jsonObject.concat("Last Name: "+ bookAFlight.getUserDetails()
				.getLastname()+"\n");
		jsonObject = jsonObject.concat("Email: "+ bookAFlight.getUserDetails().getEmail()+"\n");
		jsonObject = jsonObject
				.concat("Address: "+ bookAFlight.getUserDetails().getAddress()+"\n");
		jsonObject = jsonObject
				.concat("Country: "+ bookAFlight.getUserDetails().getCountry()+"\n");
		jsonObject = jsonObject.concat("Telephone: "+ bookAFlight.getUserDetails()
				.getTelephone()+"\n");

		jsonObject = jsonObject.concat("Aircraft Type: "+ bookAFlight.getAircraft()+"\n");

		boolean isreturn = bookAFlight.isIsreturnflight();
		
		if(isreturn)
		{
			jsonObject = jsonObject.concat("Trip: "+ "Return"+"\n");
		}
		else
		{
			jsonObject = jsonObject.concat("Trip: "+ "One Way"+"\n");
		}

		

		jsonObject = jsonObject.concat("From: "+ bookAFlight.getSource()+"\n");
		jsonObject = jsonObject.concat("Departure Date: "+ bookAFlight.getSource_date()+"\n");
		jsonObject = jsonObject.concat("Departure Time : "+ bookAFlight.getSource_time()+"\n");
		jsonObject = jsonObject.concat("To: "+ bookAFlight.getDestination()+"\n");
		if (isreturn) {
			jsonObject = jsonObject.concat("Return Date: "+
					bookAFlight.getDestination_date()+"\n");
			jsonObject = jsonObject.concat("Return Time: "+
					bookAFlight.getDestination_time()+"\n");
		}

		jsonObject = jsonObject.concat("Adults: "+
				bookAFlight.getPassenger_adults()+"\n");
		jsonObject = jsonObject.concat("Children: "+
				bookAFlight.getPassenger_children()+"\n");
		jsonObject = jsonObject.concat("Infants: "+
				bookAFlight.getPassenger_infants()+"\n");

		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < bookAFlight.getPassengers().size(); i++) {

			Passengers passengers = bookAFlight.getPassengers().get(i);
			String jsonObject2 = "";
			jsonObject2 = jsonObject2.concat((i+1)+". Name: "+ passengers.getFirst_name());
			jsonObject2 = jsonObject2
					.concat(" Last Name: "+ passengers.getLastname());
			jsonObject2 = jsonObject2.concat(" Weight: "+ passengers.getWeight()+"\n");
			jsonObject =  jsonObject.concat(jsonObject2);

		}

		/*postParameters = new ArrayList<NameValuePair>();
		postParameters
				.add(new BasicNameValuePair("data", jsonObject.toString()));*/

		//String send_request = postParameters.toString();

		Log.i("MenuActivity", "send_request: " + jsonObject);
		
		sendMail("androidpunedemo@gmail.com", "booaflight details", jsonObject);
		
		/*Intent mEmail = new Intent(Intent.ACTION_SEND);
		mEmail.putExtra(Intent.EXTRA_EMAIL, new String[]{ "nsugandhi190@gmail.com"});
		
		mEmail.putExtra(Intent.EXTRA_SUBJECT, "request");
		mEmail.putExtra(Intent.EXTRA_TEXT, "message: \n"+jsonObject);
		// prompts to choose email client
		mEmail.setType("message/rfc822");
		startActivity(Intent.createChooser(mEmail, "Choose an email client to send your"));*/
	}
	
	private void sendMail(String email, String subject, String messageBody) {
	    Session session = createSessionObject();
	 
	    try {
	        Message message = createMessage(email, subject, messageBody, session);
	        new SendMailTask().execute(message);
	    } catch (AddressException e) {
	        e.printStackTrace();
	    } catch (MessagingException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    }
	}
	
	private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
	    Message message = new MimeMessage(session);
	    message.setFrom(new InternetAddress("amit@eclatsol.com", "Book Flight"));
	    message.addRecipient(MimeMessage.RecipientType.TO, new InternetAddress(email, email));
	    message.setSubject(subject);
	    message.setText(messageBody);
	    return message;
	}
	
	private Session createSessionObject() {
	    Properties properties = new Properties();
	    properties.put("mail.smtp.auth", "true");
	    properties.put("mail.smtp.starttls.enable", "true");
	    properties.put("mail.smtp.host", "smtp.gmail.com");
	    properties.put("mail.smtp.port", "587");
	 
	    return Session.getInstance(properties, new javax.mail.Authenticator() {
	        protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication("amit@eclatsol.com", "eclatsolpune");
	        }
	    });
	}
	
	private class SendMailTask extends AsyncTask<Message, Void, Void> {
	    private ProgressDialog progressDialog;
	 
	    @Override
	    protected void onPreExecute() {
	        super.onPreExecute();
	        progressDialog = ProgressDialog.show(MenuActivity.this, "Please wait", "Sending mail", true, false);
	    }
	 
	    @Override
	    protected void onPostExecute(Void aVoid) {
	        super.onPostExecute(aVoid);
	        progressDialog.dismiss();
	    }
	 
	    @Override
	    protected Void doInBackground(Message... messages) {
	        try {
	            Transport.send(messages[0]);
	        } catch (MessagingException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	}

	private void inflateSettingView() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
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

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
			Log.i("", "@@@@@@ back stack entry count : "
					+ getSupportFragmentManager().getBackStackEntryCount());
			getSupportFragmentManager().popBackStack();
		} else {

			finish();
			Intent intent = new Intent(MenuActivity.this, HomeActivity.class);
			intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
			startActivity(intent);
			super.onBackPressed();
		}

	}

	@Override
	public void responseFromService(String response, String url) {
		// TODO Auto-generated method stub

	}
	
	public boolean isIsfromemptylegs() {
		return isfromemptylegs;
	}

	public void setIsfromemptylegs(boolean isfromemptylegs) {
		this.isfromemptylegs = isfromemptylegs;
	}

	/*
	 * public void inflateBookingView(RelativeLayout relative_destination,
	 * RelativeLayout relative_dateandtime, RelativeLayout relative_passengers,
	 * RelativeLayout relative_aircraft, boolean isbookingview) { // TODO
	 * Auto-generated method stub
	 * 
	 * boolean issource = true; selectDestination(relative_destination,
	 * relative_dateandtime, relative_passengers, relative_aircraft, issource,
	 * isbookingview);
	 * 
	 * }
	 */

	/*
	 * private void selectDestination(final RelativeLayout relative_destination,
	 * final RelativeLayout relative_dateandtime, final RelativeLayout
	 * relative_passengers, final RelativeLayout relative_aircraft, final
	 * boolean issource, final boolean isbookingview) { // TODO Auto-generated
	 * method stub
	 * 
	 * if (isbookingview) { relative_destination
	 * .setBackgroundResource(R.drawable.light_rounded_square); }
	 * menu_right_list.removeAllViews();
	 * 
	 * View view = getLayoutInflater().inflate(R.layout.inflate_bookaflight,
	 * menu_right_list, false); final CheckBox checkBox_returnFlight =
	 * (CheckBox) view .findViewById(R.id.checkBox_returnFlight); Button
	 * button_destination_next = (Button) view
	 * .findViewById(R.id.button_destination_next);
	 * 
	 * TextView textview_returnFlight = (TextView) view
	 * .findViewById(R.id.textview_returnFlight);
	 * 
	 * ListView booking_listview = (ListView) view
	 * .findViewById(R.id.booking_listview);
	 * 
	 * TextView bookaflight_title = (TextView) view
	 * .findViewById(R.id.bookaflight_title);
	 * 
	 * EfficientAdapter adapter = null; if (issource) { adapter = new
	 * EfficientAdapter(this, R.layout.flight_row, source); } else {
	 * bookaflight_title.setText("Choose Destination"); adapter = new
	 * EfficientAdapter(this, R.layout.flight_row, destination); }
	 * 
	 * 
	 * final ArrayAdapter<String> adapter;
	 * 
	 * adapter = new ArrayAdapter<String>(this,
	 * android.R.layout.simple_list_item_1, android.R.id.text1, values_string);
	 * 
	 * 
	 * booking_listview.setAdapter(adapter);
	 * 
	 * booking_listview.setOnItemClickListener(new OnItemClickListener() {
	 * 
	 * @Override public void onItemClick(AdapterView<?> parent, View view, int
	 * position, long id) { // TODO Auto-generated method stub
	 * 
	 * // set_destination = values_string[position]; if (issource) {
	 * set_destination = source.get(position); destination =
	 * utils.getDestinations(source, position); } else { set_destination =
	 * destination.get(position); }
	 * 
	 * } });
	 * 
	 * if (isbookingview) { if (issource) {
	 * 
	 * checkBox_returnFlight.setVisibility(View.GONE);
	 * textview_returnFlight.setVisibility(View.GONE);
	 * 
	 * } else { isreturnflight = false;
	 * checkBox_returnFlight.setOnClickListener(new OnClickListener() { //
	 * dkjabdkjaq
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub if (checkBox_returnFlight.isChecked()) { isreturnflight = true;
	 * 
	 * } else { isreturnflight = false; } } }); } } else {
	 * checkBox_returnFlight.setVisibility(View.GONE);
	 * textview_returnFlight.setVisibility(View.GONE); isreturnflight = false; }
	 * 
	 * button_destination_next.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub
	 * 
	 * if (issource) {
	 * 
	 * if (validations.isValidString(set_destination)) {
	 * bookAFlight.setSource(set_destination);
	 * 
	 * Toast.makeText(getApplicationContext(), "" + bookAFlight.getSource(),
	 * 1000).show();
	 * 
	 * set_destination = "none"; if (isbookingview) {
	 * selectDestination(relative_destination, relative_dateandtime,
	 * relative_passengers, relative_aircraft, false, isbookingview); } else {
	 * selectDestination(null, null, null, null, false, isbookingview); }
	 * 
	 * } else { validations.displayValidationMessage(MenuActivity.this,
	 * R.string.select_departure);
	 * 
	 * Toast.makeText(getApplicationContext(), "Select Departure",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * }
	 * 
	 * } else { if (validations.isValidString(set_destination)) {
	 * bookAFlight.setDestination(set_destination);
	 * bookAFlight.setIsreturnflight(isreturnflight);
	 * 
	 * Toast.makeText( getApplicationContext(), "" +
	 * bookAFlight.getDestination() + " " + bookAFlight.isIsreturnflight(),
	 * 1000) .show();
	 * 
	 * set_destination = "none"; if (isbookingview) {
	 * 
	 * updateDestinationView(relative_destination);
	 * 
	 * selectDate(relative_dateandtime, relative_passengers, relative_aircraft,
	 * isbookingview);
	 * 
	 * } else {
	 * 
	 * // selectDate(null, null, null, isbookingview);
	 * 
	 * }
	 * 
	 * } else { validations.displayValidationMessage(MenuActivity.this,
	 * R.string.select_destination);
	 * 
	 * Toast.makeText(getApplicationContext(), "Select Destination",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * } }
	 * 
	 * } });
	 * 
	 * menu_right_list.addView(view); }
	 */

	/*
	 * protected void updateDestinationView(RelativeLayout relative_destination)
	 * { // TODO Auto-generated method stub
	 * relative_destination.removeAllViews(); View view = getLayoutInflater()
	 * .inflate(R.layout.book_a_flight_destination, relative_destination,
	 * false);
	 * 
	 * LinearLayout linear_source_return = (LinearLayout) view
	 * .findViewById(R.id.linear_source_return); if (isreturnflight) {
	 * linear_source_return.setVisibility(View.VISIBLE); setParameters(view,
	 * false); } else { setParametersNoReturnFlight(view, linear_source_return,
	 * false);
	 * 
	 * } relative_destination.addView(view); }
	 */
	/*
	 * private void setParametersNoReturnFlight(View view, LinearLayout
	 * linear_source_return, boolean isdate) { // TODO Auto-generated method
	 * stub LinearLayout linear_source = (LinearLayout) view
	 * .findViewById(R.id.linear_source);
	 * linear_source.setVisibility(View.GONE);
	 * linear_source_return.setVisibility(View.GONE);
	 * 
	 * RelativeLayout relative_source = (RelativeLayout) view
	 * .findViewById(R.id.relative_source); RelativeLayout
	 * relative_source_return = (RelativeLayout) view
	 * .findViewById(R.id.relative_source_return);
	 * relative_source.setVisibility(View.VISIBLE);
	 * relative_source_return.setVisibility(View.VISIBLE);
	 * 
	 * TextView textview_source1 = (TextView) view
	 * .findViewById(R.id.textview_source1); TextView textview_return_source1 =
	 * (TextView) view .findViewById(R.id.textview_return_source1); if (isdate)
	 * {
	 * 
	 * textview_source1.setText(bookAFlight.getSource_dateandtime());
	 * textview_return_source1 .setText(bookAFlight.getSource_dateandtime());
	 * 
	 * } else { textview_source1.setText(bookAFlight.getSource());
	 * textview_return_source1.setText(bookAFlight.getDestination()); }
	 * 
	 * }
	 */

	/*
	 * protected void updateDateView(RelativeLayout relative_dateandtime) { //
	 * TODO Auto-generated method stub relative_dateandtime.removeAllViews();
	 * 
	 * View view = getLayoutInflater().inflate(R.layout.select_date_view,
	 * relative_dateandtime, false); TextView textview_source1 = (TextView) view
	 * .findViewById(R.id.textview_source1); TextView textview_return_source1 =
	 * (TextView) view .findViewById(R.id.textview_return_source1);
	 * 
	 * textview_source1.setText(bookAFlight.getSource_dateandtime()); if
	 * (isreturnflight) { textview_return_source1.setText(bookAFlight
	 * .getDestination_dateandtime());
	 * 
	 * } else { // textview_return_source1.setVisibility(View.GONE);
	 * textview_return_source1.setText("No Return Flight"); }
	 * 
	 * 
	 * 
	 * View view = getLayoutInflater()
	 * .inflate(R.layout.book_a_flight_destination, relative_dateandtime,
	 * false); LinearLayout linear_source_return = (LinearLayout) view
	 * .findViewById(R.id.linear_source_return); if (isreturnflight) {
	 * linear_source_return.setVisibility(View.VISIBLE); setParameters(view,
	 * true);
	 * 
	 * } else { setParametersNoReturnFlight(view, linear_source_return, true);
	 * 
	 * }
	 * 
	 * 
	 * relative_dateandtime.addView(view); }
	 */

	/*
	 * protected void selectDate(final RelativeLayout relative_dateandtime,
	 * final RelativeLayout relative_passengers, final RelativeLayout
	 * relative_aircraft, final boolean isbookingview) { // TODO Auto-generated
	 * method stub if (isbookingview) { relative_dateandtime
	 * .setBackgroundResource(R.drawable.light_rounded_square); ; }
	 * menu_right_list.removeAllViews();
	 * 
	 * View view1 = getLayoutInflater().inflate(R.layout.inflate_selectdate,
	 * menu_right_list, false); LinearLayout linear_date_source = (LinearLayout)
	 * view1 .findViewById(R.id.linear_date_source); LinearLayout
	 * linear_date_destination = (LinearLayout) view1
	 * .findViewById(R.id.linear_date_destination); final Button
	 * button_destination_next = (Button) view1
	 * .findViewById(R.id.button_destination_next); airplane_date_return =
	 * (TextView) view1 .findViewById(R.id.airplane_date_return); airplane_date
	 * = (TextView) view1.findViewById(R.id.airplane_date);
	 * 
	 * linear_updatedestinationdate = (RelativeLayout) view1
	 * .findViewById(R.id.linear_updatedestinationdate); linear_updatesourcedate
	 * = (RelativeLayout) view1 .findViewById(R.id.linear_updatesourcedate);
	 * setParameters(view1, false);
	 * 
	 * //setCurrentDateOnView();
	 * 
	 * linear_date_source.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub showDialog(DATE_DIALOG_ID); //issource_date = true; if
	 * (!isreturnflight) { button_destination_next.setVisibility(View.VISIBLE);
	 * } } });
	 * 
	 * if (!isreturnflight) { linear_date_destination.setVisibility(View.GONE);
	 * } else { linear_date_destination.setVisibility(View.VISIBLE);
	 * linear_date_destination.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub showDialog(DATE_RETURN_DIALOG_ID); //issource_date = false;
	 * button_destination_next.setVisibility(View.VISIBLE); } }); }
	 * 
	 * button_destination_next.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub if (isbookingview) {
	 * 
	 * boolean isvalidreturndate = validations.isReturnDateValid( pyear, pmonth,
	 * pday, pyear_return, pmonth_return, pday_return); if (!issource_date &&
	 * !isvalidreturndate) {
	 * validations.displayValidationMessage(MenuActivity.this,
	 * R.string.enter_valid_return_date);
	 * 
	 * Toast.makeText(getApplicationContext(), "Enter Valid Return Date",
	 * Toast.LENGTH_LONG) .show();
	 * 
	 * } else {
	 * 
	 * Toast.makeText( getApplicationContext(), "" +
	 * bookAFlight.getSource_dateandtime() + " " + bookAFlight
	 * .getDestination_dateandtime(), 1000).show();
	 * 
	 * updateDateView(relative_dateandtime);
	 * selectPassengers(relative_passengers, relative_aircraft, isbookingview);
	 * }
	 * 
	 * } else { selectPassengers(null, null, isbookingview); }
	 * 
	 * } });
	 * 
	 * menu_right_list.addView(view1);
	 * 
	 * }
	 */

	/*
	 * protected void selectPassengers(final RelativeLayout relative_passengers,
	 * final RelativeLayout relative_aircraft, final boolean isbookingview) { //
	 * TODO Auto-generated method stub if (isbookingview) { relative_passengers
	 * .setBackgroundResource(R.drawable.light_rounded_square); }
	 * menu_right_list.removeAllViews();
	 * 
	 * View view = getLayoutInflater().inflate(R.layout.inflate_passengers,
	 * menu_right_list, false); final EditText adults = (EditText)
	 * view.findViewById(R.id.adults); final EditText children = (EditText)
	 * view.findViewById(R.id.children); final EditText infants = (EditText)
	 * view.findViewById(R.id.infants);
	 * 
	 * final Button button_destination_next = (Button) view
	 * .findViewById(R.id.button_destination_next);
	 * 
	 * button_destination_next.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub int get_adults, get_children, get_infants; if
	 * (adults.getText().toString().equals("")) { get_adults = 0; } else {
	 * get_adults = Integer.parseInt(adults.getText().toString()); }
	 * 
	 * if (children.getText().toString().equals("")) { get_children = 0; } else
	 * { get_children = Integer.parseInt(children.getText() .toString()); }
	 * 
	 * if (infants.getText().toString().equals("")) { get_infants = 0; } else {
	 * get_infants = Integer .parseInt(infants.getText().toString()); }
	 * 
	 * bookAFlight.setPassenger_adults(get_adults);
	 * bookAFlight.setPassenger_children(get_children);
	 * bookAFlight.setPassenger_infants(get_infants);
	 * 
	 * if (validations.isValidPassengers(bookAFlight)) { if (isbookingview) {
	 * updatePassengerView(relative_passengers);
	 * selectAircraft(relative_aircraft, isbookingview); } else {
	 * selectAircraft(null, isbookingview); }
	 * 
	 * 
	 * Toast.makeText( getApplicationContext(), "" +
	 * bookAFlight.getPassenger_adults() + " " +
	 * bookAFlight.getPassenger_children() + "" +
	 * bookAFlight.getPassenger_infants(), 1000) .show();
	 * 
	 * } else { validations.displayValidationMessage(MenuActivity.this,
	 * R.string.select_passengers);
	 * 
	 * Toast.makeText(getApplicationContext(), "Select Passengers",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * }
	 * 
	 * } });
	 * 
	 * menu_right_list.addView(view); }
	 */

	/*
	 * protected void selectAircraft(final RelativeLayout relative_aircraft,
	 * final boolean isbookingview) { // TODO Auto-generated method stub if
	 * (isbookingview) { relative_aircraft
	 * .setBackgroundResource(R.drawable.light_rounded_square); }
	 * 
	 * menu_right_list.removeAllViews();
	 * 
	 * View view = getLayoutInflater().inflate(R.layout.inflate_aircraft,
	 * menu_right_list, false); final RelativeLayout relative_helicopter =
	 * (RelativeLayout) view .findViewById(R.id.relative_helicopter); final
	 * RelativeLayout relative_fixedwing = (RelativeLayout) view
	 * .findViewById(R.id.relative_fixedwing);
	 * 
	 * relative_helicopter.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub aircraft = 0; relative_fixedwing.setBackgroundColor(Color
	 * .parseColor("#FFFFFF"));
	 * relative_helicopter.setBackgroundColor(getResources().getColor(
	 * R.color.header_light));
	 * 
	 * } });
	 * 
	 * relative_fixedwing.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub aircraft = 1; relative_helicopter.setBackgroundColor(Color
	 * .parseColor("#FFFFFF"));
	 * relative_fixedwing.setBackgroundColor(getResources().getColor(
	 * R.color.header_light));
	 * 
	 * } });
	 * 
	 * final Button button_destination_next = (Button) view
	 * .findViewById(R.id.button_destination_next);
	 * button_destination_next.setOnClickListener(new OnClickListener() {
	 * 
	 * @Override public void onClick(View v) { // TODO Auto-generated method
	 * stub bookAFlight.setAircraft(aircraft); if
	 * (validations.isValidAircraft(bookAFlight)) { if (isbookingview) { //
	 * updateAircraftView(relative_aircraft); }
	 * 
	 * closeRightMenu();
	 * 
	 * Toast.makeText(getApplicationContext(), "" + bookAFlight.getAircraft(),
	 * 1000).show();
	 * 
	 * // callBookAFlightAPi(); } else {
	 * validations.displayValidationMessage(MenuActivity.this,
	 * R.string.select_aircraft);
	 * 
	 * Toast.makeText(getApplicationContext(), "Select Aircraft",
	 * Toast.LENGTH_LONG).show();
	 * 
	 * }
	 * 
	 * } }); menu_right_list.addView(view); }
	 */

	/*
	 * protected void updateAircraftView(RelativeLayout relative_aircraft) { //
	 * TODO Auto-generated method stub ImageView image4 = (ImageView)
	 * relative_aircraft .findViewById(R.id.image4);
	 * image4.setBackgroundResource(R.drawable.aircraft); TextView textview4 =
	 * (TextView) relative_aircraft .findViewById(R.id.textview4);
	 * textview4.setText("Fly Wings"); }
	 */

	/*
	 * protected void updatePassengerView(RelativeLayout relative_passengers) {
	 * // TODO Auto-generated method stub
	 * 
	 * ImageView image3 = (ImageView) relative_passengers
	 * .findViewById(R.id.image3); image3.setVisibility(View.GONE); TextView
	 * txtview3 = (TextView) relative_passengers .findViewById(R.id.txtview3);
	 * txtview3.setVisibility(View.GONE); TextView passengers = (TextView)
	 * relative_passengers .findViewById(R.id.passengers);
	 * passengers.setVisibility(View.VISIBLE);
	 * 
	 * passengers.setText(bookAFlight.getPassenger_adults() + " Adults & " +
	 * bookAFlight.getPassenger_children() + " Children & " +
	 * bookAFlight.getPassenger_infants() + " Infants");
	 * 
	 * }
	 */

	/*
	 * public void setCurrentDateOnView() {
	 * 
	 * Calendar today = Calendar.getInstance(); pyear =
	 * today.get(Calendar.YEAR); pmonth = today.get(Calendar.MONTH); pday =
	 * today.get(Calendar.DAY_OF_MONTH);
	 * 
	 * }
	 */

	/*
	 * private DatePickerDialog.OnDateSetListener dateSetListener = new
	 * DatePickerDialog.OnDateSetListener() {
	 * 
	 * @Override public void onDateSet(DatePicker view, int year, int
	 * monthOfYear, int dayOfMonth) { // TODO Auto-generated method stub if
	 * (issource_date) { pyear = year; pmonth = monthOfYear; pday = dayOfMonth;
	 * 
	 * String airplanedate = new StringBuilder() // Month is 0 based, just add 1
	 * .append(pmonth + 1).append("/").append(pday)
	 * .append("/").append(pyear).append(" ").toString();
	 * airplane_date.setVisibility(View.GONE);
	 * linear_updatesourcedate.setVisibility(View.VISIBLE);
	 * bookAFlight.setSource_dateandtime(airplanedate);
	 * 
	 * TextView source_month = (TextView) linear_updatesourcedate
	 * .findViewById(R.id.source_month);
	 * source_month.setText(utils.getMonth(pmonth) + " " + pyear);
	 * 
	 * TextView source_day = (TextView) linear_updatesourcedate
	 * .findViewById(R.id.source_day); source_day.setText("" + pday);
	 * 
	 * pyear_return = pyear; pmonth_return = pmonth; pday_return = pday; } else
	 * { pyear_return = year; pmonth_return = monthOfYear; pday_return =
	 * dayOfMonth; String airplanedatereturn = new StringBuilder() // Month is 0
	 * based, just add 1 .append(pmonth_return + 1).append("/")
	 * .append(pday_return).append("/").append(pyear_return)
	 * .append(" ").toString(); airplane_date_return.setVisibility(View.GONE);
	 * linear_updatedestinationdate.setVisibility(View.VISIBLE);
	 * bookAFlight.setDestination_dateandtime(airplanedatereturn);
	 * 
	 * TextView destination_month = (TextView) linear_updatedestinationdate
	 * .findViewById(R.id.destination_month);
	 * destination_month.setText(utils.getMonth(pmonth_return) + " " +
	 * pyear_return);
	 * 
	 * TextView destination_day = (TextView) linear_updatedestinationdate
	 * .findViewById(R.id.destination_day); destination_day.setText("" +
	 * pday_return); } } };
	 * 
	 * protected Dialog onCreateDialog(int id) { switch (id) { case
	 * DATE_DIALOG_ID: return new DatePickerDialog(this, dateSetListener, pyear,
	 * pmonth, pday); case DATE_RETURN_DIALOG_ID: return new
	 * DatePickerDialog(this, dateSetListener, pyear_return, pmonth_return,
	 * pday_return);
	 * 
	 * } return new DatePickerDialog(this, dateSetListener, pyear, pmonth,
	 * pday); }
	 */

	/*
	 * private void setParameters(View view, boolean isdate) { // TODO
	 * Auto-generated method stub TextView textview_source = (TextView) view
	 * .findViewById(R.id.textview_source); TextView textview_destination =
	 * (TextView) view .findViewById(R.id.textview_destination); TextView
	 * textview_return_source = (TextView) view
	 * .findViewById(R.id.textview_return_source); TextView
	 * textview_return_destination = (TextView) view
	 * .findViewById(R.id.textview_return_destination);
	 * 
	 * if (isdate) {
	 * 
	 * 
	 * String getsourcedate = utils.getDateFormat(bookAFlight
	 * .getSource_dateandtime());
	 * 
	 * String getdestinationdate; textview_source.setText(getsourcedate);
	 * 
	 * if (isreturnflight) {
	 * 
	 * 
	 * getdestinationdate = utils.getDateFormat(bookAFlight
	 * .getDestination_dateandtime());
	 * textview_destination.setText(getdestinationdate);
	 * 
	 * // textview_return_source.setText(getdestinationdate);
	 * textview_return_destination.setText(getsourcedate); }
	 * 
	 * } else { Log.i("source", "" + bookAFlight.getSource());
	 * Log.i("destination", "" + bookAFlight.getDestination());
	 * textview_source.setText(bookAFlight.getSource());
	 * textview_destination.setText(bookAFlight.getDestination()); if
	 * (isreturnflight) {
	 * 
	 * textview_return_source.setText(bookAFlight.getDestination());
	 * textview_return_destination.setText(bookAFlight.getSource()); }
	 * 
	 * }
	 * 
	 * }
	 */

}