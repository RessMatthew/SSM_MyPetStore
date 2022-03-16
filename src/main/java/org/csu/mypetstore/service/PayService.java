package org.csu.mypetstore.service;

import com.alipay.api.AlipayApiException;
import org.csu.mypetstore.vo.OrderVo;


public interface PayService {
    /*支付宝*/
    String aliPay(OrderVo orderVo) throws AlipayApiException;
}
