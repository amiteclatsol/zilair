package com.example.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.example.zilair.R;
import com.example.zilair.dataclass.BookAFlight;
import com.example.zilair.dataclass.UserDetails;

public class Validations {
	
	Context context;
	
	public Validations() {
		// TODO Auto-generated constructor stub
	}
	
	public Validations(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	
	

	public boolean isValidString(String string) {
		if (string.equals("") || string.equals(null) || string.equals("none")) {
			return false;
		}
		return true;
	}

	public boolean isValidInt(int i) {
		if (i == -1 || i==0) {
			return false;
		}
		return true;
	}
	
	public final boolean isValidEmail(CharSequence target) {
	    if (target == null) {
	        return false;
	       
	    } else {
	        return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
	    }
	}

	public boolean isValidPassengers(BookAFlight bookAFlight) {
		// TODO Auto-generated method stub
		if ((bookAFlight.getPassenger_adults() == 0
				&& bookAFlight.getPassenger_children() == 0
				&& bookAFlight.getPassenger_infants() == 0) || (bookAFlight.getPassenger_adults() == -1
				&& bookAFlight.getPassenger_children() == -1
				&& bookAFlight.getPassenger_infants() == -1)) {
			return false;
		}
		return true;
	}

	public boolean isValidAircraft(BookAFlight bookAFlight) {
		// TODO Auto-generated method stub
		if (bookAFlight.getAircraft().equals("") || bookAFlight.getAircraft().equals(null) || bookAFlight.getAircraft().equals("none")) {
			return false;
		}
		return true;

	}

	/*public boolean isBookAFlightApiValid(BookAFlight bookAFlight) {
		// TODO Auto-generated method stub
		if (!bookAFlight.isIsreturnflight()) {
			if (isValidString(bookAFlight.getSource())
					&& isValidString(bookAFlight.getDestination())
					&& isValidString(bookAFlight.getSource_dateandtime())
					&& isValidPassengers(bookAFlight)
					&& isValidAircraft(bookAFlight)) {
				return true;
			}

		} else {
			if (isValidString(bookAFlight.getSource())
					&& isValidString(bookAFlight.getDestination())
					&& isValidString(bookAFlight.getSource_dateandtime())
					&& isValidString(bookAFlight.getDestination_dateandtime())
					&& isValidPassengers(bookAFlight)
					&& isValidAircraft(bookAFlight)) {
				return true;
			}

		}
		return false;
	}
*/
	public boolean isValidDetails(UserDetails details) {
		// TODO Auto-generated method stub
		if (isValidString(details.getFirstname())
				&& isValidString(details.getLastname())
				&& isValidEmail(details.getEmail())
				&& isValidString(details.getAddress())
				&& isValidString(details.getCountry())
				&& isValidString(details.getTelephone())) {
			return true;
		}
		return false;
	}

	public boolean isReturnDateValid(int pyear, int pmonth, int pday,
			int pyear_return, int pmonth_return, int pday_return) {
		// TODO Auto-generated method stub
		if(pyear_return < pyear)
		{
			return false;
		}
		else if(pyear_return == pyear && pmonth_return < pmonth)
		{
			return false;
		}
		else if(pmonth_return == pmonth && pday_return < pday)
		{
			return false;
		}
		return true;
	}
	
	public void displayValidationMessage(Context context, int id)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	      alertDialogBuilder.setMessage(id);
	      alertDialogBuilder.setNegativeButton("OK", 
	      new DialogInterface.OnClickListener() {
				
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
			 }
	      });
		    
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	}
	
	public void displayValidationMessage(Context context, String message)
	{
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);
	      alertDialogBuilder.setMessage(message);
	      alertDialogBuilder.setNegativeButton("OK", 
	      new DialogInterface.OnClickListener() {
				
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	            dialog.dismiss();
			 }
	      });
		    
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	}

	public boolean ifSourceEqualsDestination(BookAFlight bookAFlight) {
		// TODO Auto-generated method stub
		if(bookAFlight.getSource().equals(bookAFlight.getDestination()))
		{
			return true;
		}
		return false;
	}

	/*public boolean isValidBookingDetails(BookAFlight bookAFlight, Context context) {
		// TODO Auto-generated method stub
		
		
		return false;
	}*/

	
}
