package merchant.service;

import merchant.mapper.MerchantMapper;
import merchant.pojo.MerchantOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class MerchantServiceImpl implements IMerchantService {

    @Autowired
    private MerchantMapper merchantMapper;

    @Override
    public List<MerchantOrder> findOrderAll() {
        return merchantMapper.findOrderAll();
    }

    @Override
    @Transactional
    public void updateOrderState(String orderId, String orderState) {
        merchantMapper.updateOrderState(orderId, orderState);
    }

    @Override
    @Transactional
    public void addOrder(MerchantOrder merchantOrder) {
        merchantMapper.addOrder(merchantOrder);
    }

    @Override
    @Transactional
    public int deleteOrder(String orderId) {
        return merchantMapper.deleteOrder(orderId);
    }

    @Override
    @Deprecated
    public Boolean updateOrderState(String orderId) {
        // 该方法已被废弃，不再使用。
        return null;
    }
}