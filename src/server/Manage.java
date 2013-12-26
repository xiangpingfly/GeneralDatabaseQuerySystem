package server;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * 
 * @author �����塢�ƶ��󡢳¸�
 *����ҵ���߼������
 */
public class Manage {
	private static Connection conn = null;
	private static Statement stmt = null;
	private static ResultSet rs = null;
	private static String str = null;
	/**
	 * ���Ӽ�¼
	 * @param ISBN ���
	 * @param name ����
	 * @param author ����
	 * @param publisher ������
	 * @param date ��������
	 * @param price �۸�
	 * @return Booleanֵ
	 */
	public static boolean addBook(String ISBN, String name, String author,
			String publisher, String date, Double price) {
		boolean flag = false;
		try {
			conn = DB.getConn();
			stmt = DB.createStmt(conn);
			String sql;
			sql = "insert into bookform values(\'" + ISBN + "\',\'" + name
					+ "\',\'" + author + "\',\'" + publisher + "\',\'" + date
					+ "\'," + price + ")";
			if (stmt.executeUpdate(sql) != 0) {
				flag = true;
			}
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ɾ����¼
	 * @param ISBN ���
	 * @return Boolean����
	 */
	public static boolean delBook(String ISBN) {
		boolean flag = false;
		try {
			conn = DB.getConn();
			stmt = DB.createStmt(conn);
			String sql;
			sql = "delete from bookform where ISBN=\'" + ISBN + "\'";
			if (stmt.executeUpdate(sql) == 1) {
				flag = true;
			}
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * �޸�ͼ��ģ��
	 * @param ISBN ���
	 * @param name ����
	 * @param author ����
	 * @param publisher ������
	 * @param date ��������
	 * @param price �۸�
	 * @return Boolean����
	 */
	public static boolean modifyBook(String ISBN, String name, String author,
			String publisher, String date, Double price) {
		boolean flag = false;
		try {
			conn = DB.getConn();
			stmt = DB.createStmt(conn);
			String sql;
			sql = "update bookform set name=\'" + name + "\',author=\'"
					+ author + "\',publisher=\'" + publisher + "\',date=\'"
					+ date + "\',price=" + price + " where ISBN=\'" + ISBN
					+ "\'";
			if (stmt.executeUpdate(sql) != 0) {
				flag = true;
			}
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
	/**
	 * ͨ����Ų�ѯ��¼
	 * @param ISBN ��ѯ���
	 * @return String����
	 */
	public static String query(String ISBN) {
		/*
		 * �����²�ѯ��ʱ������str��Ϊ��ֵ����Ȼ�ڷ����������ڼ��һ�εĲ�ѯ��һֱ������str�У�
		 * ��������ʹ��־λʼ��Ϊtrue��
		 */
		str = null;
		boolean flag = false;
		try {
			conn = DB.getConn();
			stmt = DB.createStmt(conn);
			String sql;
			sql = "select ISBN,name,author,publisher,date,price from bookform where ISBN=\'"
					+ ISBN + "\'";
			rs = DB.getRs(stmt, sql);
			while (rs.next()) {
				str = rs.getString("ISBN") +","+ rs.getString("name") +","+ rs.getString("author") 
				+","+ rs.getString("publisher") +","+ rs.getString("date") +","+ rs.getString("price");
			}
			/*
			 * ���strΪ�����ʾû�в�ѯ���κεĽ��
			 */
			if (str != null) {
				flag = true;
			}
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag + "," + str;//�ѱ�־�Ͳ�ѯ���һ�𷢵��ͻ��ˣ�Ȼ��ͻ��˶�����н���
	}
	/**
	 * ��½���
	 * @param userName �û���
	 * @param password ����
	 * @return Booleanֵ�������ж��Ƿ��½.true-��½�ɹ���false-��½ʧ��
	 */
	public static boolean check(String userName, String password) {
		boolean flag = false;
		try {
			conn = DB.getConn();
			stmt = DB.createStmt(conn);
			String sql;
			sql = "select userName,password from login ";
			rs = DB.getRs(stmt, sql);
			while (rs.next()) {
				if (rs.getString("userName").trim().equals(userName)
						&& rs.getString("password").trim().equals(password)) {
					flag = true;
					break;
				} else {
					flag = false;
				}
			}
			DB.close(conn);
			DB.close(stmt);
			DB.close(rs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
