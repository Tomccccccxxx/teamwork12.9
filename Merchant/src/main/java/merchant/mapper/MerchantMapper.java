package merchant.mapper;

import merchant.pojo.MerchantOrder;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 商家 mapper。
 */
@Mapper
@Repository
public interface MerchantMapper {

    /**
     * 查询所有 【未发货】 的商家订单情况。
     *
     * @param orderState 订单状态
     * @return 查询到的结果。
     */
    @Select("SELECT * FROM `merchantorder` WHERE `orderState` = #{orderState}")
    List<MerchantOrder> selectUnshippedOrders(@Param("orderState") String orderState);

    /**
     * 修改指定订单号为指定状态。
     * 注意：
     *      若不加 @Param 注解 则会引发 org.apache.ibatis.binding.BindingException: 绑定错误，即没有找到指定的key 。
     *      发生错误类 MapperMethod 的 get() 查询不到 orderState key。
     *
     * @param orderId 订单号
     * @param orderState 订单状态
     */
    @Update("UPDATE `merchantorder` SET `orderState`=#{orderState} WHERE orderId=#{orderId}")
    void updateOrderState(@Param("orderId") String orderId, @Param("orderState") String orderState);

    /**
     * 添加商家订单信息。
     *
     * @param merchantOrder 商家订单信息。
     */
    @Insert("INSERT INTO `merchantorder`(orderId, userPhone, itemDesc, targetSite, date, `orderState`) " +
            "VALUES(#{orderId}, #{userPhone}, #{itemDesc}, #{targetSite}, #{date}, #{orderState})")
    void addOrder(MerchantOrder merchantOrder);

    /**
     * 删除订单信息。
     *
     * @param orderId 订单号
     * @return true：删除成功。false：删除失败。
     */
    @Delete("DELETE FROM `merchantorder` WHERE orderId=#{orderId}")
    int deleteOrder(String orderId);

    List<MerchantOrder> findOrderAll();
}