package client;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.StringTokenizer;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Administrator extends JFrame implements ActionListener,WindowListener {
	JButton buttonAdd;
	JButton buttonDel;
	JButton buttonMod;
	JPanel panelCenter;
	
	Socket s = null;
	DataOutputStream dos = null;
	DataInputStream dis = null;
    /*
     * ��������ʼ��
     */
	public Administrator() {
		super("ͼ�����");
		this.setLayout(new BorderLayout());
		
		buttonAdd = new JButton(new ImageIcon("res/add.png"));
		buttonDel = new JButton(new ImageIcon("res/del.png"));
		buttonMod = new JButton(new ImageIcon("res/mod.png"));
		panelCenter = new JPanel();
		
		panelCenter.setLayout(new BorderLayout());
		JPanel panelWest = new JPanel();
		panelWest.setLayout(new GridLayout(3, 1, 0, 40));

//		����JButton�����
		buttonAdd.setMargin(new Insets(0, 0, 0, 0));
		buttonAdd.setHideActionText(true);
		buttonAdd.setFocusPainted(false);
		buttonAdd.setBorderPainted(false);
		buttonAdd.setContentAreaFilled(false);
		
		buttonDel.setMargin(new Insets(0, 0, 0, 0));
		buttonDel.setHideActionText(true);
		buttonDel.setFocusPainted(false);
		buttonDel.setBorderPainted(false);
		buttonDel.setContentAreaFilled(false);
		
		buttonMod.setMargin(new Insets(0, 0, 0, 0));
		buttonMod.setHideActionText(true);
		buttonMod.setFocusPainted(false);
		buttonMod.setBorderPainted(false);
		buttonMod.setContentAreaFilled(false);
		
		panelWest.add(buttonAdd);
		panelWest.add(buttonDel);
		panelWest.add(buttonMod);
		buttonAdd.addActionListener(this);
		buttonDel.addActionListener(this);
		buttonMod.addActionListener(this);
		getContentPane().add("West", panelWest);
		getContentPane().add("Center", panelCenter);
		this.setBounds(250, 300, 410, 300);
		this.addWindowListener(this);
		this.setResizable(false);
		this.setVisible(true);
	}
/*
 * (non-Javadoc)
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
	public void actionPerformed(ActionEvent e) {
//		�Ƴ�Panel�е��������
		panelCenter.removeAll();

		if (e.getSource() == buttonAdd) {
			panelCenter.add(new Add());
			this.add("Center", panelCenter);
			this.setVisible(true);
		} else if (e.getSource() == buttonDel) {
			panelCenter.add(new Del());
			this.add("Center", panelCenter);
			this.setVisible(true);
		} else if (e.getSource() == buttonMod) {
			panelCenter.add(new Mod());
			this.add("Center", panelCenter);
			this.setVisible(true);
		}
	}

	/**
	 * 
	 * @author �ƶ��󣬷����壬�¸�
	 *�޸�ͼ��ģ��
	 */
	@SuppressWarnings("serial")
	class Mod extends JPanel implements ActionListener {
		JLabel alabel;
		JTextField afield;
		JButton buttonMod;
		JButton buttonOk,buttonCancel;
		String[] str = { "��    ��", "��   ��", "��    ��", "������", "��	    ��", "��    ��" };
		JLabel label[] = new JLabel[6];
		JTextField field[] = new JTextField[20];
/**
 * ��ʼ������
 */
		public Mod() {
			alabel = new JLabel("���������һ�������������Ҫ�޸���ı��:");
			afield = new JTextField(18);
			buttonMod = new JButton("��ѯ");
			buttonOk = new JButton("�޸�");
			buttonCancel = new JButton("ȡ��");
			this.add(alabel);
			this.add(buttonMod);
			this.add(afield);
			
			buttonMod.addActionListener(this);
			for (int i = 0; i < str.length; i++) {
				label[i] = new JLabel(str[i], JLabel.RIGHT);
				field[i] = new JTextField(20);
				this.add(label[i]);
				this.add(field[i]);
			}
			field[0].setEditable(false);//�ñ�Ų��ɱ༭

			this.add(buttonOk);
			this.add(buttonCancel);
			buttonOk.addActionListener(this);
			buttonCancel.addActionListener(this);

		}
		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonMod) {
				if (afield.getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "������Ҫ�޸���ı��!");
				}
				/*
				 * �޸�����֮ǰ�Ĳ�ѯ����
				 */
				String sign = "query";
				String str = null;
				try {
					s = new Socket("cng911.vicp.net", 6666);
					dos = new DataOutputStream(s.getOutputStream());
					dis = new DataInputStream(s.getInputStream());
					dos.writeUTF(sign);// д�������־
					dos.writeUTF(afield.getText().trim());// ���������д���鱾���
					str = dis.readUTF();// ��ȡ��ѯ���
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (dos != null) {
							dos.close();
							dos = null;
						}
						if (dis != null) {
							dis.close();
							dis = null;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				String ss[] = new String[7];//�洢��������ַ���
				StringTokenizer fenxi = new StringTokenizer(str, ",");// �����ַ���
				if (fenxi.hasMoreTokens())
					ss[0] = fenxi.nextToken();//��ѯ��־�����Ƿ���������¼
				if (fenxi.hasMoreTokens())
					ss[1] = fenxi.nextToken();// ����
				if (fenxi.hasMoreTokens())
					ss[2] = fenxi.nextToken();// ����
				if (fenxi.hasMoreTokens())
					ss[3] = fenxi.nextToken();// ����
				if (fenxi.hasMoreTokens())
					ss[4] = fenxi.nextToken();// ������
				if (fenxi.hasMoreTokens())
					ss[5] = fenxi.nextToken();// ��������
				if (fenxi.hasMoreTokens())
					ss[6] = fenxi.nextToken();// �۸�
				/*
				 * ����־λss[0]Ϊfalse����ʾû�в�ѯ�������Ϣ
				 * ����֮ǰ�Ĳ�ѯ������
				 */
				if (ss[0].equals("false")) {
					for (int i = 0; i < 6; i++) {
						field[i].setText("");
					}
					JOptionPane.showMessageDialog(null, "û�в鵽��ؼ�¼��");
					return;
				}
				/*
				 * �����ڲ�ѯ�����ֱ����ʾ�������ı�����
				 */
				for (int i = 0; i < 6; i++) {
					field[i].setText(ss[i+1]);
				}
			} else if (e.getSource() == buttonCancel) {
				this.setVisible(false);
//				�����Ƕ��������ݵļ��
			} else if (e.getSource() == buttonOk) {
				if (field[1].getText().trim().equals("")) {
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (field[2].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (field[3].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�����������!");
				} else if (field[4].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (field[5].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "������۸�!");
				}
//				����������ʽ������ڸ�ʽ�ͼ۸�
				if(!field[4].getText().trim().matches("\\d*+[-]\\d*+[-]\\d*")) {
					JOptionPane.showMessageDialog(null, "�밴��ȷ��ʽ�������ڣ����磺2009-11-23");
					return;
				}
				if(!field[5].getText().trim().matches("\\d++[.]?\\d*")) {
					JOptionPane.showMessageDialog(null, "�����������ȷ�ļ۸����磺23��85.8�ȵ�");
					return;
				}

				// �����ݲ��뵽���ݿ��У��������޸�ISBN
				/*
				 * ��������޸����ݲ���
				 */
				String sign = "mod";
				boolean flag = false;
				try {
					s = new Socket("cng911.vicp.net", 6666);
					dos = new DataOutputStream(s.getOutputStream());
					dis = new DataInputStream(s.getInputStream());
					dos.writeUTF(sign);
					dos.writeUTF(field[0].getText().trim());//ISBN
					dos.writeUTF(field[1].getText().trim());//name
					dos.writeUTF(field[2].getText().trim());//author
					dos.writeUTF(field[3].getText().trim());//publisher
					dos.writeUTF(field[4].getText().trim());//date
					dos.writeDouble(Double.parseDouble(field[5].getText().trim()));//price
					flag = dis.readBoolean();
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if (dos != null) {
							dos.close();
							dos = null;
						}
						if (dis != null) {
							dis.close();
							dis = null;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				if (!flag) {
					JOptionPane.showMessageDialog(null, "�޸�����ʧ��!����...");
				} else {
					for (int i = 0; i < 6; i++) {
						field[i].setText("");
					}
					JOptionPane.showMessageDialog(null, "�޸����ݳɹ�!");
				}
			}
		}
	}
/**
 * 
 * @author �ƶ��󣬷����壬�¸�
 *ɾ��ͼ��
 */
	@SuppressWarnings("serial")
	class Del extends JPanel implements ActionListener {
		JLabel label;
		JTextField field;
		JButton buttonDel;
/*
 * ��ʼ��ɾ������
 */
		public Del() {
			label = new JLabel("���������ı���������Ҫɾ����ı��:");
			field = new JTextField(13);
			buttonDel = new JButton("ɾ��");
			this.add(label);
			this.add(field);
			this.add(buttonDel);
			buttonDel.addActionListener(this);
		}
/*
 * (non-Javadoc)
 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
 */
		public void actionPerformed(ActionEvent e) {
//			�ж������Ƿ�Ϊ��
			if (field.getText().equals("")) {
				JOptionPane.showMessageDialog(null, "������Ҫɾ����ı��!");
				return;
			}

			String sign = "del";//����ɾ��������־
			boolean flag = false;
			try {
				s = new Socket("cng911.vicp.net", 6666);
				dos = new DataOutputStream(s.getOutputStream());
				dis = new DataInputStream(s.getInputStream());
				dos.writeUTF(sign);//����ɾ����־
				dos.writeUTF(field.getText().trim());//����Ҫɾ������ı��
				flag = dis.readBoolean();//��ȡ�����Ƿ�ɹ��ı�־
			} catch (NumberFormatException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			} finally {
				try {
					if (dos != null) {
						dos.close();
						dos = null;
					}
					if (dis != null) {
						dis.close();
						dis = null;
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			if (!flag) {
				JOptionPane.showMessageDialog(null, "û���ҵ���Ҫɾ����ͼ��!");
			} else {
				field.setText("");
				JOptionPane.showMessageDialog(null, "ɾ�����ݳɹ�!");
			}
		}
	}

	/**
	 * 
	 * @author �ƶ��󣬷����壬�¸�
	 *���ͼ�鲿��
	 */
	@SuppressWarnings("serial")
	class Add extends JPanel implements ActionListener {

		JButton buttonOK;
		JButton buttonCancel;
		String[] str = { "��   ��", "��   ��", "��    ��", "������", "��    ��", "��    ��" };
		JLabel label[] = new JLabel[6];
		JTextField field[] = new JTextField[20];

		/*
		 * ��ʼ����ӽ���
		 */
		public Add() {

			buttonOK = new JButton("ȷ��");
			buttonCancel = new JButton("ȡ��");
			for (int i = 0; i < str.length; i++) {
				label[i] = new JLabel(str[i], JLabel.RIGHT);
				field[i] = new JTextField(20);
				this.add(label[i]);
				this.add(field[i]);
			}

			this.add(buttonOK);
			this.add(buttonCancel);
			buttonOK.addActionListener(this);
			buttonCancel.addActionListener(this);
		}

		/*
		 * (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == buttonOK) {
//				���������ݽ��м��
				if (field[0].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "��������!");
				} else if (field[1].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (field[2].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (field[3].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "�����������!");
				} else if (field[4].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "����������!");
				} else if (field[5].getText().equals("")) {
					JOptionPane.showMessageDialog(null, "������۸�!");
				}
				
				if(!field[4].getText().trim().matches("\\d*+[-]\\d*+[-]\\d*")) {
					JOptionPane.showMessageDialog(null, "�밴��ȷ��ʽ�������ڣ����磺2009-11-23");
					return;
				}
				if(!field[5].getText().trim().matches("\\d++[.]?\\d*")) {
					JOptionPane.showMessageDialog(null, "�����������ȷ�ļ۸����磺23��85.8�ȵ�");
					return;
				}
				String sign = "add";
				boolean flag = false;
				try {
					s = new Socket("cng911.vicp.net",6666);
					dos = new DataOutputStream(s.getOutputStream());
					dis = new DataInputStream(s.getInputStream());
					dos.writeUTF(sign);
					dos.writeUTF(field[0].getText().trim());//ISBN
					dos.writeUTF(field[1].getText().trim());//name
					dos.writeUTF(field[2].getText().trim());//author
					dos.writeUTF(field[3].getText().trim());//publisher
					dos.writeUTF(field[4].getText().trim());//date
					dos.writeDouble(Double.parseDouble(field[5].getText().trim()));//price
					flag = dis.readBoolean();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				} finally {
					try {
						if(dos!=null) {
							dos.close();
							dos = null;
						}
						if(dis!=null) {
							dis.close();
							dis = null;
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
				if (!flag) {
					JOptionPane.showMessageDialog(null, "��������ʧ��!\n����...");
				} else {
					for (int i = 0; i < str.length; i++) {
						field[i].setText("");
					}
					JOptionPane.showMessageDialog(null, "�������ݳɹ�!");
				}
			} else if (e.getSource() == buttonCancel) {
				this.setVisible(false);
			}
		}
	}
	
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}
}
