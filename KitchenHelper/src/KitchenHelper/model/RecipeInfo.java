package KitchenHelper.model;

public class RecipeInfo {
	private String recipeNo;
	private String recipeName;
	private String userNo;
	private String recipeDetail;
	private double scoreTotal;
	private int collectCount;
	private int lookCount;

	public String getRecipeNo() {
		return recipeNo;
	}

	public void setRecipeNo(String recipeNo) {
		this.recipeNo = recipeNo;
	}

	public String getRecipeName() {
		return recipeName;
	}

	public void setRecipeName(String recipeName) {
		this.recipeName = recipeName;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getRecipeDetail() {
		return recipeDetail;
	}

	public void setRecipeDetail(String recipeDetail) {
		this.recipeDetail = recipeDetail;
	}

	public double getScoreTotal() {
		return scoreTotal;
	}

	public void setScoreTotal(double scoreTotal) {
		this.scoreTotal = scoreTotal;
	}

	public int getCollectCount() {
		return collectCount;
	}

	public void setCollectCount(int collectCount) {
		this.collectCount = collectCount;
	}

	public int getLookCount() {
		return lookCount;
	}

	public void setLookCount(int lookCount) {
		this.lookCount = lookCount;
	}
}
