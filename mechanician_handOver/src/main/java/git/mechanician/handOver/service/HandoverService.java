package git.mechanician.handOver.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import git.mechanician.handOver.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


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
        long time = new Date().getTime();
        long enableTime = time - (1000 * 60 * 60 * 24 * 3);
        Date date = new Date();
        date.setTime(enableTime);
        return handoverDao.findAllByDateAfter(date);
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
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy年MM月dd日 HH:mm:ss");
        String format = simpleDateFormat.format(date);
        handover.setDate(date);
        handover.setDates(format);
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
                // 交接Id
                if (searchMap.get("Id") != null && !"".equals(searchMap.get("Id"))) {
                    predicateList.add(cb.like(root.get("Id").as(String.class), "%" + (String) searchMap.get("Id") + "%"));
                }
                // 交接内容
                if (searchMap.get("main") != null && !"".equals(searchMap.get("main"))) {
                    predicateList.add(cb.like(root.get("main").as(String.class), "%" + (String) searchMap.get("main") + "%"));
                }
                // 建立时间字符串格式
                if (searchMap.get("dates") != null && !"".equals(searchMap.get("dates"))) {
                    predicateList.add(cb.like(root.get("dates").as(String.class), "%" + (String) searchMap.get("dates") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
