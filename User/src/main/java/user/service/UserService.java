package user.service;

import merchant.mapper.MerchantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import user.mapper.UserMapper;
import user.pojo.Order;

import java.util.List;


@Service
public class UserService {

    /**
     * 用户mapper层。
     */
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 查询订单号是否存在。
     *
     * @param orderId：要查询的订单号。
     * @return true：订单号存在。
     * false：订单号不存在、
     */
    public Boolean isOrderId(String orderId) {
        // 查询用户流水是否存在
        return userMapper.isOrderId(orderId) > 0;
//        return "order_01_01".equals(orderId);
    }

    /**
     * 查询订单信息。
     *
     * @param orderId：需要查询的订单号。
     * @return 查询到的订单信息，查询不到返回 null。
     */
    public Order findOrder(String orderId) {
        // 根据 订单号 查询订单信息。
        return userMapper.findOrder(orderId);
//        return new Order("123456", "张三", "12345678900",
//                        new Address("广东省","清远市","清城区", "广东工程职业技术学院"),
//                "order_01_01",new Date(), "洋娃娃", "2","10");
    }

    /**
     * 添加订单
     *
     * @param order：订单信息。
     */
    public void addOrder(Order order) {
        userMapper.addOrder(order);
        // 该订单会被显示到用户和商家两家地方。
        // 即该数据会在用户和商家两张表中分别都添加数据。
        // 客户为基本信息
        // 商家为 用户编号，用户手机号，描述信息，目标站点，下单时间，发货状态。
//         return new Order("123456", "李四", "12345432100",
//                new Address("广东省", "清远市", "清城区", "广东工程职业技术学院"),
//                "order_01_02", new Date(), "洋娃娃11", "0", "10");
    }

    /**
     * 查询所有的订单信息。
     */
    public List<Order> findOrderAll() {
        return userMapper.findOrderAll();
//        List<Order> list = new ArrayList<>();
//
//        for (int i = 0; i < 10; i++) {
//            // 添加用户订单。
//            list.add(
//                    new Order("123456", "张三", "12345678900",
//                            new Address("广东省","清远市","清城区", "广东工程职业技术学院"),
//                            "order_01_0"+i,new Date(), "洋娃娃"+i, ""+new Random().nextInt(11),"10")
//            );
//        }
//        return list;
    }

    /**
     * 修改用户订单状态。
     * @param orderId：用户订单号。
     */
    public void updateThisSite(String orderId, String thisSite) {
        userMapper.updateThisSite(orderId, thisSite);
    }

    /**
     * 删除用户订单。【退货】
     *
     * @param orderId：用户订单号。
     * @return true：   删除成功。
     * false：  删除失败。
     */
    public Boolean deleteOrder(String orderId) {
        return userMapper.deleteOrder(orderId);
    }


}
