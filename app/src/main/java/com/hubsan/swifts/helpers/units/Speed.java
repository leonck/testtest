package com.hubsan.swifts.helpers.units;

/**
 * Created by chengshengkun on 16/11/3.
 */

public class Speed extends Length {


    public Speed(double speedInMeters) {
        super(speedInMeters);
    }

    public Length subtract(Speed toSubtract) {
        return new Length(this.valueInMeters()-toSubtract.valueInMeters());
    }
}
