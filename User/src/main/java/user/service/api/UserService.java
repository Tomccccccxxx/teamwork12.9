package user.service.api;

import org.springframework.web.bind.annotation.PostMapping;
import user.pojo.Order;

import java.util.List;

public interface UserService {
    boolean isOrderId(String orderId);

    Order findOrder(String orderId);

    void addOrder(Order order);

    List<Order> findOrderAll();

    void updateThisSite(String orderId, String thisSite);

    boolean deleteOrder(String orderId);
    public interface CourierServiceClient {
        @PostMapping("/courier/resetQRCode")
        void resetQRCode();
    }
}