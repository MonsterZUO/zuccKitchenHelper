package KitchenHelper.model;

public class RecipeUse {
	//	private Object tblUseTitle[] = {"食材名称","数量","单位", };
	public String getCell(int col) {
		if (col == 0) return recipeNo;
		else if (col == 1) return foodNo;
		else if (col == 2) return String.valueOf(amount);
		else if (col == 3) return unit;
		else return "";
	}

	private String recipeNo;
	private String foodNo;
	private double amount;
	private String unit;

	private String foodName;//只储存食材名称,不在用料表中保存食材名称

	public String getRecipeNo() {
		return recipeNo;
	}

	public void setRecipeNo(String recipeNo) {
		this.recipeNo = recipeNo;
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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getFoodName() {
		return foodName;
	}

	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}

}
