package com.insurance.service;

/**
 * Service interface for accessing application configuration.
 */
public interface IConfigService {

    /**
     * Returns the company name for UI display.
     *
     * @return the company name
     */
    String getCompanyName();

    /**
     * Returns the application version string.
     *
     * @return the version string
     */
    String getAppVersion();

    /**
     * Returns the developer names for UI display.
     *
     * @return the developer names
     */
    String getDeveloperNames();
}