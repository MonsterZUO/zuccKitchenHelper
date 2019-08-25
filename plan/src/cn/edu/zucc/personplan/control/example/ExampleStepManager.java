package cn.edu.zucc.personplan.control.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.personplan.itf.IStepManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanStep;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DbException;
import cn.edu.zucc.personplan.util.HibernateUtil;

public class ExampleStepManager implements IStepManager {

	@Override
	public void add(BeanPlan plan, String name, String planstartdate, String planfinishdate) throws BaseException {
		Connection conn = null;
		if (name == null || "".equals(name))
			throw new BusinessException("步骤名称不能为空");
		if (" ".equals(name))
			throw new BusinessException("步骤名称不能为空格");
		if (planstartdate == null || "".equals(planstartdate))
			throw new BusinessException("请输入计划开始时间");
		if (planfinishdate == null || "".equals(planfinishdate))
			throw new BusinessException("请输入计划结束时间");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date startDate = null;
		Date finishDate = null;
		try {
			startDate = sdf.parse(planstartdate);
			finishDate = sdf.parse(planfinishdate);
		} catch (Exception e) {
			throw new BusinessException("时间格式不正确，格式为：xxxx-xx-xx");
		}
		try {
			conn = DBUtil.getConnection();
			String sql = "select max(step_Order) from tbl_step where plan_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanId());
			java.sql.ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				i += rs.getInt(1);
			}
			rs.close();
			pst.close();
			sql = "insert into tbl_step(plan_Id,step_Order,step_Name,plan_Begin_time,plan_End_time,real_Begin_time,real_End_time) values(?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanId());
			pst.setInt(2, i);
			pst.setString(3, name);
			pst.setTimestamp(4, new java.sql.Timestamp(startDate.getTime()));
			pst.setTimestamp(5, new java.sql.Timestamp(finishDate.getTime()));
			pst.setDate(6, null);
			pst.setDate(7, null);
			pst.execute();
			pst.close();
			sql = "update tbl_plan set step_Count=step_Count+1 where plan_Id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

	public void addHb(BeanPlan plan, String name, String planstartdate, String planfinishdate) throws BaseException {
		if (name == null || "".equals(name))
			throw new BusinessException("步骤名称不能为空");
		if (" ".equals(name))
			throw new BusinessException("步骤名称不能为空格");
		if (planstartdate == null || "".equals(planstartdate))
			throw new BusinessException("请输入计划开始时间");
		if (planfinishdate == null || "".equals(planfinishdate))
			throw new BusinessException("请输入计划结束时间");

		Session session = HibernateUtil.getSession();
		BeanStep one = (BeanStep) session.get(BeanStep.class, plan.getPlanId());
		Transaction tx = session.beginTransaction();
		session.save(one);
		BeanPlan two = (BeanPlan) session.get(BeanPlan.class, plan.getPlanId());
		two.setStepCount(two.getStepCount() + 1);
		session.save(two);
		tx.commit();

	}

	@Override
	public List<BeanStep> loadSteps(BeanPlan plan) throws BaseException {
		List<BeanStep> result = new ArrayList<BeanStep>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from tbl_step where plan_Id=? order by step_Order";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanId());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanStep s = new BeanStep();
				s.setStepId(rs.getInt(1));
				s.setPlanId(rs.getInt(2));
				s.setStepOrder(rs.getInt(3));
				s.setStepName(rs.getString(4));
				s.setPlanBegindate(rs.getDate(5));
				s.setPlanEndDate(rs.getDate(6));
				s.setRealBeginDate(rs.getDate(7));
				s.setRealEndDate(rs.getDate(8));
				result.add(s);
			}
			rs.close();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return result;

	}

	public List<BeanStep> loadStepsHb(BeanPlan plan) throws BaseException {
		org.hibernate.query.Query qry = HibernateUtil.getSession().createQuery("from BeanStep");
		return qry.list();
	}

	@Override
	public void deleteStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "delete from tbl_step where step_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getStepId());
			pst.execute();
			pst.close();
			sql = "update tbl_step set step_Order=step_Order-1 where step_Id>?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getStepId());
			pst.execute();
			pst.close();
			if (step.getRealEndDate() == null && step.getRealBeginDate() != null) {
				sql = "update tbl_plan set step_Count=step_Count-1,start_Step_Count=start_Step_Count-1 where plan_Id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, step.getPlanId());
				pst.execute();
				pst.close();
			} else if (step.getRealEndDate() != null && step.getRealBeginDate() != null) {
				sql = "update tbl_plan set step_Count=step_Count-1,start_Step_Count=start_Step_Count-1,finished_Step_Count=finished_Step_Count-1 where plan_Id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, step.getPlanId());
				pst.execute();
				pst.close();
			} else {
				sql = "update tbl_plan set step_count=step_count-1 where plan_id=?";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, step.getPlanId());
				pst.executeUpdate();
				pst.close();
			}
			conn.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void startStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update tbl_step set real_Begin_time=? where step_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			pst.setInt(2, step.getStepId());
			pst.execute();
			pst.close();
			sql = "update tbl_plan set start_Step_Count=start_Step_Count+1 where plan_Id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getPlanId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	public void startStepHb(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Session session = HibernateUtil.getSession();
		BeanStep one = (BeanStep) session.get(BeanStep.class, step.getStepId());
		Transaction tx = session.beginTransaction();
		session.save(one);
		BeanPlan two = (BeanPlan) session.get(BeanPlan.class, step.getPlanId());
		two.setStartStepCount(two.getStartStepCount() + 1);
		session.update(two);
		tx.commit();

	}

	@Override
	public void finishStep(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update tbl_step set real_End_time=? where step_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			pst.setInt(2, step.getStepId());
			pst.execute();
			pst.close();
			sql = "update tbl_plan set finished_Step_Count=finished_Step_Count+1 where plan_Id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getPlanId());
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void moveUp(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		if (step.getStepOrder() == 1)
			throw new BusinessException("已经是第一个步骤");
		try {
			conn = DBUtil.getConnection();
			String sql = "select step_Id,step_Order from tbl_step where step_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getStepId());
			java.sql.ResultSet rs = pst.executeQuery();
			int Id = 0;
			int order = 0;
			while (rs.next()) {
				Id = rs.getInt(1);
				order = rs.getInt(2);
			}
			rs.close();
			pst.close();
			sql = "update tbl_step set step_Order=0 where step_Order=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, order - 1);
			pst.execute();
			pst.close();
			sql = "update tbl_step set step_Order=step_Order-1 where step_Id=? ";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, Id);
			pst.execute();
			pst.close();
			sql = "update tbl_step set step_Order=? where step_Order=0";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, order);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

	@Override
	public void moveDown(BeanStep step) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select max(step_Order) from tbl_step where plan_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getPlanId());
			java.sql.ResultSet rs = pst.executeQuery();
			int max = 0;
			while (rs.next()) {
				max = rs.getInt(1);
			}
			if (step.getStepOrder() == max)
				throw new BusinessException("已经是最后一个步骤");
			rs.close();
			pst.close();
			sql = "select step_Id,step_Order from tbl_step where step_Id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, step.getStepId());
			rs = pst.executeQuery();
			int Id = 0;
			int order = 0;
			while (rs.next()) {
				Id = rs.getInt(1);
				order = rs.getInt(2);
			}
			rs.close();
			pst.close();
			sql = "update tbl_step set step_Order=0 where step_Order=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, order + 1);
			pst.execute();
			pst.close();
			sql = "update tbl_step set step_Order=step_Order+1 where step_Id=? and step_Order<?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, Id);
			pst.setInt(2, max);
			pst.execute();
			pst.close();
			sql = "update tbl_step set step_Order=? where step_Order=0";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, order);
			pst.execute();
			pst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}

//	public static void main(String[] args){
//		BeanStep s=new BeanStep();
//		BeanPlan plan=new BeanPlan();
//		ExampleStepManager sm=new ExampleStepManager();
//		try {
//			
//			List<BeanStep> lst=sm.loadStepsHb(plan);
//			for(int i=0;i<lst.size();i++){
//				s=lst.get(i);
//				System.out.println(s.getStepId()+","+s.getPlanId()+","+s.getStepOrder()+","+s.getStepName()+","+s.getPlanBegindate()+","+s.getPlanEndDate()+","+s.getRealBeginDate()+","+s.getRealEndDate());
//			}
//		} catch (BaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}	
}
