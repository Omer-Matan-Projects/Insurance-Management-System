package com.insurance.service;

import com.insurance.data.InsuranceDataManager;
import com.insurance.factory.InsuranceFactory;
import com.insurance.model.Claim;
import com.insurance.model.Policy;

import java.time.LocalDate;
import java.util.List;

/**
 * Implementation of IClaimService.
 * Handles claim submission and retrieval.
 */
public class ClaimService implements IClaimService {

    private final InsuranceDataManager dataManager;

    /**
     * Creates a new ClaimService.
     *
     * @param dataManager the data manager for persisting claims
     */
    public ClaimService(InsuranceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Claim submitClaim(String policyId, String description, LocalDate claimDate) {
        // Verify the policy exists
        Policy policy = dataManager.getPolicyById(policyId);
        if (policy == null) {
            throw new IllegalArgumentException("Policy not found: " + policyId);
        }

        // Create and save the claim
        Claim claim = InsuranceFactory.createClaim(policyId, description, claimDate);
        dataManager.saveClaim(claim);

        return claim;
    }

    @Override
    public List<Claim> getClaimsByPolicy(String policyId) {
        return dataManager.getClaimsByPolicyId(policyId);
    }

    @Override
    public List<Claim> getAllClaims() {
        return dataManager.getAllClaims();
    }
}
