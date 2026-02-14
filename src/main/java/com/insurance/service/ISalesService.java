package com.insurance.service;

import com.insurance.model.InsuranceType;
import com.insurance.model.Policy;

import java.time.LocalDate;

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
     * Deletes a policy by its ID.
     *
     * @param policyId the ID of the policy to delete
     * @return true if the policy was found and deleted, false otherwise
     */
    boolean deletePolicy(String policyId);

    /**
     * Registers an observer to be notified when a policy is created or deleted.
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