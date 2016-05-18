package com.worklight.ibmmobilefirstplatformfoundationliveupdate.cache;

import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;

/**
 * CacheFileManager
 * a facade class to the CacheFileManager
 *
 * @since 8.0.0
 * @author Ishai Borovoy
 * @see CacheFileManager
 */
public class LocalCache {

    public synchronized static void saveConfiguration(Configuration configuration) {
        CacheFileManager.save (configuration);
    }

    public synchronized static Configuration getConfiguration(String configurationId) {
        return CacheFileManager.isExpired(configurationId) ? null : CacheFileManager.configuration(configurationId);
    }
}
