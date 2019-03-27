package bean;

import java.sql.Timestamp;

public class OrderBean {
    private int id;
    private String name;
    private float amount;
    private float number;
    private float uprice;
    private Timestamp makeTime;
    private int refAdmin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public float getUprice() {
        return uprice;
    }

    public void setUprice(float uprice) {
        this.uprice = uprice;
    }

    public Timestamp getMakeTime() {
        return makeTime;
    }

    public void setMakeTime(Timestamp makeTime) {
        this.makeTime = makeTime;
    }

    public int getRefAdmin() {
        return refAdmin;
    }

    public void setRefAdmin(int refAdmin) {
        this.refAdmin = refAdmin;
    }

    @Override
    public String toString() {
        return "OrderBean{" +
                "id=" + id +
                ", amount=" + amount +
                ", number=" + number +
                ", uprice=" + uprice +
                ", makeTime=" + makeTime +
                ", refAdmin=" + refAdmin +
                '}';
    }
}
