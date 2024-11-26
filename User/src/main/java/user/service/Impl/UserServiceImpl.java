package user.service.Impl;

import org.springframework.stereotype.Service;
import user.mapper.UserMapper;
import user.pojo.Order;
import user.service.api.UserService;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean isOrderId(String orderId) {
        return userMapper.isOrderId(orderId) > 0;
    }

    @Override
    public Order findOrder(String orderId) {
        return userMapper.findOrder(orderId);
    }

    @Override
    public void addOrder(Order order) {
        userMapper.addOrder(order);
    }

    @Override
    public List<Order> findOrderAll() {
        return userMapper.findOrderAll();
    }

    @Override
    public void updateThisSite(String orderId, String thisSite) {
        userMapper.updateThisSite(orderId, thisSite);
    }

    @Override
    public boolean deleteOrder(String orderId) {
        return userMapper.deleteOrder(orderId);
    }
}