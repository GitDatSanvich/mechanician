package git.mechanician.tools.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import git.mechanician.tools.pojo.Tools;

import javax.persistence.Id;
import java.util.List;

/**
 * 数据访问接口
 * @author Administrator
 *
 */
public interface ToolsDao extends JpaRepository<Tools,String>,JpaSpecificationExecutor<Tools>{
    public List<Tools> findByTask(String Id);

    public void deleteByTask(String Id);
}
