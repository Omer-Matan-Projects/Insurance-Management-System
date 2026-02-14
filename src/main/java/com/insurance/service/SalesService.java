package com.insurance.service;

import com.insurance.config.ConfigService;
import com.insurance.data.InsuranceDataManager;
import com.insurance.factory.InsuranceFactory;
import com.insurance.logger.AppLogger;
import com.insurance.model.InsuranceType;
import com.insurance.model.Policy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of ISalesService.
 * Handles policy creation, deletion, logging, and observer notifications.
 *
 * <p>Design Pattern: Observer (this class is the Subject)</p>
 */
public class SalesService implements ISalesService {

    private final InsuranceDataManager dataManager;
    private final List<PolicyObserver> observers = new ArrayList<>();
    private static final DateTimeFormatter LOG_DATE_FORMAT = DateTimeFormatter.ofPattern("d.M.yyyy");

    /**
     * Creates a new SalesService.
     *
     * @param dataManager the data manager for persisting policies
     */
    public SalesService(InsuranceDataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public Policy createPolicy(String firstName, String lastName, LocalDate date,
                               String remarks, InsuranceType type) {
        // Create the policy using the factory
        Policy policy = InsuranceFactory.createPolicy(firstName, lastName, date, remarks, type);

        // Save to data file
        dataManager.savePolicy(policy);

        // Log the transaction
        String formattedDate = date.format(LOG_DATE_FORMAT);
        AppLogger.getInstance().log(firstName, lastName, formattedDate, remarks, type.getDisplayName());

        // Notify all registered observers
        notifyObservers(policy);

        return policy;
    }

    @Override
    public boolean deletePolicy(String policyId) {
        boolean deleted = dataManager.deletePolicy(policyId);

        if (deleted) {
            // Log the deletion
            String formattedDate = LocalDate.now().format(LOG_DATE_FORMAT);
            AppLogger.getInstance().log("SYSTEM", "", formattedDate,
                    "Policy deleted: " + policyId, "DELETION");

            // Notify all registered observers
            notifyObserversDeleted(policyId);
        }

        return deleted;
    }

    @Override
    public List<InsuranceType> getAvailableInsuranceTypes() {
        return ConfigService.getInstance().getAvailableInsuranceTypes();
    }

    @Override
    public void addPolicyObserver(PolicyObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removePolicyObserver(PolicyObserver observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all registered observers that a new policy was created.
     *
     * @param policy the newly created policy
     */
    private void notifyObservers(Policy policy) {
        for (PolicyObserver observer : observers) {
            observer.onPolicyCreated(policy);
        }
    }

    /**
     * Notifies all registered observers that a policy was deleted.
     *
     * @param policyId the ID of the deleted policy
     */
    private void notifyObserversDeleted(String policyId) {
        for (PolicyObserver observer : observers) {
            observer.onPolicyDeleted(policyId);
        }
    }
}