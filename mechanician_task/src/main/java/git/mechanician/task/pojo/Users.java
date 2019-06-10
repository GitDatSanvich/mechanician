package git.mechanician.task.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @ClassName Users
 * @Author GitDatSanvich
 * @Date 2019/5/29 9:28
 **/
@Entity
@Table(name = "users")
public class Users implements Serializable {
    @Id
    private String Id;//用户id
    private String username;//用户名称
    private String email;//用户邮箱
    private int num;//用户积分(文章数)
    private String role;//用户角色
    private String password;//用户密码
    private String statue;//用户状态

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Users{");
        sb.append("Id='").append(Id).append('\'');
        sb.append(", username='").append(username).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", num=").append(num);
        sb.append(", role='").append(role).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", statue='").append(statue).append('\'');
        sb.append('}');
        return sb.toString();
    }

    public String getStatue() {
        return statue;
    }

    public void setStatue(String statue) {
        this.statue = statue;
    }

    public Users() {
    }

    public Users(String id, String username, String email, int num, String role, String password, String statue) {
        Id = id;
        this.username = username;
        this.email = email;
        this.num = num;
        this.role = role;
        this.password = password;
        this.statue = statue;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
