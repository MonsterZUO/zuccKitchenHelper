package KitchenHelper.control;

import KitchenHelper.model.RecipeInfo;
import KitchenHelper.model.RecipeStep;
import KitchenHelper.util.BaseException;
import KitchenHelper.util.BusinessException;
import KitchenHelper.util.DBUtil;
import KitchenHelper.util.DbException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RecipeManager {

    public List<RecipeInfo> loadAll() throws BaseException {
        List<RecipeInfo> result = new ArrayList<RecipeInfo>();
        Connection conn = null;
        try {
            conn = DBUtil.getConnection();
            String sql = "select * from recipeinfo where userNo=? order by recipeNo";
            java.sql.PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, UserManager.currentUser.getUserNo());
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

    public void createRecipe(RecipeInfo recipe) throws BaseException {
        // TODO Auto-generated method stub
        Connection conn = null;
        if (recipe == null || "".equals(recipe))
            throw new BusinessException("�������Ʋ���Ϊ��");
        if (" ".equals(recipe))
            throw new BusinessException("����������Ϊ�ո�");
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
}