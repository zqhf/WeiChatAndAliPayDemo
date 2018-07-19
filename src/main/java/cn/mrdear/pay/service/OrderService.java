package cn.mrdear.pay.service;

import java.util.Map;

import cn.mrdear.pay.model.Orders;

import com.example.controller.ServerData;

public interface OrderService {

	public ServerData pay(Long orderNo, String path, Integer id);

	public ServerData alicallback(Map<String, String> params);

	public ServerData queryOrderPayStatus(Long orderNo, Integer id);

	public void alipayOrder(Orders param);

	public Orders findOneByTradeCode(String out_trade_no);

	public void payOrder(Orders order);

}
