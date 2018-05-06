package com.example.mobile.course.reviewmyplace.object;

import android.util.Log;

public enum EstablishmentType {
    NONE(-1),
    RESTAURANT(1),
    COFFEE_SHOP(2),
    BAR(3);

    private int code_value;

    EstablishmentType(int value) {
        this.code_value = code_value;
    }

    public int getValue() {
        return code_value;
    }

    public static EstablishmentType fromValue(int value)
            throws IllegalArgumentException {
        try {
            return EstablishmentType.values()[value];
        } catch(ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException("Unknown enum value :"+ value);
        }
    }

}
