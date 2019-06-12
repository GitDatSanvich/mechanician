package git.mechanician.handOver.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name = "handover")
public class Handover implements Serializable {

    @Id
    private String Id;//交接Id


    private java.util.Date date;//建立时间
    private String main;//交接内容
    private String dates;//建立时间字符串格式
    private String username;

    public Handover() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Handover(String id, Date date, String main, String dates, String username) {
        Id = id;
        this.date = date;
        this.main = main;
        this.dates = dates;
        this.username = username;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public java.util.Date getDate() {
        return date;
    }

    public void setDate(java.util.Date date) {
        this.date = date;
    }

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDates() {
        return dates;
    }

    public void setDates(String dates) {
        this.dates = dates;
    }
}
