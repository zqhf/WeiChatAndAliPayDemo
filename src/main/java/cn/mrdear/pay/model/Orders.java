package cn.mrdear.pay.model;

import java.io.Serializable;
import java.util.Date;

public class Orders implements Serializable{
	
	private static final long serialVersionUID = -9147291616286081197L;
	
	private Integer status;
	private String goodId;//订单ID
	private String tradeCode;//订单号
	private String money;//金额
	private String subject;//订单名称
	private String remark;//订单描述
	private Integer tradeStatus;
	private String transactionId;
	private Date payedAt;
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getGoodId() {
		return goodId;
	}
	public void setGoodId(String goodId) {
		this.goodId = goodId;
	}
	public String getTradeCode() {
		return tradeCode;
	}
	public void setTradeCode(String tradeCode) {
		this.tradeCode = tradeCode;
	}
	public String getMoney() {
		return money;
	}
	public void setMoney(String money) {
		this.money = money;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getTradeStatus() {
		return tradeStatus;
	}
	public void setTradeStatus(Integer tradeStatus) {
		this.tradeStatus = tradeStatus;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public Date getPayedAt() {
		return payedAt;
	}
	public void setPayedAt(Date payedAt) {
		this.payedAt = payedAt;
	}
}
