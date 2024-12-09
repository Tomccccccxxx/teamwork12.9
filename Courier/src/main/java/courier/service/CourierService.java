package courier.service;

import courier.mapper.CourierMapper;
import merchant.mapper.MerchantMapper;
import merchant.service.MerchantService;
import user.mapper.UserMapper;
import user.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 快递员 service 层。
 */
@Service
public class CourierService {

    @Autowired
    private MerchantService merchantService; // 现在引用的是接口

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CourierMapper courierMapper;

    /**
     * 订单是否存在。
     * @param orderId：订单号。
     * @return
     *      true：存在。
     *      false：不存在。
     */
    public Boolean isOrderId(String orderId) {
        return userMapper.isOrderId(orderId) == 1;
    }

    /**
     * 查询订单表所有未到站点的信息。
     * @return
     *      查询到的结果集。
     */
    public List<Courier> findOrderAll() {
        return courierMapper.findOrderAll();
    }

    /**
     * 设置快递为丢失状态。【开启事务】
     *
     * 若事务发送错误，则会抛出异常，并将事务回滚未上一次断点处【恢复到最近一次的状态】。
     *
     * @param order：订单对象。
     */
    @Transactional
    public void lostCourier(Order order) {
        //  修改 商家表的【订单状态】为已丢失。
        merchantService.updateOrderState(order.getOrderId(), "已丢失");
        //  修改 订单表的 【当前站点】为-1。
        userService.updateThisSite(order.getOrderId(), "-1");
    }
}