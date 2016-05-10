package com.worklight.ibmmobilefirstplatformfoundationliveupdate;

import com.worklight.common.Logger;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ishaib on 09/05/16.
 */
public class ConfigurationInstance implements Configuration{
    private static final Logger logger = Logger.getInstance(ConfigurationInstance.class.getName());
    
    public static final String FEATURES_KEY = "features";
    public static final String PROPERTIES_KEY = "properties";
    public static final String DATA_KEY = "data";

    private JSONObject data;
    private String id;

    public JSONObject getData() {
        return data;
    }

    public String getId() {
        return id;
    }

    public ConfigurationInstance(String id, JSONObject data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public Boolean isFeatureEnabled(String featureName) {
        Boolean isFeatureEnabled = null;
        try {
            isFeatureEnabled =  this.data.getJSONObject(DATA_KEY).getJSONObject(FEATURES_KEY).getBoolean(featureName);
        } catch (JSONException e) {
            logger.error("isFeatureEnabled: Cannot get feature " + featureName);
        }
        return isFeatureEnabled;
    }

    @Override
    public String getProperty(String propertyName) {
        String property = null;
        try {
            property =  this.data.getJSONObject(DATA_KEY).getJSONObject(PROPERTIES_KEY).getString(propertyName);
        } catch (JSONException e) {
            logger.error("getProperty: Cannot get property " + propertyName);
        }
        return property;
    }

    @Override
    public String toString() {
        return "ConfigurationInstance{" +
                "data=" + data +
                ", id='" + id + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationInstance that = (ConfigurationInstance) o;

        if (data != null ? !data.equals(that.data) : that.data != null) return false;
        return id != null ? id.equals(that.id) : that.id == null;
    }

    @Override
    public int hashCode() {
        int result = data != null ? data.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        return result;
    }
}
