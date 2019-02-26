package git.mechanician.tools.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import git.mechanician.tools.dao.ToolsDao;
import git.mechanician.tools.pojo.Tools;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class ToolsService {

	@Autowired
	private ToolsDao toolsDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Tools> findAll() {
		return toolsDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Tools> findSearch(Map whereMap, int page, int size) {
		Specification<Tools> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return toolsDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Tools> findSearch(Map whereMap) {
		Specification<Tools> specification = createSpecification(whereMap);
		return toolsDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Tools findById(String id) {
		return toolsDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param tools
	 */
	public void add(Tools tools) {
		if (tools.getId() == null || tools.getId().equals("") || tools.getId().equals("null")) {
			tools.setId(idWorker.nextId() + "");
		}
		toolsDao.save(tools);
	}

	/**
	 * 修改
	 * @param tools
	 */
	public void update(Tools tools) {
		toolsDao.save(tools);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		toolsDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Tools> createSpecification(Map searchMap) {

		return new Specification<Tools>() {

			@Override
			public Predicate toPredicate(Root<Tools> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
				// 工具文本id
				if (searchMap.get("id") != null && !"".equals(searchMap.get("id"))) {
					predicateList.add(cb.like(root.get("id").as(String.class), "%" + (String) searchMap.get("id") + "%"));
				}
				// 任务ID
				if (searchMap.get("taskId") != null && !"".equals(searchMap.get("taskId"))) {
					predicateList.add(cb.like(root.get("taskId").as(String.class), "%" + (String) searchMap.get("taskId") + "%"));
				}
                // 工具文本
                if (searchMap.get("tools")!=null && !"".equals(searchMap.get("tools"))) {
                	predicateList.add(cb.like(root.get("tools").as(String.class), "%"+(String)searchMap.get("tools")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

	public Tools findByTaskId(String id) {
		return toolsDao.findByTask(id);
	}

	public void deleteByTask(String id) {
		toolsDao.deleteByTask(id);
	}
}
