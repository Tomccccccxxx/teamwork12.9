package merchant.service;

import user.config.MyCodeState;
import user.config.Results;
import courier.controller.CourierController;
import merchant.mapper.MerchantMapper;
import user.pojo.Address;
import merchant.pojo.MerchantOrder;
import user.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 商家。
 * @author： 广东工程职业技术学院 19级物联网应用技术B班 廖理运
 * @version: 1.0
 * @since： 1.8
 */
@Service
public class MerchantService {

    @Autowired
    private UserService userService;

    @Autowired
    private MerchantMapper merchantMapper;

    /**
     * 查询当前订单状态下的所有未发货的订单。
     *
     * @return
     */
    public List<MerchantOrder> findOrderAll() {
        return merchantMapper.findOrderAll();
//        List<MerchantOrder> list = new ArrayList<>();
//
//        for (int i = 0; i < 1; i++) {
//            // 添加用户订单。
//            list.add(
//                    new MerchantOrder("order_01_0"+i, "12345678900",
//                            "洋娃娃"+i, "10",  new Date(),"未发货" )
//            );
//        }
//        return list;
    }

    /**
     * 修改用户订单状态。
     *
     * @param orderId：用户订单状态。
     * @return 修改后的商家订单信息。
     */
    public void updateOrderState(String orderId, String orderState) {
        merchantMapper.updateOrderState(orderId, orderState);
//        return new MerchantOrder(orderId, "12345678900",
//                "洋娃娃", "10",  new Date(),orderState )
//                ;
    }

    /**
     * 添加商家订单。
     *
     * @param merchantOrder：订单信息
     */
    public void addOrder(MerchantOrder merchantOrder) {
        merchantMapper.addOrder(merchantOrder);

    }



    /**
     * 删除订单
     *
     * @param orderId：订单号
     * @return true：删除成功。
     * false：删除失败。
     */
    public Boolean deleteOrder(String orderId) {
        // 删除订单【商家】
        return merchantMapper.deleteOrder(orderId);
    }

    /**
     * 修改用户订单状态。
     *
     * @param orderId：订单对象
     * @return
     *      true：修改成功。
     *      false：修改失败。
     */
    @Deprecated
    public Boolean updateOrderState(String orderId) {
        return null;
    }

}
