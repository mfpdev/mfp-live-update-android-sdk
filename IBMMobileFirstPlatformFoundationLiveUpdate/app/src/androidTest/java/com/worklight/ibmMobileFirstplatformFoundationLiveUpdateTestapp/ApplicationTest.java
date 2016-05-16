package com.worklight.ibmMobileFirstplatformFoundationLiveUpdateTestapp;

import android.app.Application;
import android.content.Context;
import android.test.ApplicationTestCase;

import com.worklight.common.Logger;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.LiveUpdateManager;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.Configuration;
import com.worklight.ibmmobilefirstplatformfoundationliveupdate.api.ConfigurationListener;
import com.worklight.wlclient.api.WLClient;
import com.worklight.wlclient.api.WLFailResponse;

import junit.framework.Assert;

import java.util.HashMap;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }

    Context context;
    Configuration testConfig;
    WLFailResponse testFailResponse;

    public void setUp() throws Exception {
        super.setUp();
        context = getContext();
        assertNotNull(context);

        WLClient.createInstance(context);
        Logger.setContext(context);
        testConfig = null;
        testFailResponse = null;
    }


    private void assertConfig () {
        assertEquals(testConfig.getProperty("property1"), "value1");
        assertEquals(testConfig.getProperty("property2"), "value2");
        assertEquals(testConfig.isFeatureEnabled("feature1"), Boolean.TRUE);
        assertEquals(testConfig.isFeatureEnabled("feature2"), Boolean.FALSE);

        assertNull(testConfig.getProperty("notExistProperty"));
        assertNull(testConfig.getProperty("featureNotExist"));
    }

    public void testObtainConfigurationWithParams() throws Exception {
        final Thread currentThread = Thread.currentThread();
        LiveUpdateManager.getInstance().obtainConfiguration(new HashMap<String, String>(), false, new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                testConfig = configuration;
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
        }

        assertConfig();
    }

    public void testObtainConfigurationWithSegment() throws Exception {
        final Thread currentThread = Thread.currentThread();
        LiveUpdateManager.getInstance().obtainConfiguration("segment1", new ConfigurationListener() {
            @Override
            public void onSuccess(final Configuration configuration) {
                testConfig = configuration;
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
        }

        assertConfig ();
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
        }

        assertEquals(404,testFailResponse.getStatus());
    }
}