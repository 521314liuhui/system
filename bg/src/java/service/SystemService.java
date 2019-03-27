package service;

import bean.AdminBean;
import bean.FruitBean;
import bean.OrderBean;
import bean.ResultStatus;
import dao.AdminDao;
import dao.FruitDao;
import dao.OrderDao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemService {
    private static AdminBean ADMIN_BEAN;

    public static ResultStatus adminLogin(String name,String password){
        AdminBean adminBean = new AdminBean();
        adminBean.setName(name);
        adminBean.setPassword(password);
        try {
            ResultStatus login = AdminDao.login(adminBean);
            if(login.isSuccess()){
                ADMIN_BEAN = (AdminBean) login.getResult();
            }
            return login;
        } catch (SQLException e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    public static ResultStatus adminForgotPassWord(String name,String newPassWord,String phone){
        AdminBean adminBean = new AdminBean();
        adminBean.setPassword(newPassWord);
        adminBean.setName(name);
        adminBean.setPhone(phone);
        try {
            return AdminDao.forgotPassWord(adminBean);
        } catch (SQLException e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    public static ResultStatus adminModifyPassWord(String name ,String passWord,String newPassWord){
        AdminBean adminBean = new AdminBean();
        adminBean.setName(name);
        adminBean.setPassword(passWord);
        try {
            if (AdminDao.login(adminBean).isSuccess()) {
                adminBean.setPassword(newPassWord);
                return AdminDao.modifyPassWord(adminBean);
            }else{
                return new ResultStatus(false,"修改密码失败，原密码输入错误");
            }
        } catch (SQLException e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    public static ResultStatus adminRegister(String name,String passWord,String phone){
        AdminBean adminBean = new AdminBean();
        try {
            return AdminDao.register(adminBean);
        } catch (SQLException e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    /**
     * 水果入库，当水果存在则修改水果数量，否则插入新的条目
     * @param bean
     * @return
     */
    public static ResultStatus fruitPush(FruitBean bean){
        try {
            FruitBean fruitBean = FruitDao.lookUp(bean.getName(), bean.getPushPrice());
            if(fruitBean == null){
                return FruitDao.insert(bean);
            }else{
                return FruitDao.updateNumber(fruitBean.getNumber() + bean.getNumber(), fruitBean.getId());
            }
        } catch (SQLException e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    /**
     * 获取所有水果
     * @return
     */
    public static ResultStatus fruitGetAll(){
        try {
            return FruitDao.getAll();
        } catch (SQLException e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    /**
     * 售出水果
     * @param fruitId
     * @param number
     * @return
     */
    public static ResultStatus fruitPay(int fruitId,float number){
        // 水果是否存在，水果数量是否存在
        try {
            FruitBean fruitBean = FruitDao.lookUp(fruitId);
            if(fruitBean == null){
                return new ResultStatus(false,"水果不存在，售出水果失败");
            }else{
                if(fruitBean.getNumber() >= number){
                    // 减少水果数量
                    if (FruitDao.updateNumber(fruitBean.getNumber() - number,fruitId).isSuccess()) {
                        OrderBean orderBean = new OrderBean();
                        orderBean.setName(fruitBean.getName());
                        orderBean.setMakeTime(new Timestamp(new Date().getTime()));
                        orderBean.setRefAdmin(ADMIN_BEAN.getId());
                        orderBean.setUprice(fruitBean.getSellPrice());
                        orderBean.setNumber(number);
                        orderBean.setAmount(number * fruitBean.getSellPrice());
                        if (OrderDao.generate(orderBean).isSuccess()) {
                            return new ResultStatus(true,"售出水果成功");
                        }else{
                            return new ResultStatus(false,"售出水果失败，系统错误");
                        }
                    }else{
                        return new ResultStatus(false,"售出水果失败，系统错误");
                    }
                }else{
                    return new ResultStatus(false,"水果库存不足，售出水果失败");
                }
            }
        } catch (Exception e) {
            return new ResultStatus(false,e.getMessage());
        }
    }

    /**
     * 统计每个月的销售额
     * @return
     */
    public static ResultStatus getMonthlyPerformance(){
        try {
            Map<Integer,Double> performance = new HashMap<Integer, Double>(12);
            ResultStatus result = OrderDao.getAll();
            List<OrderBean> orderBeanList = (List<OrderBean>) result.getResult();
            for (OrderBean order : orderBeanList) {
                int month = order.getMakeTime().getMonth();
                Double value = performance.get(month);
                if(value == null){
                    performance.put(month, (double) order.getAmount());
                }else{
                    performance.put(month,value+order.getAmount());
                }
            }
            return new ResultStatus(true,performance);
        } catch (SQLException e) {
            return new ResultStatus(false,"获取月销售额统计数据失败");
        }
    }
}
