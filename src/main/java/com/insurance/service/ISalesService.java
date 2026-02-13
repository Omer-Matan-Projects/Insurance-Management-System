package com.insurance.service;

import com.insurance.model.InsuranceType;
import com.insurance.model.Policy;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for selling insurance policies.
 */
public interface ISalesService {

    /**
     * Creates and saves a new insurance policy.
     *
     * @param firstName customer first name
     * @param lastName  customer last name
     * @param date      policy date
     * @param remarks   additional remarks
     * @param type      the type of insurance
     * @return the created policy
     */
    Policy createPolicy(String firstName, String lastName, LocalDate date, String remarks, InsuranceType type);

    /**
     * Returns the list of available insurance types.
     *
     * @return list of insurance types
     */
    List<InsuranceType> getAvailableInsuranceTypes();

    /**
     * Registers an observer to be notified when a policy is created.
     *
     * @param observer the observer to register
     */
    void addPolicyObserver(PolicyObserver observer);

    /**
     * Unregisters a previously registered observer.
     *
     * @param observer the observer to remove
     */
    void removePolicyObserver(PolicyObserver observer);
}