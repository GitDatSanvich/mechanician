package git.mechanician.user.service;

import git.mechanician.user.dao.UsersDao;
import git.mechanician.user.pojo.Users;
import git.mechanician.user.utils.IdWorker;
import git.mechanician.user.utils.SHA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @ClassName UsersService
 * @Author GitDatSanvich
 * @Date 2019/5/29 9:42
 **/
@Service
public class UsersService {


    @Autowired
    private UsersDao usersDao;

    @Autowired
    private IdWorker idWorker;
    @Autowired
    private SHA sha;

    /**
     * 条件查询+分页
     *
     * @param whereMap
     * @param page
     * @param size
     * @return
     */
    public Page<Users> findSearch(Map whereMap, int page, int size) {
        Specification<Users> specification = createSpecification(whereMap);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return usersDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param whereMap
     * @return
     */
    public List<Users> findSearch(Map whereMap) {
        Specification<Users> specification = createSpecification(whereMap);
        return usersDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Users findById(String id) {
        return usersDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param users
     * @return
     */
    public String add(Users users) {
        Users u = usersDao.findByUsername(users.getUsername());
        if (u == null) {
            String password = sha.getResult(users.getPassword());
            users.setPassword(password);
            users.setId(idWorker.nextId() + "");
            users.setNum(0);
            users.setRole("1");
            users.setStatue("0");
            usersDao.save(users);
            return "用户注册成功";
        } else {
            return "用户名已被占用";
        }
    }

    /**
     * 修改
     *
     * @param users
     */
    public void update(Users users) {
        usersDao.save(users);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        usersDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param searchMap
     * @return
     */
    private Specification<Users> createSpecification(Map searchMap) {

        return new Specification<Users>() {

            @Override
            public Predicate toPredicate(Root<Users> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 用户id
                if (searchMap.get("Id") != null && !"".equals(searchMap.get("Id"))) {
                    predicateList.add(cb.like(root.get("Id").as(String.class), "%" + (String) searchMap.get("Id") + "%"));
                }
                // 用户名称
                if (searchMap.get("username") != null && !"".equals(searchMap.get("username"))) {
                    predicateList.add(cb.like(root.get("username").as(String.class), "%" + (String) searchMap.get("username") + "%"));
                }
                // 用户邮箱
                if (searchMap.get("email") != null && !"".equals(searchMap.get("email"))) {
                    predicateList.add(cb.like(root.get("email").as(String.class), "%" + (String) searchMap.get("email") + "%"));
                }
                //用户积分(文章数)
                if (searchMap.get("num") != null && !"".equals(searchMap.get("num"))) {
                    predicateList.add(cb.like(root.get("num").as(String.class), "%" + (String) searchMap.get("num") + "%"));
                }
                //用户角色
                if (searchMap.get("role") != null && !"".equals(searchMap.get("role"))) {
                    predicateList.add(cb.like(root.get("role").as(String.class), "%" + (String) searchMap.get("role") + "%"));
                }
                //用户密码
                if (searchMap.get("password") != null && !"".equals(searchMap.get("password"))) {
                    predicateList.add(cb.like(root.get("password").as(String.class), "%" + (String) searchMap.get("password") + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
                /*private String Id;//用户id
                private String username;//用户名称
                private String email;//用户邮箱
                private int num;//用户积分(文章数)
                private String role;//用户角色
                private String password;//用户密码*/
            }
        };

    }

    public List<Users> findAll() {
        return usersDao.findAll();
    }

    public Users login(String username, String password) {
        String result = sha.getResult(password);
        return usersDao.findByUsernameAndPassword(username, result);
    }

    public Users checkLogin(String username, String password) {
        return usersDao.findByUsernameAndPassword(username, password);
    }
}