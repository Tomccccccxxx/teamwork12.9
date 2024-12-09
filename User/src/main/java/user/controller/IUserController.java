package user.controller;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import user.config.Results;
import user.pojo.Order;

import java.text.ParseException;
import java.util.List;

/**
 * 用户控制器接口。
 */
public interface IUserController {

    /**
     * 获取订单信息。若 userId 未指定，则获取所有的订单信息。
     *
     * @param userId 要查询的用户号。
     * @return 查询到的所有订单信息。
     */
    Results<List<Order>> getOrderAll(String userId);

    /**
     * 用户下单。【事务处理】
     *
     * @param order 订单信息。
     * @return 下单结果。
     */
    Results<Order> addOrder(@RequestBody Order order);

    /**
     * 删除订单号。【退款】【事务】
     *
     * @param orderId 要删除的订单号。
     * @return 删除情况。
     */
    Results<String> deleteOrder(String orderId);

    /**
     * 下单。【事务处理】
     *
     * @param order   订单信息。
     * @param orderId 要下单的订单号。
     * @return 订单信息。
     */
    @Transactional
    Order addOrderTran(Order order, String orderId) throws ParseException;

    /**
     * 删除订单。【事务处理】
     *
     * @param order 订单对象。
     * @return true：删除成功；false:删除失败。
     */
    @Transactional
    Boolean deleteOrderTran(Order order);
}