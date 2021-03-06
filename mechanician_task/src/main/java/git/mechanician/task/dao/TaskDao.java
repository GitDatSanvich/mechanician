package git.mechanician.task.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import git.mechanician.task.pojo.Task;

import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface TaskDao extends JpaRepository<Task,String>,JpaSpecificationExecutor<Task>{
    public List<Task> findByEnable(String statues);
}
