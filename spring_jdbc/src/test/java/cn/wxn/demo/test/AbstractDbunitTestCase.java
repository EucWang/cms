package cn.wxn.demo.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlProducer;
import org.dbunit.operation.DatabaseOperation;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.xml.sax.InputSource;
import cn.wxn.demo.utils.DbUtil;

public class AbstractDbunitTestCase {
	public static IDatabaseConnection dbunitCon;
	
	/**
	 * 备份数据库数据的临时文件
	 */
	private File tempFile;

	/**
	 * 初始化时获取 dbUnit的Connection
	 */
	@BeforeClass
	public static void init() throws InstantiationException, IllegalAccessException, ClassNotFoundException,
			DatabaseUnitException, IOException, SQLException {
		dbunitCon = new DatabaseConnection(DbUtil.getConnection());
	}

	/**
	 * 销毁时关闭Connection
	 */
	@AfterClass
	public static void destroy() {
		try {
			if (dbunitCon != null) {
				dbunitCon.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 创建dataSet
	 * @param name  对应的xml配置的测试数据的文件名, 不包括后缀名
	 * @return
	 */
	protected IDataSet createDataSet(String name) throws DataSetException {
		InputStream inputStream = AbstractDbunitTestCase.class.getClassLoader()
				.getResourceAsStream("dbunit_xml/" + name + ".xml");
		Assert.assertNotNull("dbunit的基本数据文件不存在", inputStream);

		InputSource inputSource = new InputSource(inputStream);
		FlatXmlProducer flatXmlProducer = new FlatXmlProducer(inputSource);
		FlatXmlDataSet flatXmlDataSet = new FlatXmlDataSet(flatXmlProducer);
		return flatXmlDataSet;
	}

	/**
	 * 备份所有的数据库表
	 * @throws SQLException
	 * @throws IOException
	 * @throws DataSetException
	 */
	protected void backupAllTable() throws SQLException, IOException, DataSetException {
		IDataSet dSet = dbunitCon.createDataSet();
		writeBackupFile(dSet);
	}

	/**
	 * 备份多张表
	 * @param tableNames
	 * @throws DataSetException
	 * @throws IOException
	 */
	protected void backupCustomTable(String[] tableNames) throws DataSetException, IOException {
		QueryDataSet dataSet = new QueryDataSet(dbunitCon);
		for (String tableName : tableNames) {
			dataSet.addTable(tableName);
		}
		writeBackupFile(dataSet);
	}

	/**
	 * 备份一张表
	 * @param tableName
	 */
	protected void backupCustomTable(String tableName) throws DataSetException, IOException {
		backupCustomTable(new String[] { tableName });
	}

	/**
	 * 将数据库中原始数据写入到备份文件中
	 * @param ds
	 */
	private void writeBackupFile(IDataSet ds) throws IOException, DataSetException {
		tempFile = File.createTempFile("back", ".xml");
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}

	/**
	 * 恢复原始数据到数据库中
	 */
	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException {
		FileInputStream inputStream = new FileInputStream(tempFile);
		InputSource inputSource = new InputSource(inputStream);
		FlatXmlProducer flatXmlProducer = new FlatXmlProducer(inputSource);
		FlatXmlDataSet flatXmlDataSet = new FlatXmlDataSet(flatXmlProducer);
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, flatXmlDataSet);
	}

}
