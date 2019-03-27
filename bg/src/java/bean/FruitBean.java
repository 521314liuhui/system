package bean;

import java.sql.Timestamp;

public class FruitBean {
    private int id;
    private String name;
    private String address;
    private float number;
    private float pushPrice;
    private float sellPrice;
    private Timestamp pushTime;
    private int liveTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getNumber() {
        return number;
    }

    public void setNumber(float number) {
        this.number = number;
    }

    public float getPushPrice() {
        return pushPrice;
    }

    public void setPushPrice(float pushPrice) {
        this.pushPrice = pushPrice;
    }

    public float getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(float sellPrice) {
        this.sellPrice = sellPrice;
    }

    public Timestamp getPushTime() {
        return pushTime;
    }

    public void setPushTime(Timestamp pushTime) {
        this.pushTime = pushTime;
    }

    public int getLiveTime() {
        return liveTime;
    }

    public void setLiveTime(int liveTime) {
        this.liveTime = liveTime;
    }

    @Override
    public String toString() {
        return "FruitBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", number=" + number +
                ", pushPrice=" + pushPrice +
                ", sellPrice=" + sellPrice +
                ", pushTime=" + pushTime +
                ", liveTime=" + liveTime +
                '}';
    }
}
