package com.example.util;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.util.Log;

import com.example.zilair.dataclass.BookAFlight;
import com.example.zilair.dataclass.Passengers;
import com.example.zilair.dataclass.UserDetails;

public class Utils {

	public String getMonth(int month) {
		String getmonth = null;
		switch (month) {
		case 0:
			getmonth = "Jan";
			break;
		case 1:
			getmonth = "Feb";
			break;
		case 2:
			getmonth = "Mar";
			break;
		case 3:
			getmonth = "Apr";
			break;
		case 4:
			getmonth = "May";
			break;
		case 5:
			getmonth = "Jun";
			break;
		case 6:
			getmonth = "Jul";
			break;
		case 7:
			getmonth = "Aug";
			break;
		case 8:
			getmonth = "Sep";
			break;
		case 9:
			getmonth = "Oct";
			break;
		case 10:
			getmonth = "Nov";
			break;
		case 11:
			getmonth = "Dec";
			break;
		default:
			getmonth = "Jan";
			break;
		}
		return getmonth;
	}

	public String getDateFormat(String date) {
		SimpleDateFormat sdf = new SimpleDateFormat("mm/dd/yyyy");
		java.util.Date testDate = null;
		try {
			testDate = sdf.parse(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		SimpleDateFormat formatter = new SimpleDateFormat("EEE dd MMM yyyy");
		String newFormat = formatter.format(testDate);
		System.out.println(".....Date..." + newFormat);
		return newFormat;
	}

	public void printFlightDetails(BookAFlight bookAFlight) {
		// TODO Auto-generated method stub
		Log.e("Flight Details",
				"Departure: " + bookAFlight.getSource() + " Destination: "
						+ bookAFlight.getDestination() + " Departure Date: "
						+ bookAFlight.getSource_date() + "Return from Destination Date:  "
						+ bookAFlight.getDestination_date() + " No of Adult Passengers: "
						+ bookAFlight.getPassenger_adults() + " No of Children Passengers: "
						+ bookAFlight.getPassenger_children() + " No of Infant Passengers: "
						+ bookAFlight.getPassenger_infants() + " Aircraft Type: "
						+ bookAFlight.getAircraft() 
						+"Departure Time "+ bookAFlight.getSource_time()
						+"Return from Destination Time "+ bookAFlight.getDestination_time());

	}

	
	public ArrayList<String> getDestinations(ArrayList<String> source, int position) {
		// TODO Auto-generated method stub
		ArrayList<String> dest = new ArrayList<String>();
		dest = source;
		dest.remove(position);
		return dest;
	}

	public void printUserDetails(UserDetails details) {
		// TODO Auto-generated method stub
		Log.e("User Details",
				"First Name: " + details.getFirstname() + "Last Name: "
						+ details.getLastname() + "Email: "
						+ details.getEmail() + "Address: "
						+ details.getAddress() + "Country: "
						+ details.getCountry() + "Telephone: "
						+ details.getTelephone());
	}

	public void printPassengerDetails(ArrayList<Passengers> passengers) {
		// TODO Auto-generated method stub
		for(int i=0; i<passengers.size(); i++)
		{
			Log.e("Passenger "+i+" Details","" +passengers.get(i).getFirst_name() +" "+ passengers.get(i).getLastname()+" "+passengers.get(i).getWeight());
		}
	}

}
