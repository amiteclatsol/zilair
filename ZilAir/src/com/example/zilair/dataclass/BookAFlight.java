package com.example.zilair.dataclass;

import java.util.ArrayList;

public class BookAFlight {
	
	String source="none", destination="none", source_date="none", destination_date="none", source_time="none", destination_time="none";
	int passenger_adults=-1, passenger_infants=-1, passenger_children=-1;
	String aircraft="none";
	
	ArrayList<Passengers> passengers;
	UserDetails userDetails;
	
	public ArrayList<Passengers> getPassengers() {
		return passengers;
	}

	public void setPassengers(ArrayList<Passengers> passengers) {
		this.passengers = passengers;
	}

	public String getSource_date() {
		return source_date;
	}

	public void setSource_date(String source_date) {
		this.source_date = source_date;
	}

	public String getDestination_date() {
		return destination_date;
	}

	public void setDestination_date(String destination_date) {
		this.destination_date = destination_date;
	}

	public String getSource_time() {
		return source_time;
	}

	public void setSource_time(String source_time) {
		this.source_time = source_time;
	}

	public String getDestination_time() {
		return destination_time;
	}

	public void setDestination_time(String destination_time) {
		this.destination_time = destination_time;
	}

	
	
	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	boolean isreturnflight;
	
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	

	public int getPassenger_adults() {
		return passenger_adults;
	}

	public void setPassenger_adults(int passenger_adults) {
		this.passenger_adults = passenger_adults;
	}

	public int getPassenger_infants() {
		return passenger_infants;
	}

	public void setPassenger_infants(int passenger_infants) {
		this.passenger_infants = passenger_infants;
	}

	public int getPassenger_children() {
		return passenger_children;
	}

	public void setPassenger_children(int passenger_children) {
		this.passenger_children = passenger_children;
	}

	

	public String getAircraft() {
		return aircraft;
	}

	public void setAircraft(String aircraft) {
		this.aircraft = aircraft;
	}

	public boolean isIsreturnflight() {
		return isreturnflight;
	}

	public void setIsreturnflight(boolean isreturnflight) {
		this.isreturnflight = isreturnflight;
	}

	

}
