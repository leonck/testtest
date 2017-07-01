package com.hubsan.swifts.helpers.units;

/**
 * 
 * Description 
 * 				高度
 * @author ShengKun.Cheng
 * @date  2015年7月28日
 * @version 
 * @see [class/class#method]
 * @since [product/model]
 */
public class Altitude extends Length {

	public Altitude(double heightInMeters) {
		super(heightInMeters);
	}

	public Length subtract(Altitude toSubtract) {
		return new Length(this.valueInMeters()-toSubtract.valueInMeters());
	}	
}
