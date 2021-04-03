package cn.edu.hziee.dash.entity;

import java.sql.Date;

public class Version {
    private Integer vid;
    private String author;
    private Date time;
    private String content;
    private String title;

    public Integer getVid() {
        return vid;
    }

    public void setVid(Integer vid) {
        this.vid = vid;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Version{" +
                "vid=" + vid +
                ", author='" + author + '\'' +
                ", time=" + time +
                ", content='" + content + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
