package com.insurance.service;

import com.insurance.model.Policy;

/**
 * Observer interface for policy creation events.
 * Classes that want to be notified when a new policy is created
 * should implement this interface.
 */
public interface PolicyObserver {

    /**
     * Called when a new policy is created.
     * @param policy the newly created policy
     */
    void onPolicyCreated(Policy policy);
}