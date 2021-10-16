package dubbo.demo.provider.impl;

import dubbo.demo.provider.api.MyService;

public class MyServiceImpl implements MyService {

    @Override
    public String getServiceInfo(String config) {
        return "Service Info: " + config;
    }
}
