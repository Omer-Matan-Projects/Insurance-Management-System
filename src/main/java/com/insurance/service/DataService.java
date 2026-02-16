package com.insurance.service;

import com.insurance.data.InsuranceDataManager;
import com.insurance.model.Policy;

import java.util.List;

/**
 * Implementation of IDataService.
 * Provides read-only access to policy data for the UI layer.
 */
public class DataService implements IDataService {

    private final InsuranceDataManager dataManager;

    /**
     * Creates a new DataService.
     *
     * @param dataManager the data manager to delegate to
     */
    public DataService(InsuranceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public List<Policy> getAllPolicies() {
        return dataManager.getAllPolicies();
    }

    @Override
    public Policy getPolicyById(String policyId) {
        return dataManager.getPolicyById(policyId);
    }
}
