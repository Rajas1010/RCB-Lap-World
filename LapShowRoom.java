import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.Image.*;
import java.awt.event.*;
import javax.swing.event.*;
import java.sql.*;
import java.io.*;
import java.awt.print.*;
import javax.swing.border.LineBorder;

public class LapShowRoom
{
	public static void main(String args[])throws SQLException
	{
		Welcome o=new Welcome();
		Thread t=new Thread(o);
		t.start();
	}
}
class c
{
	static int X,Y;
	static Toolkit tk=Toolkit.getDefaultToolkit();
	static String cq="select count(id) from laptop";
	static String aq="select * from laptop where id=";
	public static Connection contn()
	{
		try
		{		
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/project","root","");
			return conn; 
		}
		catch(Exception exc)
		{
			System.out.println("error");
			return null;
		}
	}
	public static JLabel img(String image)
	{
		ImageIcon i1= new ImageIcon(image);
		Image img=i1.getImage();
		Image modifyImage=img.getScaledInstance(c.getX(),c.getY(),Image.SCALE_SMOOTH);
		JLabel L=new JLabel(new ImageIcon(modifyImage));
		return L;
	}
	public static int getX()
	{
		X=(int)tk.getScreenSize().getWidth();
		return X;
	}
	public static int getY()
	{
		Y=(int)tk.getScreenSize().getHeight();
		return Y;
	}
}

class Welcome extends JFrame implements Runnable
{
	public void run()
	{
		try
		{
			this.setSize(c.getX(),c.getY());
			this.setLayout(null);
			setUndecorated(true);	//for disible close button taskbar
			JPanel p=new JPanel();
			p.setBounds(0,0,c.getX(),c.getY());
			p.setBackground(Color.BLACK);
			setContentPane(p);
			p.setLayout(null);
			JLabel L1=new JLabel();
			ImageIcon i1= new ImageIcon(this.getClass().getResource( "/rcb720.gif"));
			L1.setIcon(i1);
			L1.setBounds(277,0,c.getX(),c.getY());		//for rcb720 gif
			//L1.setBounds(363,204,690,390);		//for rcb360 gif
			p.add(L1);

			this.setVisible(true);
			Thread.sleep(4500);		//for rcb360 - 10000 (or) rcb720 - 4500
			new LoginPage();
			this.dispose();
		}catch(Exception e){}
	}
}

class LoginPage extends JFrame implements ActionListener,KeyListener
{
	JButton b1;
	JLabel l1,l2,l3,l4;
	JTextField t1;
	JPasswordField t2;
	JPanel p1;
	Font F1=new Font("times new roman",Font.PLAIN,14);
	Font F2=new Font("times new roman",Font.BOLD,40);
	
	String qry="";
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public void clear()
	{
		t1.setText("");
		t2.setText("");
	}

	public LoginPage()
	{
		conn=c.contn();
		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(c.img("2970045.jpg"));
		
		p1=new JPanel();
		p1.setLayout(null);
		p1.setBounds(140,220,270,150);
		p1.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));
		p1.setBorder(new LineBorder(Color.BLACK, 1));

		l1=new JLabel("USER NAME :");
		l1.setBounds(10,10,100,25);
		l1.setFont(F1);
		p1.add(l1);
		
		l2=new JLabel("PASSWORD  : ");
		l2.setBounds(10,50,100,25);
		l2.setFont(F1);
		p1.add(l2);
		
		l3=new JLabel("RCB LAP WORLD");
		l3.setBounds(350,10,500,80);
		l3.setFont(F2);
		l3.setForeground(new Color(0,123,208));
		this.add(l3);
		
		l4=new JLabel("");
		l4.setBounds(175,250,100,25);
		p1.add(l4);
		
		t1=new JTextField();
		t1.setFont(F1);
		t1.setBounds(160,10,100,25);
		p1.add(t1);
		
		t2=new JPasswordField();
		t2.setBounds(160,50,100,25);
		p1.add(t2);
		
		b1=new JButton("LOG IN");
		b1.setFont(F1);
		b1.setBounds(85,110,100,25);
		b1.setFocusable(false);
		b1.addActionListener(this);
		t1.addKeyListener(this);
		t2.addKeyListener(this);
		p1.add(b1);
		
		add(p1);
		this.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			try
			{
				qry="select * from users where username = ? and password = ? ";
				pst=conn.prepareStatement(qry);
				pst.setString(1,t1.getText());
				pst.setString(2,t2.getText());
				rs=pst.executeQuery();
				clear();
				if(rs.next())
				{
					l4.setText("Login success");
					System.out.println(rs.next());
					
					JOptionPane.showMessageDialog(null,"LOGIN SUCCESS "," LoginDetail",JOptionPane.PLAIN_MESSAGE);
					this.dispose();
					new HomePage(c.cq,c.aq);
						}
				else
					JOptionPane.showMessageDialog(null,"Login Failed","LoginDetail",JOptionPane.ERROR_MESSAGE);
			}catch(SQLException ex){System.err.println(ex);}	
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}
		}
	}
	public void keyReleased(KeyEvent kr)
	{
		if(kr.getKeyCode()==KeyEvent.VK_ENTER)
		{
			try
			{
				qry="select * from users where username = ? and password = ? ";
				pst=conn.prepareStatement(qry);
				pst.setString(1,t1.getText());
				pst.setString(2,t2.getText());
				rs=pst.executeQuery();
				clear();
				if(rs.next())
				{
					l4.setText("Login success");
					JOptionPane.showMessageDialog(null,"LOGIN SUCCESS "," LoginDetail",JOptionPane.PLAIN_MESSAGE);
					this.dispose();
					new HomePage(c.cq,c.aq);
						}
				else
					JOptionPane.showMessageDialog(null,"Login Failed","LoginDetail",JOptionPane.ERROR_MESSAGE);
			}catch(SQLException ex){System.err.println(ex);}	
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}
		}
	}
	public void keyPressed(KeyEvent kp){}
	public void keyTyped(KeyEvent kt){}
}

class HomePage
 extends JFrame implements ActionListener
{
	String q,pic;
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	int idCount,i,j=1,ans;
	JPanel p[]=new JPanel[50];
	JPanel p2[]=new JPanel[50];
	JLabel l[]=new JLabel[50];
	JLabel l2[]=new JLabel[50];
	JLabel l3[]=new JLabel[50];
	JLabel l4[]=new JLabel[50];
	JLabel l5[]=new JLabel[50];
	JLabel l6[]=new JLabel[50];
	JButton b[]=new JButton[50];
	JButton b2[]=new JButton[50];
	
	JFrame f2,f3;
	JButton b3,b4,b5,b6;
	JTextField t1,t2,t3;
	JLabel l7,l8;
	JMenuBar mb;
	JMenu m,m1,m2;
	JMenuItem ad,prs,byPrs,byBrand,exit,adDlt,adLap,previous,upAvl;
	ImageIcon i1[]=new ImageIcon[50];
	JScrollPane sp;
	JPanel p1;
	
	public HomePage(String qry,String qry1) 
	{
		conn=c.contn();
		int x=c.getX();
		int y=c.getY();
		this.setSize(x,y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		try
		{
			pst=conn.prepareStatement(qry);
			rs=pst.executeQuery();
			if (rs.next()) {
			idCount=rs.getInt("count(id)");
			}
		}catch(SQLException ex){System.err.println(ex);
		System.out.println("this");
		}	
		finally
		{
			try
			{
				rs.close();
				pst.close();
			}
			catch(Exception exc){}
		}
		p1=new JPanel();
		p1.setBackground(Color.BLACK);
		p1.setPreferredSize(new Dimension(x,idCount*155));
		sp=new JScrollPane();
		sp.getVerticalScrollBar().setUnitIncrement(10);
		sp.setViewportView(p1);
		add(sp);
		
		mb=new JMenuBar();
		m=new JMenu("Add");
		m1=new JMenu("Update");
		m2=new JMenu("Filter");
		
		ad=new JMenuItem("Add Admin");
		ad.addActionListener(this);
		m.add(ad);
		
		adDlt=new JMenuItem("Delete Admin");
		adDlt.addActionListener(this);
		m.add(adDlt);
		
		exit=new JMenuItem("Exit");
		exit.addActionListener(this);
		m.add(exit);
		
		adLap=new JMenuItem("Add Laptop");
		adLap.addActionListener(this);
		m1.add(adLap);
		
		prs=new JMenuItem("Update Price");		
		prs.addActionListener(this);
		m1.add(prs);
		
		upAvl=new JMenuItem("Add Quantity");		
		upAvl.addActionListener(this);
		m1.add(upAvl);
		
		byBrand=new JMenuItem("byBrand");		
		byBrand.addActionListener(this);
		m2.add(byBrand);
		
		byPrs=new JMenuItem("byPrice");
		m2.add(byPrs);		
		byPrs.addActionListener(this);
		
		previous=new JMenuItem("Show All");
		m2.add(previous);		
		previous.addActionListener(this);
		
		mb.add(m);
		mb.add(m1);
		mb.add(m2);
		
		this.setJMenuBar(mb);
		String q1=qry1;
		for(i=1;i<=idCount;i++)
		{		
			p[i]=new JPanel();
			p[i].setBackground(Color.WHITE);
			p[i].setPreferredSize(new Dimension(x,150));
			p[i].setLayout(null);
			
			p2[i]=new JPanel();								//for Image
			p2[i].setBackground(Color.WHITE);
			p2[i].setPreferredSize(new Dimension(150,150));
			p2[i].setBounds(10,10,130,130);
			
			i1[i]= new ImageIcon("iconDell01.png");
			l5[i]=new JLabel();
			l5[i].setPreferredSize(new Dimension(140,140));
			l5[i].setIcon(i1[i]);
			l5[i].setBounds(10,10,130,130);
			p2[i].add(new JLabel(i1[i]));
			
			l[i]=new JLabel(); 		//for name
			l[i].setBounds(200,10,500,20);
			l[i].setPreferredSize(new Dimension(200,15));
			p[i].add(l[i]);
			
			l4[i]=new JLabel();		//for detail
			l4[i].setBounds(200,35,500,20);
			l4[i].setPreferredSize(new Dimension(200,15));
			p[i].add(l4[i]);
	
			l2[i]=new JLabel();		//for ram
			l2[i].setBounds(200,60,200,20);
			l2[i].setPreferredSize(new Dimension(100,15));
			p[i].add(l2[i]);
	
			l3[i]=new JLabel();		// for price
			l3[i].setBounds(550,40,200,20);
			l3[i].setPreferredSize(new Dimension(100,15));
			p[i].add(l3[i]);
			
			l6[i]=new JLabel();		//for avaliable
			l6[i].setBounds(550,60,200,20);
			l6[i].setPreferredSize(new Dimension(100,15));
			p[i].add(l6[i]);
	
			b[i]=new JButton("All Details");
			b[i].setFocusable(false);
			b[i].setBounds(200,90,150,20);
			b[i].addActionListener(this);
			b[i].setPreferredSize(new Dimension(100,15));
			p[i].add(b[i]);
	
			b2[i]=new JButton("Buy");
			b2[i].setFocusable(false);
			b2[i].setBounds(550,90,100,20);
			b2[i].addActionListener(this);
			b2[i].setPreferredSize(new Dimension(100,15));
			p[i].add(b2[i]);
			
			p1.add(p[i]);	
			p[i].add(p2[i]);
		
			if(qry1=="select * from laptop where id=")
			{
				try
				{
					this.q=qry1+" "+i;
					pst=conn.prepareStatement(q);
					rs=pst.executeQuery();
					if(rs.next())
					{	
						l[i].setText(rs.getString("lname"));
						l4[i].setText(rs.getString("detail"));
						l2[i].setText("Ram : "+ rs.getString("RAM"));
						l3[i].setText("Price : "+ rs.getInt("price"));
						l6[i].setText("Avaliable : "+ rs.getString("Avaliable"));
					}
				}
				catch(SQLException ex){System.err.println(ex);}
				finally
				{
					try
					{
						rs.close();
						pst.close();
					}
					catch(Exception exc)
					{
						System.out.println("expn in filter");
					}
				}
			}
		}
		if(qry1==q1)
		{
			try
			{
				pst=conn.prepareStatement(qry1);
				rs=pst.executeQuery();
				while(rs.next() && j<=idCount)
				{		
					l[j].setText(rs.getString("lname")+" "+rs.getInt("id"));
					l4[j].setText(rs.getString("detail"));
					l2[j].setText("Ram : "+ rs.getString("RAM"));
					l3[j].setText("Price : "+ rs.getInt("price"));
					l6[j].setText("Avaliable : "+ rs.getString("Avaliable"));
					j++;
				}
			}
			catch(SQLException ex){System.err.println(ex);}
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc)
				{
					System.out.println("expn in filter");
				}
			}
		}
		this.setVisible(true);
	}
	
	void brandFilter()
	{
		f2=new JFrame();
		f2.setSize(300,150);
		f2.setLayout(null);
		f2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		t1=new JTextField();
		t1.setBounds(10,10,150,25);
		f2.add(t1);
		
		b4=new JButton("Search");
		b4.setBounds(100,50,80,25);
		b4.addActionListener(this);		
		b4.setFocusable(false);
		f2.add(b4);
		f2.setVisible(true);
		
		b3=new JButton("Back");
		b3.setBounds(10,50,80,25);
		b3.addActionListener(this);
		b3.setFocusable(false);
		f2.add(b3);
		
		f2.setVisible(true);
	}
	
	void updateAvl()
	{
		f3=new JFrame();
		f3.setSize(300,200);
		f3.setLayout(null);
		f3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		l7=new JLabel("id");
		l7.setBounds(10,10,80,20);
		f3.add(l7);
		
		l8=new JLabel("add");
		l8.setBounds(10,50,80,20);
		f3.add(l8);
		t2=new JTextField();
		t2.setBounds(100,10,150,25);
		f3.add(t2);
		
		t3=new JTextField();
		t3.setBounds(100,50,150,25);
		f3.add(t3);
		
		b5=new JButton("update");
		b5.setBounds(100,90,80,25);
		b5.addActionListener(this);		
		b5.setFocusable(false);
		f3.add(b5);
		f3.setVisible(true);
		
		b6=new JButton("Back");
		b6.setBounds(10,90,80,25);
		b6.addActionListener(this);
		b6.setFocusable(false);
		f3.add(b6);
		
		f3.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==byPrs)
		{
			String  qry="select count(id) from laptop ORDER by Price asc";
			String  qry1="select * from laptop ORDER by Price asc";
			this.dispose();
			new HomePage(qry,qry1);
		}
		if(e.getSource()==upAvl)
		{
			updateAvl();
		}
		if(e.getSource()==previous)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);
		}
		if(e.getSource()==b3)
		{
			f2.dispose();
		}
		if(e.getSource()==b6)
		{
			f3.dispose();
		}
		if(e.getSource()==byBrand)
		{
			brandFilter();
		}
		if(e.getSource()==b4)
		{
			String s=t1.getText();
			String  qry="select count(id) from laptop where Lname like '%"+s+"%'";
			String  qry1="select * from laptop where Lname like '%"+s+"%'";
			this.dispose();
			new HomePage(qry,qry1);
			f2.dispose();
		}
		if(e.getSource()==b5)
		{
			int g=Integer.parseInt(t2.getText());
			int h=Integer.parseInt(t3.getText());
			try
				{
					conn=c.contn();	
					String  qry="update laptop set Avaliable=Avaliable+"+h+" where id ="+g;
					pst=conn.prepareStatement(qry);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"update success","update Details",JOptionPane.PLAIN_MESSAGE);
					this.dispose();
					this.dispose();
					f3.dispose();
					new HomePage(c.cq,c.aq);
				}catch(SQLException ex)
					{
						System.err.println(ex);
						JOptionPane.showMessageDialog(null,"update Failed","update Details",JOptionPane.PLAIN_MESSAGE);
					}
				catch(Exception exc){}
				finally
				{
					try
					{
						rs.close();
						pst.close();
						new HomePage(c.cq,c.aq);
					}
					catch(Exception exc){}
				}
			f2.setVisible(false);
		}
		if(e.getSource()==ad)
		{
			this.dispose();
			new SignUp();
		}
		if(e.getSource()==adDlt)
		{
			this.dispose();
			new RemoveAdmin();
		}
		if(e.getSource()==exit)
		{
			ans=JOptionPane.showConfirmDialog(null,"Are You Sure ?","EXIT",JOptionPane.YES_NO_OPTION);
			if(ans==0)
			{
				System.exit(0);
			}
		}
		if(e.getSource()==adLap)
		{
			this.dispose();
			new AddLap();
		}
		if(e.getSource()==prs)
		{
			this.dispose();
			new LapPriceUpdate();
		}
		for(int i=1;i<=idCount;i++)
		{
			if(e.getSource()==b2[i])			//buy
			{	
				try
				{	
					conn=c.contn();	
					String qry="update laptop set Avaliable=Avaliable-1 where id="+i;
					pst=conn.prepareStatement(qry);
					pst.executeUpdate();
					JOptionPane.showMessageDialog(null,"success","Adding Avaliable Details",JOptionPane.PLAIN_MESSAGE);
					this.setVisible(false);
					this.dispose();
					new Bill(i);
				}catch(SQLException ex){System.err.println(ex);}	
				finally
				{
					try
					{
						rs.close();
						pst.close();
					}
					catch(Exception exc){}
				}
			}
			if(e.getSource()==b[i])
			{
				this.dispose();
				new LapDetails(i);
			}
		}
	}
}
class SignUp extends JFrame implements ActionListener
{
	JFrame f2;
	JLabel l5,l6,l7;
	JButton b2,b1;
  	JTextField t3,t4;
	
	String qry;
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public SignUp()
	{
		conn=c.contn();
		f2=new JFrame();
		
		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(c.img("3016822.jpg"));
		
		JPanel p3=new JPanel();
		p3.setLayout(null);
		p3.setBounds(470,500,300,150);
		p3.setPreferredSize(new Dimension(450,145));
		p3.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

		l7=new JLabel("ADMIN SIGNUP");
		l7.setBounds(120,10,150,25);
		p3.add(l7);
		
		l5=new JLabel("USERNAME");
		l5.setBounds(10,40,80,25);
		p3.add(l5);
		
		l6=new JLabel("PASSWORD");
		l6.setBounds(10,75,80,25);
		p3.add(l6);
		
		t3=new JTextField();
		t3.setBounds(130,40,120,25);
		p3.add(t3);
		t4=new JTextField();
		t4.setBounds(130,75,120,25);
		p3.add(t4);
		
		b2=new JButton("Sign Up");
		b2.setBounds(120,115,80,25);		
		b2.setFocusable(false);
		p3.add(b2);
		
		b1=new JButton("Back");
		b1.setBounds(20,115,80,25);		
		b1.setFocusable(false);
		b1.addActionListener(this);
		p3.add(b1);
		
		this.add(p3);
		b2.addActionListener(this);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b2)
		{			
			try
			{	
				qry="insert into users (username,password) values(?,?)";
				pst=conn.prepareStatement(qry);
				pst.setString(1,t3.getText());
				pst.setString(2,t4.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null,"sign up success","SignUpDetails",JOptionPane.PLAIN_MESSAGE);
			}catch(SQLException ex){System.err.println(ex);}
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}
		}
		if(e.getSource()==b1)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);	
		}
	}
}
class RemoveAdmin extends JFrame implements ActionListener
{
	JFrame f3;
	JLabel l8,l9;
	JButton b3,b1;
  	JTextField t5;
	String qry;
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public RemoveAdmin()
	{
		conn=c.contn();
		f3=new JFrame();
		
		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(c.img("3016822.jpg"));
		
		JPanel p3=new JPanel();
		p3.setLayout(null);
		p3.setBounds(470,500,300,150);
		p3.setPreferredSize(new Dimension(450,100));
		p3.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

		l9=new JLabel("ADMIN REMOVE");
		l9.setBounds(120,10,150,25);
		p3.add(l9);
		
		l8=new JLabel("USERNAME");
		l8.setBounds(10,40,80,25);
		p3.add(l8);
		t5=new JTextField();
		t5.setBounds(130,40,120,25);
		p3.add(t5);
		
		b3=new JButton("Delete");
		b3.setBounds(120,75,80,25);		
		b3.setFocusable(false);
		p3.add(b3);
		
		b1=new JButton("back");
		b1.setBounds(10,75,80,25);		
		b1.setFocusable(false);
		p3.add(b1);
		
		this.add(p3);
		b3.addActionListener(this);
		b1.addActionListener(this);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b3)
		{			
			try
			{
				qry="delete from users where username=?";
				pst=conn.prepareStatement(qry);
				pst.setString(1,t5.getText());
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null,"Delete Success","Remove Admin Details",JOptionPane.PLAIN_MESSAGE);
			}catch(SQLException ex){System.err.println(ex);}
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}
		}
		if(e.getSource()==b1)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);	
		}
	}
}
class AddLap extends JFrame implements ActionListener
{
	JFrame f3;
	int h;
	JLabel l1,l2,l3,l4,l5,l6,l7;
	JButton b1,b2;
  	JTextField t1,t2,t3,t4,t5,t6,t7;
	String qry,q2;
	Connection conn=null;	
	ResultSet rs=null;
	PreparedStatement pst=null;
	public AddLap()
	{
		conn=c.contn();
		q2="select count(id) from laptop";
		try
			{
				pst=conn.prepareStatement(q2);
				rs=pst.executeQuery();
				if(rs.next()){
					h=rs.getInt("count(id)");
				}
			}catch(SQLException ex){System.err.println(ex);}	
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}

		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(c.img("1393206.jpg"));
		
		JPanel p3=new JPanel();
		p3.setLayout(null);
		p3.setBounds(459,50,400,290);
		p3.setPreferredSize(new Dimension(500,600));
		p3.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

		l7=new JLabel("ADD LAPTOP");
		l7.setBounds(170,10,150,25);
		p3.add(l7);
		
		l1=new JLabel("Laptop name");
		l1.setBounds(10,40,80,20);
		p3.add(l1);
		
		l2=new JLabel("Details");
		l2.setBounds(10,70,80,20);
		p3.add(l2);
		
		l3=new JLabel("RAM");
		l3.setBounds(10,100,80,20);
		p3.add(l3);
		
		l4=new JLabel("price");
		l4.setBounds(10,130,80,20);
		p3.add(l4);
		
		l5=new JLabel("avaliable");
		l5.setBounds(10,160,80,20);
		p3.add(l5);
		
		l6=new JLabel("Image");
		l6.setBounds(10,190,80,20);
		p3.add(l6);
		
		l7=new JLabel("id");
		l7.setBounds(10,220,80,20);
		p3.add(l7);
		
		t1=new JTextField();
		t1.setBounds(130,40,250,20);
		p3.add(t1);
		
		t2=new JTextField();
		t2.setBounds(130,70,250,20);
		p3.add(t2);
		
		t3=new JTextField();
		t3.setBounds(130,100,250,20);
		p3.add(t3);
		
		t4=new JTextField();
		t4.setBounds(130,130,250,20);
		p3.add(t4);
		
		t5=new JTextField();
		t5.setBounds(130,160,250,20);
		p3.add(t5);
		
		t6=new JTextField();
		t6.setBounds(130,190,250,20);
		p3.add(t6);
		
		t7=new JTextField(String.valueOf(h+1));
		t7.setBounds(130,220,250,20);
		t7.setEditable(false);
		p3.add(t7);
		
		b2=new JButton("Add Laptop");
		b2.setBounds(200,250,120,25);		
		b2.setFocusable(false);
		b2.addActionListener(this);
		p3.add(b2);	
		
		b1=new JButton("Back ");
		b1.setBounds(50,250,120,25);		
		b1.setFocusable(false);
		b1.addActionListener(this);
		p3.add(b1);
		
		this.add(p3);
		this.setVisible(true);
	}	
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b2)
		{			
			try
			{
				qry="insert into laptop (id,Lname,detail,RAM,Price,Avaliable,Image) values(?,?,?,?,?,?,?)";
				pst=conn.prepareStatement(qry);
				pst.setString(1,t7.getText());
				pst.setString(2,t1.getText());
				pst.setString(3,t2.getText());
				pst.setString(4,t3.getText());
				pst.setString(5,t4.getText());
				pst.setString(6,t5.getText());
				pst.setString(7,"\\Icon128\\"+t6.getText()+".png");
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null,"Success","Adding Laptop Details",JOptionPane.PLAIN_MESSAGE);
			}catch(SQLException ex){System.err.println(ex);}
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}
		}
			if(e.getSource()==b1)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);	
		}
	}
}
class LapPriceUpdate extends JFrame implements ActionListener
{
	JFrame f2;
	JLabel l1,l2,l3;
	JButton b1,b2;
  	JTextField t1,t2;
	String qry;
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public LapPriceUpdate()		// invoke details button
	{
		conn=c.contn();	
		f2=new JFrame();
		
		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(c.img("3016822.jpg"));
		
		JPanel p3=new JPanel();
		p3.setLayout(null);
		p3.setBounds(470,500,300,150);
		p3.setPreferredSize(new Dimension(450,145));
		p3.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

		l1=new JLabel("Price Update");
		l1.setBounds(120,10,150,25);
		p3.add(l1);
		
		l3=new JLabel("Laptop Id");
		l3.setBounds(10,40,80,25);
		p3.add(l3);
		
		l2=new JLabel("New Price");
		l2.setBounds(10,75,80,25);
		p3.add(l2);
		
		t1=new JTextField();
		t1.setBounds(130,40,120,25);
		p3.add(t1);
		
		t2=new JTextField();
		t2.setBounds(130,75,120,25);
		p3.add(t2);
		
		b1=new JButton("Update");
		b1.setBounds(160,115,80,25);		
		b1.setFocusable(false);
		b1.addActionListener(this);
		p3.add(b1);
		
		b2=new JButton("Back");
		b2.setBounds(40,115,80,25);		
		b2.setFocusable(false);
		b2.addActionListener(this);
		p3.add(b2);
		this.add(p3);
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{			
			try
			{		
				qry="update laptop set price=? where id=?";
				pst=conn.prepareStatement(qry);
				System.out.println(t2.getText());
				int idA=Integer.parseInt(t1.getText());
				pst.setString(1,t2.getText());
				pst.setInt(2,idA);
				pst.executeUpdate();
				JOptionPane.showMessageDialog(null,"update success","UpdateDetails",JOptionPane.PLAIN_MESSAGE);
			}catch(SQLException ex){System.err.println(ex);}
			finally
			{
				try
				{
					rs.close();
					pst.close();
				}
				catch(Exception exc){}
			}
		}
		if(e.getSource()==b2)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);	
		}
	}
}
class LapDetails extends JFrame implements ActionListener
{
	JFrame f2;
	JLabel L[]=new JLabel[16];
	JButton b1;
	JTextField t1;
	String qry;
	
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	public LapDetails(int x)
	{
		conn=c.contn();	
		f2=new JFrame();
		int y=20;
		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(c.img("3016822.jpg"));
		
		JPanel p3=new JPanel();
		p3.setLayout(null);
		p3.setBounds(10,10,1300,650);
		p3.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

		for (int i=0;i<16;i++)
		{
			L[i]= new JLabel();
			L[i].setBounds(20,y,500,20);
			y+=25;
			p3.add(L[i]);	
		}
		b1=new JButton("back");
		b1.setBounds(120,y,80,25);		
		b1.setFocusable(false);
		p3.add(b1);
		this.add(p3);
		b1.addActionListener(this);
		this.setVisible(true);
		try
		{			
			qry="select * from details where id ="+x;  //?-b[]
			pst=conn.prepareStatement(qry);
			rs=pst.executeQuery();
			if(rs.next())
			{
				L[0].setText("Laptop Id : "+x);
				L[1].setText("Name : "+ rs.getString("Name"));
				L[2].setText("Processor : "+ rs.getString("Processor"));
				L[3].setText("Generation : "+ rs.getString("Generation"));
				L[4].setText("SSD : "+ rs.getString("SSD"));
				L[5].setText("SSD CAPACITY : "+ rs.getString("SSD CAPACITY"));
				L[6].setText("RAM : "+ rs.getString("RAM"));
				L[7].setText("CLOCK SPEED : "+ rs.getString("CLOCK SPEED"));
				L[8].setText(" CACHE: "+ rs.getString("CACHE"));
				L[9].setText("CORES : "+ rs.getString("CORES"));
				L[10].setText("OS_ARCH : "+ rs.getString("OS_ARCH"));
				L[11].setText("SCREEN_SIZE : "+ rs.getString("SCREEN_SIZE"));
				L[12].setText("WIRELESS : "+ rs.getString("WIRELESS"));
				L[13].setText("SLOT : "+ rs.getString("SLOT"));
				L[14].setText("WEIGHT : "+ rs.getString("WEIGHT"));
				L[15].setText("WARRANTY : "+ rs.getString("WARRANTY"));
			}
		}catch(SQLException ex){System.err.println(ex);}
		catch(Exception e){System.err.println(e);}
		finally
		{
			try
			{
				rs.close();
				pst.close();
			}
			catch(Exception exc){}
		}
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);	
		}
	}
}

class Bill extends JFrame implements ActionListener
{
	static JFrame f1;
	int pr,tax,Gtot;
	JButton b1,b2,b3;
	JLabel l1,l2,l3,l4,l5;
	JTextField t1,t2,t3,t4,t5;
	JTextArea ta1;
	JCheckBox cb1,cb2,cb3,cb4;
	String s1,s2,s3,s4,qry;
	
	Connection conn=null;
	ResultSet rs=null;
	PreparedStatement pst=null;
	
	public Bill(int x)
	{	
		conn=c.contn();
		f1=new JFrame("panel");
		this.setLayout(null);
		this.setSize(c.getX(),c.getY());
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel p1=new JPanel();
		p1.setLayout(null);
		p1.setBackground(new Color(1.0f,1.0f,1.0f,0.0f));

		setContentPane(c.img("1393334.jpg"));
		try
		{			
			qry="select * from laptop where id ="+x;  //?-b[]
			pst=conn.prepareStatement(qry);
			rs=pst.executeQuery();
			if(rs.next())
			{
				s1=rs.getString("Lname");
				s2=rs.getString("detail");
				s3=rs.getString("id");
				pr=rs.getInt("price");
			}
			tax=pr/100*12;
			Gtot=pr+tax;
		}catch(SQLException ex){System.err.println(ex);}
		catch(Exception e){System.err.println(e);}
		finally
		{
			try
			{
				rs.close();
				pst.close();
			}
			catch(Exception exc){}
		}
		l1=new JLabel("Customer Name");
		l1.setBounds(10,10,150,25);
		p1.add(l1);
		
		l2=new JLabel("Customer Age");
		l2.setBounds(10,60,150,25);
		p1.add(l2);
		
		l3=new JLabel("Customer Address");
		l3.setBounds(10,110,150,25);
		p1.add(l3);
		
		l4=new JLabel("Customer Mobile No");
		l4.setBounds(10,160,150,25);
		p1.add(l4);
		
		l5=new JLabel("Customer ID");
		l5.setBounds(10,210,150,25);
		p1.add(l5);
		
		t1=new JTextField();
		t1.setBounds(250,10,200,25);
		p1.add(t1);
		
		t2=new JTextField();
		t2.setBounds(250,60,200,25);
		p1.add(t2);
		
		t3=new JTextField();
		t3.setBounds(250,110,200,25);
		p1.add(t3);
		
		t4=new JTextField();
		t4.setBounds(250,160,200,25);
		p1.add(t4);
		
		t5=new JTextField();
		t5.setBounds(250,210,200,25);
		p1.add(t5);
		
		b1=new JButton("Add");
		b1.setBounds(200,260,100,25);
		p1.add(b1);
		b1.addActionListener(this);
		
		ta1=new JTextArea();
		ta1.setBounds(750,50,450,500);
		this.add(ta1);
		
		b2=new JButton("Print");
		b2.setBounds(325,260,100,25);
		p1.add(b2);
		b2.addActionListener(this);
		
		b3=new JButton("Back");
		b3.setBounds(75,260,100,25);
		p1.add(b3);
		b3.addActionListener(this);
		
		p1.setBounds(50,50,500,300);
		p1.setPreferredSize(new Dimension(500,500));
		this.add(p1);
		this.show();
		this.setVisible(true);
	}
	public void actionPerformed(ActionEvent e)
	{
		if(e.getSource()==b1)
		{			
			ta1.setText("\n"
							+"	======================================="+"\n"
							+"		RCB LAP WORLD"+"\n"
							+"	======================================="+"\n"
							+"		Customer Details"+"\n"
							+"	Customer Name	: "+t1.getText()+"\n"
							+"	Customer Age		: "+t2.getText()+"\n"
							+"	Customer Address	: "+t3.getText()+"\n"
							+"	Customer Mobile	: "+t4.getText()+"\n"
							+"	Customer ID		: "+t5.getText()+"\n"
							+"	======================================="+"\n"
							+"		Laptop Details"+"\n\n"
							+"	Laptop Name	:"+s1+"\n"
							+"	Laptop Model	:"+s2+"\n"
							+"	Laptop id	:"+s3+"\n"
							+"	======================================="+"\n"
							+"		Price Details "+"\n\n"
							+"	Laptop Price	:"+pr+".00"+"\n"
							+"	Laptop Tax	:"+tax+".00"+"\n"
							+"	Grant Total	:"+Gtot+".00"+"\n"
			);
		}
		if(e.getSource()==b2)
		{
			try
			{
				ta1.print();
			}
			catch(PrinterException ex){}
		}
		if(e.getSource()==b3)
		{
			this.dispose();
			new HomePage(c.cq,c.aq);
		}
	}
}