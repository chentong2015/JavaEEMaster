package Redis_Basics.Redis_Cache.service;

import Redis_Basics.Redis_Cache.model.Order;

public class OrderService {

    public static Order selectOrderById(int id) {
        Order order = new Order("apple");
        return order;
    }
}
