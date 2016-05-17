# MFP LiveUpdate Android SDK

## Sample usages of the API

#### By Segment :

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

#### By Params :

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
