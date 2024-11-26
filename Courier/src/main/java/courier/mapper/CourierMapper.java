package courier.mapper;
import courier.pojo.Courier;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 快递员 mapper。
 */
@Mapper
@Repository
public interface CourierMapper {

    /**
     * 查询 未到【目标站点】的所有的订单数。
     * @return
     *      结果集。
     */
    @Select("SELECT merch.*, ord.thisSite FROM `order` ord,`merchantorder` merch WHERE ord.orderId=merch.orderId AND merch.orderState!='已签收'")
    List<Courier> findOrderAll();
}
