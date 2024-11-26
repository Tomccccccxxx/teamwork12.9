package user.service.api;

import user.pojo.Order;

import java.util.List;

public interface UserService {
    boolean isOrderId(String orderId);

    Order findOrder(String orderId);

    void addOrder(Order order);

    List<Order> findOrderAll();

    void updateThisSite(String orderId, String thisSite);

    boolean deleteOrder(String orderId);
}