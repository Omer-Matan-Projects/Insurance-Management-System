package com.insurance.model;

import java.time.LocalDate;

/**
 * Represents an insurance claim submitted against a policy.
 */
public class Claim {

    private final String id;
    private final String policyId;
    private final String description;
    private final LocalDate claimDate;

    /**
     * Creates a new claim.
     *
     * @param id          unique claim identifier
     * @param policyId    the ID of the associated policy
     * @param description claim description
     * @param claimDate   date the claim was submitted
     */
    public Claim(String id, String policyId, String description, LocalDate claimDate) {
        this.id = id;
        this.policyId = policyId;
        this.description = description;
        this.claimDate = claimDate;
    }

    /** @return unique claim identifier */
    public String getId() {
        return id;
    }

    /** @return the ID of the associated policy */
    public String getPolicyId() {
        return policyId;
    }

    /** @return claim description */
    public String getDescription() {
        return description;
    }

    /** @return date the claim was submitted */
    public LocalDate getClaimDate() {
        return claimDate;
    }
}