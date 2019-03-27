package dao;

import bean.FruitBean;
import bean.ResultStatus;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;
import service.SystemService;
import util.JdbcUtil;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public class FruitDao {

    /**
     * 水果入库
     * @param bean 水果对象、
     * @return 返回状态信息对象
     *
     */
    public static ResultStatus put(FruitBean bean) throws SQLException {
        FruitBean fruitBean = lookUp(bean.getName(),bean.getPushPrice());
        if(fruitBean == null){
            // 水果不存在，插入新水果
            return insert(bean);
        }else{
            // 水果存在，则修改水果数量完成入库
            return updateNumber(fruitBean.getId(), fruitBean.getNumber() + bean.getNumber());
        }
    }

    /**
     * 插入新的水果
     * @param bean
     * @return
     * @throws SQLException
     */
    public static ResultStatus insert(FruitBean bean) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        int row = query.update(conn,
                "insert into fruit_info values(?,?,?,?,?,?,?,?)",
                null,
                bean.getName(),
                bean.getAddress(),
                bean.getNumber(),
                bean.getPushPrice(),
                bean.getSellPrice(),
                bean.getPushTime(),
                bean.getLiveTime());
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"插入水果信息成功");
        }else{
            return new ResultStatus(false,"插入水果信息失败");
        }
    }

    public static ResultStatus delete(int id) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        int row = query.update(conn,
                "delete form fruit_info id=?",
                id);
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"删除水果信息成功");
        }else{
            return new ResultStatus(false,"删除水果信息失败");
        }
    }

     /**
     * 更新水果数量
     * @param number 新的水果数量
     * @param id
     * @return
     * @throws SQLException
     */
    public static ResultStatus updateNumber(float number, float id) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();
        int row = query.update(conn,
                "update fruit_info set number=? where id=?",
                number,id);
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"修改水果数量成功");
        }else {
            return new ResultStatus(false,"修改水果数量失败");
        }
    }

    /**
     * 修改水果售出单价
     * @param sellPrice 新的售出单价
     * @param id
     * @return
     * @throws SQLException
     */
    public static ResultStatus updateSellPrice(float sellPrice,int id) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();
        int row = query.update(conn,
                "update fruit_info set number=? where sellPrice=?",
                sellPrice,id);
        conn.close();
        if(row > 0){
            return new ResultStatus(true,"修改水果单价成功");
        }else {
            return new ResultStatus(false,"修改水果单价失败");
        }
    }

    /**
     * 根据水果名和水果入库价格查询水果对象
     * @param name
     * @return
     * @throws SQLException
     */
    public static FruitBean lookUp(String name,float pushPrice) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        FruitBean result = query.query(conn,
                "select *from fruit_info where name=? and pushPrice=?",
                new BeanHandler<FruitBean>(FruitBean.class),
                name,pushPrice);
        conn.close();
        return result;
    }

    public static FruitBean lookUp(int id) throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        FruitBean result = query.query(conn,
                "select *from fruit_info where id=?",
                new BeanHandler<FruitBean>(FruitBean.class),
                id);
        conn.close();
        return result;
    }

    /**
     * 获取所有水果信息
     * @return
     * @throws SQLException
     */
    public static ResultStatus getAll() throws SQLException {
        Connection conn = JdbcUtil.getConn();
        QueryRunner query = new QueryRunner();

        List<FruitBean> result = query.query(conn,
                "select *from fruit_info ",
                new BeanListHandler<FruitBean>(FruitBean.class));
        conn.close();
        if(result == null){
            return new ResultStatus(false,"系统发生错误，请联系管理员");
        }
        return new ResultStatus(true,result);
    }

    public static void main(String[] args) {
        FruitBean fruitBean = new FruitBean();
        fruitBean.setName("香蕉");
        fruitBean.setAddress("南宁");
        fruitBean.setPushPrice(3.0f);
        fruitBean.setSellPrice(4.0f);
        fruitBean.setNumber(1000);
        fruitBean.setPushTime(new Timestamp(new Date().getTime()));
        fruitBean.setLiveTime(10);
        System.out.println(SystemService.adminLogin("ex", "1rfaf"));
        System.out.println(SystemService.fruitPay(3, 30));
        System.out.println(SystemService.getMonthlyPerformance());
    }
}
