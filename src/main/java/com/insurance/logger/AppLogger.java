package com.insurance.logger;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Singleton logger that records insurance purchase transactions
 * to a comma-delimited text file (log.txt).
 *
 * Log format: Name,FamilyName,Date,Remarks,InsuranceType
 *
 * Design Pattern: Singleton
 */
public class AppLogger {

    private static AppLogger instance;
    private static final String LOG_FILE = "log.txt";

    /** Private constructor to prevent external instantiation. */
    private AppLogger() {
    }

    /**
     * Returns the single instance of AppLogger.
     *
     * @return the AppLogger instance
     */
    public static AppLogger getInstance() {
        if (instance == null) {
            instance = new AppLogger();
        }
        return instance;
    }

    /**
     * Logs a purchase transaction to log.txt in comma-delimited format.
     *
     * @param name          customer first name
     * @param familyName    customer last name
     * @param date          policy date in d.M.yyyy format
     * @param remarks       additional remarks
     * @param insuranceType the type of insurance
     */
    public void log(String name, String familyName, String date, String remarks, String insuranceType) {
        String line = name + "," + familyName + "," + date + "," + remarks + "," + insuranceType;
        try (PrintWriter writer = new PrintWriter(new FileWriter(LOG_FILE, true))) {
            writer.println(line);
        } catch (IOException e) {
            System.err.println("Failed to write to log file: " + e.getMessage());
        }
    }
}