/**
 *   © Copyright 2016 IBM Corp.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
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
     * @param featureId - the feature name to be checked
     * @return true if feature is enabled and null for non existing feature.
     */
    public Boolean isFeatureEnabled (String featureId);


    /**
     * Get value of a property
     * @param propertyId -  the property name
     * @return the value for the given propertyName, or null in case the property is not exist
     */
    public String getProperty (String propertyId);
}
