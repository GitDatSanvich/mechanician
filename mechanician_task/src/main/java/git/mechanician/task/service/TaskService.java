package git.mechanician.task.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Selection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import util.IdWorker;

import git.mechanician.task.dao.TaskDao;
import git.mechanician.task.pojo.Task;

/**
 * 服务层
 * 
 * @author Administrator
 *
 */
@Service
public class TaskService {

	@Autowired
	private TaskDao taskDao;
	
	@Autowired
	private IdWorker idWorker;

	/**
	 * 查询全部列表
	 * @return
	 */
	public List<Task> findAll() {
		return taskDao.findAll();
	}

	
	/**
	 * 条件查询+分页
	 * @param whereMap
	 * @param page
	 * @param size
	 * @return
	 */
	public Page<Task> findSearch(Map whereMap, int page, int size) {
		Specification<Task> specification = createSpecification(whereMap);
		PageRequest pageRequest =  PageRequest.of(page-1, size);
		return taskDao.findAll(specification, pageRequest);
	}

	
	/**
	 * 条件查询
	 * @param whereMap
	 * @return
	 */
	public List<Task> findSearch(Map whereMap) {
		Specification<Task> specification = createSpecification(whereMap);
		return taskDao.findAll(specification);
	}

	/**
	 * 根据ID查询实体
	 * @param id
	 * @return
	 */
	public Task findById(String id) {
		return taskDao.findById(id).get();
	}

	/**
	 * 增加
	 * @param task
	 */
	public void add(Task task) {
		task.setId( idWorker.nextId()+"" );
		taskDao.save(task);
	}

	/**
	 * 修改
	 * @param task
	 */
	public void update(Task task) {
		taskDao.save(task);
	}

	/**
	 * 删除
	 * @param id
	 */
	public void deleteById(String id) {
		taskDao.deleteById(id);
	}

	/**
	 * 动态条件构建
	 * @param searchMap
	 * @return
	 */
	private Specification<Task> createSpecification(Map searchMap) {

		return new Specification<Task>() {

			@Override
			public Predicate toPredicate(Root<Task> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicateList = new ArrayList<Predicate>();
                // 文章主键
                if (searchMap.get("Id")!=null && !"".equals(searchMap.get("Id"))) {
                	predicateList.add(cb.like(root.get("Id").as(String.class), "%"+(String)searchMap.get("Id")+"%"));
                }
                // 
                if (searchMap.get("chapter1")!=null && !"".equals(searchMap.get("chapter1"))) {
                	predicateList.add(cb.like(root.get("chapter1").as(String.class), "%"+(String)searchMap.get("chapter1")+"%"));
                }
                // 
                if (searchMap.get("chapter2")!=null && !"".equals(searchMap.get("chapter2"))) {
                	predicateList.add(cb.like(root.get("chapter2").as(String.class), "%"+(String)searchMap.get("chapter2")+"%"));
                }
                // 
                if (searchMap.get("chapter3")!=null && !"".equals(searchMap.get("chapter3"))) {
                	predicateList.add(cb.like(root.get("chapter3").as(String.class), "%"+(String)searchMap.get("chapter3")+"%"));
                }
                // 标题
                if (searchMap.get("title")!=null && !"".equals(searchMap.get("title"))) {
                	predicateList.add(cb.like(root.get("title").as(String.class), "%"+(String)searchMap.get("title")+"%"));
                }
                // 工作概要
                if (searchMap.get("main")!=null && !"".equals(searchMap.get("main"))) {
                	predicateList.add(cb.like(root.get("main").as(String.class), "%"+(String)searchMap.get("main")+"%"));
                }
                // 注意事项
                if (searchMap.get("notice")!=null && !"".equals(searchMap.get("notice"))) {
                	predicateList.add(cb.like(root.get("notice").as(String.class), "%"+(String)searchMap.get("notice")+"%"));
                }
                // 是否可用
                if (searchMap.get("enable")!=null && !"".equals(searchMap.get("enable"))) {
                	predicateList.add(cb.like(root.get("enable").as(String.class), "%"+(String)searchMap.get("enable")+"%"));
                }
				
				return cb.and( predicateList.toArray(new Predicate[predicateList.size()]));

			}
		};

	}

}
