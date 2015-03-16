package com.example.proxy;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.zilair.dataclass.BookAFlight;
import com.example.zilair.dataclass.Passengers;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

public class PostNGetResponce extends AsyncTask<String, String, String> {

	Context context;	
	String TAG = "PostNGetResponce";	
	String url;
	
	BookAFlight bookAFlight;

	ArrayList<NameValuePair> postParameters;
	ProxyInterface proxyInterface;
	ProgressDialog progressDialog;
	String response = "";

	JSONObject jArrayData = new JSONObject();
	JSONObject jObjectType = new JSONObject();
	
	JSONObject jsonObject = new JSONObject();
	// 2nd array for user information
	//JSONObject jObjectData = new JSONObject();

	ArrayList<String> saveTagData = new ArrayList<String>();

	/*
	 * public PostNGetResponce(Context mainActivity, ProxyInterface
	 * logInActivity, String url, String username, String password, String
	 * pageName) { // TODO Auto-generated constructor stub
	 * 
	 * this.context = mainActivity; this.proxyInterface = logInActivity;
	 * this.url = url; this.username = username; this.password = password;
	 * this.page = pageName;
	 * 
	 * }
	 */

	public PostNGetResponce(Context mainActivity, ProxyInterface logInActivity,
			String url, BookAFlight bookAFlight) {
		// TODO Auto-generated constructor stub

		this.context = mainActivity;
		this.proxyInterface = logInActivity;
		this.url = url;
		this.bookAFlight = bookAFlight;

	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		try {		
			
			jsonObject.put("first_name", bookAFlight.getUserDetails().getFirstname());
			jsonObject.put("last_name", bookAFlight.getUserDetails().getLastname());
			jsonObject.put("email", bookAFlight.getUserDetails().getEmail());
			jsonObject.put("address", bookAFlight.getUserDetails().getAddress());
			jsonObject.put("country", bookAFlight.getUserDetails().getCountry());
			jsonObject.put("telephone", bookAFlight.getUserDetails().getTelephone());
			
			jsonObject.put("aircraft_type", bookAFlight.getAircraft());
			
			boolean isreturn = bookAFlight.isIsreturnflight();
			
			jsonObject.put("isreturn_flight", isreturn);
			
			jsonObject.put("departure", bookAFlight.getSource());
			jsonObject.put("departure_date", bookAFlight.getSource_date());
			jsonObject.put("departure_time", bookAFlight.getSource_time());
			jsonObject.put("destination", bookAFlight.getDestination());
			if(isreturn)
			{
				jsonObject.put("destination_date", bookAFlight.getDestination_date());
				jsonObject.put("destination_time", bookAFlight.getDestination_time());
			}
			
			jsonObject.put("passengers_adults", bookAFlight.getPassenger_adults());
			jsonObject.put("passengers_children", bookAFlight.getPassenger_children());
			jsonObject.put("passengers_infants", bookAFlight.getPassenger_infants());
			
			JSONArray jsonArray = new JSONArray();
			
			for(int i=0; i<bookAFlight.getPassengers().size();i++)
			{
							
				Passengers passengers= bookAFlight.getPassengers().get(i);
				JSONObject jsonObject2 = new JSONObject();
				jsonObject2.put("passengers_name", passengers.getFirst_name());
				jsonObject2.put("passengers_lastname", passengers.getLastname());
				jsonObject2.put("passengers_weight", passengers.getWeight());
				jsonArray.put(jsonObject2);
				
			}
			
			jsonObject.put("passengers", jsonArray);
			

		/*	jObjectData.put("userID", username);
			jObjectData.put("encryptedPWD", password);*/

			jArrayData.put("data", jsonObject);
			Log.i(TAG, "login data" + jArrayData.toString());

		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("data", jsonObject
				.toString()));

		try {
			response = SimpleHttpClient.executeHttpPost(url, postParameters);
			String str = response.toString();
			str = str.replaceAll("\\s+", "");

		} catch (Exception e) {
			e.printStackTrace();

		}

		return response;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub

		super.onPostExecute(result);

		proxyInterface.responseFromService(result, url);

		progressDialog.dismiss();

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressDialog.show();

	}

}
