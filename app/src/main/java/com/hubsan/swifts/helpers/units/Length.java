package com.hubsan.swifts.helpers.units;

import java.util.Locale;

/**
 * 
 * Description 
 * 				长度
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class Length {
	private double lengthInMeters;

	public Length(double lengthInMeters) {
		set(lengthInMeters);
	}

	public double valueInMeters() {
		return lengthInMeters;
	}

	public void set(double lengthInMeters) {
		this.lengthInMeters = lengthInMeters;
	}

	@Override
	public String toString() {
		if (lengthInMeters >= 1000) {
			return String.format(Locale.US, "%2.1f km", lengthInMeters / 1000);
		} else if (lengthInMeters >= 1) {
			return String.format(Locale.US, "%2.1f m", lengthInMeters);
		} else if (lengthInMeters >= 0.001) {
			return String.format(Locale.US, "%2.1f mm", lengthInMeters * 1000);
		} else {
			return lengthInMeters + " m";
		}
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Length) {
			return lengthInMeters == ((Length) o).lengthInMeters;
		}
		return false;
	}

}
