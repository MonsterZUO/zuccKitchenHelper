package KitchenHelper.model;

public class RecipeComment {
	public String getCell(int col) {
		if (col == 0) return recipeNo;
		else if (col == 1) return userNo;
		else if (col == 2) return comment;
		else return "";
	}

	private String recipeNo;
	private String userNo;
	private String comment;
	private Boolean look;
	private Boolean collect;
	private double score;

	public String getRecipeNo() {
		return recipeNo;
	}

	public void setRecipeNo(String recipeNo) {
		this.recipeNo = recipeNo;
	}

	public String getUserNo() {
		return userNo;
	}

	public void setUserNo(String userNo) {
		this.userNo = userNo;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Boolean getLook() {
		return look;
	}

	public void setLook(Boolean look) {
		this.look = look;
	}

	public Boolean getCollect() {
		return collect;
	}

	public void setCollect(Boolean collect) {
		this.collect = collect;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}


}
