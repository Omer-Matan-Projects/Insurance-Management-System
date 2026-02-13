package com.insurance.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.insurance.model.Claim;
import com.insurance.model.Policy;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Manages reading and writing insurance data to a JSON file.
 * Handles persistence for both policies and claims.
 */
public class InsuranceDataManager {

    private static final String DATA_FILE = "data/purchases.json";
    private final Gson gson;

    /**
     * Creates a new data manager with Gson configured for Policy and LocalDate handling.
     */
    public InsuranceDataManager() {
        this.gson = new GsonBuilder()
                .registerTypeAdapter(Policy.class, new PolicyAdapter())
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
    }

    /**
     * Returns all saved policies.
     *
     * @return list of all policies
     */
    public List<Policy> getAllPolicies() {
        return loadData().policies;
    }

    /**
     * Returns a specific policy by its ID.
     *
     * @param id the policy ID to search for
     * @return the matching policy, or null if not found
     */
    public Policy getPolicyById(String id) {
        for (Policy policy : loadData().policies) {
            if (policy.getId().equals(id)) {
                return policy;
            }
        }
        return null;
    }

    /**
     * Searches policies by customer name (partial match, case-insensitive).
     *
     * @param name the name to search for
     * @return list of matching policies
     */
    public List<Policy> searchPoliciesByName(String name) {
        String lowerName = name.toLowerCase();
        return loadData().policies.stream()
                .filter(p -> p.getFirstName().toLowerCase().contains(lowerName)
                        || p.getLastName().toLowerCase().contains(lowerName))
                .collect(Collectors.toList());
    }

    /**
     * Saves a new policy to the data file.
     *
     * @param policy the policy to save
     */
    public void savePolicy(Policy policy) {
        DataStore data = loadData();
        data.policies.add(policy);
        saveData(data);
    }

    /**
     * Returns all saved claims.
     *
     * @return list of all claims
     */
    public List<Claim> getAllClaims() {
        return loadData().claims;
    }

    /**
     * Returns all claims associated with a specific policy.
     *
     * @param policyId the policy ID to filter by
     * @return list of claims for that policy
     */
    public List<Claim> getClaimsByPolicyId(String policyId) {
        return loadData().claims.stream()
                .filter(c -> c.getPolicyId().equals(policyId))
                .collect(Collectors.toList());
    }

    /**
     * Saves a new claim to the data file.
     *
     * @param claim the claim to save
     */
    public void saveClaim(Claim claim) {
        DataStore data = loadData();
        data.claims.add(claim);
        saveData(data);
    }

    /**
     * Loads data from the JSON file.
     * Returns an empty DataStore if the file does not exist or is invalid.
     */
    private DataStore loadData() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return new DataStore();
        }
        try (FileReader reader = new FileReader(file)) {
            DataStore data = gson.fromJson(reader, DataStore.class);
            return data != null ? data : new DataStore();
        } catch (IOException e) {
            System.err.println("Failed to load data file: " + e.getMessage());
            return new DataStore();
        }
    }

    /**
     * Saves data to the JSON file. Creates the data directory if needed.
     */
    private void saveData(DataStore data) {
        File file = new File(DATA_FILE);
        file.getParentFile().mkdirs();
        try (FileWriter writer = new FileWriter(file)) {
            gson.toJson(data, writer);
        } catch (IOException e) {
            System.err.println("Failed to save data file: " + e.getMessage());
        }
    }

    /**
     * Internal class representing the structure of the JSON data file.
     */
    private static class DataStore {
        List<Policy> policies = new ArrayList<>();
        List<Claim> claims = new ArrayList<>();
    }
}