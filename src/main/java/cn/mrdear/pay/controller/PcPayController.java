package cn.mrdear.pay.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.mrdear.pay.alipay.AliPayConfig;
import cn.mrdear.pay.model.Orders;
import cn.mrdear.pay.service.OrderService;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayDataDataserviceBillDownloadurlQueryRequest;
import com.alipay.api.request.AlipayTradeCloseRequest;
import com.alipay.api.request.AlipayTradeFastpayRefundQueryRequest;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradeQueryRequest;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.response.AlipayDataDataserviceBillDownloadurlQueryResponse;
import com.alipay.api.response.AlipayTradeCloseResponse;
import com.alipay.api.response.AlipayTradeFastpayRefundQueryResponse;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.example.util.IMoocJSONResult;
@Controller
@RequestMapping("pay")
public class PcPayController {
	private static final Logger logger = LoggerFactory.getLogger(PcPayController.class);
	@Autowired
	private OrderService orderService;
	/**
	 * 即时到账下单接口
	 * @param request
	 * @param response
	 * @throws AlipayApiException 
	 * @throws IOException 
	 */
	@RequestMapping("payOrder")
	public void payOrder(HttpServletRequest req, Model mod, HttpServletResponse rep,
			@RequestParam(value = "goodId", required = true)String goodId) throws AlipayApiException, IOException{
		logger.debug("进入获取订单信息接口方法");
		//系统下单
		Orders  param = new Orders();
		param.setGoodId(goodId);
		orderService.alipayOrder(param);   //生成订单信息，根据自己项目改动

		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);

		//设置请求参数
		AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
		alipayRequest.setReturnUrl(AliPayConfig.return_url);
		alipayRequest.setNotifyUrl(AliPayConfig.notify_url);

		//商户订单号，商户网站订单系统中唯一订单号，必填
		String out_trade_no = param.getTradeCode();
		//付款金额，必填
		String total_amount = param.getMoney().toString();
		//订单名称，必填
		String subject = param.getSubject();
		//商品描述，可空
		String body = param.getRemark();

		alipayRequest.setBizContent("{\"out_trade_no\":\""+ out_trade_no +"\"," 
				+ "\"total_amount\":\""+ total_amount +"\"," 
				+ "\"subject\":\""+ subject +"\"," 
				+ "\"body\":\""+ body +"\"," 
				+ "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");

		//请求
		String result = alipayClient.pageExecute(alipayRequest).getBody();

		rep.setContentType("text/html;charset=" + AliPayConfig.charset);
		rep.getWriter().write(result);//直接将完整的表单html输出到页面
		rep.getWriter().flush();
		rep.getWriter().close();
	}

	/**
	 * 同步回调路径return_url
	 * @param request
	 * @param response
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("return_url")
	public String returnUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, UnsupportedEncodingException{
		logger.info("进入同步回调路径return_url");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_public_key, AliPayConfig.charset,AliPayConfig.sign_type);
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			request.setAttribute("out_trade_no", out_trade_no);
			request.setAttribute("trade_no", trade_no);
			request.setAttribute("total_amount", total_amount);


			logger.info("订单处理：系统订单号" + out_trade_no + "支付宝交易号：" + trade_no);
			//系统处理根据支付宝回调更改订单状态或者其他关联表的数据
			Orders order = orderService.findOneByTradeCode(out_trade_no);
			if(order == null){
				signVerified = false;
				request.setAttribute("signVerified", signVerified); 
				request.setAttribute("reason", "商户订单号不存在");
				logger.error("系统订单："+ out_trade_no + "不存在。");
			}else{
				if(!order.getMoney().toString().equals(total_amount)){
					signVerified = false;
					request.setAttribute("signVerified", signVerified); 
					request.setAttribute("reason", "付款金额不对");
					return "notify_url";
				}


				if(order.getTradeStatus() == 1){//判断当前订单是否已处理，避免重复处理
					logger.info("系统订单："+ out_trade_no + "无需重复处理。");
				}else{
					order.setTradeStatus(1);//修改订单状态为已支付
					Date payedAt = new Date();
					order.setTransactionId(trade_no);
					order.setPayedAt(payedAt);
					orderService.payOrder(order);
					logger.info("系统订单："+ out_trade_no + "成功支付。");
				}

			}
		}else{
			request.setAttribute("reason", "验签失败");
		}
		request.setAttribute("signVerified", signVerified);
		return "return_url";
	}

	/**
	 * 异步回调路径notify_url
	 * @param request
	 * @param response
	 * @throws AlipayApiException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping("notify_url")
	public void notifyUrl(HttpServletRequest request, HttpServletResponse response) throws AlipayApiException, UnsupportedEncodingException{
		logger.info("进入异步回调路径notify_url");
		//获取支付宝POST过来反馈信息
		Map<String,String> params = new HashMap<String,String>();
		Map requestParams = request.getParameterMap();
		for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
			String name = (String) iter.next();
			String[] values = (String[]) requestParams.get(name);
			String valueStr = "";
			for (int i = 0; i < values.length; i++) {
				valueStr = (i == values.length - 1) ? valueStr + values[i]
						: valueStr + values[i] + ",";
			}
			//乱码解决，这段代码在出现乱码时使用。
			//valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
			params.put(name, valueStr);
		}
		//切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
		//boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String sign_type)
		boolean signVerified = AlipaySignature.rsaCheckV1(params, AliPayConfig.alipay_public_key, AliPayConfig.charset,AliPayConfig.sign_type);
		if(signVerified) {
			//商户订单号
			String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//支付宝交易号
			String trade_no = new String(request.getParameter("trade_no").getBytes("ISO-8859-1"),"UTF-8");

			//付款金额
			String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"),"UTF-8");

			request.setAttribute("out_trade_no", out_trade_no);
			request.setAttribute("trade_no", trade_no);
			request.setAttribute("total_amount", total_amount);


			logger.info("订单处理：系统订单号" + out_trade_no + "支付宝交易号：" + trade_no);
			//系统处理根据支付宝回调更改订单状态或者其他关联表的数据
			Orders order = orderService.findOneByTradeCode(out_trade_no);
			if(order == null){
				signVerified = false;
				request.setAttribute("signVerified", signVerified); 
				request.setAttribute("reason", "商户订单号不存在");
				logger.error("系统订单："+ out_trade_no + "不存在。");
			}else{
				if(!order.getMoney().toString().equals(total_amount)){
					signVerified = false;
					request.setAttribute("signVerified", signVerified); 
					request.setAttribute("reason", "付款金额不对");
				}


				if(order.getTradeStatus() == 1){//判断当前订单是否已处理，避免重复处理
					logger.info("系统订单："+ out_trade_no + "无需重复处理。");
				}else{
					order.setTradeStatus(1);//修改订单状态为已支付
					Date payedAt = new Date();
					order.setTransactionId(trade_no);
					order.setPayedAt(payedAt);
					orderService.payOrder(order);
					logger.info("系统订单："+ out_trade_no + "成功支付。");
				}

			}
		}else{
			request.setAttribute("reason", "验签失败");
		}
		response.setContentType("text/html;charset=" + AliPayConfig.charset);
		try {
			response.getWriter().write("success");//直接将完整的表单html输出到页面
			response.getWriter().flush();
			response.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 支付宝交易查询接口
	 * @param request
	 * @param response
	 * @throws UnsupportedEncodingException 
	 * @throws AlipayApiException 
	 */
	@RequestMapping("queryOrder")
	@ResponseBody
	public CommonResponse queryOrder(HttpServletRequest req, Model mod, HttpServletResponse rep,
			@RequestParam(value = "tradeCode", required = true)String tradeCode,
			@RequestParam(value = "tradeNo", required = true)String tradeNo) throws UnsupportedEncodingException, AlipayApiException{

		CommonResponse cr = new CommonResponse();
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type); //获得初始化的AlipayClient
		AlipayTradeQueryRequest request = new AlipayTradeQueryRequest();//创建API对应的request类
		request.setBizContent("{" +
				"   \"out_trade_no\":\""+tradeCode+"\"," +
				"   \"trade_no\":\""+tradeNo+"\"" +
				"  }");//设置业务参数
		//根据response中的结果继续业务逻辑处理
		String orderString = null;  
		try {
			//调用查询方法
			AlipayTradeQueryResponse response = alipayClient.execute(request);//通过alipayClient调用API，获得对应的response类
			if(response.isSuccess()){
				System.out.println("调用成功");
			} else {
				System.out.println("调用失败");
			}
			orderString = response.getBody();//就是orderString 可以直接给客户端请求，无需再做处理。
		} catch (AlipayApiException e) {
			e.printStackTrace();
		}
		cr.setData(orderString);    //返回orderString
		IMoocJSONResult.ok(cr);
		return cr;
	}
	/**
	 * 支付宝交易退款接口
	 */
	@RequestMapping("refundOrder")
	@ResponseBody
	public void refundOrder(HttpServletRequest req,HttpServletResponse rep,
			@RequestParam(value = "tradeCode")String tradeCode,
			@RequestParam(value = "tradeNo")String tradeNo,
			@RequestParam(value = "refundAmount", required = true)String refundAmount,
			@RequestParam(value = "refundReason")String refundReason,
			@RequestParam(value = "outrequestNo", required = true)String outrequestNo) throws UnsupportedEncodingException, AlipayApiException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);
		//设置请求参数
		AlipayTradeRefundRequest alipayRequest = new AlipayTradeRefundRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ tradeCode +"\"," 
				+ "\"trade_no\":\""+ tradeNo +"\"," 
				+ "\"refund_amount\":\""+ refundAmount +"\"," 
				+ "\"refund_reason\":\""+ refundReason +"\"," 
				+ "\"out_request_no\":\""+ outrequestNo +"\"}");
		//请求
		AlipayTradeRefundResponse response = alipayClient.execute(alipayRequest);
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		String result = alipayClient.execute(alipayRequest).getBody();
		//输出
		try {
			rep.getWriter().write(result);//直接将完整的表单html输出到页面
			rep.getWriter().flush();
			rep.getWriter().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 支付宝交易退款查询接口
	 */
	@RequestMapping("queryrefundOrder")
	@ResponseBody
	public void queryrefundOrder(HttpServletRequest req,HttpServletResponse rep,
			@RequestParam(value = "tradeCode")String tradeCode,
			@RequestParam(value = "tradeNo")String tradeNo,
			@RequestParam(value = "outrequestNo", required = true)String outrequestNo) throws AlipayApiException, IOException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);
		//设置请求参数
		AlipayTradeFastpayRefundQueryRequest alipayRequest = new AlipayTradeFastpayRefundQueryRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ tradeCode +"\"," 
				+"\"trade_no\":\""+ tradeNo +"\","
				+"\"out_request_no\":\""+ outrequestNo +"\"}");

		//请求
		AlipayTradeFastpayRefundQueryResponse response = alipayClient.execute(alipayRequest);
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		String result = alipayClient.execute(alipayRequest).getBody();
		rep.getWriter().write(result);//直接将完整的表单html输出到页面
		rep.getWriter().flush();
		rep.getWriter().close();
	}
	/**
	 * 即时到账交易关闭接口
	 */
	@RequestMapping("tradeClose")
	@ResponseBody
	public void tradeClose(HttpServletRequest req,HttpServletResponse rep,
			@RequestParam(value = "tradeCode")String tradeCode,
			@RequestParam(value = "tradeNo")String tradeNo) throws AlipayApiException, IOException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);

		//设置请求参数
		AlipayTradeCloseRequest alipayRequest = new AlipayTradeCloseRequest();
		alipayRequest.setBizContent("{\"out_trade_no\":\""+ tradeCode +"\"," +"\"trade_no\":\""+ tradeNo +"\"}");

		//请求
		AlipayTradeCloseResponse response = alipayClient.execute(alipayRequest);
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		String result = alipayClient.execute(alipayRequest).getBody();
		rep.getWriter().write(result);//直接将完整的表单html输出到页面
		rep.getWriter().flush();
		rep.getWriter().close();
	}
	/**
	 *  查询对账单下载地址接口
	 */
	@RequestMapping("tradedownloadurl")
	@ResponseBody
	public void tradedownloadurl(HttpServletRequest req,HttpServletResponse rep,
			@RequestParam(value = "billtype")String billType,
			@RequestParam(value = "billdate")String billDate) throws AlipayApiException, IOException{
		//获得初始化的AlipayClient
		AlipayClient alipayClient = new DefaultAlipayClient(AliPayConfig.gatewayUrl, AliPayConfig.app_id, AliPayConfig.merchant_private_key, "json", AliPayConfig.charset, AliPayConfig.alipay_public_key, AliPayConfig.sign_type);
		AlipayDataDataserviceBillDownloadurlQueryRequest alipayRequest = new AlipayDataDataserviceBillDownloadurlQueryRequest();//创建API对应的request类
		alipayRequest.setBizContent("{" +
				"    \"bill_type\":\"trade\"," +
				"   \"bill_date\":\""+billDate+"\"" +
				"  }");//设置业务参数
		AlipayDataDataserviceBillDownloadurlQueryResponse response = alipayClient.execute(alipayRequest);
		System.out.print(response.getBody());
		String urlStr = response.getBillDownloadUrl();
		downloadBillurl(urlStr);
	}
	/**
	 * 下载账单
	 */
	public void downloadBillurl(String urlStr){
		//指定希望保存的文件路径
		String filePath = "d:/logo/fund_bill_20160405.csv";
		URL url = null;
		HttpURLConnection httpUrlConnection = null;
		InputStream fis = null;
		FileOutputStream fos = null;
		try {
			url = new URL(urlStr);
			httpUrlConnection = (HttpURLConnection) url.openConnection();
			httpUrlConnection.setConnectTimeout(5 * 1000);
			httpUrlConnection.setDoInput(true);
			httpUrlConnection.setDoOutput(true);
			httpUrlConnection.setUseCaches(false);
			httpUrlConnection.setRequestMethod("GET");
			httpUrlConnection.setRequestProperty("Charsert", "UTF-8");
			httpUrlConnection.connect();
			fis = httpUrlConnection.getInputStream();
			byte[] temp = new byte[1024];
			int b;
			fos = new FileOutputStream(new File(filePath));
			while ((b = fis.read(temp)) != -1) {
				fos.write(temp, 0, b);
				fos.flush();
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if(fis!=null) fis.close();
				if(fos!=null) fos.close();
				if(httpUrlConnection!=null) httpUrlConnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
