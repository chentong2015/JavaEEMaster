package dubbo.demo.provider.services;

public class MyServiceImpl implements MyService {

    @Override
    public String getServiceInfo(String config) {
        return "Service Info: " + config;
    }
}
