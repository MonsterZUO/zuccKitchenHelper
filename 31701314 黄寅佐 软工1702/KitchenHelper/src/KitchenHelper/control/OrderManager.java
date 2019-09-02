package KitchenHelper.control;

import KitchenHelper.model.*;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.BusinessException;
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
		List<FoodInfo> fList = new ArrayList<FoodInfo>();
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
				FoodInfo f = new FoodInfo();
				f.setFoodNo(rs.getString(1));
				f.setFoodPrice(rs.getDouble(4));
				f.setDiscount(rs.getDouble(8));
				fList.add(f);
			}
			rs.close();
			pst.close();
			for(int i = 0; i < fList.size(); i++){
				m.put(fList.get(i).getFoodName(), fList.get(i));
			}
			for (int i = 0; i < rList.size(); i++) {
				sql = "insert into orderdetail(orderno, foodno, amount, price, discount) value (?,?,?,?,?)";
				pst = conn.prepareStatement(sql);
				pst.setInt(1, fo.getOrderNo());
				pst.setString(2, rList.get(i).getFoodNo());
				pst.setDouble(3, rList.get(i).getAmount());
				pst.setDouble(4, (m.get(rList.get(i).getFoodName())).getFoodPrice());
				pst.setDouble(5, (m.get(rList.get(i).getFoodName())).getDiscount());
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
			while(rs.next()){
				f.setOrderNo(rs.getInt(1));
			}
			rs.close();
			pst.close();
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
				p.setUserNo(rs.getString(2));
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

	public void addOrderFood(OrderDetail orderDetail) throws BaseException {
		Connection conn = null;
		try {
			FoodInfo f = new FoodInfo();
			conn = DBUtil.getConnection();
			String sql = "select * from foodinfo where foodno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, orderDetail.getFoodNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new BusinessException("食材信息不存在");
			}
			f.setFoodNo(rs.getString(1));
			f.setFoodPrice(rs.getDouble(4));
			f.setDiscount(rs.getDouble(8));
			rs.close();
			pst.close();
			sql = "insert into orderdetail(orderno, foodno, amount, price, discount) value(?,?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, orderDetail.getOrderNo());
			pst.setString(2, orderDetail.getFoodNo());
			pst.setDouble(3, orderDetail.getAmount());
			pst.setDouble(4, f.getFoodPrice());
			pst.setDouble(5, f.getDiscount());
			pst.execute();
			rs.close();
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
	}

	public void modifyOrderFood(OrderDetail orderDetail, String foodNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update orderdetail set foodno = ?, amount = ?, price = ?, discount = ? where orderno = ? and foodno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, foodNo);
			pst.setDouble(2, orderDetail.getAmount());
			pst.setDouble(3, orderDetail.getPrice());
			pst.setDouble(4, orderDetail.getDiscount());
			pst.setInt(5, orderDetail.getOrderNo());
			pst.setString(6, orderDetail.getFoodNo());
			pst.execute();
			pst.close();
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

	public void removeOrderFood(OrderDetail orderDetail) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from orderdetail where orderno = ? and foodno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, orderDetail.getOrderNo());
			pst.setString(2, orderDetail.getFoodNo());
			pst.execute();
			pst.close();
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
	}

	public FoodOrder modifyFoodOrder(FoodOrder fo, UserInfo currentUser)throws BaseException {
		FoodOrder foNew = fo;
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "update foodorder set receiverName=?, phone=?, address=? where orderno=? and userno=?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, fo.getReceiverName());
			pst.setString(2, fo.getPhone());
			pst.setString(3, fo.getAddress());
			pst.setInt(4, fo.getOrderNo());
			pst.setString(5, fo.getUserNo());
			pst.execute();
			pst.close();
			sql = "select * from foodorder where orderno=? and userno=?";
			pst = conn.prepareStatement(sql);
			pst.setInt(1, fo.getOrderNo());
			pst.setString(2, fo.getUserNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if(rs.next()){
				foNew.setOrderNo(rs.getInt(1));
				foNew.setUserNo(rs.getString(2));
				foNew.setReceiverName(rs.getString(3));
				foNew.setAddress(rs.getString(6));
				foNew.setPhone(rs.getString(7));
				foNew.setOrderStatus(rs.getString(8));
			}
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
		return foNew;
	}

	public void removeOrder(FoodOrder order) throws BaseException {
		Connection conn = null;
		try{
			conn = DBUtil.getConnection();
			String sql = "delete from foodorder where orderno = ? and userno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setInt(1, order.getOrderNo());
			pst.setString(2, order.getUserNo());
			pst.execute();
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
}
