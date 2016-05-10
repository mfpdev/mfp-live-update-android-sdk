package com.worklight.ibmmobilefirstplatformfoundationliveupdate.api;

/**
 * Created by ishaib on 09/05/16.
 */
public interface Configuration {
    public Boolean isFeatureEnabled (String featureName);
    public String getProperty (String propertyName);
}
