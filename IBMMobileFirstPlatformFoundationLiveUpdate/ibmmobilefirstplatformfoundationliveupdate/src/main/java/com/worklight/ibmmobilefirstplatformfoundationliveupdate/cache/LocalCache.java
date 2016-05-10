package com.worklight.ibmmobilefirstplatformfoundationliveupdate.cache;

import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;

/**
 * Created by ishaib on 09/05/16.
 */
public class LocalCache {

    public synchronized static void saveConfiguration(Configuration configuration) {
        CacheFileManager.save (configuration);
    }

    public synchronized static Configuration getConfiguration(String configurationId) {
        return CacheFileManager.isExpired(configurationId) ? null : CacheFileManager.configuration(configurationId);
    }
}
