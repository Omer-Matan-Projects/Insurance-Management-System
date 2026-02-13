package com.insurance.model;

/**
 * Represents the available types of insurance policies.
 */
public enum InsuranceType {

    CAR("Car"),
    APARTMENT("Apartment"),
    LIFE("Life"),
    HEALTH("Health");

    private final String displayName;

    InsuranceType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Returns the display name for this insurance type.
     *
     * @return the display name
     */
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}