package KitchenHelper.model;

public class RecipeStep {
	//	private Object tblStepTitle[] = {"²ËÆ×±àºÅ", "²½ÖèĞòºÅ", "²½ÖèÏêÇé"};
	public String getCell(int col) {
		if (col == 0) return recipeNo;
		else if (col == 1) return String.valueOf(stepNo);
		else if (col == 2) return stepDetail;
		else return "";
	}

	private String recipeNo;
	private int stepNo;
	private String stepDetail;

	public String getRecipeNo() {
		return recipeNo;
	}

	public void setRecipeNo(String recipeNo) {
		this.recipeNo = recipeNo;
	}

	public int getStepNo() {
		return stepNo;
	}

	public void setStepNo(int stepNo) {
		this.stepNo = stepNo;
	}

	public String getStepDetail() {
		return stepDetail;
	}

	public void setStepDetail(String stepDetail) {
		this.stepDetail = stepDetail;
	}
}
