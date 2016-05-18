package com.worklight.ibmmobilefirstplatformfoundationliveupdate.api;

/**
 * Configuration interface
 * <p/>
 * This interface provides API for an obtained configuration.
 * This API let check whether a feature is enabled or getting value for a property.
 * @author Ishai Borovoy
 * @since 8.0.0
 */
public interface Configuration {
    /**
     * Check if a feature is enabled
     * @param featureName - the feature name to be checked
     * @return true if feature is enabled and null for non existing feature.
     */
    public Boolean isFeatureEnabled (String featureName);


    /**
     * Get value of a property
     * @param propertyName -  the property name
     * @return the value for the given propertyName, or null in case the property is not exist
     */
    public String getProperty (String propertyName);
}
