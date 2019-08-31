package KitchenHelper.control;

import KitchenHelper.model.FoodBuy;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.DBUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyManager {

	public List<FoodBuy> loadAll(String keyword) throws BaseException {
		List<FoodBuy> result = new ArrayList<FoodBuy>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT fb.*, fi.foodName from foodbuy fb, foodinfo fi where fb.foodNo = fi.foodNo";
			if (keyword != null && !"".equals(keyword)) {
				sql += " where (fb.buyno like ? or fb.foodno like ?)";
			}
			sql += " order by buyno desc ";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pst.setInt(1, Integer.parseInt(keyword));
				pst.setString(2, "%" + keyword + "%");
			}
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FoodBuy f = new FoodBuy();
				f.setBuyNo(rs.getInt(1));
				f.setFoodNo(rs.getString(2));
				f.setFoodName(rs.getString(5));
				f.setAmount(rs.getDouble(3));
				f.setBuyStatus(rs.getString(4));
				result.add(f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	public void addFoodBuy(FoodBuy foodBuy) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into foodbuy(foodno, amount, buyStatus) value(?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, foodBuy.getFoodNo());
			pst.setDouble(2, foodBuy.getAmount());
			pst.setString(3, "ordered");
			pst.execute();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void modifyBuy(FoodBuy foodBuy) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update foodbuy set foodno= ?, amount = ?, buyStatus = ? where buyno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, foodBuy.getFoodNo());
			pst.setDouble(2, foodBuy.getAmount());
			pst.setString(3, "ordered");
			pst.setInt(4, foodBuy.getBuyNo());
			pst.execute();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void removeBuy(int buyNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from foodbuy  where buyno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, buyNo);
			pst.execute();
			pst.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (conn != null) {
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
