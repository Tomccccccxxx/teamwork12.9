package user.mapper;

import user.pojo.Order;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户 mapper层。

 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 查询用户流水是否存在。
     * @param orderId：要查询的用户。
     * @return
     *      返回是否存在。
     */
    @Select("SELECT count(1) FROM `order` WHERE orderId=#{orderId}")
    Integer isOrderId(String orderId);

    /**
     * 根据 订单号 查询订单信息。
     * @param orderId：需要查询的订单号。
     * @return
     *      查询到的订单信息。
     */
    @Select("SELECT * FROM `order` WHERE orderId=#{orderId}")
    Order findOrder(String orderId);

    /**
     * 添加用户。
     * @param order：要添加的数据。
     * @return
     *
     */
    @Insert("INSERT INTO " +
            "`order`(userId,userName,userPhone,address,orderId,date,itemDesc,thisSite,targetSite) " +
            "VALUES" +
            "(#{userId},#{userName},#{userPhone},#{address.city},#{orderId},#{date},#{itemDesc},#{thisSite},#{targetSite})")
    void addOrder(Order order);

    /**
     * 查询所有订单信息。
     * @return
     *      查询到的结果
     */
    @Select("SELECT * FROM `order`")
    List<Order> findOrderAll();

    /**
     * 修改站点。
     * 注意：
     *      若不加 @Param 注解 则会引发 org.apache.ibatis.binding.BindingException: 绑定错误，即没有找到指定的key 。
     *      引发错误。
     *      发生错误类 MapperMethod 的 get() 查询不到 orderState key。
     * @param orderId：订单号。
     * @param thisSite：当前站点
     * @return
     */
    @Update("UPDATE `order` SET thisSite=#{thisSite} WHERE orderId=#{orderId}")
    void updateThisSite(@Param("orderId") String orderId,@Param("thisSite") String thisSite);

    /**
     * 删除订单信息。
     * @param orderId：订单号
     * @return
     *      true：删除成功。
     *      false：删除失败。
     */
    @Delete("DELETE FROM `order` WHERE orderId=#{orderId}")
    Boolean deleteOrder(String orderId);

}
