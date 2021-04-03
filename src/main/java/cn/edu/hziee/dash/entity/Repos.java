package cn.edu.hziee.dash.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Repos {
    private Integer rid;
    private String name;
    private Integer uid;
    private Date time;

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Date getTime() {
        return time;
    }

    public String showTime() {
        SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd ");
        return sf.format(time);
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Repos{" +
                "rid=" + rid +
                ", name='" + name + '\'' +
                ", uid=" + uid +
                ", time=" + time +
                '}';
    }
}
