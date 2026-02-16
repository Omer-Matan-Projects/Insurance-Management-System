package com.insurance.service;

import com.insurance.model.Claim;

import java.time.LocalDate;
import java.util.List;

/**
 * Service interface for managing insurance claims.
 */
public interface IClaimService {

    /**
     * Submits a new claim for an existing policy.
     *
     * @param policyId    the ID of the policy to claim against
     * @param description claim description
     * @param claimDate   date the claim is submitted
     * @return the created claim
     */
    Claim submitClaim(String policyId, String description, LocalDate claimDate);

    /**
     * Returns all claims associated with a specific policy.
     *
     * @param policyId the policy ID to filter by
     * @return list of claims for that policy
     */
    List<Claim> getClaimsByPolicy(String policyId);



}