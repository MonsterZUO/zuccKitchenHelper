package KitchenHelper.control;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import KitchenHelper.model.FoodInfo;
import KitchenHelper.model.FoodTypeInfo;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.BusinessException;
import KitchenHelper.util.DBUtil;
import KitchenHelper.util.DbException;

public class FoodManager {

	public List<FoodInfo> searchFood(String keyword, int foodTypeNo) throws BaseException {
		List<FoodInfo> result = new ArrayList<FoodInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select r.foodNo,r.foodName,r.foodTypeNo,r.foodPrice,r.foodAmount,r.foodUnit,r.foodDetail,rt.foodTypeName"
					+ "  from foodinfo r,foodtypeinfo rt where r.foodTypeNo=rt.foodTypeNo";
			if (foodTypeNo > 0)
				sql += " and r.foodTypeNo=" + foodTypeNo;
			if (keyword != null && !"".equals(keyword))
				sql += " and (foodNo like ? or foodName like ?)";
			sql += " order by foodNo";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pst.setString(1, "%" + keyword + "%");
				pst.setString(2, "%" + keyword + "%");

			}

			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FoodInfo r = new FoodInfo();
				r.setFoodNo(rs.getString(1));
				r.setFoodName(rs.getString(2));
				r.setFoodTypeNo(rs.getInt(3));
				r.setFoodPrice(rs.getDouble(4));
				r.setFoodAmount(rs.getFloat(5));
				r.setFoodUnit(rs.getString(6));
				r.setFoodDetail(rs.getString(7));
				r.setFoodTypeName(rs.getString(8));
				result.add(r);
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

	public void deleteFoodType(int id) throws BaseException {
		if (id <= 0) {
			throw new BusinessException("食材类别ID必须是大于0的整数");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select foodTypeName from foodtypeinfo where foodTypeNo=" + id;
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				throw new BusinessException("食材类别不存在");
			String readerTypeName = rs.getString(1);
			rs.close();
			sql = "select count(*) from foodinfo where foodTypeNo=" + id;
			rs = st.executeQuery(sql);
			rs.next();
			int n = rs.getInt(1);
			if (n > 0)
				throw new BusinessException("已经有" + n + "个食材是" + readerTypeName + "了，不能删除");
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
			throw new BusinessException("食材类别ID必须是大于0的整数");
		}
		if (rt.getFoodTypeName() == null || "".equals(rt.getFoodTypeName()) || rt.getFoodTypeName().length() > 20) {
			throw new BusinessException("食材类别名称必须是1-20个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from foodtypeinfo where foodTypeNo=" + rt.getFoodTypeNo();
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				throw new BusinessException("食材类别不存在");
			rs.close();
			st.close();
			sql = "select * from foodtypeinfo where foodTypeName=? and foodTypeNo<>" + rt.getFoodTypeNo();
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, rt.getFoodTypeName());
			rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("食材类别名称已经被占用");
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
			throw new BusinessException("食材类别名称必须是1-20个字");
		}
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from foodtypeinfo where foodTypeName=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, rt.getFoodTypeName());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next())
				throw new BusinessException("食材类别名称已经被占用");
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
