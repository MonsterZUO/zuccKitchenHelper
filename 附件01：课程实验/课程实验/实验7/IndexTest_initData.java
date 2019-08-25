package cn.edu.zucc.booklib;

import java.sql.Connection;
import java.sql.SQLException;

import cn.edu.zucc.booklib.util.DBUtil;

public class IndexTest_initData {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection conn=null;
		try {
			conn=DBUtil.getConnection();
			//编写代码，在数据库中添加1000个读者，读者id为:r1-r1000，读者名称为：读者1-读者1000，读者类别随意从数据库中取一个
			//编写代码，在数据库中随机添加1000本，规则自定义
			//编写代码，给所有用户借阅所有书籍，其中returnDate也用当前时间
			
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
