package com.insurance.factory;

import com.insurance.model.*;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Factory class for creating insurance policies and claims.
 * Uses a standard switch on InsuranceType to instantiate the correct Policy subclass.
 *
 * <p>Design Pattern: Factory</p>
 */
public class InsuranceFactory {

    /**
     * Creates a new insurance policy of the specified type.
     * Generates a unique ID automatically.
     *
     * @param firstName customer first name
     * @param lastName  customer last name
     * @param date      policy date
     * @param remarks   additional remarks
     * @param type      the type of insurance to create
     * @return the created policy of the appropriate subclass
     */
    public static Policy createPolicy(String firstName, String lastName, LocalDate date,
                                      String remarks, InsuranceType type) {
        String id = generateId();

        switch (type) {
            case CAR:
                return new CarInsurance(id, firstName, lastName, date, remarks);
            case APARTMENT:
                return new ApartmentInsurance(id, firstName, lastName, date, remarks);
            case LIFE:
                return new LifeInsurance(id, firstName, lastName, date, remarks);
            case HEALTH:
                return new HealthInsurance(id, firstName, lastName, date, remarks);
            default:
                throw new IllegalArgumentException("Unknown insurance type: " + type);
        }
    }

    /**
     * Creates a new insurance claim.
     * Generates a unique ID automatically.
     *
     * @param policyId    the ID of the policy to claim against
     * @param description claim description
     * @param claimDate   date the claim is submitted
     * @return the created claim
     */
    public static Claim createClaim(String policyId, String description, LocalDate claimDate) {
        String id = generateId();
        return new Claim(id, policyId, description, claimDate);
    }

    /**
     * Generates a short unique identifier.
     *
     * @return an 8-character unique ID
     */
    private static String generateId() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}