package merchant.pojo;

import java.util.Date;

public interface MerchantOrder {

    /**
     * 获取订单号。
     * @return 订单号
     */
    String getOrderId();

    /**
     * 设置订单号。
     * @param orderId 订单号
     */
    void setOrderId(String orderId);

    /**
     * 获取用户手机号。
     * @return 用户手机号
     */
    String getUserPhone();

    /**
     * 设置用户手机号。
     * @param userPhone 用户手机号
     */
    void setUserPhone(String userPhone);

    /**
     * 获取描述信息。
     * @return 描述信息
     */
    String getItemDesc();

    /**
     * 设置描述信息。
     * @param itemDesc 描述信息
     */
    void setItemDesc(String itemDesc);

    /**
     * 获取目标站点。
     * @return 目标站点
     */
    String getTargetSite();

    /**
     * 设置目标站点。
     * @param targetSite 目标站点
     */
    void setTargetSite(String targetSite);

    /**
     * 获取格式化之后的日期。
     * @return 格式化之后的日期信息
     */
    String getDate();

    /**
     * 设置日期。
     * @param date 日期
     */
    void setDate(Date date);

    /**
     * 获取订单状态。
     * @return 订单状态
     */
    String getOrderState();

    /**
     * 设置订单状态。
     * @param orderState 订单状态
     */
    void setOrderState(String orderState);
}