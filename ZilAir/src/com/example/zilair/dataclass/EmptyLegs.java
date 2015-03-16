package com.example.zilair.dataclass;

public class EmptyLegs {

	String aircraft_type = "none";
	String date = "none", time = "none", toandfrom = "none";

	public EmptyLegs() {
		// TODO Auto-generated constructor stub
	}

	public EmptyLegs(String aircraft_type, String date, String time,
			String toandfrom) {
		// TODO Auto-generated constructor stub
		this.aircraft_type=aircraft_type;
		this.date=date;
		this.time=time;
		this.toandfrom=toandfrom;
	}

	public String getAircraft_type() {
		return aircraft_type;
	}

	public void setAircraft_type(String aircraft_type) {
		this.aircraft_type = aircraft_type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToandfrom() {
		return toandfrom;
	}

	public void setToandfrom(String toandfrom) {
		this.toandfrom = toandfrom;
	}

}
