package cn.wxn.demo.test.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.mysql.jdbc.PreparedStatement;

public class DbUtil {

	public static Connection getConnection() throws SQLException{
		Connection connection = null;
		connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/cms_test", "root","root");
		return connection;
	}
	
	
	public static void close(Connection connection) {
		try {
			if(connection != null){
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(PreparedStatement preparedStatement) {
		try {
			if (preparedStatement != null) {
				preparedStatement.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
