package com.worklight.ibmmobilefirstplatformfoundationliveupdate.api;

import com.worklight.wlclient.api.WLFailResponse;

/**
 * ConfigurationListener interface
 * </p>
 * Listener to use when calling to obtainConfiguration
 *
 * @see com.worklight.ibmmobilefirstplatformfoundationliveupdate.LiveUpdateManager
 * @author Ishai Borovoy
 * @since 8.0.0
 */
public interface ConfigurationListener {
    /***
     * This method is called when succeed to obtain configuration from the server / cache
     *
     * @param configuration - the obtained configuration from server / cache
     * @see Configuration
     */
    void onSuccess(Configuration configuration);

    /***
     * This method is called when failed to obtain configuration from the server / cache
     *
     * @param wlFailResponse - the failure response object
     * @see  WLFailResponse
     */
    void onFailure(WLFailResponse wlFailResponse);
}
