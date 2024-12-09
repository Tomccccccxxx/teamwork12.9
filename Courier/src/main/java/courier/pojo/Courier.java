package courier.pojo;

import java.util.Date;

/**
 * 快递员 接口。
 */
public interface Courier {

    /**
     * 订单号
     */
    String getOrderId();
    void setOrderId(String orderId);

    /**
     * 用户手机号
     */
    String getUserPhone();
    void setUserPhone(String userPhone);

    /**
     * 描述信息
     */
    String getItemDesc();
    void setItemDesc(String itemDesc);

    /**
     * 当前站点
     */
    String getThisSite();
    void setThisSite(String thisSite);

    /**
     * 目标站点
     */
    String getTargetSite();
    void setTargetSite(String targetSite);

    /**
     * 下单时间
     */
    String getDate(); // 注意这里返回的是String类型，因为原方法返回格式化后的日期字符串
    void setDate(Date date);

    /**
     * 订单状态
     */
    String getOrderState();
    void setOrderState(String orderState);

    /**
     * 格式化订单对象
     */
    DateFormat getDateFormat();

}