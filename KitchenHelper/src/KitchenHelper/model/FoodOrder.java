package KitchenHelper.model;

import java.util.Date;

public class FoodOrder {
	/*{"订单编号", "收货人姓名", "收货地址", "收货人手机号码", "订单状态"}*/
	public String getCell(int col) {
		if (col == 0) return String.valueOf(orderNo);
		else if (col == 1) return receiverName;
		else if (col == 2) return address;
		else if (col == 3) return phone;
		else if (col == 4) return orderStatus;
		else return "";
	}

	private int orderNo;
	private String userNo;
	private String receiverName;
	private Date deliverTime;
	private Date createTime;
	private String address;
	private String phone;
	private String orderStatus;


	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public Date getDeliverTime() {
		return deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}


}
