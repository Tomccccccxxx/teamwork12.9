package user.controller;

import courier.controller.CourierController;
import lombok.SneakyThrows;
import merchant.pojo.MerchantOrder;
import merchant.service.IMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import user.config.MyCodeState;
import user.config.Results;
import user.pojo.Order;
import user.service.UserService;
import user.util.QRCodeUtilImpl;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@RestController
@RequestMapping("/user")
@Service
@Component
public class UserControllerImpl implements IUserController {

    private static int ordNum2;
    private static int ordNum1;

    /**
     * 格式化
     */
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Autowired
    private UserService userService;

    @Autowired
    private IMerchantService merchantService;
    @Autowired
    private QRCodeUtilImpl qrCodeUtil;
    private Object CourierController;

    /**
     * 根据订单ID生成二维码。
     *
     * @param orderId 订单ID。
     * @return Base64编码的PNG格式二维码图像字符串。
     */
    @SneakyThrows
    @GetMapping("/generateQRCode/{orderId}")
    public Results<String> generateQRCode(@PathVariable String orderId) {
        try {
            Order order = userService.findOrder(orderId);
            if (order == null) {
                MyCodeState.NULL.setCodeMsg("订单不存在。");
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), null);
            }

            String qrUrl = "http://yourwebsite.com/order/" + orderId; // 示例URL
            String qrCodeBase64 = qrCodeUtil.createQRCode(qrUrl);

            return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), qrCodeBase64);
        } catch (IOException e) {
            MyCodeState.FAIL.setCodeMsg("生成二维码失败：" + e.getMessage());
            return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
        }
    }
    @FeignClient(name = "courier-service", url = "${courier.service.url}")
    public interface CourierServiceClient {
        @PostMapping("/courier/resetQRCode")
        void resetQRCode();
    }
    @FeignClient(name = "courier-service", url = "${courier.service.url}")
    public interface CourierServiceClient {
        @PostMapping("/courier/resetQRCode")
        @Retryable(maxAttempts = 3)
        void resetQRCode();
    }
    @Override
    public Results<List<Order>> getOrderAll(String userId) {
        try {
            List<Order> orderAll = userService.findOrderAll();
            if (orderAll.isEmpty()) {
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), null);
            }
            return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), orderAll);
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    public Results<Order> addOrder(@RequestBody Order order) {
        String orderId = "order_0" + ++ordNum1 + "_0" + ++ordNum2;
        System.out.println("下单的订单对象信息：order ===> " + order);

        try {
            Boolean isOrderId = userService.isOrderId(orderId);
            System.out.println("下单：订单是否存在：" + isOrderId);

            if (isOrderId) {
                Order resOrder = userService.findOrder(orderId);
                MyCodeState.EXISTING.setCodeMsg(resOrder.getOrderId() + "  ---  订单已存在。");
                return new Results<>(MyCodeState.EXISTING.toString(), MyCodeState.EXISTING.getCodeMsg(), resOrder);
            } else {
                try {
                    Order resOrder = this.addOrderTran(order, orderId);
                    return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), resOrder);
                } catch (ParseException e) {
                    MyCodeState.FAIL.setCodeMsg("下单失败");
                    return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
                }
            }
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    public Results<String> deleteOrder(String orderId) {
        System.out.println("用户退款：订单号：orderId = " + orderId);

        try {
            Order order = userService.findOrder(orderId);
            if (order != null) {
                Boolean success = this.deleteOrderTran(order);
                if (!success){
                    MyCodeState.FAIL.setCodeMsg("订单退款失败。");
                    return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "删除失败。");
                }
                return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), "删除成功");
            } else {
                MyCodeState.NULL.setCodeMsg("订单不存在。");
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), "订单不存在。");
            }
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    @Transactional
    public Order addOrderTran(Order order, String orderId) throws ParseException {
        if (order != null) {
            order.setOrderId(orderId);
            order.setThisSite("0");
            order.setTargetSite("10");
            order.setDate(new Date());
            userService.addOrder(order);

            MerchantOrder merchantOrder = new MerchantOrder(
            ) {
                @Override
                public String getOrderId() {
                    return null;
                }

                @Override
                public void setOrderId(String orderId) {

                }

                @Override
                public String getUserPhone() {
                    return null;
                }

                @Override
                public void setUserPhone(String userPhone) {

                }

                @Override
                public String getItemDesc() {
                    return null;
                }

                @Override
                public void setItemDesc(String itemDesc) {

                }

                @Override
                public String getTargetSite() {
                    return null;
                }

                @Override
                public void setTargetSite(String targetSite) {

                }

                @Override
                public String getDate() {
                    return null;
                }

                @Override
                public void setDate(Date date) {

                }

                @Override
                public String getOrderState() {
                    return null;
                }

                @Override
                public void setOrderState(String orderState) {

                }
            };
            merchantService.addOrder(merchantOrder);

            return userService.findOrder(order.getOrderId());
        }
        return null;
    }

    @Override
    @Transactional
    public Boolean deleteOrderTran(Order order) {
        Boolean userDeleted = userService.deleteOrder(order.getOrderId());
        Boolean merchantDeleted = merchantService.deleteOrder(order.getOrderId());
        CourierController.resetQRCode();

        System.out.println("删除订单：用户订单删除情况：" + userDeleted);
        System.out.println("删除订单：商家订单删除情况：" + merchantDeleted);

        return userDeleted && merchantDeleted;
    }

    @PostConstruct
    private void initOrdNums() {
        // 初始化订单编号计数器
        ordNum1 = 0;
        ordNum2 = 0;
    }
}