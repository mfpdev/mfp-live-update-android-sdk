package com.worklight.ibmmobilefirstplatformfoundationliveupdate.api;

import com.worklight.wlclient.api.WLFailResponse;

/**
 * Created by ishaib on 09/05/16.
 */
public interface ConfigurationListener {
    void onSuccess(Configuration configuration);

    void onFailure(WLFailResponse var1);
}
