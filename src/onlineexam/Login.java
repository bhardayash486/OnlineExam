package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.awt.Image;
import java.awt.event.*;
import java.sql.*;
public class Login extends JFrame implements ActionListener
{
    JTextField ut;
    JPasswordField pf;
    JButton login,back,signup;
    Choice ch;
    Login()
    {
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/main.jpg"));
        Image i2 = i1.getImage().getScaledInstance(2000,1000,Image.SCALE_DEFAULT);
        ImageIcon i3 = new ImageIcon(i2);
        JLabel image = new JLabel(i3);
        setContentPane(image);
        image.setLayout(null);

        JPanel jp = new JPanel();
        jp.setBounds(600,100,600,600);
        jp.setBackground(new Color(0,0,128,180));
        jp.setLayout(null);
        add(jp);

        ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource("icons/logo.png"));
        JLabel image1 = new JLabel(logo);
        image1.setBounds(230,50,150,150);
        jp.add(image1);

        ch = new Choice();
        ch.setBounds(200,210,200,30);
        ch.setFont(new Font("Calibri",Font.BOLD,25));
        ch.add("None");
        ch.add("Admin");
        ch.add("Student");
        jp.add(ch);

        JLabel heading = new JLabel("ONLINE EXAM SYSTEM");
        heading.setBounds(530,0,470,50);
        heading.setForeground(Color.blue);
        heading.setFont(new Font("Serif",Font.BOLD,40));
        add(heading);

        JLabel lh = new JLabel("LOGIN");
        lh.setBounds(230,0,250,50);
        lh.setForeground(Color.white);
        lh.setFont(new Font("Serif",Font.BOLD,40));
        jp.add(lh);

        JLabel ul = new JLabel("User Name :");
        ul.setBounds(100,270,150,30);
        ul.setForeground(Color.white);
        ul.setFont(new Font("Calibri",Font.BOLD,25));
        jp.add(ul);

        JLabel pl = new JLabel("Password :");
        pl.setBounds(100,330,150,30);
        pl.setForeground(Color.white);
        pl.setFont(new Font("Calibri",Font.BOLD,25));
        jp.add(pl);

        ut = new JTextField();
        ut.setBounds(250,270,200,30);
        ut.setFont(new Font("Calibari",Font.BOLD,25));
        jp.add(ut);

        pf = new JPasswordField();
        pf.setBounds(250,330,200,30);
        pf.setFont(new Font("Calibari",Font.BOLD,25));
        jp.add(pf);

        login = new JButton("Login");
        login.setBounds(100,420,130,30);
        login.setBackground(Color.green);
        login.setForeground(Color.white);
        login.setFont(new Font("Calibri",Font.BOLD,25));
        login.addActionListener(this);
        jp.add(login);

        signup = new JButton("Sign Up");
        signup.setBounds(250,420,130,30);
        signup.setFont(new Font("Calibri",Font.BOLD,25));
        jp.add(signup);
        signup.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new SignUp();
            }
        });

        back = new JButton("Back");
        back.setBounds(400,420,130,30);
        back.setBackground(Color.red);
        back.setForeground(Color.white);
        back.setFont(new Font("Calibri",Font.BOLD,25));
        jp.add(back);
        back.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
            }
        });

        setSize(1500,800);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == login)
       {
            try
            {
                DBConnect db = new DBConnect();
                String user = ut.getText();
                String pwd = pf.getText();
                String role = ch.getSelectedItem();
                if(role.equals("None"))
                {
                    JOptionPane.showMessageDialog(null,"Please Select Your Role.");
                    return;
                }
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
                if(role.equals("Admin"))
                {
                    String check = "Select * From staff_login Where staff_username = '"+user+"' AND staff_password = '"+pwd+"'";
                    ResultSet rs = db.stmt.executeQuery(check);
                    if(rs.next())
                    {
                        if(user.equals(rs.getString("staff_username")) && pwd.equals(rs.getString("staff_password")))
                        {
                            JOptionPane.showMessageDialog(null,"Login Success, Welcome To Admin Panel.");
                            new AdminPanel(user,role);
                            setVisible(false);
                        }
                        else
                        {
                           JOptionPane.showMessageDialog(null,"Invalid Username Or Password.");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Invalid Username Or Password.");
                    }
                }
                else if(role.equals("Student"))
                {
                    String check = "Select * From students_login Where sl_username = '"+user+"' AND sl_password = '"+pwd+"'";
                    ResultSet rs = db.stmt.executeQuery(check);
                    if(rs.next())
                    {
                        if(user.equals(rs.getString("sl_username")) && pwd.equals(rs.getString("sl_password")))
                        {
                            JOptionPane.showMessageDialog(null,"Login Success, Start Exam.");
                            new Exam_Page(user);
                            setVisible(false);
                        }
                        else
                        {
                            JOptionPane.showMessageDialog(null,"Invalid Username Or Password.");
                        }
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Invalid Username Or Password.");
                    }
                }
                db.con.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
       }
    }
    public static void main(String[] args)  
    {
        new Login();
    }
}