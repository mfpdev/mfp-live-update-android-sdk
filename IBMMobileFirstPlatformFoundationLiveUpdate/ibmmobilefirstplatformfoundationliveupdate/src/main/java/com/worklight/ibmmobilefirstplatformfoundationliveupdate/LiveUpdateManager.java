package com.worklight.ibmmobilefirstplatformfoundationliveupdate;

import com.worklight.common.Logger;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.ConfigurationListener;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.cache.LocalCache;
import com.worklight.wlclient.api.WLFailResponse;
import com.worklight.wlclient.api.WLResourceRequest;
import com.worklight.wlclient.api.WLResponse;
import com.worklight.wlclient.api.WLResponseListener;

import org.json.JSONObject;

import java.net.URI;
import java.util.Map;

/**
 * Created by ishaib on 09/05/16.
 */

public class LiveUpdateManager {

    private static LiveUpdateManager instance = new LiveUpdateManager();
    private final static String CONFIGURATION_SCOPE = "configuration-user-login";
    private final String SERVICE_URL = "adapters/liveUpdateAdapter/configuration";

    private static final Logger logger = Logger.getInstance(LiveUpdateManager.class.getName());
    /**
     * getInstance
     * @return LiveUpdateManager instance
     */
    public static LiveUpdateManager getInstance() {
        return instance;
    }

    private LiveUpdateManager() {
    }

    /**
     * obtainConfiguration
     * @param segment
     * @param configurationListener
     */
    public void obtainConfiguration (String segment, ConfigurationListener configurationListener) {
        this.obtainConfiguration(segment, true, configurationListener);
    }

    /**
     * obtainConfiguration
     * @param params
     * @param configurationListener
     */
    public void obtainConfiguration (Map<String,String> params, ConfigurationListener configurationListener) {
        this.obtainConfiguration(params, true, configurationListener);
    }

    /**
     * obtainConfiguration
     * @param segment
     * @param useCache
     * @param configurationListener
     */
    public void obtainConfiguration (String segment, boolean useCache, ConfigurationListener configurationListener) {
        URI url = URI.create(SERVICE_URL + "/" + segment);


        logger.debug("obtainConfiguration: segment = " + segment + ", useCache = " + useCache + ", url = " + url);
        this.obtainConfiguration(segment, url, null, useCache, configurationListener);
    }

    /**
     * obtainConfiguration
     * @param params
     * @param useCache
     * @param configurationListener
     */
    public void obtainConfiguration (Map<String,String> params, boolean useCache, ConfigurationListener configurationListener) {
        URI url = URI.create(SERVICE_URL);
        String id = buildIDFromParams(params);

        logger.debug("obtainConfiguration: params = " + params + ", useCache = " + useCache + ", url = " + url);
        this.obtainConfiguration(id, url, params, useCache, configurationListener);
    }


    private void obtainConfiguration (String id, URI url, Map<String,String> params, boolean useCache, final ConfigurationListener configurationListener) {
        Configuration cachedConfiguration = LocalCache.getConfiguration(id);

        if (cachedConfiguration != null && useCache) {
            logger.debug("obtainConfiguration: Retrieved cached configuration. configuration = " + cachedConfiguration);
            configurationListener.onSuccess(cachedConfiguration);
        } else {
            sendConfigRequest(id, url, params, configurationListener);
        }
    }

    private void sendConfigRequest(final String id, URI url, Map<String,String> params, final ConfigurationListener configurationListener) {
        WLResourceRequest configurationServiceRequest = new WLResourceRequest(url, WLResourceRequest.GET, CONFIGURATION_SCOPE);

        logger.trace("sendConfigRequest: id = " + id + ", url = " + url + "params = " + params);

        if (params != null) {
            for (String paramKey : params.keySet()) {
                configurationServiceRequest.setQueryParameter(paramKey, params.get(paramKey));
            }

        }

        configurationServiceRequest.send(new WLResponseListener() {
            @Override
            public void onSuccess(WLResponse wlResponse) {
                JSONObject json = wlResponse.getResponseJSON();

                if (json == null) {
                    logger.error("sendConfigRequest: invalid JSON response");
                    json = new JSONObject();
                }
                Configuration configuration = new ConfigurationInstance(id, json);
                // Save to cache

                logger.trace("sendConfigRequest: saving configuration to cache. configuration = " +configuration);
                LocalCache.saveConfiguration(configuration);
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                logger.error("sendConfigRequest: error while retriving configuration from server. error = " + wlFailResponse.getErrorMsg());
                configurationListener.onFailure(wlFailResponse);
            }
        });
    }

    private String buildIDFromParams (Map<String,String> params) {
        logger.trace("buildIDFromParams: params = " + params);
        String paramsId = "";
        if (params != null && params.size() > 0) {
            for (String paramKey : params.keySet()) {
                paramsId += "_" + paramKey + "" + params.get(paramKey);
            }
        }
        logger.trace("buildIDFromParams: paramsId = " + paramsId);
        return paramsId;
    }
}
