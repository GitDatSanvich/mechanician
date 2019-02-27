package git.mechanician.handOver.service;

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

import git.mechanician.handOver.dao.HandoverDao;
import git.mechanician.handOver.pojo.Handover;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class HandoverService {

    @Autowired
    private HandoverDao handoverDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Handover> findAll() {
        return handoverDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Handover> findSearch(Map whereMap, int page, int size) {
        Specification<Handover> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return handoverDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Handover> findSearch(Map whereMap) {
        Specification<Handover> specification = createSpecification(whereMap);
        return handoverDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Handover findById(String id) {
        return handoverDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param handover
     */
    public void add(Handover handover) {
        handover.setId(idWorker.nextId() + "");
        handoverDao.save(handover);
    }

    /**
     * 修改
     *
     * @param handover
     */
    public void update(Handover handover) {
        handoverDao.save(handover);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        handoverDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Handover> createSpecification(Map searchMap) {

        return new Specification<Handover>() {

            @Override
            public Predicate toPredicate(Root<Handover> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 交接内容
                if (searchMap.get("main") != null && !"".equals(searchMap.get("main"))) {
                    predicateList.add(cb.like(root.get("main").as(String.class), "%" + (String) searchMap.get("main") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
