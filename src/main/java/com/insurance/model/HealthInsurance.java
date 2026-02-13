package com.insurance.model;

import java.time.LocalDate;

/**
 * Represents a health insurance policy.
 */
public class HealthInsurance extends Policy {

    /**
     * Creates a new health insurance policy.
     *
     * @param id        unique policy identifier
     * @param firstName customer first name
     * @param lastName  customer last name
     * @param date      policy date
     * @param remarks   additional remarks
     */
    public HealthInsurance(String id, String firstName, String lastName, LocalDate date, String remarks) {
        super(id, firstName, lastName, date, remarks);
    }

    @Override
    public InsuranceType getType() {
        return InsuranceType.HEALTH;
    }
}