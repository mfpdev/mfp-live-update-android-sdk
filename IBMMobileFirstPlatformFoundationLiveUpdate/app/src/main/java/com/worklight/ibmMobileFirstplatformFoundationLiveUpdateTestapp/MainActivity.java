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
package com.worklight.ibmMobileFirstplatformFoundationLiveUpdateTestapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.worklight.common.Logger;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.LiveUpdateManager;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.ConfigurationListener;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Test Application for LiveUpdate
 *
 * @author Ishai Borovoy
 * @since 8.0.0
 */
public class MainActivity extends AppCompatActivity {
    private EditText segmentEditText;
    private EditText paramsEditText;
    private EditText featureEditText;
    private EditText propertyEditText;
    private CheckBox useCacheCheckBox;

    private TextView propertyValue;
    private TextView featureValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        segmentEditText = (EditText) this.findViewById(R.id.segmentEditText);
        paramsEditText = (EditText) this.findViewById(R.id.paramsEditText);
        featureEditText = (EditText) this.findViewById(R.id.featureEditText);
        propertyEditText = (EditText) this.findViewById(R.id.propertyEditText);
        useCacheCheckBox = (CheckBox) this.findViewById(R.id.useCacheCheckBox);
        featureValue = (TextView) this.findViewById(R.id.featureValue);
        propertyValue = (TextView) this.findViewById(R.id.propertyValue);

        WLClient.createInstance(this);
        Logger.setContext(this);
    }

    public void obtainConfiguration (View view) {
        String segment = segmentEditText.getText().toString();
        boolean useCache = useCacheCheckBox.isChecked();
        final String feature =  featureEditText.getText().toString();
        final String property = propertyEditText.getText().toString();

        propertyValue.setText("");
        featureValue.setText("");

        if (feature.trim().equals("") || property.trim().equals("")) {
            Log.e("obtainConfiguration", "Feature and property is mandatory fields");
            return;
        }

        //Obtain configuration by segment
        if (!segment.trim().equals("")) {
            LiveUpdateManager.getInstance().obtainConfiguration(segment, useCache, new ConfigurationListener() {
                @Override
                public void onSuccess(final Configuration configuration) {
                    updateResults(configuration, property, feature);
                }

                @Override
                public void onFailure(WLFailResponse wlFailResponse) {
                    MainActivity.this.onFailure(wlFailResponse);
                }
            });
        } else {
            Map<String, String> params = getParams();
            //Obtain configuration by params
            LiveUpdateManager.getInstance().obtainConfiguration(params, useCache, new ConfigurationListener() {
                @Override
                public void onSuccess(final Configuration configuration) {
                    updateResults(configuration, property, feature);
                }

                @Override
                public void onFailure(WLFailResponse wlFailResponse) {
                    MainActivity.this.onFailure(wlFailResponse);
                }
            });
        }
    }

    private void updateResults(final Configuration configuration, final String property, final String feature) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                propertyValue.setText(configuration.getProperty(property));
                Boolean isFeatureEnabled = configuration.isFeatureEnabled(feature);
                if (isFeatureEnabled != null) {
                    featureValue.setText(configuration.isFeatureEnabled(feature).toString());
                }
            }
        });
    }

    @NonNull
    private Map<String, String> getParams() {
        Map<String,String> params = new HashMap<>();
        for (String paramPair : paramsEditText.getText().toString().split(",")) {
            String [] paramPairArray = paramPair.split(":");
            if (paramPairArray.length > 1) {
                params.put(paramPairArray[0], paramPairArray[1]);
            }
        }
        return params;
    }


    private void onFailure(WLFailResponse wlFailResponse) {
        Log.e("obtainConfiguration", wlFailResponse.getErrorMsg());
    }
}
