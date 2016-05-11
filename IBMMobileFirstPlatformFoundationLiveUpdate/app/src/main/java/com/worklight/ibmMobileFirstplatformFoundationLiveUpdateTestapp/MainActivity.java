package com.worklight.ibmMobileFirstplatformFoundationLiveUpdateTestapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.worklight.common.Logger;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.LiveUpdateManager;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.ConfigurationListener;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        WLClient.createInstance(this);
        Logger.setContext(this);
    }

    public void obtainConfiguration (View view) {
        LiveUpdateManager.getInstance().obtainConfiguration("ishai", new ConfigurationListener() {
            @Override
            public void onSuccess(Configuration configuration) {
                Log.e("YYY", configuration.getProperty("ishai"));
                Log.e("YYY", configuration.isFeatureEnabled("ishai").toString());
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                Log.e("XXX", wlFailResponse.getErrorMsg());
                Log.e("XXX", wlFailResponse.getErrorCode().getDescription());
            }
        });
    }
}
