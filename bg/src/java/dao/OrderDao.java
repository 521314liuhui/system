package dao;

import bean.FruitBean;
import bean.OrderBean;
import bean.ResultStatus;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class OrderDao {

    public static ResultStatus generate(OrderBean bean) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        int row = query.update(conn,
                "insert into order_info values(?,?,?,?,?,?,?)",
                null,
                bean.getAmount(),
                bean.getUprice(),
                bean.getNumber(),
                bean.getRefAdmin(),
                bean.getMakeTime(),
                bean.getName());
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"生成订单成功");
        }else{
            return new ResultStatus(false,"生成订单失败");
        }
    }

    public static ResultStatus delete(int id) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        int row = query.update(conn,
                "delete form order_info id=?",
                id);
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"删除订单信息成功");
        }else{
            return new ResultStatus(false,"删除订单信息失败");
        }
    }

    /**
     * 获取所有订单信息
     * @return
     * @throws SQLException
     */
    public static ResultStatus getAll() throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        List<OrderBean> result = query.query(conn,
                "select *from order_info ",
                new BeanListHandler<OrderBean>(OrderBean.class));
        conn.close();
        if(result == null){
            return new ResultStatus(false,"系统发生错误，请联系管理员");
        }
        return new ResultStatus(true,result);
    }
}
