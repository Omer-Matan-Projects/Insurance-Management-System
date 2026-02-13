package com.insurance.model;

import java.time.LocalDate;

/**
 * Represents an apartment insurance policy.
 */
public class ApartmentInsurance extends Policy {

    /**
     * Creates a new apartment insurance policy.
     *
     * @param id        unique policy identifier
     * @param firstName customer first name
     * @param lastName  customer last name
     * @param date      policy date
     * @param remarks   additional remarks
     */
    public ApartmentInsurance(String id, String firstName, String lastName, LocalDate date, String remarks) {
        super(id, firstName, lastName, date, remarks);
    }

    @Override
    public InsuranceType getType() {
        return InsuranceType.APARTMENT;
    }
}