package git.mechanician.handOver.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 实体类
 *
 * @author Administrator
 */
@Entity
@Table(name = "mechanician_handover")
public class Handover implements Serializable {

    @Id
    private String Id;//交接Id


    private java.util.Date date;//建立时间
    private String main;//交接内容


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


}