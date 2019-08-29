package KitchenHelper.control;

import KitchenHelper.model.*;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.DBUtil;
import KitchenHelper.util.DbException;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderManager {
	public void addQuickOrder(RecipeInfo r, FoodOrder fo) throws BaseException {
		List<RecipeUse> rList = new ArrayList<RecipeUse>();
		Map<String, FoodInfo> m = new HashMap<String, FoodInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipeuse where recipeNo = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, r.getRecipeNo());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RecipeUse ru = new RecipeUse();
				ru.setRecipeNo(rs.getString(1));
				ru.setFoodNo(rs.getString(2));
				ru.setAmount(rs.getDouble(3));
				ru.setUnit(rs.getString(4));
				rList.add(ru);
			}
			rs.close();
			pst.close();
			sql = "select * from foodinfo";
			pst = conn.prepareStatement(sql);
			rs = pst.executeQuery();
			while (rs.next()) {
				m.put(rs.getString(1), (FoodInfo) rs);
			}
			rs.close();
			pst.close();
			for (int i = 0; i < rList.size(); i++) {
				sql = "insert into orderdetail(orderno, foodno, amount, price, discount) value (?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, fo.getOrderNo());
				pst.setString(2, rList.get(i).getCell(1));
				pst.setDouble(3, Double.parseDouble(rList.get(i).getCell(2)));
				pst.setDouble(4, (m.get(rList.get(i).getCell(1))).getFoodPrice());
				pst.setDouble(5, (m.get(rList.get(i).getCell(1))).getDiscount());
				pst.execute();
				pst.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
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

	public FoodOrder addFoodOrder(FoodOrder fo, UserInfo u) throws BaseException {
		Connection conn = null;
		FoodOrder f = fo;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into foodorder(userno, receivername, address, phone, createtime,orderStatus ) value (?,?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, u.getUserNo());
			pst.setString(2, fo.getReceiverName());
			pst.setString(3, fo.getAddress());
			pst.setString(4, fo.getPhone());
			pst.setTimestamp(5, new java.sql.Timestamp(System.currentTimeMillis()));
			pst.setString(6, "ordered");
			pst.execute();
			sql = "SELECT LAST_INSERT_ID(orderNo) from foodorder";
			pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery();
			f.setOrderNo(rs.getInt(1));
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (conn != null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return f;
	}

	public List<FoodOrder> loadAll(String keyword, UserInfo currentUser) throws BaseException {
		List<FoodOrder> result = new ArrayList<FoodOrder>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from foodorder where userno = ?";
			if (keyword != null && !"".equals(keyword)) {
				sql += "and   (orderno like ? or receiverName like ? or address like ? or phone like ? or orderStatus like ? or createtime like ?) ";
			}
			sql += "  order by createtime desc, orderno";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, currentUser.getUserNo());
			if (keyword != null && !"".equals(keyword)) {
				pst.setString(2, "%" + keyword + "%");
				pst.setString(3, "%" + keyword + "%");
				pst.setString(4, "%" + keyword + "%");
				pst.setString(5, "%" + keyword + "%");
				pst.setString(6, "%" + keyword + "%");
				pst.setString(7, "%" + keyword + "%");
			}

			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				FoodOrder p = new FoodOrder();
				p.setOrderNo(rs.getInt(1));
				p.setAddress(rs.getString(6));
				p.setPhone(rs.getString(7));
				p.setOrderStatus(rs.getString(8));
				p.setReceiverName(rs.getString(3));
//				p.setCreateTime(rs.getDate(8));
				result.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DbException(e);
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

	public List<OrderDetail> loadOrderDetail(FoodOrder curOrder) throws BaseException {
		List<OrderDetail> result = new ArrayList<OrderDetail>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT o.orderno,o.foodno, f.foodName, o.amount,o.price, o.discount from orderdetail o, foodinfo f WHERE o.orderno = ? and o.foodNo = f.foodNo";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, curOrder.getOrderNo());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				OrderDetail s = new OrderDetail();
				s.setOrderNo(rs.getInt(1));
				s.setFoodNo(rs.getString(2));
				s.setFoodName(rs.getString(3));
				s.setAmount(rs.getDouble(4));
				s.setPrice(rs.getDouble(5));
				s.setDiscount(rs.getDouble(6));
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
}
