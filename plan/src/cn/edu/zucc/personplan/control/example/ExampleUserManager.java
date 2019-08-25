package cn.edu.zucc.personplan.control.example;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.edu.zucc.personplan.itf.IUserManager;
import cn.edu.zucc.personplan.model.BeanUser;
import cn.edu.zucc.personplan.util.BaseException;
import cn.edu.zucc.personplan.util.BusinessException;
import cn.edu.zucc.personplan.util.DBUtil;
import cn.edu.zucc.personplan.util.DBUtil2;
import cn.edu.zucc.personplan.util.DbException;
import cn.edu.zucc.personplan.util.HibernateUtil;

public class ExampleUserManager implements IUserManager {

	@Override
	public BeanUser reg(String userid, String pwd, String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		BeanUser user = new BeanUser();
		if ("".equals(userid))
			throw new BusinessException("用户名不能为空");
		if ("".equals(pwd))
			throw new BusinessException("密码不能为空");
		if (!pwd.equals(pwd2))
			throw new BusinessException("注册失败,两次输入的密码必须一致");
		try {
			conn = DBUtil2.getInstance().getConnection();
			String sql = "select * from tbl_user where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("用户已存在，不能重复注册");
			rs.close();
			pst.close();
			sql = "insert into tbl_user (user_id,user_pwd,register_time) values(?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			pst.setString(2, pwd);
			pst.setTimestamp(3, new java.sql.Timestamp(System.currentTimeMillis()));
			user.setUserId(userid);
			user.setPwd(pwd2);
			user.setRegisterDate(new Date());
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
		return user;
	}

	public BeanUser regHb(String userid, String pwd, String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		if ("".equals(userid))
			throw new BusinessException("用户名不能为空");
		if ("".equals(pwd))
			throw new BusinessException("密码不能为空");
		if (!pwd.equals(pwd2))
			throw new BusinessException("注册失败,两次输入的密码必须一致");
		Session session = HibernateUtil.getSession();
		BeanUser one = (BeanUser) session.get(BeanUser.class, userid);
		if (one != null)
			throw new BusinessException("用户已存在，不能重复注册");

		Transaction tx = session.beginTransaction();
		session.save(one);
		tx.commit();
		return one;

	}

	@Override
	public BeanUser login(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		BeanUser user = new BeanUser();
		if ("".equals(userid))
			throw new BusinessException("用户名不能为空");
		if ("".equals(pwd))
			throw new BusinessException("密码不能为空");
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_id,user_pwd from tbl_user where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userid);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("用户不存在");
			if (!pwd.equals(rs.getString(2)))
				throw new BusinessException("密码错误");
			user.setUserId(userid);
			user.setPwd(pwd);
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
		return user;
	}

	public BeanUser loginHb(String userid, String pwd) throws BaseException {
		// TODO Auto-generated method stub
		if ("".equals(userid))
			throw new BusinessException("用户名不能为空");
		if ("".equals(pwd))
			throw new BusinessException("密码不能为空");
		Session session = HibernateUtil.getSession();
		BeanUser one = (BeanUser) session.get(BeanUser.class, userid);
		if (one == null)
			throw new BusinessException("用户不存在");
		if (!pwd.equals(one.getPwd()))
			throw new BusinessException("密码错误");

		Transaction tx = session.beginTransaction();
		session.save(one);
		tx.commit();
		return one;

	}

	@Override
	public void changePwd(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		if (newPwd == null || "".equals(newPwd))
			throw new BaseException("密码不能为空");
		try {
			conn = DBUtil.getConnection();
			String sql = "select user_id,user_pwd from tbl_user where user_id=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserId());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("用户不存在");
			if (!oldPwd.equals(rs.getString(2)))
				throw new BusinessException("原密码错误");
			if (!newPwd2.equals(newPwd))
				throw new BusinessException("两次密码不同");
			rs.close();
			pst.close();
			sql = "update tbl_user set user_pwd=? where user_id=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPwd2);
			pst.setString(2, user.getUserId());
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
//		BeanUser u=new BeanUser();
//		ExampleUserManager um=new ExampleUserManager();
//        try {
// 			um.login("zucc", "zucc");
// 		} catch (BaseException e) {
// 			// TODO Auto-generated catch block
// 			e.printStackTrace();
// 		}
//
//	}   
	public void changePwdHb(BeanUser user, String oldPwd, String newPwd, String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		if (newPwd == null || "".equals(newPwd))
			throw new BaseException("密码不能为空");
		Session session = HibernateUtil.getSession();
		user = (BeanUser) session.get(BeanUser.class, user.getUserId());
		if (user == null)
			throw new BusinessException("用户不存在");
		if (user.getPwd().equals(oldPwd) == false)
			throw new BusinessException("原始密码错误");
		if (!newPwd2.equals(newPwd))
			throw new BusinessException("两次密码不同");

		Transaction tx = session.beginTransaction();
		BeanUser two = (BeanUser) session.get(BeanUser.class, user.getUserId());
		two.setPwd(newPwd);
		session.save(two);
		tx.commit();

	}

}
