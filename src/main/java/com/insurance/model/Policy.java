package com.insurance.model;

import java.time.LocalDate;

/**
 * Abstract base class for all insurance policies.
 * Each concrete subclass represents a specific type of insurance.
 */
public abstract class Policy {

    private final String id;
    private final String firstName;
    private final String lastName;
    private final LocalDate date;
    private final String remarks;

    /**
     * Creates a new policy with the given details.
     *
     * @param id        unique policy identifier
     * @param firstName customer first name
     * @param lastName  customer last name
     * @param date      policy date
     * @param remarks   additional remarks
     */
    public Policy(String id, String firstName, String lastName, LocalDate date, String remarks) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.date = date;
        this.remarks = remarks;
    }

    /**
     * Returns the type of insurance for this policy.
     *
     * @return the insurance type
     */
    public abstract InsuranceType getType();

    /** @return unique policy identifier */
    public String getId() {
        return id;
    }

    /** @return customer first name */
    public String getFirstName() {
        return firstName;
    }

    /** @return customer last name */
    public String getLastName() {
        return lastName;
    }

    /** @return policy date */
    public LocalDate getDate() {
        return date;
    }

    /** @return additional remarks */
    public String getRemarks() {
        return remarks;
    }
}