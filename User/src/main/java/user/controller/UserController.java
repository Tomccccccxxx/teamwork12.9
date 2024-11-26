package user.controller;

import courier.controller.CourierController;
import merchant.pojo.MerchantOrder;
import merchant.service.MerchantService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import user.config.MyCodeState;
import user.config.Results;
import user.pojo.Order;
import user.service.UserService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 用户控制器。

 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static int ordNum2;
    private static int ordNum1;

    /**
     * 格式化
     */
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    /**
     * 用户 service
     */
    @Autowired
//    @lombok.Setter
    private UserService userService;

//    @lombok.Getter
    @Autowired
    private MerchantService merchantService;

    /**
     * 获取订单信息。若 userId 未指定，则获取所有的订单信息。
     *
     * @param userId：要查询的用户号。
     * @return 查询到的所有订单信息。
     */
    @GetMapping({"/order", "/order/{userId}"})
    public Results<List<Order>> getOrderAll(@PathVariable(value = "userId", required = false) String userId) {
        try {
// 获取所有的订单号
            List<Order> orderAll = userService.findOrderAll();
//            orderAll.forEach(System.out::println);
            // 没有订单。
            if (orderAll.size() == 0) {
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), null);
            }
            // 查询到数据。
            return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), orderAll);
        }
        // 数据库连接失败
        catch (Throwable throwable) {
//            System.out.println("throwable.getMessage() = " + throwable.getMessage());
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    /**
     * 用户下单。【事务处理】
     *
     * @return 下单结果。
     */
    @PostMapping("/order")
    public Results<Order> addOrder(
//            @PathVariable("orderId") String orderId
            @RequestBody Order order
//            , @RequestParam("userId") String userId
    ) {

        /**
         * 问题：
         * orderId=""。订单号由服务器生成。
         * itemDesc= number。原因，前端字符串拼接问题。
         */
//        System.out.println("order：" + order);
        System.out.println("======================================================================");
        // 生成订单
        String orderId = "order_0" + ++ordNum1 + "_0" + ++ordNum2;
        // 测试订单。order_01_01。
        System.out.println("下单的订单对象信息：order ===> " + order);

        try {


            // 1. 查询订单是否存在。【数据库查询】
            Boolean isOrderId = null;
            isOrderId = userService.isOrderId(orderId);
            System.out.println("下单：订单是否存在：" + isOrderId);

            //      1.1 订单存在。
            if (isOrderId) {
                // 查询订单信息并返回。
                Order resOrder = userService.findOrder(orderId);
                MyCodeState.EXISTING.setCodeMsg(resOrder.getOrderId() + "  ---  订单已存在。");
                // 则返回订单信息【基本信息与到达位置信息】。
                return new Results<>(MyCodeState.EXISTING.toString(), MyCodeState.EXISTING.getCodeMsg(), resOrder);
            }
            //      1.2 订单不存在。
            else {

                try {
                    // 事务处理。
                    Order resOrder = this.addOrderTran(order, orderId);
                    return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), resOrder);
                } catch (ParseException e) {
//                    e.printStackTrace();
                    MyCodeState.FAIL.setCodeMsg("下单失败");
                    return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
                }
            }
        }
        // 数据库连接失败
        catch (Throwable throwable) {
//            System.out.println("throwable.getMessage() = " + throwable.getMessage());
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }


    /**
     * 删除订单号。【退款】【事务】
     *
     * @param orderId：
     * @return 删除情况。
     */
    @DeleteMapping("/order/{orderId}")
    public Results<String> deleteOrder(@PathVariable("orderId") String orderId) {
        System.out.println("用户退款：订单号：orderId = " + orderId);

        try {
            // 退货 --- 即删除 订单表指定数据，和 商家表指定数据。
            // 1. 查看当前 订单号 是否存在。
            Order order = userService.findOrder(orderId);
            // 1.1 存在。
            if (order != null) {
                // 添加了事务的删除订单方法。
                Boolean aBoolean = this.deleteOrderTran(order);
                // 删除失败。
                if (!aBoolean){
                    MyCodeState.FAIL.setCodeMsg("订单退款失败。");
                    return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "删除失败。");
                }
                return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), "删除成功");
            }
            // 1.2 不存在。
            // 返回错误信息。说该订单不存在。
            MyCodeState.NULL.setCodeMsg("订单不存在。");
            return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), "订单不存在。");
        }
        // 数据库连接失败
        catch (Throwable throwable) {
//            System.out.println("throwable.getMessage() = " + throwable.getMessage());
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }



    /**
     * 下单。【事务处理】
     * <p>
     * <p>
     * <p>
     * 订单扩充：
     * // 2. 订单查询之后生成订单。【快递订单】     【锁，避免高并发情况】
     * // 2.1 订单为 order_时间搓+随机值。【尽可能保证唯一性】
     * // 2.2 生成一个订单对象，并时刻保持着。【每下一个订单就多一份快递。】
     * // 2.3 若订单超时【暂时不做】
     *
     * @param order：订单信息。
     * @param orderId：要下单的订单号。
     * @return
     *      订单信息。
     */
    @Transactional
    public Order addOrderTran(Order order, String orderId) throws ParseException {
        if (order != null) {

            /************** 用户表订单生成 *********************/
            // 则创建订单。【生成快递订单】
            // 把订单保存到数据库。
            order.setOrderId(orderId);
            order.setThisSite("0");
            order.setTargetSite("10");
            order.setDate(new Date());
            userService.addOrder(order);

            /************** 商家表订单生成 *********************/
            // 创建一个商家订单对象。
            MerchantOrder merchantOrder = new MerchantOrder(
                    order.getOrderId(), order.getUserPhone(), order.getItemDesc(),
                    order.getTargetSite(), dateFormat.parse(order.getDate()), "未发货"
            );
//                    System.out.println("merchantOrder ===> " + merchantOrder);
            merchantService.addOrder(merchantOrder);

            // 查询订单。
//            Order resOrder = userService.findOrder(order.getOrderId());

//            return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), resOrder);
            return userService.findOrder(order.getOrderId());
        }
        return null;
    }

    /**
     * 删除订单。【事务处理】
     * @param order：订单号。
     * @return
     *      true：删除成功。
     *      false:删除失败。
     */
    @Transactional
    public Boolean deleteOrderTran(Order order){
        // 删除。
        Boolean aBoolean = userService.deleteOrder(order.getOrderId());
        Boolean bBoolean = merchantService.deleteOrder(order.getOrderId());
        // 清除二维码状态。设置为 FAIL。
        CourierController.resetQRCode();

        System.out.println("删除订单：用户订单删除情况：" + aBoolean);
        System.out.println("删除订单：商家订单删除情况：" + bBoolean);

        return aBoolean && bBoolean;
    }

}
