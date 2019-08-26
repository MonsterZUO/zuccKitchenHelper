package KitchenHelper.model;

public class RecipeInfo {

	//	private Object tblRecipeTitle[]={"菜谱编号", "菜谱名称", "贡献用户", "菜谱详情", "综合得分", "收藏数", "浏览数"};
	public String getCell(int col) {
		if (col == 0) return String.valueOf(recipeNo);
		else if (col == 1) return recipeName;
		else if (col == 2) return userNo;
		else if (col == 3) return recipeDetail;
		else if (col == 4) return String.valueOf(scoreTotal);
		else if (col == 5) return String.valueOf(collectCount);
		else if (col == 6) return String.valueOf(lookCount);
		else return "";
	}

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
