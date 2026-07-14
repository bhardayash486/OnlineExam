package onlineexam;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
public class AdminPanel extends JFrame implements ActionListener
{
    String admin,role;
    JTextField qid,question,optionA,optionB,optionC,optionD,correctOption;
    JButton addQuestion,updateQuestion,deleteQuestion,view_result,addAdmin,logout;

    AdminPanel(String user,String role)
    {
        this.admin = user;
        this.role = role;

        JPanel ap = new JPanel();
        ap.setBounds(0,0,1000,800);
        ap.setBackground(new Color(245,245,245));
        ap.setSize(1000,800);
        ap.setLayout(null);
        add(ap);

        JLabel lh = new JLabel("Admin Panel");
        lh.setBounds(350,20,400,50);
        lh.setForeground(new Color(25,25,112));
        lh.setFont(new Font("Serif",Font.BOLD,50));
        ap.add(lh);

        JLabel questionId = new JLabel("Q_Id");
        questionId.setBounds(50,100,200,30);
        questionId.setForeground(new Color(25,25,112));
        questionId.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(questionId);

        JLabel questionLabel = new JLabel("Question_Text");
        questionLabel.setBounds(50,150,200,30);
        questionLabel.setForeground(new Color(25,25,112));
        questionLabel.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(questionLabel);

        JLabel optiona = new JLabel("Option_A[0]");
        optiona.setBounds(50,200,200,30);
        optiona.setForeground(new Color(25,25,112));
        optiona.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optiona);

        JLabel optionb = new JLabel("Option_B[1]");
        optionb.setBounds(50,250,200,30);
        optionb.setForeground(new Color(25,25,112));
        optionb.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optionb);

        JLabel optionc = new JLabel("Option_C[2]");
        optionc.setBounds(50,300,200,30);
        optionc.setForeground(new Color(25,25,112));
        optionc.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optionc);

        JLabel optiond = new JLabel("Option_D[3]");
        optiond.setBounds(50,350,200,30);
        optiond.setForeground(new Color(25,25,112));
        optiond.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optiond);

        JLabel corrOption = new JLabel("Correct_Option[0-3]");
        corrOption.setBounds(50,400,218,30);
        corrOption.setForeground(new Color(25,25,112));
        corrOption.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(corrOption);

        qid = new JTextField();
        qid.setBounds(270,100,350,30);
        qid.setFont(new Font("Serif",Font.BOLD,25));
        qid.setEnabled(false);
        ap.add(qid);
        
        question = new JTextField();
        question.setBounds(270,150,350,30);
        question.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(question);

        optionA = new JTextField();
        optionA.setBounds(270,200,350,30);
        optionA.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optionA);

        optionB = new JTextField();
        optionB.setBounds(270,250,350,30);
        optionB.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optionB);

        optionC = new JTextField();
        optionC.setBounds(270,300,350,30);
        optionC.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optionC);

        optionD = new JTextField();
        optionD.setBounds(270,350,350,30);
        optionD.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(optionD);

        correctOption = new JTextField();
        correctOption.setBounds(270,400,350,30);
        correctOption.setFont(new Font("Serif",Font.BOLD,25));
        ap.add(correctOption);

        addQuestion = new JButton("Add");
        addQuestion.setBounds(100,460,120,30);
        addQuestion.setFont(new Font("Serif",Font.PLAIN,20));
        addQuestion.addActionListener(this);
        ap.add(addQuestion);

        updateQuestion = new JButton("Update");
        updateQuestion.setBounds(240,460,120,30);
        updateQuestion.setFont(new Font("Serif",Font.PLAIN,20));
        updateQuestion.addActionListener(this);
        ap.add(updateQuestion);

        deleteQuestion = new JButton("Delete");
        deleteQuestion.setBounds(380,460,120,30);
        deleteQuestion.setFont(new Font("Serif",Font.PLAIN,20));
        deleteQuestion.addActionListener(this);
        ap.add(deleteQuestion);

        view_result = new JButton("View Result");
        view_result.setBounds(520,460,150,30);
        view_result.setFont(new Font("Serif",Font.PLAIN,20));
        view_result.addActionListener(this);
        ap.add(view_result);

        addAdmin = new JButton("Add Admin");
        addAdmin.setBounds(690,460,130,30);
        addAdmin.setFont(new Font("Serif",Font.PLAIN,20));
        addAdmin.addActionListener(this);
        ap.add(addAdmin);

        logout = new JButton("Logout");
        logout.setBounds(840,460,120,30);
        logout.setFont(new Font("Serif",Font.PLAIN,20));
        logout.addActionListener(this);
        ap.add(logout);

        setSize(1090,600);
        setTitle("Welcome To Admin Panel "+user);
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
    }
    public static void main(String[] args)
    {
        new AdminPanel("","");
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == addQuestion)
        {
            try
            {
                DBConnect dbInsert = new DBConnect();
                String Question = question.getText();
                String oa = optionA.getText();
                String ob = optionB.getText();
                String oc = optionC.getText();
                String od = optionD.getText();
                String co = correctOption.getText();

                if(Question.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Question.");
                    return;
                }
                if(oa.isEmpty() || ob.isEmpty() || oc.isEmpty() || od.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Options.");
                    return;
                }
                if(co.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Correct Options.");
                    return;
                }

                try
                {
                    int co1 = Integer.parseInt(co);
                    if (co1 < 0 || co1 > 3)
                    {
                        JOptionPane.showMessageDialog(null, "Enter Correct Option Between 0 to 3.");
                        return;
                    }
                }
                catch(NumberFormatException n)
                {
                    JOptionPane.showMessageDialog(null, "Enter Correct Option In Valid Number.");
                    return;
                }

                int InsertAns = dbInsert.stmt.executeUpdate("Insert Into questions(question_text,option_A,option_B,option_C,option_D,"
                + "correct_option) Values('"+Question+"','"+oa+"','"+ob+"','"+oc+"','"+od+"',"+co+")");
                if(InsertAns>0)
                    JOptionPane.showMessageDialog(null,InsertAns+" Question Inserted.");
                dbInsert.con.close();
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == updateQuestion)
        {
            qid.setEnabled(true);
            try
            {
                DBConnect dbUpdate = new DBConnect();
                String check_id = qid.getText();
                if(check_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Enter Question Id To Update.");
                    return;
                }

                int Id = Integer.parseInt(qid.getText());
                String Updated_Question = question.getText();
                String Updated_oa = optionA.getText();
                String Updated_ob = optionB.getText();
                String Updated_oc = optionC.getText();
                String Updated_od = optionD.getText();
                String Updated_co = correctOption.getText();

                if(Updated_Question.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Updated Question.");
                    return;
                }
                if(Updated_oa.isEmpty() || Updated_ob.isEmpty() || Updated_oc.isEmpty() || Updated_od.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Updated Options.");
                    return;
                }
                if(Updated_co.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Please Enter Updated Correct Options.");
                    return;
                }

                try
                {
                    int Updated_co1 = Integer.parseInt(Updated_co);
                    if (Updated_co1 < 0 || Updated_co1 > 3)
                    {
                        JOptionPane.showMessageDialog(null, "Enter Updated Correct Option Between 0 to 3.");
                        return;
                    }
                }
                catch(NumberFormatException num)
                {
                    JOptionPane.showMessageDialog(null, "Enter Updated Correct Option In Valid Number.");
                    return;
                }

                String updateqry = "Update questions Set question_text='"+Updated_Question+"',option_A='"+Updated_oa+"',option_B='"+Updated_ob+"',"
                + "option_C='"+Updated_oc+"',option_D='"+Updated_od+"',correct_option="+Updated_co+" Where q_id = "+Id+" ";
                int updateAns = dbUpdate.stmt.executeUpdate(updateqry);
                if(updateAns>0)
                    JOptionPane.showMessageDialog(null,updateAns+" Question Updated.");
                else
                    JOptionPane.showMessageDialog(null,"Question Id Not Found.");
                dbUpdate.con.close();
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == deleteQuestion)
        {
            qid.setEnabled(true);
            try
            {
                DBConnect dbDelete = new DBConnect();
                String q_id = qid.getText();
                if(q_id.isEmpty())
                {
                    JOptionPane.showMessageDialog(null,"Enter Question Id To Delete.");
                    return;
                }

                int id = Integer.parseInt(qid.getText());
                int deleteAns = dbDelete.stmt.executeUpdate("Delete From questions Where q_id = "+id+" ");

                if(deleteAns>0)
                    JOptionPane.showMessageDialog(null,deleteAns+" Question Deleted.");
                else
                    JOptionPane.showMessageDialog(null,"Question Id Not Found.");
            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
        }
        else if(e.getSource() == view_result)
        {
            setVisible(false);
            new ViewResult(admin,role);
        }
        else if(e.getSource() == addAdmin)
        {
            new AddNewAdmin(admin,role);
            setVisible(false);
        }
        else if(e.getSource() == logout)
        {
            new Login();
            setVisible(false);
        }
    }
}