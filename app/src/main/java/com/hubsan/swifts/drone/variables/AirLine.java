package com.hubsan.swifts.drone.variables;

public class AirLine {
	int airline_id;
	int airline_num;
	double airline_length;
	long airling_date;

	public AirLine(int airline_id) {
		super();
		this.airline_id = airline_id;
	}

	public AirLine(int airline_id, long airling_date) {
		super();
		this.airline_id = airline_id;
		this.airling_date = airling_date;
	}

	public AirLine() {
		super();
	}

	public int getAirline_id() {
		return airline_id;
	}

	public void setAirline_id(int airline_id) {
		this.airline_id = airline_id;
	}

	public int getAirline_num() {
		return airline_num;
	}

	public void setAirline_num(int airline_num) {
		this.airline_num = airline_num;
	}

	public double getAirline_length() {
		return airline_length;
	}

	public void setAirline_length(double airline_length) {
		this.airline_length = airline_length;
	}

	public long getAirling_date() {
		return airling_date;
	}

	public void setAirling_date(long airling_date) {
		this.airling_date = airling_date;
	}

}