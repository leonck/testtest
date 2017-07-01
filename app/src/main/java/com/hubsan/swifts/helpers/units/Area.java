package com.hubsan.swifts.helpers.units;

import java.util.Locale;

/**
 * 
 * Description 
 * 			区域，面积，范围
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class Area {
	public final static String SQUARE_SYMBOL = "\u00B2";
	private double areaInSqMeters; //面积平方公尺

	public Area(double areaInSqMeters) {
		this.areaInSqMeters = areaInSqMeters;
	}

	public double valueInSqMeters() {
		return areaInSqMeters;
	}

	public void set(double areaInSqMeters) {
		this.areaInSqMeters = areaInSqMeters;
	}

	@Override
	public String toString() {
		if (areaInSqMeters >= 100000) {
			return String.format(Locale.US, "%2.1f km" + SQUARE_SYMBOL,
					areaInSqMeters / 1000000);
		} else if (areaInSqMeters >= 1) {
			return String.format(Locale.US, "%2.1f m" + SQUARE_SYMBOL,
					areaInSqMeters);
		} else if (areaInSqMeters >= 0.00001) {
			return String.format(Locale.US, "%2.2f cm" + SQUARE_SYMBOL,
					areaInSqMeters * 10000);
		} else {
			return areaInSqMeters + " m" + SQUARE_SYMBOL;
		}

	}

}
