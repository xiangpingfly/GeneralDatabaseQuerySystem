package server;

import java.net.*;
import java.io.*;
/**
 * 
 * @author �����塢�ƶ��󡢳¸�
 *��������
 */
public class DatabaseServer {
	static ServerSocket server;
	static Socket serverThread;
	static InetAddress yourAddress;

	public DatabaseServer() {
		System.out.println("���Ƿ������˳���,�������û�����������");
	}
	
	public static void main(String args[]) {
		while (true) {
			try {
				server = new ServerSocket(6666);//�����˿�
			} catch (IOException e1) {
				System.out.println("���ڼ���:");
			}
			try {
				System.out.println("�ȴ��û�����.");
				serverThread = server.accept();//���ܿͻ���
				yourAddress = serverThread.getInetAddress();//�õ��ͻ���IP
				System.out.println("�ͻ���IP:" + yourAddress);
			} catch (IOException e) {
			}
			if (serverThread != null) {
				new ServerThread(serverThread).start();//�������������߳�
			} else
				continue;
		}
	}
}
