package com.insurance.config;

import com.google.gson.Gson;
import com.insurance.service.IConfigService;

import java.io.FileReader;
import java.io.IOException;

/**
 * Singleton service that loads application configuration from input.json.
 *
 * Design Pattern: Singleton
 */
public class ConfigService implements IConfigService {

    private static ConfigService instance;
    private static final String CONFIG_FILE = "input.json";

    private String companyName;
    private String appVersion;
    private String developerNames;

    /** Private constructor - loads configuration from file. */
    private ConfigService() {
        loadConfig();
    }

    /**
     * Returns the single instance of ConfigService.
     *
     * @return the ConfigService instance
     */
    public static ConfigService getInstance() {
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
     * Inner class that maps directly to the input.json structure.
     */
    private static class ConfigData {
        String companyName;
        String appVersion;
        String developerNames;
    }
}