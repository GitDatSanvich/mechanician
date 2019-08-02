package git.mechanician.user.dao;

import git.mechanician.user.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * @author TangChen
 * 用户查询
 */
public interface UsersDao extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
    /**
     * @param username 用户名
     * @param password 密码
     * @return Users
     */
    public Users findByUsernameAndPassword(String username, String password);

    /**
     * @param username 用户名
     * @return Users
     */
    public Users findByUsername(String username);

    /**
     * @param role 用户角色
     * @return List
     */
    public List<Users> findByRole(String role);
}
