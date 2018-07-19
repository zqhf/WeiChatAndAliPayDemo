package cn.mrdear.pay.service.impl;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import cn.mrdear.pay.model.Orders;
import cn.mrdear.pay.service.OrderService;

import com.example.controller.ServerData;
import com.util.RandomGenretorUtils;
@Service(value = "orderService")
public class OrderServiceImpl implements OrderService{
	private Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);
	@Override
	public ServerData pay(Long orderNo, String path, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerData alicallback(Map<String, String> params) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServerData queryOrderPayStatus(Long orderNo, Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void alipayOrder(Orders param) {
		String ordercode=RandomGenretorUtils.generateSerialNumber(param.getGoodId().toString());
		logger.debug("生成的订单号"+ordercode);
		param.setTradeCode(ordercode);
		param.setMoney("0.01");
		param.setSubject("订单"+ordercode);
		param.setRemark("订单描述");
	}

	@Override
	public Orders findOneByTradeCode(String out_trade_no) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void payOrder(Orders order) {
		// TODO Auto-generated method stub
		
	}

}
