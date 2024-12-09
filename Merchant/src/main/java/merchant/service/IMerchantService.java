package merchant.service;

import merchant.pojo.MerchantOrder;

import java.util.List;

/**
 * 商家服务接口。
 */
public interface IMerchantService {

    /**
     * 查询当前订单状态下的所有未发货的订单。
     *
     * @return 所有未发货的订单列表。
     */
    List<MerchantOrder> findOrderAll();

    /**
     * 修改用户订单状态。
     *
     * @param orderId   订单ID。
     * @param orderState 新的订单状态。
     */
    void updateOrderState(String orderId, String orderState);

    /**
     * 添加商家订单。
     *
     * @param merchantOrder 订单信息。
     */
    void addOrder(MerchantOrder merchantOrder);

    /**
     * 删除订单。
     *
     * @param orderId 订单号。
     * @return true：删除成功；false：删除失败。
     */
    int deleteOrder(String orderId);

    /**
     * 修改用户订单状态（已废弃）。
     *
     * @deprecated 使用带有两个参数的updateOrderState方法代替。
     * @param orderId 订单对象。
     * @return true：修改成功；false：修改失败。
     */
    @Deprecated
    Boolean updateOrderState(String orderId);
}