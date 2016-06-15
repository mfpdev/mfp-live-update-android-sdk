MobileFirst Foundation - LiveUpdate Android SDK
===

###Contents
LiveUpdate Android SDK lets you query runtime configuration properties and features which you set in the LiveUpdate Settings screen in the MobileFirst Operations Console.
With LiveUpdate integrated in your application you can implement feature toggling, A/B testing, feature segmentation and more.

###Installation

### Install Using Gradle
```gradle
dependencies {
    compile group: 'com.ibm.mobile.foundation',
            name: 'ibmmobilefirstplatformfoundationliveupdate',
            version: '8.0.+',
            ext: 'aar',
            transitive: true
}   
```

### Configuration In MobileFirst Operation Console
In your application under Scope-Elements Mapping in security tab you must map the scope 'configuration-user-login' to security check, you can map it to empty string if you want to use the default protection.  More info about [scope mapping](https://mobilefirstplatform.ibmcloud.com/tutorials/en/foundation/8.0/authentication-and-security/authorization-concepts/#scope-mapping)

### Sample Usages Of The API

#### Obtain Configuration By Segment :

```Java
LiveUpdateManager.getInstance().obtainConfiguration("segment1", new ConfigurationListener() {

    @Override
    public void onSuccess(final Configuration configuration) {
      Log.i("LiveUpdateSample", configuration.getProperty("property1"));
    }

    @Override
    public void onFailure(WLFailResponse wlFailResponse) {
        Log.e("LiveUpdateSample", wlFailResponse.getErrorMsg());
    }
});
```

#### Obtain Configuration By Params :

```Java
LiveUpdateManager.getInstance().obtainConfiguration(new HashMap<String, String>() {{
            put("paramKey","paramValue");
        }}, new ConfigurationListener() {

    @Override
    public void onSuccess(final Configuration configuration) {
      Log.i("LiveUpdateSample", configuration.getProperty("property1"));
    }

    @Override
    public void onFailure(WLFailResponse wlFailResponse) {
        Log.e("LiveUpdateSample", wlFailResponse.getErrorMsg());
    }
});
```


#### Disable cache (by default the cache is enabled):

```Java
LiveUpdateManager.getInstance().obtainConfiguration("segment1", false, new ConfigurationListener() {

    @Override
    public void onSuccess(final Configuration configuration) {
      Log.i("LiveUpdateSample", configuration.getProperty("property1"));
    }

    @Override
    public void onFailure(WLFailResponse wlFailResponse) {
        Log.e("LiveUpdateSample", wlFailResponse.getErrorMsg());
    }
});
```

###Supported Levels
- API Level: 16 Android 4.1 and above

Copyright 2015 IBM Corp.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
