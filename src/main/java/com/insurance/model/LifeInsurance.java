package com.insurance.model;

import java.time.LocalDate;

/**
 * Represents a life insurance policy.
 */
public class LifeInsurance extends Policy {

    /**
     * Creates a new life insurance policy.
     *
     * @param id        unique policy identifier
     * @param firstName customer first name
     * @param lastName  customer last name
     * @param date      policy date
     * @param remarks   additional remarks
     */
    public LifeInsurance(String id, String firstName, String lastName, LocalDate date, String remarks) {
        super(id, firstName, lastName, date, remarks);
    }

    @Override
    public InsuranceType getType() {
        return InsuranceType.LIFE;
    }
}