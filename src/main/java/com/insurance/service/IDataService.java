package com.insurance.service;

import com.insurance.model.Policy;

import java.util.List;

/**
 * Service interface for reading policy data.
 */
public interface IDataService {

    /**
     * Returns all saved policies.
     *
     * @return list of all policies
     */
    List<Policy> getAllPolicies();

    /**
     * Returns a specific policy by its ID.
     *
     * @param policyId the policy ID to search for
     * @return the matching policy, or null if not found
     */
    Policy getPolicyById(String policyId);

    /**
     * Searches policies by customer name (partial match supported).
     *
     * @param name the name to search for
     * @return list of matching policies
     */
    List<Policy> searchPoliciesByName(String name);
}