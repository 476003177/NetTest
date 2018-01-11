/*
 * 客户端程序，连接服务器端
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
	PrintWriter pw=null;//把信息发给客户端的对象
	Socket s=null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyClient1 msl=new MyClient1();
	}
	
	public MyClient1()
	{
		jta=new JTextArea();
		jta.setEditable(false);//设置不可编辑
		jta.setCursor(new Cursor(Cursor.TEXT_CURSOR));//设置光标
		jsp=new JScrollPane(jta);
		jtf=new JTextField(10);
		jtf.addKeyListener(this);
		jb1=new JButton("发送");
		jb1.addActionListener(this);
		jp1=new JPanel();
		jp1.add(jtf);
		jp1.add(jb1);
		this.add(jsp,"Center");
		this.add(jp1,"South");
		
		this.setTitle("qq简易聊天（客户端）");                             //设置窗体标题
		this.setSize(300,200);                         //以像素为单位设置窗体长宽	
		this.setLocation(200,200);                      //设置初试横纵位置
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//设置关闭窗口即退出
		this.setResizable(false);                       //禁止用户改变窗体大小
		this.setVisible(true);                          //显示
		jtf.requestFocus();//设置光标在jtf
		
		
		try {
			s=new Socket("127.0.0.1",9999);//Socket()连接某个服务器端
			this.pw=new PrintWriter(s.getOutputStream(),true);//通过pw向X写数据，true表示即时刷新
			InputStreamReader isr=new InputStreamReader(s.getInputStream());//通过IO流读取s中的数据
			BufferedReader br=new BufferedReader(isr);
			while(true)
			{
				String info=br.readLine();//不停地读取从服务器发来的信息
				jta.append("他对我说："+info+"\r\n");
				if(info.equals("bye"))
				{
					System.out.println("客户端关闭");
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
		//把服务器在jtf写的内容发送给客户端
		String info=jtf.getText();
		jta.append("我对他说："+info+"\r\n");
		pw.println(info);
		if(info.equals("bye"))
		{
			System.out.println("客户端关闭");
			try {
				s.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		jtf.setText("");//清空内容
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
		if(e.getKeyCode()==KeyEvent.VK_ENTER)//按下回车键
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
