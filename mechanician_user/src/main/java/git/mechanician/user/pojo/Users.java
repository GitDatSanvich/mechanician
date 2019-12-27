package git.mechanician.user.pojo;

import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName Users
 * @Author GitDatSanvich
 * @Date 2019/5/29 9:28
 **/
@Getter
@Setter
@Entity
@Table(name = "users")
public class Users implements Serializable {
    /**
     * 用户id
     */
    @Id
    private String id;
    /**
     * 用户名称
     */
    private String username;
    /**
     * 用户邮箱
     */
    private String email;
    /**
     * 用户积分(文章数)
     */
    private int num;
    /**
     * 用户角色
     */
    private String role;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户状态
     */
    private String statue;

    @Override
    public String toString() {
        return "Users{" + "Id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", num=" + num +
                ", role='" + role + '\'' +
                ", password='" + password + '\'' +
                ", statue='" + statue + '\'' +
                '}';
    }
    public Users(String id, String username, String email, int num, String role, String password, String statue) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.num = num;
        this.role = role;
        this.password = password;
        this.statue = statue;
    }
}
