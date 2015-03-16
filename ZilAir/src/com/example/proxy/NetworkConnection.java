package com.example.proxy;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class NetworkConnection extends AsyncTask<Void, Void, String> {

	String url;
	Context context;
	ProxyInterface proxyInterface;
	private ProgressDialog progressDialog;
	private String TAG="NetworkConnection";

	public NetworkConnection(String url, Context context,
			ProxyInterface proxyInterface) {
		this.url = url;
		this.context = context;
		this.proxyInterface = proxyInterface;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		proxyInterface.responseFromService(result,url);
		Log.i(TAG, "post execute response: "+result);
		//progressDialog.dismiss();
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		/*progressDialog = new ProgressDialog(context);
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progressDialog.setMessage("Thank You For patience ...");
		progressDialog.show();*/
	}

	@Override
	protected String doInBackground(Void... params) {
		Log.e(TAG, "URL Hit : "+url);
		String response = new Proxy().getDetailFromUrl(url);
		
		
		return response;
	}

}
