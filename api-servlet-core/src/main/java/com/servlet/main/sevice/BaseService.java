package com.servlet.main.sevice;

import java.util.ArrayList;
import java.util.List;

public class BaseService {

    public List<String> getHelloMessages() {
        List<String> helloMessages = new ArrayList<>();
        helloMessages.add("Item 01");
        helloMessages.add("Item 02");
        return helloMessages;
    }
}
