package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class AddNewAdmin extends JFrame implements ActionListener
{
    JTextField ut;
    JPasswordField pf,cpf;
    JButton add_admin,login,back;
    String admin,role;

    AddNewAdmin(String admin,String role)
    {
        this.admin = admin;
        this.role = role;

        JPanel jp = new JPanel();
        jp.setBounds(30,70,700,500);
        jp.setBackground(new Color(0,0,128,180));
        jp.setLayout(null);
        add(jp);

        JLabel heading = new JLabel("ONLINE EXAM SYSTEM");
        heading.setBounds(190,0,470,50);
        heading.setForeground(Color.blue);
        heading.setFont(new Font("Serif",Font.BOLD,40));
        add(heading);

        JLabel lh = new JLabel("Add New Admin");
        lh.setBounds(230,0,290,60);
        lh.setForeground(Color.white);
        lh.setFont(new Font("Serif",Font.BOLD,40));
        jp.add(lh);

        JLabel ul = new JLabel("User Name :");
        ul.setBounds(200,150,150,30);
        ul.setForeground(Color.white);
        ul.setFont(new Font("Calibri",Font.BOLD,23));
        jp.add(ul);

        JLabel pl = new JLabel("Password :");
        pl.setBounds(200,200,150,30);
        pl.setForeground(Color.white);
        pl.setFont(new Font("Calibri",Font.BOLD,23));
        jp.add(pl);

        JLabel cp = new JLabel("Confirm Password :");
        cp.setBounds(200,250,200,30);
        cp.setForeground(Color.white);
        cp.setFont(new Font("Calibri",Font.BOLD,23));        
        jp.add(cp);

        ut = new JTextField();
        ut.setBounds(385,150,200,30);
        ut.setFont(new Font("Calibari",Font.BOLD,23));
        jp.add(ut);

        pf = new JPasswordField();
        pf.setBounds(385,200,200,30);
        pf.setFont(new Font("Calibari",Font.BOLD,23));
        jp.add(pf);

        cpf = new JPasswordField();
        cpf.setBounds(385,250,200,30);
        cpf.setFont(new Font("Calibari",Font.BOLD,23));
        jp.add(cpf);

        add_admin = new JButton("Add Admin");
        add_admin.setBounds(200,330,150,30);
        add_admin.setBackground(Color.green);
        add_admin.setForeground(Color.white);
        add_admin.setFont(new Font("Calibri",Font.BOLD,25));
        add_admin.addActionListener(this);
        jp.add(add_admin);

        login = new JButton("Login");
        login.setBounds(400,330,150,30);
        login.setBackground(Color.green);
        login.setForeground(Color.white);
        login.setFont(new Font("Calibri",Font.BOLD,25));
        login.addActionListener(this);
        jp.add(login);

        back = new JButton("Back");
        back.setBounds(300,390,150,30);
        back.setBackground(Color.red);
        back.setForeground(Color.white);
        back.setFont(new Font("Calibri",Font.BOLD,25));
        back.addActionListener(new AbstractAction()
        {
            public void actionPerformed(ActionEvent e)
            {
                setVisible(false);
                new AdminPanel(admin,role);
            }
        });
        jp.add(back);

        setSize(780,660);
        getContentPane().setBackground(Color.white);
        setLayout(null);
        setTitle("Add New Admin");
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == add_admin)
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

                ResultSet rs = db.stmt.executeQuery("Select * From staff_login Where staff_username = '"+user+"'");
                if(rs.next())
                {
                    JOptionPane.showMessageDialog(null,"Username Exists.");
                    return;
                }
                else
                {
                    int ans = db.stmt.executeUpdate("Insert Into staff_login (staff_username,staff_password) Values('"+user+"','"+pwd+"')");
                    if(ans>0)
                        JOptionPane.showMessageDialog(null,"Admin Added Successfully.");
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
        new AddNewAdmin("","");
    }
}