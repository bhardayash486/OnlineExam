package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.*;
public class ViewResult extends JFrame implements ActionListener
{
    String admin,role;
    JButton view,back;
    JTable result;
    DefaultTableModel model;
    ViewResult(String user,String role)
    {
        this.admin = user;
        this.role = role;

        JLabel th = new JLabel("Result Table");
        th.setBounds(390,10,400,50);
        th.setForeground(new Color(0,0,139));
        th.setFont(new Font("Serif",Font.BOLD,50));
        add(th);

        model = new DefaultTableModel(new Object[]{"Id","User Name", "Score"},0)
        {
            public boolean isCellEditable(int row,int column)
            {
                return false;
            }
        };

        result = new JTable(model);
        result.setBackground(Color.white);
        result.setForeground(Color.black);
        result.setFont(new Font("Calibri",Font.PLAIN,20));
        result.setRowHeight(25);
        result.getTableHeader().setReorderingAllowed(false);

        JScrollPane sp = new JScrollPane(result);
        sp.setBounds(100,80,800,400);
        add(sp);

        JTableHeader header = result.getTableHeader();
        header.setFont(new Font("Calibri",Font.PLAIN,25));
        header.setBackground(new Color(0,102,204));
        header.setForeground(Color.white);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < result.getColumnCount(); i++)
        {
            result.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        view = new JButton("View Result");
        view.setBounds(300,500,160,40);
        view.setBackground(Color.green);
        view.setForeground(Color.white);
        view.setFont(new Font("Calibri",Font.BOLD,23));
        view.addActionListener(this);
        add(view);

        back = new JButton("Back");
        back.setBounds(500,500,160,40);
        back.setBackground(Color.red);
        back.setForeground(Color.white);
        back.setFont(new Font("Calibri",Font.BOLD,23));
        back.addActionListener(this);
        add(back);

        setSize(1000,600);
        setTitle("Welcome Result Table "+user);
        setLayout(null);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new ViewResult("","");
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == view)
        {
            model.setRowCount(0);
            try
            {
                DBConnect db = new DBConnect();
                ResultSet rs = db.stmt.executeQuery("Select * From result");
                if(role.equals("Admin"))
                {
                    while(rs.next())
                    {
                        int id = rs.getInt("r_id");
                        String username = rs.getString("sl_username");
                        int score = rs.getInt("score");
                        model.addRow(new Object[]
                        {
                            id,username,score
                        });
                    }
                }
                db.con.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        if(e.getSource() == back)
        {
            setVisible(false);
            new AdminPanel(admin,role);
        }   
    }
}