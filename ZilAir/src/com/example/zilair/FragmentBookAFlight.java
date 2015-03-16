package com.example.zilair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import android.app.AlertDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.util.Validations;
import com.example.zilair.dataclass.BookAFlight;
import com.example.zilair.dataclass.Passengers;
import com.example.zilair.dataclass.UserDetails;

public class FragmentBookAFlight extends Fragment {

	RelativeLayout relative_user_details, relative_aircraft,
			relative_destination, relative_passengers, relative_confirmation,
			relative_terms;
	boolean isreturnflight = false;

	String departure, destination;
	int get_adults = 0, get_children = 0, get_infants = 0;
	int total_passengers = 0;
	// relative_dateandtime,relative_passengers;

	// Button send_bookflight_request;

	BookAFlight bookAFlight;
	Validations validations;

	// private static final int DATE_DIALOG_ID = 999;
	// private static final int DATE_RETURN_DIALOG_ID = 9999;
	int pmonth, pday, pyear;
	int pmonth_return, pday_return, pyear_return;
	boolean issource_date = true;
	static boolean issource_time = true;

	TextView from_date, to_date;
	static TextView to_time, from_time;

	String aircraft = "";
	ArrayList<String> destination_list, destination_list_temp;

	public static Fragment newInstance(Context context) {
		FragmentBookAFlight f = new FragmentBookAFlight();
		return f;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		ViewGroup root = (ViewGroup) inflater.inflate(
				R.layout.fragment_bookaflight_details, null);

		bookAFlight = new BookAFlight();

		final LinearLayout content_main = (LinearLayout) root
				.findViewById(R.id.content_main);

		// ((MenuActivity) getActivity()).newFlight();

		relative_user_details = (RelativeLayout) root
				.findViewById(R.id.relative_user_details);
		relative_aircraft = (RelativeLayout) root
				.findViewById(R.id.relative_aircraft);
		relative_destination = (RelativeLayout) root
				.findViewById(R.id.relative_destination);
		relative_passengers = (RelativeLayout) root
				.findViewById(R.id.relative_passengers);
		relative_confirmation = (RelativeLayout) root
				.findViewById(R.id.relative_confirmation);
		relative_terms = (RelativeLayout) root
				.findViewById(R.id.relative_terms);

		final boolean isfromemptylegs = ((MenuActivity) getActivity())
				.isIsfromemptylegs();

		if (isfromemptylegs) {
			String source, destination, sourcedate, sourcetime;
			String aircraft;
			boolean isreturnflight;
			Bundle bundle = this.getArguments();
			if (bundle != null) {
				source = bundle.getString("source");
				destination = bundle.getString("destination");
				sourcedate = bundle.getString("sourcedate");
				isreturnflight = bundle.getBoolean("isreturnflight");
				aircraft = bundle.getString("aircraft");
				sourcetime = bundle.getString("sourcetime");
				bookAFlight.setSource(source);
				bookAFlight.setSource_date(sourcedate);
				bookAFlight.setAircraft(aircraft);
				bookAFlight.setSource_time(sourcetime);
				bookAFlight.setDestination(destination);
				bookAFlight.setIsreturnflight(isreturnflight);
			}

		}

		inflatePersonalDetailsView(content_main, isfromemptylegs);

		relative_user_details.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflatePersonalDetailsView(content_main, isfromemptylegs);

			}
		});

		relative_aircraft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflateChooseAircraftView(content_main, isfromemptylegs);
			}
		});

		relative_destination.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflateChooseDestinationAndDate(content_main, isfromemptylegs);
			}
		});

		relative_passengers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflateSelectPassengerView(content_main);
			}
		});

		relative_terms.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflateTermsView(content_main);
			}
		});

		/*
		 * relative_dateandtime = (RelativeLayout) root
		 * .findViewById(R.id.relative_dateandtime); relative_passengers =
		 * (RelativeLayout) root .findViewById(R.id.relative_passengers);
		 */

		/*
		 * send_bookflight_request = (Button) root
		 * .findViewById(R.id.send_bookflight_request);
		 */

		// setDestination();

		/*
		 * send_bookflight_request.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub //((MenuActivity) getActivity()).callBookAFlightAPi(details);
		 * 
		 * } });
		 */

		return root;
	}

	private void inflatePersonalDetailsView(final LinearLayout content_main,
			final boolean isfromemptylegs) {
		// TODO Auto-generated method stub
		content_main.removeAllViews();

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.inflate_personal_details, content_main, false);

		final EditText fname, lname, email, address, country, telephone;
		final UserDetails details = new UserDetails();
		validations = new Validations(getActivity());
		fname = (EditText) view.findViewById(R.id.fname);
		lname = (EditText) view.findViewById(R.id.lname);
		email = (EditText) view.findViewById(R.id.email);
		address = (EditText) view.findViewById(R.id.address);
		country = (EditText) view.findViewById(R.id.country);
		telephone = (EditText) view.findViewById(R.id.telephone);

		Button next_personaldetails = (Button) view
				.findViewById(R.id.next_personaldetails);
		next_personaldetails.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				details.setFirstname(fname.getText().toString());
				details.setLastname(lname.getText().toString());
				details.setEmail(email.getText().toString());
				details.setAddress(address.getText().toString());
				details.setCountry(country.getText().toString());
				details.setTelephone(telephone.getText().toString());

				if (validations.isValidString(details.getFirstname())) {
					if (validations.isValidString(details.getLastname())) {
						if (validations.isValidEmail(details.getEmail())) {
							if (validations.isValidString(details.getAddress())) {
								if (validations.isValidString(details
										.getCountry())) {
									if (validations.isValidString(details
											.getTelephone())) {
										bookAFlight.setUserDetails(details);
										inflateChooseAircraftView(content_main,
												isfromemptylegs);
									} else {
										validations.displayValidationMessage(
												getActivity(),
												R.string.enter_valid_number);
									}
								} else {
									validations.displayValidationMessage(
											getActivity(),
											R.string.enter_country);
								}
							} else {
								validations.displayValidationMessage(
										getActivity(), R.string.enter_address);
							}

						} else {
							validations.displayValidationMessage(getActivity(),
									R.string.enter_valid_email);
						}
					} else {
						validations.displayValidationMessage(getActivity(),
								R.string.enter_last_name);
					}
				} else {
					validations.displayValidationMessage(getActivity(),
							R.string.enter_first_name);
				}

				/*
				 * int gettelephone = 0; if
				 * (telephone.getText().toString().equals("")) { gettelephone =
				 * -1; } else { try { gettelephone =
				 * Integer.parseInt(telephone.getText() .toString()); } catch
				 * (NumberFormatException e) { // TODO Auto-generated catch
				 * block validations.displayValidationMessage(getActivity(),
				 * R.string.enter_valid_number); } }
				 */

			}
		});

		content_main.addView(view);

	}

	protected void inflateChooseAircraftView(final LinearLayout content_main,
			final boolean isfromemptylegs) {
		// TODO Auto-generated method stub
		content_main.removeAllViews();
		relative_aircraft.setBackgroundResource(R.drawable.flow_chart_two);
		if (isfromemptylegs) {
			inflateChooseDestinationAndDate(content_main, isfromemptylegs);

		} else {

			View view = getActivity().getLayoutInflater().inflate(
					R.layout.inflate_choose_aircraft, content_main, false);

			final RelativeLayout relative_fleet1 = (RelativeLayout) view
					.findViewById(R.id.relative_fleet1);
			/*final RelativeLayout relative_fleet2 = (RelativeLayout) view
					.findViewById(R.id.relative_fleet2);*/
			final RelativeLayout relative_fleet3 = (RelativeLayout) view
					.findViewById(R.id.relative_fleet3);
			Button next_chooseaircraft = (Button) view
					.findViewById(R.id.next_chooseaircraft);

			final RelativeLayout relative_fleet1_background, relative_fleet3_background;

			relative_fleet1_background = (RelativeLayout) view
					.findViewById(R.id.relative_fleet1_background);
			/*relative_fleet2_background = (RelativeLayout) view
					.findViewById(R.id.relative_fleet2_background);*/
			relative_fleet3_background = (RelativeLayout) view
					.findViewById(R.id.relative_fleet3_background);

			relative_fleet1.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					aircraft = "helicopter";
					/*relative_fleet2_background.setBackgroundColor(Color
							.parseColor("#FFFFFF"));*/
					relative_fleet3_background.setBackgroundColor(Color
							.parseColor("#FFFFFF"));
					relative_fleet1_background
							.setBackgroundColor(getResources().getColor(
									R.color.header_light));

				}
			});

			/*relative_fleet2.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					aircraft = "plane_p68c";
					relative_fleet1_background.setBackgroundColor(Color
							.parseColor("#FFFFFF"));
					relative_fleet3_background.setBackgroundColor(Color
							.parseColor("#FFFFFF"));
					relative_fleet2_background
							.setBackgroundColor(getResources().getColor(
									R.color.header_light));

				}
			});*/

			relative_fleet3.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					aircraft = "fly_wings";
					relative_fleet1_background.setBackgroundColor(Color
							.parseColor("#FFFFFF"));
					/*relative_fleet2_background.setBackgroundColor(Color
							.parseColor("#FFFFFF"));*/
					relative_fleet3_background
							.setBackgroundColor(getResources().getColor(
									R.color.header_light));

				}
			});

			next_chooseaircraft.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					bookAFlight.setAircraft(aircraft);
					if (validations.isValidAircraft(bookAFlight)) {

						inflateChooseDestinationAndDate(content_main,
								isfromemptylegs);

					} else {
						validations.displayValidationMessage(getActivity(),
								R.string.select_aircraft);
						/*
						 * Toast.makeText(getApplicationContext(),
						 * "Select Aircraft", Toast.LENGTH_LONG).show();
						 */
					}
				}
			});

			content_main.addView(view);
		}

	}

	protected void inflateChooseDestinationAndDate(
			final LinearLayout content_main, final boolean isfromemptylegs) {
		// TODO Auto-generated method stub
		content_main.removeAllViews();

		relative_destination.setBackgroundResource(R.drawable.flow_chart_two);
		if (isfromemptylegs) {
			inflateSelectPassengerView(content_main);
		} else {

			View view = getActivity().getLayoutInflater().inflate(
					R.layout.inflate_choose_destinationanddate, content_main,
					false);

			RadioButton rb1 = (RadioButton) view
					.findViewById(R.id.radio_oneway);

			RadioButton rb2 = (RadioButton) view
					.findViewById(R.id.radio_return);

			final Spinner from = (Spinner) view.findViewById(R.id.from);
			final Spinner to = (Spinner) view.findViewById(R.id.to);

			ImageView linear_date_source = (ImageView) view
					.findViewById(R.id.linear_updatesourcedate);
			ImageView linear_date_destination = (ImageView) view
					.findViewById(R.id.linear_date_destination);
			ImageView linear_time_source = (ImageView) view
					.findViewById(R.id.linear_time_source);
			ImageView linear_time_destination = (ImageView) view
					.findViewById(R.id.linear_time_destination);
			from_date = (TextView) view.findViewById(R.id.from_date);
			to_date = (TextView) view.findViewById(R.id.to_date);

			from_time = (TextView) view.findViewById(R.id.from_time);
			to_time = (TextView) view.findViewById(R.id.to_time);

			Button next_choosedestination = (Button) view
					.findViewById(R.id.next_choosedestination);

			OnClickListener listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					switch (v.getId()) {
					case R.id.radio_oneway:
						isreturnflight = false;
						to_date.setText("");
						to_time.setText("");
						Log.i("", "isreturnflight" + isreturnflight);
						break;

					case R.id.radio_return:
						isreturnflight = true;
						Log.i("", "isreturnflight" + isreturnflight);
						break;

					default:
						break;
					}

				}
			};

			rb1.setOnClickListener(listener);
			rb2.setOnClickListener(listener);

			from.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					departure = from.getSelectedItem().toString();
					/*
					 * destination_list = new ArrayList<String>(Arrays
					 * .asList(getResources().getStringArray(
					 * R.array.country_arrays)));
					 * destination_list.remove(position); destination_list_temp
					 * = destination_list;
					 */
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});

			to.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					destination = to.getSelectedItem().toString();
					/*
					 * destination_list = new ArrayList<String>(Arrays
					 * .asList(getResources().getStringArray(
					 * R.array.country_arrays)));
					 * destination_list.remove(position); destination_list_temp
					 * = destination_list;
					 */
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});

			destination_list = new ArrayList<String>(
					Arrays.asList(getResources().getStringArray(
							R.array.source_arrays)));

			ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
					getActivity(), android.R.layout.simple_spinner_item,
					destination_list);
			dataAdapter
					.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			to.setAdapter(dataAdapter);

			// setCurrentDateOnView();

			linear_date_source.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					issource_date = true;
					showDatePicker();

				}
			});

			linear_time_source.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					issource_time = true;
					showTimepicker();
				}
			});

			linear_date_destination.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isreturnflight) {
						issource_date = false;
						showDatePicker();
					}

				}
			});

			linear_time_destination.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (isreturnflight) {
						issource_time = false;
						showTimepicker();
					}

				}
			});

			next_choosedestination.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					bookAFlight.setIsreturnflight(isreturnflight);
					bookAFlight.setSource(departure);
					bookAFlight.setDestination(destination);
					bookAFlight.setSource_date(from_date.getText().toString());
					bookAFlight.setSource_time(from_time.getText().toString());
					if (bookAFlight.isIsreturnflight()) {
						bookAFlight.setDestination_date(to_date.getText()
								.toString());
						bookAFlight.setDestination_time(to_time.getText()
								.toString());
					}
					/*
					 * if(validations.isValidBookingDetails(bookAFlight,
					 * getActivity())) {
					 * 
					 * }
					 */

					if (validations.isValidString(bookAFlight.getSource())) {
						if (validations.isValidString(bookAFlight
								.getDestination())) {

							if (!validations
									.ifSourceEqualsDestination(bookAFlight)) {

								if (validations.isValidString(bookAFlight
										.getSource_date())) {
									if (validations.isValidString(bookAFlight
											.getSource_time())) {
										if (bookAFlight.isIsreturnflight()) {
											if (validations.isValidString(bookAFlight
													.getDestination_date())) {
												if (validations
														.isReturnDateValid(
																pyear, pmonth,
																pday,
																pyear_return,
																pmonth_return,
																pday_return)) {
													if (validations
															.isValidString(bookAFlight
																	.getDestination_time())) {
														inflateSelectPassengerView(content_main);
														// inflateConfirmBookingView(content_main);
													} else {
														validations
																.displayValidationMessage(
																		getActivity(),
																		R.string.select_return_time);
													}
												} else {
													validations
															.displayValidationMessage(
																	getActivity(),
																	R.string.enter_valid_return_date);
												}
											} else {
												validations
														.displayValidationMessage(
																getActivity(),
																R.string.enter_return_date);
											}
										} else {
											inflateSelectPassengerView(content_main);
											// inflateConfirmBookingView(content_main);
										}
									} else {
										validations.displayValidationMessage(
												getActivity(),
												R.string.select_source_time);
									}
								} else {
									validations.displayValidationMessage(
											getActivity(),
											R.string.select_source_date);
								}

							} else {
								validations.displayValidationMessage(
										getActivity(),
										R.string.source_destination_same);
							}
						} else {
							validations.displayValidationMessage(getActivity(),
									R.string.select_destination);
						}
					} else {
						validations.displayValidationMessage(getActivity(),
								R.string.select_departure);
					}

				}

			});

			content_main.addView(view);
		}

	}

	private void inflateSelectPassengerView(final LinearLayout content_main) {
		// TODO Auto-generated method stub

		content_main.removeAllViews();

		relative_passengers.setBackgroundResource(R.drawable.flow_chart_two);

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.inflate_select_passengers, content_main, false);
		Button next_choosepassengers = (Button) view
				.findViewById(R.id.next_choosepassengers);

		final Spinner adults = (Spinner) view.findViewById(R.id.adults);
		final Spinner children = (Spinner) view.findViewById(R.id.children);
		final Spinner infants = (Spinner) view.findViewById(R.id.infants);

		final EditText passenger1_name = (EditText) view
				.findViewById(R.id.passenger1_name);
		final EditText passenger2_name = (EditText) view
				.findViewById(R.id.passenger2_name);
		final EditText passenger3_name = (EditText) view
				.findViewById(R.id.passenger3_name);
		final EditText passenger4_name = (EditText) view
				.findViewById(R.id.passenger4_name);
		final EditText passenger5_name = (EditText) view
				.findViewById(R.id.passenger5_name);

		final EditText passenger1_lastname = (EditText) view
				.findViewById(R.id.passenger1_lastname);
		final EditText passenger2_lastname = (EditText) view
				.findViewById(R.id.passenger2_lastname);
		final EditText passenger3_lastname = (EditText) view
				.findViewById(R.id.passenger3_lastname);
		final EditText passenger4_lastname = (EditText) view
				.findViewById(R.id.passenger4_lastname);
		final EditText passenger5_lastname = (EditText) view
				.findViewById(R.id.passenger5_lastname);

		final EditText passenger1_weight = (EditText) view
				.findViewById(R.id.passenger1_weight);
		final EditText passenger2_weight = (EditText) view
				.findViewById(R.id.passenger2_weight);
		final EditText passenger3_weight = (EditText) view
				.findViewById(R.id.passenger3_weight);
		final EditText passenger4_weight = (EditText) view
				.findViewById(R.id.passenger4_weight);
		final EditText passenger5_weight = (EditText) view
				.findViewById(R.id.passenger5_weight);

		final LinearLayout passenger_linear1, passenger_linear2, passenger_linear3, passenger_linear4, passenger_linear5;

		passenger_linear1 = (LinearLayout) view
				.findViewById(R.id.passenger_linear1);
		passenger_linear2 = (LinearLayout) view
				.findViewById(R.id.passenger_linear2);
		passenger_linear3 = (LinearLayout) view
				.findViewById(R.id.passenger_linear3);
		passenger_linear4 = (LinearLayout) view
				.findViewById(R.id.passenger_linear4);
		passenger_linear5 = (LinearLayout) view
				.findViewById(R.id.passenger_linear5);

		/*ArrayList<String> passengers_list = new ArrayList<String>(
				Arrays.asList(getResources().getStringArray(
						R.array.source_passengers)));*/

		/*
		 * ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(
		 * getActivity(), android.R.layout.simple_spinner_item,
		 * passengers_list); dataAdapter
		 * .setDropDownViewResource(android.R.layout
		 * .simple_spinner_dropdown_item);
		 */

		
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				getActivity(), R.array.source_passengers,
				R.layout.my_spinner_textview);

		adapter.setDropDownViewResource(R.layout.my_spinner_textview);
		adults.setAdapter(adapter);
		children.setAdapter(adapter);
		infants.setAdapter(adapter);

		adults.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				get_adults = Integer.parseInt(adults.getSelectedItem()
						.toString());
				total_passengers = get_adults + get_children + get_infants;
				setPassengerDetailsVisibility(passenger_linear1,
						passenger_linear2, passenger_linear3,
						passenger_linear4, passenger_linear5);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				get_adults = 0;
			}
		});

		children.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				get_children = Integer.parseInt(children.getSelectedItem()
						.toString());
				total_passengers = get_adults + get_children + get_infants;
				setPassengerDetailsVisibility(passenger_linear1,
						passenger_linear2, passenger_linear3,
						passenger_linear4, passenger_linear5);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				get_children = 0;
			}
		});

		infants.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				get_infants = Integer.parseInt(infants.getSelectedItem()
						.toString());
				total_passengers = get_adults + get_children + get_infants;
				setPassengerDetailsVisibility(passenger_linear1,
						passenger_linear2, passenger_linear3,
						passenger_linear4, passenger_linear5);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub
				get_infants = 0;

			}
		});

		next_choosepassengers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				bookAFlight.setPassenger_adults(get_adults);
				bookAFlight.setPassenger_children(get_children);
				bookAFlight.setPassenger_infants(get_infants);

				ArrayList<Passengers> list_passengers = new ArrayList<Passengers>();

				switch (total_passengers) {
				case 0:
					break;
				case 1:
					addDetailsPassenger1(list_passengers, passenger1_name,
							passenger1_lastname, passenger1_weight);
					break;
				case 2:
					addDetailsPassenger1(list_passengers, passenger1_name,
							passenger1_lastname, passenger1_weight);
					addDetailsPassenger2(list_passengers, passenger2_name,
							passenger2_lastname, passenger2_weight);
					break;

				case 3:
					addDetailsPassenger1(list_passengers, passenger1_name,
							passenger1_lastname, passenger1_weight);
					addDetailsPassenger2(list_passengers, passenger2_name,
							passenger2_lastname, passenger2_weight);
					addDetailsPassenger3(list_passengers, passenger3_name,
							passenger3_lastname, passenger3_weight);
					break;

				case 4:
					addDetailsPassenger1(list_passengers, passenger1_name,
							passenger1_lastname, passenger1_weight);
					addDetailsPassenger2(list_passengers, passenger2_name,
							passenger2_lastname, passenger2_weight);
					addDetailsPassenger3(list_passengers, passenger3_name,
							passenger3_lastname, passenger3_weight);
					addDetailsPassenger4(list_passengers, passenger4_name,
							passenger4_lastname, passenger4_weight);
					break;

				case 5:
				default:
					addDetailsPassenger1(list_passengers, passenger1_name,
							passenger1_lastname, passenger1_weight);
					addDetailsPassenger2(list_passengers, passenger2_name,
							passenger2_lastname, passenger2_weight);
					addDetailsPassenger3(list_passengers, passenger3_name,
							passenger3_lastname, passenger3_weight);
					addDetailsPassenger4(list_passengers, passenger4_name,
							passenger4_lastname, passenger4_weight);
					addDetailsPassenger5(list_passengers, passenger5_name,
							passenger5_lastname, passenger5_weight);
					break;

				}

				bookAFlight.setPassengers(list_passengers);
				
				int cnt=0;

				if (validations.isValidPassengers(bookAFlight)) {
					
					for(int i=0;i<list_passengers.size();i++)
					{
						if(validations.isValidString(list_passengers.get(i).getFirst_name()))
						{
							if(validations.isValidString(list_passengers.get(i).getLastname()))
							{
								if(validations.isValidInt(list_passengers.get(i).getWeight()))
								{
									cnt++;
									if(cnt==list_passengers.size())
									{
										inflateConfirmBookingView(content_main);
									}
								}
								else
								{
									validations.displayValidationMessage(getActivity(),
											"Enter Passegner "+(i+1)+" Weight");
									break;
								}
							}
							else
							{
								validations.displayValidationMessage(getActivity(),
										"Enter Passegner "+(i+1)+" Last Name");
								break;
							}
						}
						else
						{
							validations.displayValidationMessage(getActivity(),
									"Enter Passegner "+(i+1)+" First Name");
							break;
						}
					}
					
				} else {
					validations.displayValidationMessage(getActivity(),
							R.string.select_passengers);
				}

			}
		});
		content_main.addView(view);
	}

	protected void setPassengerDetailsVisibility(
			LinearLayout passenger_linear1, LinearLayout passenger_linear2,
			LinearLayout passenger_linear3, LinearLayout passenger_linear4,
			LinearLayout passenger_linear5) {
		// TODO Auto-generated method stub

		passenger_linear1.setVisibility(View.GONE);
		passenger_linear2.setVisibility(View.GONE);
		passenger_linear3.setVisibility(View.GONE);
		passenger_linear4.setVisibility(View.GONE);
		passenger_linear5.setVisibility(View.GONE);

		switch (total_passengers) {
		case 0:
			break;
		case 1:
			passenger_linear1.setVisibility(View.VISIBLE);
			break;
		case 2:
			passenger_linear1.setVisibility(View.VISIBLE);
			passenger_linear2.setVisibility(View.VISIBLE);
			break;

		case 3:
			passenger_linear1.setVisibility(View.VISIBLE);
			passenger_linear2.setVisibility(View.VISIBLE);
			passenger_linear3.setVisibility(View.VISIBLE);
			break;

		case 4:
			passenger_linear1.setVisibility(View.VISIBLE);
			passenger_linear2.setVisibility(View.VISIBLE);
			passenger_linear3.setVisibility(View.VISIBLE);
			passenger_linear4.setVisibility(View.VISIBLE);
			break;
		case 5:
		default:
			passenger_linear1.setVisibility(View.VISIBLE);
			passenger_linear2.setVisibility(View.VISIBLE);
			passenger_linear3.setVisibility(View.VISIBLE);
			passenger_linear4.setVisibility(View.VISIBLE);
			passenger_linear5.setVisibility(View.VISIBLE);
			break;

		}

	}

	protected void addDetailsPassenger5(ArrayList<Passengers> list_passengers,
			EditText passenger5_name, EditText passenger5_lastname,
			EditText passenger5_weight) {
		// TODO Auto-generated method stub
		
		int weight = -1;
		try {
			weight = Integer.parseInt(passenger5_weight.getText()
					.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Passengers passengers = new Passengers();
		passengers.setFirst_name(passenger5_name.getText().toString());
		passengers.setLastname(passenger5_lastname.getText().toString());
		passengers.setWeight(weight);
		list_passengers.add(passengers);

	}

	protected void addDetailsPassenger4(ArrayList<Passengers> list_passengers,
			EditText passenger4_name, EditText passenger4_lastname,
			EditText passenger4_weight) {
		// TODO Auto-generated method stub
		int weight = -1;
		try {
			weight = Integer.parseInt(passenger4_weight.getText()
					.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Passengers passengers = new Passengers();
		passengers.setFirst_name(passenger4_name.getText().toString());
		passengers.setLastname(passenger4_lastname.getText().toString());
		passengers.setWeight(weight);
		list_passengers.add(passengers);

	}

	protected void addDetailsPassenger3(ArrayList<Passengers> list_passengers,
			EditText passenger3_name, EditText passenger3_lastname,
			EditText passenger3_weight) {
		// TODO Auto-generated method stub
		
		int weight = -1;
		try {
			weight = Integer.parseInt(passenger3_weight.getText()
					.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Passengers passengers = new Passengers();
		passengers.setFirst_name(passenger3_name.getText().toString());
		passengers.setLastname(passenger3_lastname.getText().toString());
		passengers.setWeight(weight);
		list_passengers.add(passengers);

	}

	protected void addDetailsPassenger2(ArrayList<Passengers> list_passengers,
			EditText passenger2_name, EditText passenger2_lastname,
			EditText passenger2_weight) {
		// TODO Auto-generated method stub
		
		int weight = -1;
		try {
			weight = Integer.parseInt(passenger2_weight.getText()
					.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Passengers passengers = new Passengers();
		passengers.setFirst_name(passenger2_name.getText().toString());
		passengers.setLastname(passenger2_lastname.getText().toString());
		passengers.setWeight(weight);
		list_passengers.add(passengers);

	}

	protected void addDetailsPassenger1(ArrayList<Passengers> list_passengers,
			EditText passenger1_name, EditText passenger1_lastname,
			EditText passenger1_weight) {
		// TODO Auto-generated method stub
		
		int weight = -1;
		try {
			weight = Integer.parseInt(passenger1_weight.getText()
					.toString());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Passengers passengers = new Passengers();
		passengers.setFirst_name(passenger1_name.getText().toString());
		passengers.setLastname(passenger1_lastname.getText().toString());
		passengers.setWeight(weight);
		list_passengers.add(passengers);
	}

	private void showTimepicker() {
		DialogFragment newFragment = new TimePickerFragment();
		FragmentManager fm = getActivity().getFragmentManager();
		android.app.FragmentTransaction ft = fm.beginTransaction();
		newFragment.show(ft, "timePicker");
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		public TimePickerFragment() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			if (issource_time) {
				from_time.setText(hourOfDay + ":" + minute);
			} else {
				to_time.setText(hourOfDay + ":" + minute);
			}

		}
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

			if (issource_date) {
				pyear = year;
				pmonth = monthOfYear;
				pday = dayOfMonth;

				String airplanedate = new StringBuilder()
						// Month is 0 based, just add 1
						.append(pmonth + 1).append("/").append(pday)
						.append("/").append(pyear).append(" ").toString();

				// bookAFlight.setSource_dateandtime(airplanedate);
				from_date.setText(airplanedate);

				pyear_return = pyear;
				pmonth_return = pmonth;
				pday_return = pday;
			} else {
				pyear_return = year;
				pmonth_return = monthOfYear;
				pday_return = dayOfMonth;
				String airplanedatereturn = new StringBuilder()
						// Month is 0 based, just add 1
						.append(pmonth_return + 1).append("/")
						.append(pday_return).append("/").append(pyear_return)
						.append(" ").toString();

				// bookAFlight.setDestination_dateandtime(airplanedatereturn);
				to_date.setText(airplanedatereturn);

			}
		}
	};

	protected void inflateConfirmBookingView(final LinearLayout content_main) {
		// TODO Auto-generated method stub

		content_main.removeAllViews();

		relative_confirmation.setBackgroundResource(R.drawable.flow_chart_two);

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.inflate_confirmbooking, content_main, false);

		TextView confirm_firstname = (TextView) view
				.findViewById(R.id.confirm_firstname);
		TextView confirm_lastname = (TextView) view
				.findViewById(R.id.confirm_lastname);
		TextView confirm_email = (TextView) view
				.findViewById(R.id.confirm_email);
		TextView confirm_address = (TextView) view
				.findViewById(R.id.confirm_address);
		TextView confirm_country = (TextView) view
				.findViewById(R.id.confirm_country);
		TextView confirm_telno = (TextView) view
				.findViewById(R.id.confirm_telno);
		TextView confirm_aircrafttype = (TextView) view
				.findViewById(R.id.confirm_aircrafttype);
		TextView confirm_from = (TextView) view.findViewById(R.id.confirm_from);
		TextView confirm_departure_date = (TextView) view
				.findViewById(R.id.confirm_departure_date);
		TextView confirm_departure_time = (TextView) view
				.findViewById(R.id.confirm_departure_time);
		TextView confirm_to = (TextView) view.findViewById(R.id.confirm_to);
		TextView confirm_to_date = (TextView) view
				.findViewById(R.id.confirm_to_date);
		TextView confirm_to_time = (TextView) view
				.findViewById(R.id.confirm_to_time);

		TextView confirm_adults = (TextView) view
				.findViewById(R.id.confirm_adults);
		TextView confirm_children = (TextView) view
				.findViewById(R.id.confirm_children);
		TextView confirm_infants = (TextView) view
				.findViewById(R.id.confirm_infants);

		TextView passenger1_name = (TextView) view
				.findViewById(R.id.passenger1_name);
		TextView passenger1_lastname = (TextView) view
				.findViewById(R.id.passenger1_lastname);
		TextView passenger1_weight = (TextView) view
				.findViewById(R.id.passenger1_weight);

		TextView passenger2_name = (TextView) view
				.findViewById(R.id.passenger2_name);
		TextView passenger2_lastname = (TextView) view
				.findViewById(R.id.passenger2_lastname);
		TextView passenger2_weight = (TextView) view
				.findViewById(R.id.passenger2_weight);

		TextView passenger3_name = (TextView) view
				.findViewById(R.id.passenger3_name);
		TextView passenger3_lastname = (TextView) view
				.findViewById(R.id.passenger3_lastname);
		TextView passenger3_weight = (TextView) view
				.findViewById(R.id.passenger3_weight);

		TextView passenger4_name = (TextView) view
				.findViewById(R.id.passenger4_name);
		TextView passenger4_lastname = (TextView) view
				.findViewById(R.id.passenger4_lastname);
		TextView passenger4_weight = (TextView) view
				.findViewById(R.id.passenger4_weight);

		TextView passenger5_name = (TextView) view
				.findViewById(R.id.passenger5_name);
		TextView passenger5_lastname = (TextView) view
				.findViewById(R.id.passenger5_lastname);
		TextView passenger5_weight = (TextView) view
				.findViewById(R.id.passenger5_weight);

		final LinearLayout passenger_linear1, passenger_linear2, passenger_linear3, passenger_linear4, passenger_linear5;

		passenger_linear1 = (LinearLayout) view
				.findViewById(R.id.passenger_linear1);
		passenger_linear2 = (LinearLayout) view
				.findViewById(R.id.passenger_linear2);
		passenger_linear3 = (LinearLayout) view
				.findViewById(R.id.passenger_linear3);
		passenger_linear4 = (LinearLayout) view
				.findViewById(R.id.passenger_linear4);
		passenger_linear5 = (LinearLayout) view
				.findViewById(R.id.passenger_linear5);

		confirm_firstname.setText("Name: "
				+ bookAFlight.getUserDetails().getFirstname());
		confirm_lastname.setText("Last Name: "
				+ bookAFlight.getUserDetails().getLastname());
		confirm_email.setText("Email: "
				+ bookAFlight.getUserDetails().getEmail());
		confirm_address.setText("Address: "
				+ bookAFlight.getUserDetails().getAddress());
		confirm_country.setText("Country: "
				+ bookAFlight.getUserDetails().getCountry());
		confirm_telno.setText("Tel: "
				+ bookAFlight.getUserDetails().getTelephone());

		confirm_aircrafttype.setText(bookAFlight.getAircraft());

		confirm_from.setText("From: " + bookAFlight.getSource());
		confirm_departure_date.setText("Departure Date: "
				+ bookAFlight.getSource_date());
		confirm_departure_time.setText("Departure Time: "
				+ bookAFlight.getSource_time());
		confirm_to.setText("To: " + bookAFlight.getDestination());
		if (bookAFlight.isIsreturnflight()) {
			confirm_to_date.setText("Return Date: "
					+ bookAFlight.getDestination_date());
			confirm_to_time.setText("Return Time: "
					+ bookAFlight.getDestination_time());
		} else {
			confirm_to_date.setText("No Return Flight");
			confirm_to_time.setText("No Return Flight ");
		}

		confirm_adults.setText("Adults: " + bookAFlight.getPassenger_adults());
		confirm_children.setText("Children: "
				+ bookAFlight.getPassenger_children());
		confirm_infants.setText("Infants: "
				+ bookAFlight.getPassenger_infants());

		setPassengerDetailsVisibility(passenger_linear1, passenger_linear2,
				passenger_linear3, passenger_linear4, passenger_linear5);

		try {
			passenger1_name.setText("Name: "
					+ bookAFlight.getPassengers().get(0).getFirst_name());
			passenger2_name.setText("Name: "
					+ bookAFlight.getPassengers().get(1).getFirst_name());
			passenger3_name.setText("Name: "
					+ bookAFlight.getPassengers().get(2).getFirst_name());
			passenger4_name.setText("Name: "
					+ bookAFlight.getPassengers().get(3).getFirst_name());
			passenger5_name.setText("Name: "
					+ bookAFlight.getPassengers().get(4).getFirst_name());
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			passenger1_lastname.setText("Name: "
					+ bookAFlight.getPassengers().get(0).getLastname());
			passenger2_lastname.setText("Name: "
					+ bookAFlight.getPassengers().get(1).getLastname());
			passenger3_lastname.setText("Name: "
					+ bookAFlight.getPassengers().get(2).getLastname());
			passenger4_lastname.setText("Name: "
					+ bookAFlight.getPassengers().get(3).getLastname());
			passenger5_lastname.setText("Name: "
					+ bookAFlight.getPassengers().get(4).getLastname());
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			passenger1_weight.setText("Name: "
					+ bookAFlight.getPassengers().get(0).getWeight());
			passenger2_weight.setText("Name: "
					+ bookAFlight.getPassengers().get(1).getWeight());
			passenger3_weight.setText("Name: "
					+ bookAFlight.getPassengers().get(2).getWeight());
			passenger4_weight.setText("Name: "
					+ bookAFlight.getPassengers().get(3).getWeight());
			passenger5_weight.setText("Name: "
					+ bookAFlight.getPassengers().get(4).getWeight());
		} catch (IndexOutOfBoundsException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Button next_confirmbooking = (Button) view
				.findViewById(R.id.next_confirmbooking);
		next_confirmbooking.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				inflateTermsView(content_main);
			}
		});
		content_main.addView(view);

	}

	protected void inflateTermsView(final LinearLayout content_main) {
		// TODO Auto-generated method stub
		content_main.removeAllViews();

		relative_terms.setBackgroundResource(R.drawable.flow_chart_two);

		View view = getActivity().getLayoutInflater().inflate(
				R.layout.inflate_termsandconditions, content_main, false);

		final CheckBox checkBox_returnFlight = (CheckBox) view
				.findViewById(R.id.checkBox_returnFlight);

		Button booknow = (Button) view.findViewById(R.id.booknow);
		booknow.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (checkBox_returnFlight.isChecked()) {

					((MenuActivity) getActivity())
							.callBookAFlightAPi(bookAFlight);

				}

			}
		});
		content_main.addView(view);
	}

	public void setDestination() {
		// TODO Auto-generated method stub
		relative_destination.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				/*
				 * if (validations.isValidDetails(details)) { ((MenuActivity)
				 * getActivity()).rightMenuListener(); ((MenuActivity)
				 * getActivity()).inflateBookingView( relative_destination,
				 * relative_dateandtime, relative_passengers, relative_aircraft,
				 * true);
				 * 
				 * } else { validations.displayValidationMessage(getActivity(),
				 * R.string.enter_user_details);
				 * 
				 * Toast.makeText(getActivity(), "Enter User Details",
				 * Toast.LENGTH_LONG).show();
				 * 
				 * }
				 */

				/*
				 * getActivity().findViewById(R.id.menu_right_list).setVisibility
				 * (View.VISIBLE);
				 * getActivity().findViewById(R.id.menu_right).setBackgroundResource
				 * (R.drawable.close);
				 */
			}
		});

		/*
		 * relative_dateandtime.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * BookAFlight aFlight = ((MenuActivity) getActivity())
		 * .getBookAFlightObject(); if
		 * (validations.isValidString(aFlight.getSource()) &&
		 * validations.isValidString(aFlight.getDestination())) {
		 * ((MenuActivity) getActivity()).rightMenuListener(); ((MenuActivity)
		 * getActivity()).selectDate( relative_dateandtime, relative_passengers,
		 * relative_aircraft, true); } else {
		 * 
		 * Toast.makeText(getActivity(), "Select Destination",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * validations.displayValidationMessage(getActivity(),
		 * R.string.select_destination); }
		 * 
		 * } });
		 * 
		 * relative_passengers.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub
		 * 
		 * BookAFlight aFlight = ((MenuActivity) getActivity())
		 * .getBookAFlightObject();
		 * 
		 * if (validations.isValidString(aFlight.getSource_dateandtime())) {
		 * ((MenuActivity) getActivity()).rightMenuListener(); ((MenuActivity)
		 * getActivity()).selectPassengers( relative_passengers,
		 * relative_aircraft, true); } else {
		 * validations.displayValidationMessage(getActivity(),
		 * R.string.select_date);
		 * 
		 * Toast.makeText(getActivity(), "Select Date",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * }
		 * 
		 * } });
		 */

		/*
		 * relative_aircraft.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * stub BookAFlight aFlight = ((MenuActivity) getActivity())
		 * .getBookAFlightObject();
		 * 
		 * if (validations.isValidPassengers(aFlight)) { ((MenuActivity)
		 * getActivity()).rightMenuListener(); ((MenuActivity)
		 * getActivity()).selectAircraft( relative_aircraft, true); } else {
		 * validations.displayValidationMessage(getActivity(),
		 * R.string.select_passengers);
		 * 
		 * Toast.makeText(getActivity(), "Select Passengers",
		 * Toast.LENGTH_LONG).show();
		 * 
		 * }
		 * 
		 * } });
		 */

	}

	/*
	 * protected Dialog onCreateDialog(int id) { switch (id) { case
	 * DATE_DIALOG_ID: return new DatePickerDialog(getActivity(),
	 * dateSetListener, pyear, pmonth, pday); case DATE_RETURN_DIALOG_ID: return
	 * new DatePickerDialog(getActivity(), dateSetListener, pyear_return,
	 * pmonth_return, pday_return);
	 * 
	 * } return new DatePickerDialog(getActivity(), dateSetListener, pyear,
	 * pmonth, pday); }
	 * 
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
	 * 
	 * airplane_date.setVisibility(View.GONE);
	 * linear_updatesourcedate.setVisibility(View.VISIBLE);
	 * 
	 * bookAFlight.setSource_dateandtime(airplanedate);
	 * 
	 * 
	 * TextView source_month = (TextView) linear_updatesourcedate
	 * .findViewById(R.id.source_month);
	 * source_month.setText(utils.getMonth(pmonth) + " " + pyear);
	 * 
	 * TextView source_day = (TextView) linear_updatesourcedate
	 * .findViewById(R.id.source_day); source_day.setText("" + pday);
	 * 
	 * 
	 * pyear_return = pyear; pmonth_return = pmonth; pday_return = pday; } else
	 * { pyear_return = year; pmonth_return = monthOfYear; pday_return =
	 * dayOfMonth; String airplanedatereturn = new StringBuilder() // Month is 0
	 * based, just add 1 .append(pmonth_return + 1).append("/")
	 * .append(pday_return).append("/").append(pyear_return)
	 * .append(" ").toString();
	 * 
	 * airplane_date_return.setVisibility(View.GONE);
	 * linear_updatedestinationdate.setVisibility(View.VISIBLE);
	 * 
	 * bookAFlight.setDestination_dateandtime(airplanedatereturn);
	 * 
	 * 
	 * TextView destination_month = (TextView) linear_updatedestinationdate
	 * .findViewById(R.id.destination_month);
	 * destination_month.setText(utils.getMonth(pmonth_return) + " " +
	 * pyear_return);
	 * 
	 * TextView destination_day = (TextView) linear_updatedestinationdate
	 * .findViewById(R.id.destination_day); destination_day.setText("" +
	 * pday_return);
	 * 
	 * } } };
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

}
