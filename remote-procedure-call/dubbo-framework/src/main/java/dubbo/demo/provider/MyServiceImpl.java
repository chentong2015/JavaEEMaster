package dubbo.demo.provider;

public class MyServiceImpl implements MyService {

    @Override
    public String getServiceInfo(String config) {
        return "Provider result: " + config;
    }
}
