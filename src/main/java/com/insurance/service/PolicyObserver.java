package com.insurance.service;

import com.insurance.model.Policy;

/**
 * Observer interface for policy lifecycle events.
 * Classes that want to be notified when policies are created or deleted
 * should implement this interface.
 */
public interface PolicyObserver {

    /**
     * Called when a new policy is created.
     * @param policy the newly created policy
     */
    void onPolicyCreated(Policy policy);

    /**
     * Called when a policy is deleted.
     * @param policyId the ID of the deleted policy
     */
    void onPolicyDeleted(String policyId);
}