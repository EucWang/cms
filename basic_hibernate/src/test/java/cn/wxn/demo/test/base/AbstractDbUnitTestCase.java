package cn.wxn.demo.test.base;

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
import org.junit.BeforeClass;
import org.xml.sax.InputSource;

import junit.framework.Assert;

public class AbstractDbUnitTestCase {

	public static IDatabaseConnection dbunitCon;
	private File tempFile;

	@BeforeClass
	public static void init() throws DatabaseUnitException, SQLException {
		dbunitCon = new DatabaseConnection(DbUtil.getConnection());
	}

	protected IDataSet createDateSet(String tname) throws DataSetException {
//		InputStream is = AbstractDbUnitTestCase.class.getClassLoader()
//				.getResourceAsStream("dbunit_xml/" + tname + ".xml");
		InputStream is = AbstractDbUnitTestCase.class.getClassLoader()
				.getResourceAsStream(tname + ".xml");
		Assert.assertNotNull("dbunit的基本数据文件不存在", is);
		return new FlatXmlDataSet(new FlatXmlProducer(new InputSource(is)));
	}

	protected void backupAllTable() throws SQLException, IOException, DataSetException {
		IDataSet ds = dbunitCon.createDataSet();
		writeBackupFile(ds);
	}

	private void writeBackupFile(IDataSet ds) throws IOException, DataSetException {
		tempFile = File.createTempFile("back", "xml");
		FlatXmlDataSet.write(ds, new FileWriter(tempFile));
	}

	protected void backupCustomTable(String[] tnames) throws DataSetException, IOException {
		QueryDataSet ds = new QueryDataSet(dbunitCon);
		for (String tname : tnames) {
			ds.addTable(tname);
		}
		writeBackupFile(ds);
	}

	protected void backupOneTable(String tname) throws DataSetException, IOException {
		backupCustomTable(new String[] { tname });
	}

	protected void resumeTable() throws FileNotFoundException, DatabaseUnitException, SQLException {
		IDataSet dataSet = new FlatXmlDataSet(new FlatXmlProducer(new InputSource(new FileInputStream(tempFile))));
		DatabaseOperation.CLEAN_INSERT.execute(dbunitCon, dataSet);
	}

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

}
