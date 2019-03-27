package util;



import javax.sql.DataSource;
import java.sql.*;

public class JdbcUtil {

    //静态加载驱动
    static{
        try {
            Class.forName(GlobalConstants.DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /**
     * 获取连接数据库
     */
    public static Connection getConn(){
        Connection conn =null;
        try {
            conn = DriverManager.getConnection(GlobalConstants.URL,GlobalConstants.USERNAME,
                    GlobalConstants.PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
    /**
     * 关闭mysql相关资源
     * @param conn
     * @param ps
     * @param rs
     */
    public static void close(Connection conn, PreparedStatement ps, ResultSet rs){
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(rs != null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("MySQL连接成功 Address：" + JdbcUtil.getConn());
    }
}
