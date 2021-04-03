package cn.edu.hziee.dash.entity;

import java.util.Date;
import java.text.SimpleDateFormat;

public class Doc {
    private Integer did;
    private String name;
    private Date time;

    public Integer getDid() {
        return did;
    }

    public void setDid(Integer did) {
        this.did = did;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return "Doc{" +
                "did=" + did +
                ", name='" + name + '\'' +
                ", time=" + time +
                '}';
    }
}
