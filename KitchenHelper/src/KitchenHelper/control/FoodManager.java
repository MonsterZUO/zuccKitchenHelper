package KitchenHelper.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.BusinessException;
import KitchenHelper.util.DBUtil;
import KitchenHelper.util.DbException;

public class FoodManager {

	public void deleteFoodType(int id) throws BaseException {
		if (id <= 0) {
			throw new BusinessException("ʳ�����ID�����Ǵ���0������");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select foodTypeName from foodtypeinfo where foodTypeNo=" + id;
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				throw new BusinessException("ʳ����𲻴���");
			String readerTypeName = rs.getString(1);
			rs.close();
			sql = "select count(*) from foodinfo where foodTypeNo=" + id;
			rs = st.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			if (n > 0)
				throw new BusinessException("�Ѿ���" + n + "��ʳ����" + readerTypeName + "�ˣ�����ɾ��");
			st.execute("delete from foodtypeinfo where foodTypeNo=" + id);
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

	public void modifyFoodType(FoodTypeInfo rt) throws BaseException {
		if (rt.getFoodTypeNo() <= 0) {
			throw new BusinessException("ʳ�����ID�����Ǵ���0������");
		}
		if (rt.getFoodTypeName() == null || "".equals(rt.getFoodTypeName()) || rt.getFoodTypeName().length() > 20) {
			throw new BusinessException("ʳ��������Ʊ�����1-20����");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from foodtypeinfo where foodTypeNo=" + rt.getFoodTypeNo();
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				throw new BusinessException("ʳ����𲻴���");
			rs.close();
			st.close();
			sql = "select * from foodtypeinfo where foodTypeName=? and foodTypeNo<>" + rt.getFoodTypeNo();
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, rt.getFoodTypeName());
			rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("ʳ����������Ѿ���ռ��");
			rs.close();
			pst.close();
			sql = "update foodtypeinfo set foodTypeName=?,foodTypeDetail=? where foodTypeNo=?";
			pst = conn.prepareStatement(sql);
			pst.setString(1, rt.getFoodTypeName());
			pst.setString(2, rt.getFoodTypeDetail());
			pst.setInt(3, rt.getFoodTypeNo());
			pst.execute();
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

	public void createFoodType(FoodTypeInfo rt) throws BaseException {
		if (rt.getFoodTypeName() == null || "".equals(rt.getFoodTypeName()) || rt.getFoodTypeName().length() > 20) {
			throw new BusinessException("ʳ��������Ʊ�����1-20����");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from foodtypeinfo where foodTypeName=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, rt.getFoodTypeName());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("ʳ����������Ѿ���ռ��");
			rs.close();
			pst.close();
			sql = "insert into foodtypeinfo(foodTypeName,foodTypeDetail) values(?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, rt.getFoodTypeName());
			pst.setString(2, rt.getFoodTypeDetail());
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

	public List<FoodTypeInfo> loadAllFoodType() throws BaseException {
		List<FoodTypeInfo> result = new ArrayList<FoodTypeInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select foodTypeNo,foodTypeName,foodTypeDetail from foodtypeinfo order by foodTypeNo";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			while (rs.next()) {
				int id = rs.getInt(1);
				String name = rs.getString(2);
				String detail = rs.getString(3);
				FoodTypeInfo rt = new FoodTypeInfo();
				rt.setFoodTypeNo(id);
				rt.setFoodTypeName(name);
				rt.setFoodTypeDetail(detail);
				result.add(rt);
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
}