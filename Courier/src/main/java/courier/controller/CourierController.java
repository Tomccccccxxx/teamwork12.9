package courier.controller;

import user.config.QRCodeState;
import user.config.Results;
import courier.pojo.Courier;
import user.pojo.Order;
import courier.service.CourierService;
import merchant.service.MerchantService;
import user.service.UserService;
import user.util.AddressUtils;
import user.util.QRCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import user.config.MyCodeState;

import java.io.IOException;
import java.util.List;

/**
 * 快递员 控制器。

 */
@RestController
@RequestMapping("/courier")
public class CourierController {

    /**
     * 用户订单服务类
     */
    @Autowired
    private UserService userService;

    /**
     * 快递员订单服务类
     */
    @Autowired
    private CourierService courierService;

    /**
     * 服务器端口。
     */
    @Value("${server.port}")
    private String port;

    /**
     * 服务器 IP。
     */
    @Value("${server.ipv4}")
    private String innetIp;
//    private String innetIp = "120.24.169.207";

    /**
     * 商家订单服务。未投入使用。
     */
    @Autowired
    private MerchantService merchantService;

    /**
     * 二维码 data-url 形式。
     */
    private static String qrCode;
    /**
     * 二维码状态。
     * 取值：
     * 0： 不存在。
     * 1：存在 但 未扫描。
     * 2：存在 已扫描 但未执行完成。
     * 3：已过期。
     */
    // 初始化 二维码 不存在。
    private static Integer qrCodeState = QRCodeState.FAIL.getqRCodeState();

    /**
     * 获取当前未到【目标站点】的所有订单号。
     *
     * @return 结果集。
     */
    @GetMapping({"/order", "/order/{orderId}"})
    public Results<List<Courier>> getOrder(@PathVariable(value = "orderId", required = false) String orderId) {

        try {
            // 获取当前快递员的虚拟表。
            List<Courier> orderAll = courierService.findOrderAll();
            if (orderAll == null) {
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), null);
            }
            // 返回快递员订单对象。
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
     * 通过扫码修改订单当前站点。
     *
     * @param orderId：订单号
     * @return 修改结果。
     */
    @PatchMapping("/order/thisSite/{orderId}")
    public Results<Object> updateOrder(@PathVariable(value = "orderId", required = false) String orderId) {
        System.out.println("要修改的订单号：orderId = " + orderId);

        try {
            // 1. 查询订单号
            Boolean isOrderId = courierService.isOrderId(orderId);
            //  1.1 订单号存在。
            if (isOrderId != null && isOrderId) {
                // 根据订单号查询用户订单信息。
                Order order = userService.findOrder(orderId);
                // 查询到订单信息之后，修改订单信息中的【当前站点】，即【当前站点】自增。
                if (order != null) {
                    // 未到达目标站点。
                    if (Integer.parseInt(order.getThisSite()) < 10) {
                        //  站点+1。【设置用户站点】
                        userService.updateThisSite(order.getOrderId(), Integer.toString(Integer.parseInt(order.getThisSite()) + 1));
                    }
                    // 到达目标站点。
                    else if (Integer.parseInt(order.getThisSite()) >= 10) {
                        MyCodeState.FAIL.setCodeMsg("订单已送达，请不要重复操作。");
                        return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
                    }
                }
                MyCodeState.FAIL.setCodeMsg("订单号不存在");
                return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");

            }
            //  1.2 订单号不存在。
            else {
                MyCodeState.FAIL.setCodeMsg("订单号不存在");
                return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
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
     * 获取二维码信息。
     *
     * @param orderId：订单号。
     * @return 返回二维码。
     */
    @GetMapping({"/QRCode", "/QRCode/{orderId}"})
    public Results<String> getQRCode(@PathVariable(value = "orderId", required = false) String orderId) {
        System.out.println("\n================== 二维码 =====================");
        System.out.println("二维码生成要扫码的订单号：orderId = " + orderId);
        try {
            Order order = userService.findOrder(orderId);
            // 获取的是公网 IP。
//            InetAddress address = InetAddress.getLocalHost();
//            System.out.println("address = " + address);
//            String hostAddress = address.getHostAddress();//返回IP地址
//            System.out.println("hostAddress = " + hostAddress);
//
//            InetAddress loopbackAddress = InetAddress.getLoopbackAddress();
//            System.out.println("loopbackAddress = " + loopbackAddress);

            // 获取内外IP。
            if (innetIp == null){
                this.innetIp = AddressUtils.getInnetIp();
                System.out.println("二维码要访问的IP【内网】：innetIp = " + innetIp);
            }else {
                System.out.println("二维码要访问的IP【服务器】：innetIp = " + innetIp);
            }
            System.out.println("================ 二维码 =======================\n");
// 生成的二维码信息。
//            QRCodeUtil.createQRCode("http://localhost:8080/QRCode/1");
//            qrCode = QRCodeUtil.createQRCode("http://www.baidu.com/");
            // 生成二维码，当扫描二维码时，会新添站点。
            qrCode = QRCodeUtil.createQRCode("http://" + innetIp + ":"+port+"/courier/order/thisSite/" + orderId + "/" + order.getThisSite());
            // 更新二维码状态为 存在 但 未扫描。
            qrCodeState = QRCodeState.NOTSCAN.getqRCodeState();
        } catch (IOException e) {
            e.printStackTrace();
            MyCodeState.FAIL.setCodeMsg("二维码生成失败。");
            return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
        }
        return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), qrCode);
    }

    /**
     * 获取二维码状态。
     *
     */
    @GetMapping({"/QRCodeState", "/QRCodeState/{orderId}"})
    public Results<Integer> getQRCodeState(@PathVariable(value = "orderId", required = false) String orderId) {
//        System.out.println("orderId = " + orderId);
        if (qrCode == null) {
            // 恢复状态。
            qrCodeState = QRCodeState.FAIL.getqRCodeState();
            MyCodeState.FAIL.setCodeMsg("二维码已失效");
            return new Results<>(QRCodeState.FAIL.toString(), QRCodeState.FAIL.getqRCodeMsg(), null);
        }
        String qRCodeMsg = null;
        // 查看当前状态
        for (QRCodeState qrCodeState : QRCodeState.values()) {
            // 当前状态编号 == 序列编号。
            if (this.qrCodeState.equals(qrCodeState.getqRCodeState())) {
                qRCodeMsg = qrCodeState.getqRCodeMsg();
                System.out.println("二维码扫描状态：qRCodeMsg = " + qRCodeMsg);
            }
        }
//        QRCodeState.SUCCESS.setqRCodeMsg();
        return new Results<>(QRCodeState.SUCCESS.toString(), qRCodeMsg.equals(null) ? "" : qRCodeMsg, qrCodeState);
    }

    /**
     * 更新订单站点。【事务处理】【二维码扫码处理】
     *
     * @param orderId：订单号。
     * @return 更新是否成功。
     */
    @GetMapping("/order/thisSite/{orderId}/{thisSite}")
//    @PatchMapping("/order/thisSite/{orderId}/{thisSite}")
    public Results<String> updateThisSite(@PathVariable("orderId") String orderId, @PathVariable("thisSite") String thisSite) {
        System.out.println("要扫码更新的订单号：orderId = " + orderId);
        System.out.println("要扫码更新订单站点：thisSite = " + thisSite);

        try {
            try {
                // 原子化更新订单操作。
                return this.updateThisSiteTran(orderId, thisSite);
            } catch (Exception e) {
                MyCodeState.FAIL.setCodeMsg("服务器发生了错误");
                return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
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
     * 快递丢失。【修改订单为丢失状态】【事务处理】
     *
     * @param orderId：订单号。
     * @return 修改结果。【成功 / 失败】
     */
    @PatchMapping("/order/orderState/{orderId}")
    public Results<String> d(@PathVariable("orderId") String orderId) {
        System.out.println("丢失的订单号：orderId = " + orderId);

        try {
            // 查询订单是否存在。
            Order order = userService.findOrder(orderId);
            // 存在。
            if (order != null) {
                try {
                    // 修改订单状态为 【已丢失】。
                    // 进行了事务处理，若发生异常，则会直接中断。
                    courierService.lostCourier(order);
                    return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), "修改成功");
                } catch (Exception e) {
                    // 【快递丢失】状态 修改异常。
                    System.out.println("【快递丢失】状态发送错误。");
                    e.printStackTrace();
                    MyCodeState.FAIL.setCodeMsg("修改失败");
                    return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
                }
            }
            // 不存在。
            else {
                //  返回
                MyCodeState.NULL.setCodeMsg("订单不存在");
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), "修改失败");
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
     * 清除 二维码 状态。
     */
    public static void resetQRCode() {
        qrCode = null;
    }

    /**
     * 更新当前站点。【事务化】
     * 中途发送错误与，则抛出异常。
     *
     * @param orderId：订单号。
     * @param thisSite：当前站点。
     * @return 结果。
     */
    @Transactional
    public Results<String> updateThisSiteTran(String orderId, String thisSite) {

        // 二维码存在，且路径相同。
        if (qrCode != null) {
            // 更新二维码状态为 存在 已扫描 但未执行完成。
            qrCodeState = QRCodeState.SCANNED.getqRCodeState();
            // 订单更新
            Order order = userService.findOrder(orderId);
            if (order != null && order.getThisSite().equals(thisSite)) {
                int thisSiteInt = Integer.parseInt(order.getThisSite());
                // 订单运送中
                if (thisSiteInt < 10 && thisSiteInt > 0) {
                    userService.updateThisSite(order.getOrderId(), Integer.toString(thisSiteInt + 1));
                    // 订单更新完成之后，需要把当前二维码置位 null 。
                    qrCode = null;
                    // 更新二维码状态为 已过期。
                    qrCodeState = QRCodeState.OVERDUE.getqRCodeState();
                    return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), "更新成功");
                }
            }
        }
        System.out.println("二维码已经失效！！！");
        MyCodeState.FAIL.setCodeMsg("更新失败，二维码已失效");
        return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
    }

}
