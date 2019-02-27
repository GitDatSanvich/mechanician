package git.mechanician.tools.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import git.mechanician.tools.pojo.Handover;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface HandoverDao extends JpaRepository<Handover, String>, JpaSpecificationExecutor<Handover> {

}
