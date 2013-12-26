package server;
import java.io.*;
import java.net.*;
import java.util.*;
import java.sql.*;
/**
 * 
 * @author �ƶ��󡢷����塢�¸�
 *�������˵ķ����
 */
public class ServerThread extends Thread {
	InetAddress yourAddress;
	Socket socket = null;
	DataOutputStream out = null;
	DataInputStream in = null;
	Connection conn = null;
	Statement stmt = null;
	ResultSet rs;
	int number;
/**
 * 
 * @param t Socket����
 */
	ServerThread(Socket t) {
		socket = t;
		conn = DB.getConn();
		stmt = DB.createStmt(conn);
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*
 * (non-Javadoc)
 * @see java.lang.Thread#run()
 */
	public void run() {
		String s = null;
		int N = 0;
		while (true) {
//			��ȡ�жϲ�������
			try {
				s = in.readUTF();
			} catch (IOException e) {
				try {
					socket.close();
					DB.close(conn);
				} catch (Exception e1) {
				}
				System.out.println("�ͻ��뿪��");
				break;
			}
//			��½
			if(s.equals("login")) {
				try {
					out.writeBoolean(Manage.check(in.readUTF(), in.readUTF()));
				} catch (IOException e) {
					try {
						socket.close();
						DB.close(conn);
					} catch (Exception e1) {
					}
					System.out.println("�ͻ��뿪��");
					break;
				}
//				��ѯ
			} else if(s.equals("query")) {
				try {
					String str = in.readUTF();
					if(str!=null) {
						out.writeUTF(Manage.query(str));
					}
				} catch (IOException e) {
					try {
						socket.close();
						DB.close(conn);
					} catch (Exception e1) {
					}
					System.out.println("�ͻ��뿪��");
					break;
				}
//				�޸�
			} else if(s.equals("mod")) {
				try {
					out.writeBoolean(Manage.modifyBook(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readDouble()));
				} catch (IOException e) {
					try {
						socket.close();
						DB.close(conn);
					} catch (Exception e1) {
					}
					System.out.println("�ͻ��뿪��");
					break;
				}
//				ɾ��
			}else if(s.equals("del")) {
				try {
					String str = in.readUTF();
					if(str!=null) {
						out.writeBoolean(Manage.delBook(str));
					}
				} catch (IOException e) {
					try {
						socket.close();
						DB.close(conn);
					} catch (Exception e1) {
					}
					System.out.println("�ͻ��뿪��");
					break;
				}
//				����
			} else if(s.equals("add")) {
				try {
					out.writeBoolean(Manage.addBook(in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readUTF(), in.readDouble()));
				} catch (IOException e) {
					try {
						socket.close();
						DB.close(conn);
					} catch (Exception e1) {
					}
					System.out.println("�ͻ��뿪��");
					break;
				}
			} else{
//				ͨ�ò�ѯ
				try {
					if (s.startsWith("�ֶθ���:")) {
						String number = s.substring(s.lastIndexOf(":") + 1);
						N = Integer.parseInt(number);//�õ��ֶ���
					} else {
						String sqlCondition = null;
//						������ȡ���ַ���
						String ���� = "", ��ѯ���� = "", �ֶ� = "", ��ѯ��ʽ = "";
						StringTokenizer fenxi = new StringTokenizer(s, ":");
						if (fenxi.hasMoreTokens())
							���� = fenxi.nextToken();
						if (fenxi.hasMoreTokens())
							��ѯ���� = fenxi.nextToken();
						if (fenxi.hasMoreTokens())
							�ֶ� = fenxi.nextToken();
						if (fenxi.hasMoreTokens())
							��ѯ��ʽ = fenxi.nextToken();
						if (��ѯ��ʽ.equals("��ȫһ��")) {
							sqlCondition = "SELECT * FROM " + ���� + " WHERE " + �ֶ�
									+ " LIKE " + "'" + ��ѯ���� + "' ";
						} else if (��ѯ��ʽ.equals("ǰ��һ��")) {
							sqlCondition = "SELECT * FROM " + ���� + " WHERE " + �ֶ�
									+ " LIKE " + "'" + ��ѯ���� + "%' ";
						} else if (��ѯ��ʽ.equals("��һ��")) {
							sqlCondition = "SELECT * FROM " + ���� + " WHERE " + �ֶ�
									+ " LIKE " + "'%" + ��ѯ���� + "' ";
						} else if (��ѯ��ʽ.equals("�м����")) {
							sqlCondition = "SELECT * FROM " + ���� + " WHERE " + �ֶ�
									+ " LIKE " + "'%" + ��ѯ���� + "%' ";
						}
						try {
//							�õ������
							rs = stmt.executeQuery(sqlCondition);
							number = 0;//��Ϊ�Ƿ��в�ѯ����ı�־��Ҳ����ֱ����rs�Ƿ�Ϊ�����ж�
							while (rs.next()) {
								number++;
								StringBuffer buff = new StringBuffer();
								for (int k = 1; k <= N; k++) {
									buff.append(rs.getString(k) + ",");
								}
								out.writeUTF("\n" + new String(buff));
							}
							if (number == 0)//������rs==null���ж�
								out.writeUTF("û�в�ѯ���κμ�¼");
						} catch (SQLException ee) {
							out.writeUTF("" + ee);
						}
					}
				} catch (IOException e) {
					try {
						socket.close();
						DB.close(conn);
					} catch (Exception e1) {
					}
					System.out.println("�ͻ��뿪��");
					break;
				}
			}
		}
	}
}
