package client;

import java.applet.Applet;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.StringTokenizer;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class DatabaseClient extends JFrame implements Runnable, ActionListener {
	String formName; // ���ݿ��еı���
	DefaultTableModel dtm;
	Label label1,label2;
	TextField �����ѯ����;
	Choice choice;
	Checkbox ��ȫһ��, ǰ��һ��, ��һ��, �м����;
	CheckboxGroup group = null;
	Button ��ѯ;
	JTable table;
	JScrollPane jsp;
	Label ��ʾ��;
	Socket socket = null;
	DataInputStream in = null;
	DataOutputStream out = null;
	static Thread thread;
	boolean ok = false;
	int N = 0; // �ֶθ���
	String[] ziduanName = { "ISBN", "name", "author", "publisher", "date", "price" }; // ����ֶ����ֵ�����
	MenuBar mb;
	MenuItem mi;
	Object[][] obj = {{" ", " ", " ", " ", " ", " "}};//���ڳ�ʼ��JTable��ֵ
	
	public DatabaseClient() {

		super("����");
		setBounds(200, 100, 500, 600);
		setResizable(false);
		setBackground(Color.white);
		setLayout(new FlowLayout());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		��һ��Panel�е����
		label1 = new Label("�����ѯ����:", Label.CENTER);
		�����ѯ���� = new TextField(19);
		choice = new Choice();
		for (int j = 0; j < ziduanName.length; j++) {
			choice.add(ziduanName[j]);
		}
		N = choice.getItemCount();
		��ѯ = new Button("��ѯ");
		
//		�ڶ���Panel�е����
		label2 = new Label("ѡ���ѯ����:", Label.CENTER);
		group = new CheckboxGroup();
		��ȫһ�� = new Checkbox("��ȫһ��", true, group);
		ǰ��һ�� = new Checkbox("ǰ��һ��", false, group);
		��һ�� = new Checkbox("��һ��", false, group);
		�м���� = new Checkbox("�м����", false, group);
		
//		������Panel�е����
		dtm = new DefaultTableModel(obj,ziduanName);  
		table = new JTable(dtm);
		jsp = new JScrollPane(table);
		
//		��Ӳ˵���
		mb = new MenuBar();
		setMenuBar(mb);
		Menu menu = new Menu("����Ա��½");
		mb.add(menu);
		mi = new MenuItem("��½");
		menu.add(mi);
		mi.addActionListener(this);
//		���ݿ��б���
		formName = "bookform";

		��ʾ�� = new Label("�������ӵ�������,���Ե�...", Label.CENTER);
		��ʾ��.setForeground(Color.red);
		��ʾ��.setFont(new Font("TimesRoman", Font.BOLD, 24));
//		��һ��Panel
		Panel box1 = new Panel();
		box1.add(label1);
		box1.add(�����ѯ����);
		box1.add(choice);
		box1.add(��ѯ);
		��ѯ.addActionListener(this);
		add(box1);
//		�ڶ���Panel
		Panel box2 = new Panel();
		box2.add(label2);
		box2.add(��ȫһ��);
		box2.add(ǰ��һ��);
		box2.add(��һ��);
		box2.add(�м����);
		add(box2);
		
//		������Panel
		Panel box3 = new Panel();
		box3.add(jsp);
		add(box3);
		
		add(��ʾ��);//��ʾ�������ݿ��Ƿ�ɹ�

		setVisible(true);
		conn();
	}
/*
 * ���ӷ�����
 */
	public void conn() {
		ok = true;
		try {
			socket = new Socket("localhost", 6666);
			in = new DataInputStream(socket.getInputStream());//�õ�������
			out = new DataOutputStream(socket.getOutputStream());//�õ������
		} catch (IOException ee) {
			��ʾ��.setText("����ʧ��");
			this.close();
		}
		if (socket != null) {
			��ʾ��.setText("���ӳɹ�");
		}
	}
/*
 * �ر��������������
 */
	public void close() {
		ok = false;
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
/*
 * (non-Javadoc)
 * @see java.lang.Runnable#run()
 */
	public void run() {
		dtm.removeRow(0);
		String s = null;
		while (true) {
			try {
				s = in.readUTF();
				if(s.equals("û�в�ѯ���κμ�¼")) {
					try {
						Thread.sleep(1000);//Ϊ�˲��ò�ѯ��������������ص�
						Applet.newAudioClip(this.getClass().getResource("û�в�ѯ���κμ�¼.wav")).play();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					String ss[] = new String[6];
					StringTokenizer fenxi = new StringTokenizer(s, ",");// �����ַ���
					if (fenxi.hasMoreTokens())
						ss[0] = fenxi.nextToken();// ����
					if (fenxi.hasMoreTokens())
						ss[1] = fenxi.nextToken();// ����
					if (fenxi.hasMoreTokens())
						ss[2] = fenxi.nextToken();// ����
					if (fenxi.hasMoreTokens())
						ss[3] = fenxi.nextToken();// ������
					if (fenxi.hasMoreTokens())
						ss[4] = fenxi.nextToken();// ��������
					if (fenxi.hasMoreTokens())
						ss[5] = fenxi.nextToken();// �۸�
					Object[] rowData = {ss[0], ss[1], ss[2], ss[3], ss[4], ss[5] };
					dtm.addRow(rowData);//����һ��
					if (ok == false)
						break;
				}
			} catch (IOException e) {
				��ʾ��.setText("��������ѶϿ�");
				this.close();
				break;
			}
		}
	}
/*
 * (non-Javadoc)
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == ��ѯ) {
			Applet.newAudioClip(this.getClass().getResource("��ѯ.wav")).play();
//			ɾ��֮ǰ�Ĳ�ѯ�����׼����ʾ�µĲ�ѯ���
			for(int i=dtm.getRowCount()-1; i>-1; i--) {
				dtm.removeRow(i);
			}
			String ��ѯ���� = "";
			��ѯ���� = �����ѯ����.getText();
			String �ֶ� = (String) choice.getSelectedItem();
			String ��ѯ��ʽ = group.getSelectedCheckbox().getLabel();
//			����������д���ѯ��Ҫ��
			if (��ѯ����.length() > 0) {
				try {
					out.writeUTF("�ֶθ���:" + N);
					out.writeUTF(formName + ":" + ��ѯ���� + ":" + �ֶ� + ":" + ��ѯ��ʽ);
				} catch (IOException e1) {
					��ʾ��.setText("��������ѶϿ�");
				}
			} else
				�����ѯ����.setText("����������");
		} else if ((MenuItem) e.getSource() == mi) {
//			��½�¼�
			Applet.newAudioClip(this.getClass().getResource("��½.wav")).play();
//			��ʼ����½��
			new LoginDialog("��½");
		}
	}
/*
 * �����������
 */
	public static void main(String[] args) {
		thread = new Thread(new DatabaseClient());
		thread.start();
	}
}
