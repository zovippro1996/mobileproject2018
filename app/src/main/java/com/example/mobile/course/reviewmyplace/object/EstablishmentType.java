package com.example.mobile.course.reviewmyplace.object;

public enum EstablishmentType {
    NONE(-1),
    RESTAURANT(1),
    COFFEE_SHOP(2),
    BAR(3);

    private int value;

    private EstablishmentType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
