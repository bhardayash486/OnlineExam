package onlineexam;
import java.sql.*;
public class DBConnect
{
    Connection con;
    Statement stmt;
    PreparedStatement pstmt;
    public DBConnect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_exam","root","");
            stmt = con.createStatement();
            pstmt = con.prepareStatement("");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void main(String[] args)
    {
    }
}