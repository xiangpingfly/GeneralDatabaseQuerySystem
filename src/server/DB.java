package server;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * 
 * @author �ƶ��󡢷����塢�¸�
 *�������ݿ�İ�װ��
 */
public class DB {
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	private static String str = "sun.jdbc.odbc.JdbcOdbcDriver";
	private static String url = "jdbc:odbc:book";
	private static String user = "sa";
	private static String pwd = "123456";
	/**
	 * �õ�Connection����
	 * @return Connection����
	 */
	public static Connection getConn() {
		try {
			Class.forName(str);
			conn = DriverManager.getConnection(url,user,pwd);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
	/**
	 * ����һ��Statement����
	 * @param conn Connection����
	 * @return Statement����
	 */
	public static Statement createStmt(Connection conn) {
		try {
			stmt = conn.createStatement();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stmt;
	}
	/**
	 * �õ�һ�������
	 * @param stmt Statement����
	 * @param sql һ��SQL���
	 * @return ResultSet�����
	 */
	public static ResultSet getRs(Statement stmt, String sql) {
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
	}
	/**
	 * �ر�Connection����
	 * @param conn Connection����
	 */
	public static void close(Connection conn) {
		if(conn!=null) {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}
	/**
	 * �ر�Statement
	 * @param stmt Statement����
	 */
	public static void close(Statement stmt) {
		if(stmt!=null) {
			try {
				stmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			stmt = null;
		}
	}
	/**
	 * �ر�ResultSet�����
	 * @param rs ResultSet���������
	 */
	public static void close(ResultSet rs) {
		if(rs!=null) {
			try {
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			rs = null;
		}
	}
}
