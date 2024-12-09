package courier.controller.impl;

import courier.controller.CourierController;
import user.config.MyCodeState;
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

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/courier")
public class CourierControllerImpl implements CourierController {

    private static String qrCode;
    private static Integer qrCodeState = QRCodeState.FAIL.getqRCodeState();

    @Autowired
    private UserService userService;

    @Autowired
    private CourierService courierService;

    @Autowired
    private MerchantService merchantService;

    @Value("${server.port}")
    private String port;

    @Value("${server.ipv4}")
    private String innetIp;

    // Implementations of the methods defined in the CourierController interface...

    @Override
    public Results<List<Courier>> getOrder(String orderId) {
        try {
            List<Courier> orderAll = courierService.findOrderAll();
            if (orderAll == null) {
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), null);
            }
            return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), orderAll);
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    public Results<Object> updateOrder(String orderId) {
        try {
            Boolean isOrderId = courierService.isOrderId(orderId);
            if (isOrderId != null && isOrderId) {
                Order order = userService.findOrder(orderId);
                if (order != null) {
                    if (Integer.parseInt(order.getThisSite()) < 10) {
                        userService.updateThisSite(order.getOrderId(), Integer.toString(Integer.parseInt(order.getThisSite()) + 1));
                    } else if (Integer.parseInt(order.getThisSite()) >= 10) {
                        MyCodeState.FAIL.setCodeMsg("订单已送达，请不要重复操作。");
                        return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
                    }
                }
                MyCodeState.FAIL.setCodeMsg("订单号不存在");
                return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
            } else {
                MyCodeState.FAIL.setCodeMsg("订单号不存在");
                return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
            }
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    public Results<String> getQRCode(String orderId) {
        System.out.println("\n================== 二维码 =====================");
        System.out.println("二维码生成要扫码的订单号：orderId = " + orderId);
        try {
            Order order = userService.findOrder(orderId);
            if (innetIp == null) {
                this.innetIp = AddressUtils.getInnetIp();
                System.out.println("二维码要访问的IP【内网】：innetIp = " + innetIp);
            } else {
                System.out.println("二维码要访问的IP【服务器】：innetIp = " + innetIp);
            }
            System.out.println("================ 二维码 =======================\n");
            qrCode = QRCodeUtil.createQRCode("http://" + innetIp + ":" + port + "/courier/order/thisSite/" + orderId + "/" + order.getThisSite());
            qrCodeState = QRCodeState.NOTSCAN.getqRCodeState();
        } catch (IOException e) {
            MyCodeState.FAIL.setCodeMsg("二维码生成失败。");
            return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
        }
        return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), qrCode);
    }

    @Override
    public Results<Integer> getQRCodeState(String orderId) {
        if (qrCode == null) {
            qrCodeState = QRCodeState.FAIL.getqRCodeState();
            MyCodeState.FAIL.setCodeMsg("二维码已失效");
            return new Results<>(QRCodeState.FAIL.toString(), QRCodeState.FAIL.getqRCodeMsg(), null);
        }
        String qRCodeMsg = null;
        for (QRCodeState state : QRCodeState.values()) {
            if (this.qrCodeState.equals(state.getqRCodeState())) {
                qRCodeMsg = state.getqRCodeMsg();
                System.out.println("二维码扫描状态：qRCodeMsg = " + qRCodeMsg);
            }
        }
        return new Results<>(QRCodeState.SUCCESS.toString(), qRCodeMsg == null ? "" : qRCodeMsg, qrCodeState);
    }

    @Override
    public Results<String> updateThisSite(String orderId, String thisSite) {
        try {
            return updateThisSiteTran(orderId, thisSite);
        } catch (Exception e) {
            MyCodeState.FAIL.setCodeMsg("服务器发生了错误");
            return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    public Results<String> lostCourier(String orderId) {
        try {
            Order order = userService.findOrder(orderId);
            if (order != null) {
                try {
                    courierService.lostCourier(order);
                    return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), "修改成功");
                } catch (Exception e) {
                    System.out.println("【快递丢失】状态发送错误。");
                    MyCodeState.FAIL.setCodeMsg("修改失败");
                    return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), "修改失败");
                }
            } else {
                MyCodeState.NULL.setCodeMsg("订单不存在");
                return new Results<>(MyCodeState.NULL.toString(), MyCodeState.NULL.getCodeMsg(), "修改失败");
            }
        } catch (Throwable throwable) {
            MyCodeState.DATABASEERROR.setCodeMsg("数据库连接失败。");
            return new Results<>(MyCodeState.DATABASEERROR.toString(), MyCodeState.DATABASEERROR.getCodeMsg(), null);
        }
    }

    @Override
    public void resetQRCode() {
        qrCode = null;
    }

    @Override
    @Transactional
    public Results<String> updateThisSiteTran(String orderId, String thisSite) {
        if (qrCode != null) {
            qrCodeState = QRCodeState.SCANNED.getqRCodeState();
            Order order = userService.findOrder(orderId);
            if (order != null && order.getThisSite().equals(thisSite)) {
                int thisSiteInt = Integer.parseInt(order.getThisSite());
                if (thisSiteInt < 10 && thisSiteInt > 0) {
                    userService.updateThisSite(order.getOrderId(), Integer.toString(thisSiteInt + 1));
                    qrCode = null;
                    qrCodeState = QRCodeState.OVERDUE.getqRCodeState();
                    return new Results<>(MyCodeState.SUCCESS.toString(), MyCodeState.SUCCESS.getCodeMsg(), "更新成功");
                }
            }
        }
        MyCodeState.FAIL.setCodeMsg("更新失败，二维码已失效");
        return new Results<>(MyCodeState.FAIL.toString(), MyCodeState.FAIL.getCodeMsg(), null);
    }
}