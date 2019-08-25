package cn.edu.zucc.personplan.model;

import java.util.Date;

public class BeanPlan {
	public static final String[] tableTitles={"序号","名称","步骤数","已完成数"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	public String getCell(int col){
		if(col==0) return String.valueOf(planOrder);
		else if(col==1) return planName;
		else if(col==2) return String.valueOf(stepCount);
		else if(col==3) return String.valueOf(finishedStepCount);
		else return "";
	}  


	private int planId;
	private String userId;
	private int planOrder;	
	private String planName;
	private Date createDate;
	private int stepCount;
	private int startStepCount;
	private int finishedStepCount;

	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String string) {
		this.userId = string;
	}
	public int getPlanOrder() {
		return planOrder;
	}
	public void setPlanOrder(int planOrder) {
		this.planOrder = planOrder;
	}
	public String getPlanName() {
		return planName;
	}
	public void setPlanName(String planName) {
		this.planName = planName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public int getStepCount() {
		return stepCount;
	}
	public void setStepCount(int stepCount) {
		this.stepCount = stepCount;
	}
	public int getStartStepCount() {
		return startStepCount;
	}
	public void setStartStepCount(int startStepCount) {
		this.startStepCount = startStepCount;
	}

	public int getFinishedStepCount() {
		return finishedStepCount;
	}
	public void setFinishedStepCount(int finishedStepCount) {
		this.finishedStepCount = finishedStepCount;
	}

}
