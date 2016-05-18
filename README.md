# MFP LiveUpdate Android SDK

## Install Using Gradle
```gradle
dependencies {
    compile group: 'com.ibm.mobile.foundation',
            name: 'ibmmobilefirstplatformfoundationliveupdate',
            version: '8.0.+',
            ext: 'aar',
            transitive: true
}   
```
## Sample usages of the API

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

## License
IBM
