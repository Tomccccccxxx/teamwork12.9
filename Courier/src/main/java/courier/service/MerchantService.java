package merchant.service;

/**
 * 商家服务接口。
 */
public interface MerchantService {

    /**
     * 更新订单状态。
     *
     * @param orderId   订单号。
     * @param newState  新的状态。
     */
    void updateOrderState(String orderId, String newState);
}