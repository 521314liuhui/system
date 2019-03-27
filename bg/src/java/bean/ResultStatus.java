package bean;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

public class ResultStatus {
    private Map<String,Object> map = new HashMap<String, Object>(2);

    public ResultStatus(boolean status,String msg){
        map.put("status",status);
        map.put("msg",msg);
    }

    public boolean isSuccess(){
        return Boolean.valueOf(map.get("status").toString());
    }

    public Object getResult(){return map.get("result");}

    public ResultStatus(boolean status,Object result){
        map.put("status",status);
        map.put("result",result);
    }

    public ResultStatus(boolean status,String msg,Object result){
        map.put("status",status);
        map.put("msg",msg);
        map.put("result",result);
    }

    public String toJson(){
        return JSON.toJSONString(map);
    }

    @Override
    public String toString() {
        return toJson();
    }
}
