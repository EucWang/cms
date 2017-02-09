package cn.wxn.demo.utils;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
 

public class DbUtil {
	
	public DbUtil() {
	}
	
	private static DbUtil dbUnit;
	
	public static DbUtil getInstance(){
		if (dbUnit == null) {
			dbUnit = new DbUtil();
		}
		return dbUnit;
	}
	
	
//	private DataSource dataSource;
//	
//	@Resource
//	public void setDataSource(DataSource ds){
//		this.dataSource = ds;
//	}
	
	public static Connection getConnection()
			throws IOException, InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		Properties properties = new Properties();
		properties.load(DbUtil.class.getClassLoader().getResourceAsStream("jdbc.properties"));

		String driver = properties.getProperty("driver");
		String url = properties.getProperty("url");
		String username = properties.getProperty("username");
		String password = properties.getProperty("password");
		Class.forName(driver).newInstance();
		Connection connection = DriverManager.getConnection(url, username, password);
		return connection;
	}
	
//	public Connection getConnection() throws SQLException{
//		return dataSource.getConnection();
//		return null;
//	}
}
