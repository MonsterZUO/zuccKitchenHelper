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
				throw new BusinessException("��½�˺Ų� ����");
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
				throw new BusinessException("��½�˺Ų� ����");
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
			throw new BusinessException("��½�˺ű�����1-20����");
		}
		if (user.getUserName() == null || "".equals(user.getUserName()) || user.getUserName().length() > 50) {
			throw new BusinessException("�˺����Ʊ�����1-50����");
		}
		if (!"admin".equals(user.getUserType())) {
			throw new BusinessException("�û��������ǹ���Ա");
		}

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from Userinfo where userno=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("��½�˺��Ѿ�����");
			rs.close();
			pst.close();
			sql = "insert into Userinfo(userNo,userName,userPassword,userType,registerTime) values(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNo());
			pst.setString(2, user.getUserName());
			user.setUserPassword(user.getUserNo());// Ĭ������Ϊ�˺�
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
			throw new BaseException("���벻��Ϊ��");
		try {
			conn = DBUtil.getConnection();
			String sql = "select userNo,userPassword from userinfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, user.getUserNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next())
				throw new BusinessException("�û�������");
			if (!oldPwd.equals(rs.getString(2)))
				throw new BusinessException("ԭ�������");
			if (!newPwd2.equals(newPwd))
				throw new BusinessException("�������벻ͬ");
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
				throw new BusinessException("��½�˺Ų� ����");
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
			throw new BusinessException("�û�������Ϊ��");
		if ("".equals(pwd))
			throw new BusinessException("���벻��Ϊ��");
		if (!pwd.equals(pwd2))
			throw new BusinessException("ע��ʧ��,����������������һ��");
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from userinfo where userNo=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, userNo);
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("�û��Ѵ��ڣ������ظ�ע��");
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
