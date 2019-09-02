package KitchenHelper.model;

public class OrderDetail {
	/*{"订单编号", "食材名称", "数量", "价格","折扣"}*/
	public String getCell(int col) {
		if (col == 0) return String.valueOf(orderNo);
		else if (col == 1) return foodName;
		else if (col == 2) return String.valueOf(amount);
		else if (col == 3) return String.valueOf(price);
		else if (col == 4) return String.valueOf(discount);
		else return "";
	}

	private int orderNo;
	private String foodNo;
	private double amount;
	private double price;
	private double discount;

	private String foodName;

	public int getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(int orderNo) {
		this.orderNo = orderNo;
	}

	public String getFoodNo() {
		return foodNo;
	}

	public void setFoodNo(String foodNo) {
		this.foodNo = foodNo;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

}
