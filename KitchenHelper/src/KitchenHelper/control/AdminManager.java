package KitchenHelper.control;

public class AdminManager {
//	public static AdminInfo currentUser = null;
//
//	public AdminInfo loadUser(String adminNo) throws BaseException {
//		Connection conn = null;
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "select adminNo,adminName,adminPassword,adminType from admininfo where adminNo=?";
//			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setString(1, adminNo);
//			java.sql.ResultSet rs = pst.executeQuery();
//			if (!rs.next())
//				throw new BusinessException("µÇÂ½ÕËºÅ²» ´æÔÚ");
//			AdminInfo u = new AdminInfo();
//			u.setAdminNo(rs.getString(1));
//			u.setAdminName(rs.getString(2));
//			u.setAdminPassword(rs.getString(3));
//			u.setAdminType(rs.getString(4));
//			rs.close();
//			pst.close();
//			return u;
//		} catch (SQLException e) {
//			e.printStackTrace();
//			throw new DbException(e);
//		} finally {
//			if (conn != null)
//				try {
//					conn.close();
//				} catch (SQLException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
//
//	}

}
