package KitchenHelper.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import KitchenHelper.model.UserInfo;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.BusinessException;
import KitchenHelper.util.DBUtil;
import KitchenHelper.util.DbException;

public class UserManager {

	public static UserInfo currentUser = null;

	public void deleteUser(String userNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from UserInfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("登陆账号不 存在");
			rs.close();
			pst.close();
			sql = "delete from UserInfo where userNo=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
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

	public void resetUserPwd(String userNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from UserInfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("登陆账号不 存在");
			rs.close();
			pst.close();
			sql = "update UserInfo set userPassword=? where userNo=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			pst.setString(2, userNo);
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

	public void createUser(UserInfo user) throws BaseException {
		if (user.getUserNo() == null || "".equals(user.getUserNo()) || user.getUserNo().length() > 20) {
			throw new BusinessException("登陆账号必须是1-20个字");
		}
		if (user.getUserName() == null || "".equals(user.getUserName()) || user.getUserName().length() > 50) {
			throw new BusinessException("账号名称必须是1-50个字");
		}
		if (!"admin".equals(user.getUserType())) {
			throw new BusinessException("用户类别必须是管理员");
		}

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Userinfo where userno=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("登陆账号已经存在");
			rs.close();
			pst.close();
			sql = "insert into Userinfo(userNo,userName,userPassword,userType,registerTime) values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNo());
			pst.setString(2, user.getUserName());
			user.setUserPassword(user.getUserNo());// 默认密码为账号
			pst.setString(3, user.getUserPassword());
			pst.setString(4, user.getUserType());
			pst.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
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

	public List<UserInfo> loadAllUsers() throws BaseException {
		List<UserInfo> result = new ArrayList<UserInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select userNo,userName,userType from userinfo";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				UserInfo u = new UserInfo();
				u.setUserNo(rs.getString(1));
				u.setUserName(rs.getString(2));
				u.setUserType(rs.getString(3));
				result.add(u);
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

	public void changePwd(UserInfo user, String oldPwd, String newPwd, String newPwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		if (newPwd == null || "".equals(newPwd))
			throw new BaseException("密码不能为空");
		try {
			conn = DBUtil.getConnection();
			String sql = "select userNo,userPassword from userinfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("用户不存在");
			if (!oldPwd.equals(rs.getString(2)))
				throw new BusinessException("原密码错误");
			if (!newPwd2.equals(newPwd))
				throw new BusinessException("两次密码不同");
			rs.close();
			pst.close();
			sql = "update userinfo set userPassword=? where userNo=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, newPwd2);
			pst.setString(2, user.getUserNo());
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

	public UserInfo loadUser(String userNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select userNo,userName,userPassword,userType from userinfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("登陆账号不 存在");
			UserInfo u = new UserInfo();
			u.setUserNo(rs.getString(1));
			u.setUserName(rs.getString(2));
			u.setUserPassword(rs.getString(3));
			u.setUserType(rs.getString(4));
			rs.close();
			pst.close();
			return u;
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

	public UserInfo reg(String userNo, String pwd, String pwd2) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		UserInfo user = new UserInfo();
		if ("".equals(userNo))
			throw new BusinessException("用户名不能为空");
		if ("".equals(pwd))
			throw new BusinessException("密码不能为空");
		if (!pwd.equals(pwd2))
			throw new BusinessException("注册失败,两次输入的密码必须一致");
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from userinfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("用户已存在，不能重复注册");
			rs.close();
			pst.close();
			sql = "insert into userinfo (userNo,userType,userPassword,registerTime) values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			pst.setString(2, "user");
			pst.setString(3, pwd2);
			pst.setTimestamp(4, new java.sql.Timestamp(System.currentTimeMillis()));
			user.setUserNo(userNo);
			user.setUserType("user");
			user.setUserPassword(pwd2);
			user.setRegisterTime(new Date());
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
}
