package cn.edu.zucc.booklib;

import java.sql.Connection;
import java.sql.SQLException;

import cn.edu.zucc.booklib.util.DBUtil;

public class IndexTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			long begin=System.currentTimeMillis();
			//编写代码，随意选择一个读者，查询该读者的借阅的图书总量，并输出
			
			System.out.println("耗时："+(System.currentTimeMillis()-begin)+"毫秒");
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(conn!=null)
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}

	}

}
