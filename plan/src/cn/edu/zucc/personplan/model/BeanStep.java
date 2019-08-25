package cn.edu.zucc.personplan.model;

import java.util.Date;

public class BeanStep {
	public static final String[] tblStepTitle={"序号","名称","计划开始时间","计划完成时间","实际开始时间","实际完成时间"};
	/**
	 * 请自行根据javabean的设计修改本函数代码，col表示界面表格中的列序号，0开始
	 */
	public String getCell(int col){
		if(col==0) return String.valueOf(stepOrder);
		else if(col==1) return stepName;
		else if(col==2) return String.valueOf(planBegindate);
		else if(col==3) return String.valueOf(planEndDate);
		else if(col==4) return String.valueOf(realBeginDate);
		else if(col==5) return String.valueOf(realEndDate);
		else return "";
	}
	
	private int stepId;
	private int planId;
	private int stepOrder;
	private String stepName;
	private Date planBegindate;
	private Date planEndDate;
	private Date realBeginDate;
	private Date realEndDate;
	
	public int getStepId() {
		return stepId;
	}
	public void setStepId(int stepId) {
		this.stepId = stepId;
	}
	public int getPlanId() {
		return planId;
	}
	public void setPlanId(int planId) {
		this.planId = planId;
	}
	public int getStepOrder() {
		return stepOrder;
	}
	public void setStepOrder(int stepOrder) {
		this.stepOrder = stepOrder;
	}
	public String getStepName() {
		return stepName;
	}
	public void setStepName(String stepName) {
		this.stepName = stepName;
	}
	public Date getPlanBegindate() {
		return planBegindate;
	}
	public void setPlanBegindate(Date planBegindate) {
		this.planBegindate = planBegindate;
	}
	public Date getPlanEndDate() {
		return planEndDate;
	}
	public void setPlanEndDate(Date planEndDate) {
		this.planEndDate = planEndDate;
	}
	public Date getRealBeginDate() {
		return realBeginDate;
	}
	public void setRealBeginDate(Date realBeginDate) {
		this.realBeginDate = realBeginDate;
	}
	public Date getRealEndDate() {
		return realEndDate;
	}
	public void setRealEndDate(Date realEndDate) {
		this.realEndDate = realEndDate;
	}

}
