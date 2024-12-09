package user.pojo;

import java.util.Date;

/**
 * 订单对象接口。
 */
public interface IOrder {
    /**
     * 获取用户编号。
     *
     * @return 用户编号
     */
    String getUserId();

    /**
     * 设置用户编号。
     *
     * @param userId 新的用户编号
     */
    void setUserId(String userId);

    /**
     * 获取用户姓名。
     *
     * @return 用户姓名
     */
    String getUserName();

    /**
     * 设置用户姓名。
     *
     * @param userName 新的用户姓名
     */
    void setUserName(String userName);

    /**
     * 获取用户手机。
     *
     * @return 用户手机
     */
    String getUserPhone();

    /**
     * 设置用户手机。
     *
     * @param userPhone 新的用户手机
     */
    void setUserPhone(String userPhone);

    /**
     * 获取用户地址。
     *
     * @return 用户地址
     */
    Address getAddress();

    /**
     * 设置用户地址。
     *
     * @param address 新的用户地址
     */
    void setAddress(Address address);

    /**
     * 获取订单号。
     *
     * @return 订单号
     */
    String getOrderId();

    /**
     * 设置订单号。
     *
     * @param orderId 新的订单号
     */
    void setOrderId(String orderId);

    /**
     * 获取格式化后的日期。
     *
     * @return 格式化后的日期字符串
     */
    String getDate();

    /**
     * 设置日期。
     *
     * @param date 新的日期
     */
    void setDate(Date date);

    /**
     * 获取物品描述。
     *
     * @return 物品描述
     */
    String getItemDesc();

    /**
     * 设置物品描述。
     *
     * @param itemDesc 新的物品描述
     */
    void setItemDesc(String itemDesc);

    /**
     * 获取当前站点。
     *
     * @return 当前站点
     */
    String getThisSite();

    /**
     * 设置当前站点。
     *
     * @param thisSite 新的当前站点
     */
    void setThisSite(String thisSite);

    /**
     * 获取目标站点。
     *
     * @return 目标站点
     */
    String getTargetSite();

    /**
     * 设置目标站点。
     *
     * @param targetSite 新的目标站点
     */
    void setTargetSite(String targetSite);
}