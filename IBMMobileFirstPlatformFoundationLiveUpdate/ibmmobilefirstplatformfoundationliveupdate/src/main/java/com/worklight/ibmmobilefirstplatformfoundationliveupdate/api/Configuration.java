package com.worklight.ibmmobilefirstplatformfoundationliveupdate.api;

/**
 * Created by ishaib on 09/05/16.
 */
public interface Configuration {
    /**
     * Check if a feature is enabled
     * @param featureName - the feature name to be checked
     * @return true if feature is enabled
     */
    public Boolean isFeatureEnabled (String featureName);


    /**
     * Get value of a property
     * @param propertyName -  the property name
     * @return the property value
     */
    public String getProperty (String propertyName);
}
