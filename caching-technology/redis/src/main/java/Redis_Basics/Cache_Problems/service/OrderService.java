package Redis_Basics.Cache_Problems.service;

import Redis_Basics.Cache_Problems.model.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderService {

    public static Order selectOrderById(int id) {
        Order order = new Order("apple");
        return order;
    }

    public static List<Order> selectAllOrders() {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order("item01"));
        orders.add(new Order("item02"));
        orders.add(new Order("item03"));
        return orders;
    }
}
