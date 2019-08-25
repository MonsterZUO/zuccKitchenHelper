package cn.edu.zucc.personplan.control.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.personplan.itf.IPlanManager;
import cn.edu.zucc.personplan.model.BeanPlan;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DbException;
import cn.edu.zucc.personplan.util.HibernateUtil;

public class ExamplePlanManager implements IPlanManager {

	@Override
	public BeanPlan addPlan(String name) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		BeanPlan p = new BeanPlan();
		if (name == null || "".equals(name))
			throw new BusinessException("计划名称不能为空");
		if (" ".equals(name))
			throw new BusinessException("计划名不能为空格");
		try {
			conn = DBUtil.getConnection();
			String sql = "select max(plan_Order) from tbl_plan where user_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			int i = 1;
			while (rs.next()) {
				i += rs.getInt(1);
			}
			rs.close();
			pst.close();
			sql = "insert into tbl_plan(user_Id,plan_Order,plan_Name,create_time,step_Count,start_Step_Count,finished_Step_Count) values(?,?,?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUserId());
			pst.setInt(2, i);
			pst.setString(3, name);
			pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setInt(5, 0);
			pst.setInt(6, 0);
			pst.setInt(7, 0);
			p.setUserId(BeanUser.currentLoginUser.getUserId());
			p.setPlanOrder(i);
			p.setCreateDate(new java.sql.Timestamp(System.currentTimeMillis()));
			p.setStepCount(0);
			p.setStartStepCount(0);
			p.setFinishedStepCount(0);
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
		return p;
	}

	public BeanPlan addPlanHb(String name) throws BaseException {
		// TODO Auto-generated method stub
		BeanPlan plan = new BeanPlan();
		if (name == null || "".equals(name))
			throw new BusinessException("计划名称不能为空");
		if (" ".equals(name))
			throw new BusinessException("计划名不能为空格");
		Session session = HibernateUtil.getSession();
		BeanPlan one = (BeanPlan) session.get(BeanPlan.class, plan.getUserId());

		Transaction tx = session.beginTransaction();
		session.save(plan);
		tx.commit();
		return plan;

	}

	@Override
	public List<BeanPlan> loadAll() throws BaseException {
		List<BeanPlan> result = new ArrayList<BeanPlan>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from tbl_plan where user_Id=? order by plan_Order";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, BeanUser.currentLoginUser.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				BeanPlan p = new BeanPlan();
				p.setPlanId(rs.getInt(1));
				p.setUserId(rs.getString(2));
				p.setPlanOrder(rs.getInt(3));
				p.setPlanName(rs.getString(4));
				p.setCreateDate(rs.getDate(5));
				p.setStepCount(rs.getInt(6));
				p.setStartStepCount(rs.getInt(7));
				p.setFinishedStepCount(rs.getInt(8));
				result.add(p);
			}
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

	public List<BeanPlan> loadAllHb() throws BaseException {
		org.hibernate.query.Query qry = HibernateUtil.getSession().createQuery("from BeanPlan");
		return qry.list();
	}

	@Override
	public void deletePlan(BeanPlan plan) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			conn.setAutoCommit(false);
			String sql = "select step_Count from tbl_plan where plan_Id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanId());
			java.sql.ResultSet rs = pst.executeQuery();
			int n = 0;
			while (rs.next()) {
				n = rs.getInt(1);
			}
			if (n > 0)
				throw new BusinessException("该计划已有步骤，不能删除");
			rs.close();
			pst.close();
			sql = "delete from tbl_plan where plan_Id=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, plan.getPlanId());
			pst.execute();
			pst.close();
			sql = "update tbl_plan set plan_Order=plan_Order-1 where user_Id=? and plan_Order>?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, plan.getUserId());
			pst.setInt(2, plan.getPlanOrder());
			pst.execute();
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

	public void deletePlanHb(BeanPlan plan) throws BaseException {
		Session session = HibernateUtil.getSession();
		BeanPlan one = (BeanPlan) session.get(BeanPlan.class, plan.getPlanId());
		if (one.getStepCount() > 0)
			throw new BusinessException("该计划已有步骤，不能删除");

		Transaction tx = session.beginTransaction();
		BeanPlan two = (BeanPlan) session.get(BeanPlan.class, plan.getPlanId());
		two.setPlanOrder(one.getPlanOrder() - 1);
		session.save(two);
		tx.commit();

	}
//	
//	public static void main(String[] args){
//		BeanPlan p=new BeanPlan();
//		ExamplePlanManager pm=new ExamplePlanManager();
//		try {
//			List<BeanPlan> lst=pm.loadAllHb();	
//			for(int i=0;i<lst.size();i++){
//				p=lst.get(i);
//				System.out.println(p.getPlanId()+","+p.getUserId()+","+p.getPlanOrder()+","+p.getPlanName()+","+p.getCreateDate()+","+p.getStepCount()+","+p.getStartStepCount()+","+p.getFinishedStepCount());
//			}
//			pm.addPlanHb("zzy");
//			System.out.println("添加成功");
//		} catch (BaseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
}
