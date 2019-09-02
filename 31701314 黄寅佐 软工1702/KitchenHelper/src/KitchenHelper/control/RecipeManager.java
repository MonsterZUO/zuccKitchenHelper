package KitchenHelper.control;

import KitchenHelper.model.RecipeComment;
import KitchenHelper.model.RecipeInfo;
import KitchenHelper.model.RecipeStep;
import KitchenHelper.model.RecipeUse;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.BusinessException;
import KitchenHelper.util.DBUtil;
import KitchenHelper.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

	public List<RecipeInfo> loadAll(String keyword) throws BaseException {
		List<RecipeInfo> result = new ArrayList<RecipeInfo>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipeinfo ";
			if (keyword != null && !"".equals(keyword)) {
				sql += " where (recipeNo like ? or recipeName like ?)";
			}
			sql += " order by recipeNo";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			if (keyword != null && !"".equals(keyword)) {
				pst.setString(1, "%" + keyword + "%");
				pst.setString(2, "%" + keyword + "%");
			}
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RecipeInfo p = new RecipeInfo();
				p.setRecipeNo(rs.getString(1));
				p.setRecipeName(rs.getString(2));
				p.setUserNo(rs.getString(3));
				p.setRecipeDetail(rs.getString(4));
				p.setScoreTotal(rs.getDouble(5));
				p.setCollectCount(rs.getInt(6));
				p.setLookCount(rs.getInt(7));
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

	public List<RecipeStep> loadSteps(RecipeInfo recipe) throws BaseException {
		List<RecipeStep> result = new ArrayList<RecipeStep>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipestep where recipeNo=? order by stepNo";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipe.getRecipeNo());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RecipeStep s = new RecipeStep();
				s.setRecipeNo(rs.getString(1));
				s.setStepNo(rs.getInt(2));
				s.setStepDetail(rs.getString(3));
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

	public List<RecipeUse> loadRecipeUse(RecipeInfo recipe) throws BaseException {
		List<RecipeUse> result = new ArrayList<RecipeUse>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT r.recipeNo,r.foodno, f.foodName, r.amount, r.unit from recipeuse r, foodinfo f WHERE r.recipeNo = ? and r.foodNo = f.foodNo";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipe.getRecipeNo());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RecipeUse s = new RecipeUse();
				s.setRecipeNo(rs.getString(1));
				s.setFoodNo(rs.getString(2));
				s.setFoodName(rs.getString(3));
				s.setAmount(rs.getDouble(4));
				s.setUnit(rs.getString(5));
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

	public void createRecipe(RecipeInfo recipe) throws BaseException {
		// TODO Auto-generated method stub
		Connection conn = null;
		if (recipe.getRecipeName() == null || "".equals(recipe.getRecipeName()))
			throw new BusinessException("菜谱名称不能为空");
		if (" ".equals(recipe.getRecipeName()))
			throw new BusinessException("菜谱名称不能为空格");
		try {
			conn = DBUtil.getConnection();
//            String sql = "select max(plan_Order) from tbl_plan where user_Id=?";
//            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, UserManager.currentUser.getUserNo());
//            java.sql.ResultSet rs = pst.executeQuery();
//            int i = 1;
//            while (rs.next()) {
//                i += rs.getInt(1);
//            }
//            rs.close();
//            pst.close();
			String sql = "insert into recipeinfo (recipeno,recipename,userno,recipeDetail,scoreTotal,collectcount,lookcount) values(?,?,?,?,?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipe.getRecipeNo());
			pst.setString(2, recipe.getRecipeName());
			pst.setString(3, UserManager.currentUser.getUserNo());
			pst.setString(4, recipe.getRecipeDetail());
			pst.setInt(5, 0);
			pst.setInt(6, 0);
			pst.setInt(7, 0);
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

	public void addRecipeStep(RecipeStep recipeStep) throws BaseException {
		Connection conn = null;
		if ("".equals(recipeStep.getStepNo()))
			throw new BusinessException("步骤编号不能为空");
		try {
			conn = DBUtil.getConnection();
//            String sql = "select max(plan_Order) from tbl_plan where user_Id=?";
//            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, UserManager.currentUser.getUserNo());
//            java.sql.ResultSet rs = pst.executeQuery();
//            int i = 1;
//            while (rs.next()) {
//                i += rs.getInt(1);
//            }
//            rs.close();
//            pst.close();
			String sql = "select * from recipestep where recipeno = " + recipeStep.getRecipeNo()
					+ " and stepno = " + recipeStep.getStepNo();
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			java.sql.ResultSet rs = pst.executeQuery(sql);
			if (rs.next()) {
				throw new BusinessException("步骤已存在,请进行修改或删除操作!");
			}
			pst.close();
			rs.close();
			sql = "insert into recipestep (recipeno,stepno,stepDetail) values(?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, recipeStep.getRecipeNo());
			pst.setInt(2, recipeStep.getStepNo());
			pst.setString(3, recipeStep.getStepDetail());
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

	public void addRecipeUse(RecipeUse recipeUse) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipeuse where recipeno = ? and foodno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipeUse.getRecipeNo());
			pst.setString(2, recipeUse.getFoodNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (rs.next()) {
				throw new BusinessException("食材已存在,请进行修改或删除操作!");
			}
			rs.close();
			pst.close();
			sql = "insert into recipeuse (recipeno,foodno,amount,unit) values(?,?,?,?)";
			pst = conn.prepareStatement(sql);
			pst.setString(1, recipeUse.getRecipeNo());
			pst.setString(2, recipeUse.getFoodNo());
			pst.setDouble(3, recipeUse.getAmount());
			pst.setString(4, recipeUse.getUnit());
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

	public void modifyRecipe(RecipeInfo r, String oldRecipeNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
//			String sql = "select * from recipeinfo where recipeNo=" + oldRecipeNo;
//			java.sql.Statement st = conn.createStatement();
//			java.sql.ResultSet rs = st.executeQuery(sql);
//			if (!rs.next())
//				throw new BusinessException("菜谱不存在");
//			rs.close();
//			st.close();
			String sql = "update recipeinfo set recipeno=?,recipename=?,recipeDetail=? where recipeNo = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst = conn.prepareStatement(sql);
			pst.setString(1, r.getRecipeNo());
			pst.setString(2, r.getRecipeName());
			pst.setString(3, r.getRecipeDetail());
			pst.setString(4, oldRecipeNo);
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

	public void removeRecipe(String recipeNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipeinfo where recipeNo='" + recipeNo + "'";
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				throw new BusinessException("菜谱不存在");
			rs.close();
			st.close();
			sql = "delete from recipeinfo where recipeNo = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipeNo);
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

//	public RecipeUse searchRecipeUse(RecipeStep recipeStep) throws BaseException {
//		RecipeUse ru = new RecipeUse();
//		Connection conn = null;
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "select * from recipeUse where recipeNo = ? and foodNo = ?";
//			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setString(1, recipeStep.getRecipeNo());
//			pst.setString(2, recipeStep.getFoodNo());
//			java.sql.ResultSet rs = pst.executeQuery();
//			if (!rs.next()) {
//				throw new BusinessException("无记录");
//			} else {
//				ru.setRecipeNo(rs.getString(1));
//				ru.setFoodNo(rs.getString(2));
//				ru.setAmount(rs.getDouble(3));
//				ru.setUnit(rs.getString(4));
//			}
//			rs.close();
//			pst.close();
//			return ru;
//		} catch (Exception e) {
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


//	public void modifyRecipeStep(RecipeStep recipeStep) throws BaseException {
//		Connection conn = null;
//		try {
//			conn = DBUtil.getConnection();
//			String sql = "select * from recipeStep where recipeNo=" + recipeStep.getRecipeNo()
//					+ "and stepno =" + recipeStep.getStepNo();
//			java.sql.Statement st = conn.createStatement();
//			java.sql.ResultSet rs = st.executeQuery(sql);
//			if (!rs.next())
//				throw new BusinessException("记录不存在");
//			rs.close();
//			st.close();
//			sql = "update recipestep set recipeno=?,stepno=?,foodno=?, stepdetail = ? where recipeNo = ? and " +
//					"stepno = ?";
//			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//			pst.setString(1, recipeStep.getRecipeNo());
//			pst.setInt(2, recipeStep.getStepNo());
//			pst.setString(3, recipeStep.getFoodNo());
//			pst.setString(4, recipeStep.getStepDetail());
//			pst.setString(5, recipeStep.getRecipeNo());
//			pst.setInt(6, recipeStep.getStepNo());
//			pst.execute();
//			pst.close();
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

	public void modifyRecipeUse(RecipeUse recipeUse, String newFoodNo) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update recipeuse set recipeno=?,foodno=?,amount=?, unit = ? where recipeNo = ? and " +
					"foodno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipeUse.getRecipeNo());
			pst.setString(2, newFoodNo);
			pst.setDouble(3, recipeUse.getAmount());
			pst.setString(4, recipeUse.getUnit());
			pst.setString(5, recipeUse.getRecipeNo());
			pst.setString(6, recipeUse.getFoodNo());
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

	public void removeRecipeFood(RecipeUse recipeUse) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
//			String sql = "select * from recipeuse where recipeNo=" + recipeUse.getRecipeNo()
//					+ "and foodno =" + recipeUse.getFoodNo();
//			java.sql.Statement st = conn.createStatement();
//			java.sql.ResultSet rs = st.executeQuery(sql);
//			if (!rs.next())
//				throw new BusinessException("步骤不存在");
//			rs.close();
//			st.close();
			String sql = "delete from recipeuse where recipeNo= ? and foodno =?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipeUse.getRecipeNo());
			pst.setString(2, recipeUse.getFoodNo());
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

	public void modifyRecipeStep(RecipeStep recipeStep) throws BaseException {
		Connection conn = null;
		if ("".equals(recipeStep.getStepNo()))
			throw new BusinessException("步骤编号不能为空");
		try {
			conn = DBUtil.getConnection();
//            String sql = "select max(plan_Order) from tbl_plan where user_Id=?";
//            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
//            pst.setString(1, UserManager.currentUser.getUserNo());
//            java.sql.ResultSet rs = pst.executeQuery();
//            int i = 1;
//            while (rs.next()) {
//                i += rs.getInt(1);
//            }
//            rs.close();
//            pst.close();
			String sql = "select * from recipestep where recipeno = ? and stepno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, recipeStep.getRecipeNo());
			pst.setInt(2, recipeStep.getStepNo());
			java.sql.ResultSet rs = pst.executeQuery();
			if (!rs.next()) {
				throw new BusinessException("步骤不存在!");
			}
			pst.close();
			rs.close();
			sql = "update recipestep set stepDetail=? where recipeNo = ? and stepno = ?";
//			update recipeuse set recipeno=?,foodno=?,amount=?, unit = ? where recipeNo = ? and " + "foodno =
			pst = conn.prepareStatement(sql);
			pst.setString(1, recipeStep.getStepDetail());
			pst.setString(2, recipeStep.getRecipeNo());
			pst.setInt(3, recipeStep.getStepNo());
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

	public void removeRecipeStep(RecipeStep recipeStep) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipestep where recipeno = " + recipeStep.getRecipeNo()
					+ " and stepno = " + recipeStep.getStepNo();
			java.sql.Statement st = conn.createStatement();
			java.sql.ResultSet rs = st.executeQuery(sql);
			if (!rs.next())
				throw new BusinessException("步骤不存在");
			rs.close();
			st.close();
			sql = "delete from recipeStep where recipeno = " + recipeStep.getRecipeNo()
					+ " and stepno = " + recipeStep.getStepNo();
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
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

	public List<RecipeComment> loadComments(RecipeInfo curPlan) throws BaseException {
		List<RecipeComment> rcList = new ArrayList<RecipeComment>();
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "select * from recipecomment where recipeno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, curPlan.getRecipeNo());
			java.sql.ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				RecipeComment rc = new RecipeComment();
				rc.setRecipeNo(rs.getString(1));
				rc.setUserNo(rs.getString(2));
				rc.setComment(rs.getString(3));
				rc.setLook(rs.getBoolean(4));
				rc.setCollect(rs.getBoolean(5));
				rc.setScore(rs.getDouble(6));
				rcList.add(rc);
			}
			rs.close();
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
		return rcList;
	}

	public void addComment(RecipeComment comment) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "insert into recipecomment(recipeno, userno, comment) value (?,?,?)";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comment.getRecipeNo());
			pst.setString(2, comment.getUserNo());
			pst.setString(3, comment.getComment());
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

	public void modifyComment(RecipeComment comment) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "update recipecomment set comment = ? where recipeno = ? and userno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comment.getComment());
			pst.setString(2, comment.getRecipeNo());
			pst.setString(3, comment.getUserNo());
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

	public void removeComment(RecipeComment comment) throws BaseException {
		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			String sql = "delete from recipecomment where recipeno = ? and userno = ?";
			java.sql.PreparedStatement pst = conn.prepareStatement(sql);
			pst.setString(1, comment.getRecipeNo());
			pst.setString(2, comment.getUserNo());
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
}
