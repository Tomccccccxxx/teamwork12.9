package courier.controller;

import user.config.MyCodeState;
import user.config.QRCodeState;
import user.config.Results;
import courier.pojo.Courier;
import user.pojo.Order;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 快递员 控制器接口。
 */
public interface CourierController {

    /**
     * 获取当前未到【目标站点】的所有订单号。
     *
     * @param orderId 订单号（可选）
     * @return 结果集。
     */
    Results<List<Courier>> getOrder(String orderId);

    /**
     * 通过扫码修改订单当前站点。
     *
     * @param orderId 订单号
     * @return 修改结果。
     */
    Results<Object> updateOrder(String orderId);

    /**
     * 获取二维码信息。
     *
     * @param orderId 订单号。
     * @return 返回二维码。
     */
    Results<String> getQRCode(String orderId);

    /**
     * 获取二维码状态。
     *
     * @param orderId 订单号（可选）
     * @return 二维码状态。
     */
    Results<Integer> getQRCodeState(String orderId);

    /**
     * 更新订单站点。【事务处理】【二维码扫码处理】
     *
     * @param orderId 订单号。
     * @param thisSite 当前站点。
     * @return 更新是否成功。
     */
    Results<String> updateThisSite(String orderId, String thisSite);

    /**
     * 快递丢失。【修改订单为丢失状态】【事务处理】
     *
     * @param orderId 订单号。
     * @return 修改结果。【成功 / 失败】
     */
    Results<String> lostCourier(String orderId);

    /**
     * 清除 二维码 状态。
     */
    void resetQRCode();

    /**
     * 更新当前站点。【事务化】
     *
     * @param orderId 订单号。
     * @param thisSite 当前站点。
     * @return 结果。
     */
    Results<String> updateThisSiteTran(String orderId, String thisSite);
}