package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.Timer;
public class Exam_Page extends JFrame implements ActionListener
{
    JLabel lh,l1;
    JRadioButton[] options = new JRadioButton[4];   
    JButton next,submit;
    ButtonGroup grp;
    int current = 0,score = 0;
    String user;
    Timer t1;
    int time_left = 60;
    JLabel tl;

    String[] questions;
    String[][] option_data;
    int[] correct_ans;

    Exam_Page(String user)
    {
        this.user = user;

        lh = new JLabel("ONLINE EXAM SYSTEM");
        lh.setBounds(500,10,500,30);
        lh.setFont(new Font("Serif",Font.BOLD,40));
        add(lh);

        l1 = new JLabel("");
        l1.setBounds(50,250,1500,50);
        l1.setFont(new Font("Serif",Font.BOLD,30));
        add(l1);

        grp = new ButtonGroup();
        int y=310;
        for(int i=0;i<4;i++)
        {
            options[i] = new JRadioButton();
            options[i].setBounds(50,y,500,30);
            options[i].setFont(new Font("Serif",Font.BOLD,20));
            options[i].setBackground(Color.white);
            add(options[i]);
            y+=40;
            grp.add(options[i]);
        }

        submit = new JButton("Submit");
        submit.setBounds(100,480,100,40);
        submit.addActionListener(this);
        submit.setEnabled(false);
        add(submit);

        next = new JButton("Next");
        next.setBounds(250,480,100,40);
        next.addActionListener(this);
        add(next);

        tl = new JLabel("Time Left 60 Seconds.");
        tl.setBounds(400,100,300,40);
        tl.setFont(new Font("Serif",Font.BOLD,30));
        add(tl);

        fetch_Questions();
        loadQuestion(current);

        t1 = new Timer(1000,e->
        {
            time_left--;
            tl.setText("Time Left "+time_left+" Seconds. ");
            if(time_left<=0)
            {
                t1.stop();
                finish_exam();
            }
        });
        t1.start();

        setSize(1500,600);
        setTitle("Online Exam User "+user);
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    } 
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == next)
        {
            if(checkAns())
                score++;
            current++;
            if(current<questions.length)
                loadQuestion(current);
            if(current==questions.length-1)
            {
                next.setEnabled(false);
                submit.setEnabled(true);
            } 
        }
        else if(e.getSource()==submit)
        {
            if(checkAns())
                score++;
            finish_exam();
        }
    }
    public void fetch_Questions()
    {
        try
        {
            DBConnect db = new DBConnect();
            ResultSet rs = db.stmt.executeQuery("Select question_text,option_A,option_B,option_C,option_D,correct_option From questions");
            rs.last();
            int num = rs.getRow();
            rs.beforeFirst();
            
            questions = new String[num];
            option_data = new String[num][4];
            correct_ans = new int[num];

            int index = 0;
            while(rs.next())
            {
                questions[index] = rs.getString("question_text");
                option_data[index][0] = rs.getString("option_A");
                option_data[index][1] = rs.getString("option_B");
                option_data[index][2] = rs.getString("option_C");
                option_data[index][3] = rs.getString("option_D");
                correct_ans[index] = rs.getInt("correct_option");
                index++;
            }
            db.con.close();
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }
    void loadQuestion(int index)
    {
        grp.clearSelection();
        l1.setText("Q"+(index+1)+": "+questions[index]);
        for(int i=0;i<4;i++)
        {
            options[i].setText(option_data[index][i]);
        }
    }
    boolean checkAns()
    {
        if(current >= correct_ans.length)
            return false;
        for (int i = 0; i < 4; i++)
        {
            if (options[i].isSelected())
            {
                return i == correct_ans[current];
            }
        }
        return false;
    }
    void finish_exam()
    {
        t1.stop();
        JOptionPane.showMessageDialog(this,"Your Score Is "+score+"/"+questions.length);
        try
        {
            DBConnect db = new DBConnect();
            ResultSet rs = db.stmt.executeQuery("Select * From result Where sl_username = '"+user+"'");
            if(rs.next())
            {
                JOptionPane.showMessageDialog(null,"You have already taken the exam.");
                setVisible(false);
                return;
            }
            else
            {
                int ans = db.stmt.executeUpdate("Insert Into result (sl_username,score) Values ('"+user+"','"+score+"')");
            }
            db.con.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        System.exit(0);
    }
    public static void main(String[] args)
    {
        new Exam_Page("");
    }
}