package com.insurance.config;

import com.google.gson.Gson;
import com.insurance.model.InsuranceType;
import com.insurance.service.IConfigService;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton service that loads application configuration from input.json.
 *
 * <p>Design Pattern: Singleton</p>
 */
public class ConfigService implements IConfigService {

    private static ConfigService instance;
    private static final String CONFIG_FILE = "input.json";

    private String companyName;
    private String appVersion;
    private String developerNames;
    private List<InsuranceType> availableTypes;

    /** Private constructor - loads configuration from file. */
    private ConfigService() {
        loadConfig();
    }

    /**
     * Returns the single instance of ConfigService.
     *
     * @return the ConfigService instance
     */
    public static synchronized ConfigService getInstance() {
        if (instance == null) {
            instance = new ConfigService();
        }
        return instance;
    }

    /**
     * Loads configuration from the input.json file.
     * Falls back to default values if the file is missing or invalid.
     */
    private void loadConfig() {
        try (FileReader reader = new FileReader(CONFIG_FILE)) {
            Gson gson = new Gson();
            ConfigData data = gson.fromJson(reader, ConfigData.class);

            this.companyName = data.companyName;
            this.appVersion = data.appVersion;
            this.developerNames = data.developerNames;

            this.availableTypes = new ArrayList<>();
            for (String typeName : data.insuranceTypes) {
                availableTypes.add(InsuranceType.valueOf(typeName));
            }
        } catch (IOException e) {
            System.err.println("Could not load config file: " + e.getMessage());
            setDefaults();
        }
    }

    /** Sets default configuration values as fallback. */
    private void setDefaults() {
        this.companyName = "Insurance App";
        this.appVersion = "Version 1.0";
        this.developerNames = "Unknown";
        this.availableTypes = List.of(InsuranceType.values());
    }

    @Override
    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String getAppVersion() {
        return appVersion;
    }

    @Override
    public String getDeveloperNames() {
        return developerNames;
    }

    /**
     * Returns the list of available insurance types loaded from configuration.
     *
     * @return list of available insurance types
     */
    public List<InsuranceType> getAvailableInsuranceTypes() {
        return availableTypes;
    }

    /**
     * Inner class that maps directly to the input.json structure.
     */
    private static class ConfigData {
        String companyName;
        String appVersion;
        String developerNames;
        List<String> insuranceTypes;
    }
}