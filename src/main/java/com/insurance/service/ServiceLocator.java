package com.insurance.service;

import com.insurance.config.ConfigService;
import com.insurance.data.InsuranceDataManager;

/**
 * Central access point for all application services.
 * Must be initialized once at application startup before any service is used.
 */
public class ServiceLocator {

    private static ISalesService salesService;
    private static IClaimService claimService;
    private static IDataService dataService;
    private static IConfigService configService;

    /** Private constructor to prevent instantiation. */
    private ServiceLocator() {
    }

    /**
     * Initializes all application services.
     * Must be called once at application startup.
     */
    public static void initialize() {
        InsuranceDataManager dataManager = new InsuranceDataManager();

        configService = ConfigService.getInstance();
        salesService = new SalesService(dataManager);
        claimService = new ClaimService(dataManager);
        dataService = new DataService(dataManager);
    }

    /**
     * Returns the sales service for creating policies.
     *
     * @return the sales service
     */
    public static ISalesService getSalesService() {
        return salesService;
    }

    /**
     * Returns the claim service for managing claims.
     *
     * @return the claim service
     */
    public static IClaimService getClaimService() {
        return claimService;
    }

    /**
     * Returns the data service for reading policy data.
     *
     * @return the data service
     */
    public static IDataService getDataService() {
        return dataService;
    }

    /**
     * Returns the config service for application settings.
     *
     * @return the config service
     */
    public static IConfigService getConfigService() {
        return configService;
    }
}