/*
 * �ͻ��˳������ӷ�������
 */
package ClientText;

import java.awt.Cursor;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import java.net.*;

import ServerTest.MyServer1;
public class MyClient1 extends JFrame implements ActionListener,KeyListener{
	
	JTextArea jta=null;
	JTextField jtf=null;
	JButton jb1=null;
	JPanel jp1=null;
	JScrollPane jsp=null;
	PrintWriter pw=null;//����Ϣ�����ͻ��˵Ķ���
	Socket s=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyClient1 msl=new MyClient1();
	}
	
	public MyClient1()
	{
		jta=new JTextArea();
		jta.setEditable(false);//���ò��ɱ༭
		jta.setCursor(new Cursor(Cursor.TEXT_CURSOR));//���ù��
		jsp=new JScrollPane(jta);
		jtf=new JTextField(10);
		jtf.addKeyListener(this);
		jb1=new JButton("����");
		jb1.addActionListener(this);
		jp1=new JPanel();
		jp1.add(jtf);
		jp1.add(jb1);
		this.add(jsp,"Center");
		this.add(jp1,"South");
		
		this.setTitle("qq�������죨�ͻ��ˣ�");                             //���ô������
		this.setSize(300,200);                         //������Ϊ��λ���ô��峤��	
		this.setLocation(200,200);                      //���ó��Ժ���λ��
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//���ùرմ��ڼ��˳�
		this.setResizable(false);                       //��ֹ�û��ı䴰���С
		this.setVisible(true);                          //��ʾ
		jtf.requestFocus();//���ù����jtf
		
		
		try {
			s=new Socket("127.0.0.1",9999);//Socket()����ĳ����������
			this.pw=new PrintWriter(s.getOutputStream(),true);//ͨ��pw��Xд���ݣ�true��ʾ��ʱˢ��
			InputStreamReader isr=new InputStreamReader(s.getInputStream());//ͨ��IO����ȡs�е�����
			BufferedReader br=new BufferedReader(isr);
			while(true)
			{
				String info=br.readLine();//��ͣ�ض�ȡ�ӷ�������������Ϣ
				jta.append("������˵��"+info+"\r\n");
				if(info.equals("bye"))
				{
					System.out.println("�ͻ��˹ر�");
					s.close();
					break;
				}			
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void send()
	{
		//�ѷ�������jtfд�����ݷ��͸��ͻ���
		String info=jtf.getText();
		jta.append("�Ҷ���˵��"+info+"\r\n");
		pw.println(info);
		if(info.equals("bye"))
		{
			System.out.println("�ͻ��˹ر�");
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		jtf.setText("");//�������
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource()==this.jb1)
		{
			this.send();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if(e.getKeyCode()==KeyEvent.VK_ENTER)//���»س���
		{
			this.send();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
