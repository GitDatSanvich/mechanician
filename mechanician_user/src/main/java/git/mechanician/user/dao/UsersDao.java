package git.mechanician.user.dao;

import git.mechanician.user.pojo.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface UsersDao extends JpaRepository<Users, String>, JpaSpecificationExecutor<Users> {
    public Users findByUsernameAndPassword(String Username, String password);

    public Users findByUsername(String Username);

    public List<Users> findByRole(String role);
}
