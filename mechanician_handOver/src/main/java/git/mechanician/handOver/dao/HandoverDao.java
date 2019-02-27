package git.mechanician.handOver.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import git.mechanician.handOver.pojo.Handover;

import java.util.Date;
import java.util.List;

/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface HandoverDao extends JpaRepository<Handover, String>, JpaSpecificationExecutor<Handover> {
    public List<Handover> findAllByDateAfter(Date date);
}
