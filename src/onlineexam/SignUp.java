package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class SignUp extends JFrame implements ActionListener
{
    JTextField ut;
    JPasswordField pf,cpf;
    JButton signup,login;
    SignUp()
    {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/main.jpg"));
        Image i2 = i1.getImage().getScaledInstance(2000,1000,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        setContentPane(image);
        image.setLayout(null);

        JPanel jp = new JPanel();
        jp.setBounds(600,100,700,500);
        jp.setBackground(new Color(0,0,128,180));
        jp.setLayout(null);
        add(jp);

        ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource("icons/logo.png"));
        JLabel image1 = new JLabel(logo);
        image1.setBounds(280,60,150,150);
        jp.add(image1);

        JLabel heading = new JLabel("ONLINE EXAM SYSTEM");
        heading.setBounds(530,0,470,50);
        heading.setForeground(Color.blue);
        heading.setFont(new Font("Serif",Font.BOLD,40));
        add(heading);

        JLabel lh = new JLabel("Sign Up");
        lh.setBounds(280,0,250,60);
        lh.setForeground(Color.white);
        lh.setFont(new Font("Serif",Font.BOLD,40));
        jp.add(lh);

        JLabel ul = new JLabel("User Name :");
        ul.setBounds(200,250,150,30);
        ul.setForeground(Color.white);
        ul.setFont(new Font("Calibri",Font.BOLD,23));
        jp.add(ul);

        JLabel pl = new JLabel("Password :");
        pl.setBounds(200,300,150,30);
        pl.setForeground(Color.white);
        pl.setFont(new Font("Calibri",Font.BOLD,23));
        jp.add(pl);

        JLabel cp = new JLabel("Confirm Password :");
        cp.setBounds(200,350,200,30);
        cp.setForeground(Color.white);
        cp.setFont(new Font("Calibri",Font.BOLD,23));        
        jp.add(cp);

        ut = new JTextField();
        ut.setBounds(385,250,200,30);
        ut.setFont(new Font("Calibari",Font.BOLD,23));
        jp.add(ut);

        pf = new JPasswordField();
        pf.setBounds(385,300,200,30);
        pf.setFont(new Font("Calibari",Font.BOLD,23));
        jp.add(pf);

        cpf = new JPasswordField();
        cpf.setBounds(385,350,200,30);
        cpf.setFont(new Font("Calibari",Font.BOLD,23));
        jp.add(cpf);

        signup = new JButton("Sign Up");
        signup.setBounds(200,430,150,30);
        signup.setBackground(Color.green);
        signup.setForeground(Color.white);
        signup.setFont(new Font("Calibri",Font.BOLD,25));
        signup.addActionListener(this);
        jp.add(signup);

        login = new JButton("Login");
        login.setBounds(400,430,150,30);
        login.setBackground(Color.green);
        login.setForeground(Color.white);
        login.setFont(new Font("Calibri",Font.BOLD,25));
        login.addActionListener(this);
        jp.add(login);

        setSize(1500,800);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == signup)
        {
            try
            {
                DBConnect db = new DBConnect();
                String user = ut.getText();
                String pwd = pf.getText();
                String cpwd = cpf.getText();

                if(user.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Username.");
                    return;
                }
                if(pwd.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Password.");
                    return;
                }
                if(!pwd.equals(cpwd))
                {
                    JOptionPane.showMessageDialog(null,"Please Check Password and Confirm Password Are Same or Not.");
                    return;
                }

                ResultSet rs = db.stmt.executeQuery("Select * From students_login Where sl_username = '"+user+"'");
                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null,"Username Exists.");
                    return;
                }
                else
                {
                    int ans = db.stmt.executeUpdate("Insert Into students_login (sl_username,sl_password) Values('"+user+"','"+pwd+"')");
                    if(ans>0)
                        JOptionPane.showMessageDialog(null,"Sign Up Successful You Can Log In.");
                }
                db.con.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == login)
        {
            setVisible(false);
            new Login();
        }
    }
    public static void main(String[] args)
    {
        new SignUp();
    }
}