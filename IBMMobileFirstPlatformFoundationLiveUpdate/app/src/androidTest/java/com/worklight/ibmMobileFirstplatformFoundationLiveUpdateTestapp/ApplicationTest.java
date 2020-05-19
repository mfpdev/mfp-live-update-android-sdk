/**
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

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;
import android.util.Log;

import com.worklight.common.Logger;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.LiveUpdateManager;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.ConfigurationListener;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    Context context;

    Configuration [] configs;
    WLFailResponse testFailResponse;

    public void setUp() throws Exception {
        super.setUp();
        context = getContext();
        assertNotNull(context);

        WLClient.createInstance(context);
        Logger.setContext(context);
        configs = new Configuration[4];
        testFailResponse = null;
    }

    private void assertConfig1 () {
        assertEquals(configs[0].getProperty("property1"), "value1");
        assertEquals(configs[0].getProperty("property2"), "value2");
        assertEquals(configs[0].isFeatureEnabled("feature1"), Boolean.TRUE);
        assertEquals(configs[0].isFeatureEnabled("feature2"), Boolean.FALSE);

        assertNull(configs[0].getProperty("notExistProperty"));
        assertNull(configs[0].getProperty("featureNotExist"));
    }

    private void assertConfig2 () {
        assertEquals(configs[1].getProperty("property1"), "value11");
        assertEquals(configs[1].getProperty("property2"), "value22");
        assertEquals(configs[1].isFeatureEnabled("feature1"), Boolean.FALSE);
        assertEquals(configs[1].isFeatureEnabled("feature2"), Boolean.TRUE);

        assertNull(configs[1].getProperty("notExistProperty"));
        assertNull(configs[1].getProperty("featureNotExist"));
    }

    private void assertConfig3 () {
        assertEquals(configs[2].getProperty("property1"), "Çàâｱｲｳ");
        assertEquals(configs[2].getProperty("property2"), "ÇàâｱｲｳÇàâｱｲｳ");
        assertEquals(configs[2].isFeatureEnabled("feature1"), Boolean.TRUE);
        assertEquals(configs[2].isFeatureEnabled("feature2"), Boolean.TRUE);
    }

    private void assertConfig4 () {
        assertEquals(configs[3].getProperty("ÇàâｱｲｳÇàâｱ ｲｳ"), "ÇàâｱｲｳÇàâｱ ｲｳ ÇàâｱｲｳÇàâｱ ｲｳ");
        assertEquals(configs[3].isFeatureEnabled("Çàâｱｲ ｳÇàâｱｲｳ"), Boolean.TRUE);
    }

    public void testObtainConfigurationWithParams() throws Exception {
        final Thread currentThread = Thread.currentThread();
        Map <String, String>params = new HashMap();
        params.put("param1", "value1");

        LiveUpdateManager.getInstance().obtainConfiguration(params, false, new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                configs[1] = configuration;
                currentThread.interrupt();
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                currentThread.interrupt();
            }
        });


        //Wait 10 seconds for response in maximum
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.i(getName(), "Forced InterruptedException to allow continue the test");
        }

        assertConfig2();
    }

    public void testObtainConfigurationWithNonEnglishAndSpaceParams() throws Exception {
        final Thread currentThread = Thread.currentThread();
        Map <String, String>params = new HashMap();
        params.put("Çà âｱｲｳ", "Çàâｱｲ ｳÇàâｱｲｳ");

        LiveUpdateManager.getInstance().obtainConfiguration(params, false, new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                configs[3] = configuration;
                currentThread.interrupt();
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                currentThread.interrupt();
            }
        });


        //Wait 10 seconds for response in maximum
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.i(getName(), "Forced InterruptedException to allow continue the test");
        }

        assertConfig4();
    }

    public void testObtainConfigurationWithSegment() throws Exception {
        final Thread currentThread = Thread.currentThread();
        LiveUpdateManager.getInstance().obtainConfiguration("segment1", new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                configs[0] = configuration;
                currentThread.interrupt();
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                currentThread.interrupt();
            }
        });


        //Wait 10 seconds for response in maximum
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.i(getName(), "Interruption forced");
        }

        assertConfig1 ();
    }

    public void testObtainConfigurationWithNonEnglishCharacterSegment() throws Exception {
        final Thread currentThread = Thread.currentThread();
        LiveUpdateManager.getInstance().obtainConfiguration("Çàâｱｲｳ", false, new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                configs[2] = configuration;
                currentThread.interrupt();
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                currentThread.interrupt();
            }
        });


        //Wait 10 seconds for response in maximum
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.i(getName(), "Interruption forced");
        }

        assertConfig3 ();
    }

    public void testObtainNonExitingSegment() throws Exception {
        final Thread currentThread = Thread.currentThread();
        WLFailResponse failResponse;
        LiveUpdateManager.getInstance().obtainConfiguration("nonExitingSegment", new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                currentThread.interrupt();
            }

            @Override
            public void onFailure(WLFailResponse wlFailResponse) {
                testFailResponse = wlFailResponse;
                currentThread.interrupt();
            }
        });


        //Wait 10 seconds for response in maximum
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            Log.i(getName(), "Forced InterruptedException to allow continue the test");
        }

        assertEquals(404,testFailResponse.getStatus());
    }
}