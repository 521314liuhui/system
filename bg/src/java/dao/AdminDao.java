package dao;

import bean.AdminBean;
import bean.ResultStatus;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

public class AdminDao {
    /**
     * 管理员登陆验证
     * @param bean 管理员对象
     * @return 返回状态信息对象
     * @throws SQLException
     */
    public static ResultStatus login(AdminBean bean) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        AdminBean result = query.query(conn,
                "select *from admin_info where name=? and password=?",
                new BeanHandler<AdminBean>(AdminBean.class),
                bean.getName(), bean.getPassword());
        conn.close();
        if(result == null){
            return new ResultStatus(false,"登陆失败，账号或者密码错误");
        }
        return new ResultStatus(true,"登陆成功",result);
    }

    /**
     * 注册管理员账号
     * @param bean 管理员对象
     * @return 返回状态信息对象
     * @throws SQLException
     */
    public static ResultStatus register(AdminBean bean) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        if(verify(bean) == false){
            return new ResultStatus(false,"请输入有效的注册信息");
        }
        if(alreadyExist(bean) == true){
            return new ResultStatus(false,"该用户名或手机已被注册");
        }

        int row = query.update(conn,
                "insert into admin_info values(?,?,?,?)",
                null,
                bean.getPassword(),
                bean.getName(),
                bean.getPhone());

        conn.close();
        if (row > 0){
            return new ResultStatus(true,"注册成功");
        }
        return new ResultStatus(true,"注册失败");
    }

    /**
     * 忘记密码后设置密码
     * 手机号和用户名正确即可
     * @param bean 管理员对象
     * @return 返回状态信息对象
     */
    public static ResultStatus forgotPassWord(AdminBean bean) throws SQLException {
        if(alreadyExist(bean) == true){
            // 账号存在，修改密码
            return modifyPassWord(bean);

        }else {
            return new ResultStatus(false,"无法找到对应账号信息，找回密码失败");
        }
    }

    /**
     * 修改管理员密码
     * @param bean
     * @return
     */
    public static ResultStatus modifyPassWord(AdminBean bean) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();
        int row = query.update(conn,
                "update admin_info set password=? where name=? and phone=?",
                bean.getPassword(),
                bean.getName(),
                bean.getPhone());
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"修改密码成功");
        }else {
            return new ResultStatus(false,"修改密码失败");
        }
    }

    /**
     * 获取所有管理员对象
     * @return 返回状态信息对象
     */
    public static ResultStatus getAll() throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        List<AdminBean> result = query.query(conn,
                "select *from admin_info ",
                new BeanListHandler<AdminBean>(AdminBean.class));
        conn.close();
        if(result == null){
            return new ResultStatus(false,"系统发生错误，请联系管理员");
        }
        return new ResultStatus(true,result);
    }

    /**
     * 判断管理员账号是否存在
     * @param bean 管理员对象
     * @return 存在返回true 不存在返回false
     * @throws SQLException
     */
    private static boolean alreadyExist(AdminBean bean) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        AdminBean result = query.query(conn,
                "select *from admin_info where name=? or phone=?",
                new BeanHandler<AdminBean>(AdminBean.class),
                bean.getName(), bean.getPhone());
        conn.close();
        if(result == null){
            return false;
        }
        return true;
    }

    /**
     * 校验管理员对象
     * @param bean 管理员对象
     * @return 合法返回true 不合法返回false
     */
    private static boolean verify(AdminBean bean){
        return PHONE_VERIFY_PATTERN.matcher(bean.getPhone()).matches();
    }

    public static void main(String[] args) throws SQLException {

        System.out.println("getAll() = " + getAll());
    }

    private static Pattern PHONE_VERIFY_PATTERN = Pattern.compile("^[1][3,4,5,7,8][0-9]{9}$");
}
