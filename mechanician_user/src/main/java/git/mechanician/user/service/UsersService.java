package git.mechanician.user.service;

import git.mechanician.user.core.MailSender;
import git.mechanician.user.dao.UsersDao;
import git.mechanician.user.entity.MailContentTypeEnum;
import git.mechanician.user.entity.Result;
import git.mechanician.user.entity.StatusCode;
import git.mechanician.user.pojo.Users;
import git.mechanician.user.utils.IdWorker;
import git.mechanician.user.utils.MagicValues;
import git.mechanician.user.utils.SHA;
import git.mechanician.user.utils.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
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


    @Resource
    private UsersDao usersDao;

    @Resource
    private IdWorker idWorker;

    @Resource
    private MailSender mailSender;

    @Resource
    private RedisTemplate redisTemplate;

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
            String password = users.getPassword();
            String msg = SHA.egPasswd(users.getPassword());     //密码格式校验
            if (StringUtils.isNotBlank(msg)) {
                return msg;
            }
            password = SHA.getResult(users.getPassword());
            users.setPassword(password);

            users.setId(idWorker.nextId() + "");
            users.setNum(0);
            users.setRole("1");
            users.setStatue("0");
            usersDao.save(users);

            try {
                String key = RandomStringUtils.randomAlphanumeric(6);
                redisTemplate.boundHashOps("activeMap").put(users.getId(), key);

                mailSender.title("这是一封激活邮件")            //邮件发送
                        .contentType(MailContentTypeEnum.HTML)
                        .targets(new ArrayList<String>() {{
                            add(users.getEmail());
                        }}).content("<h3 style=\"width: 100%; text-align: center\">您好,感谢您使用这套经验分享平台.</h3>\n" +
                        "<h3 style=\"width: 100%; text-align: center\">您的账号需要点击下面的链接进行激活.</h3>\n" +
                        "<h3 style=\"width: 100%; text-align: center\">激活后,后台管理员(可能就是我或者其他人)会对您的身份进行核实.</h3>\n" +
                        "<h3 style=\"width: 100%; text-align: center\">核实完成后.会以邮件的方式通知您是否通过,通过后即可登录使用此系统.</h3>\n" +
                        "<h3 style=\"width: 100%; text-align: center\">如长时间没有人回复或者通知您是否可以登录,您也可以邮件回复/询问我.</h3>\n" +
                        "<div style=\"width: 100%; text-align: center\">\n" +
                        "    <h3><a href=" +
                        //TODO 上线时改成服务器IP
                        "\"http://47.107.145.120:9001/task/userActive/" + users.getId() + "/" + key + "\"" +
                        ">激活!</a></h3>\n" +
                        "</div>").send();
            } catch (Exception e) {
                return "注册邮件未发送成功";
            }

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
                if (searchMap.get(MagicValues.ID) != null && !"".equals(searchMap.get(MagicValues.ID))) {
                    predicateList.add(cb.like(root.get(MagicValues.ID).as(String.class), "%" + (String) searchMap.get(MagicValues.ID) + "%"));
                }
                // 用户名称
                if (searchMap.get(MagicValues.USERNAME) != null && !"".equals(searchMap.get(MagicValues.USERNAME))) {
                    predicateList.add(cb.like(root.get(MagicValues.USERNAME).as(String.class), "%" + (String) searchMap.get(MagicValues.USERNAME) + "%"));
                }
                // 用户邮箱
                if (searchMap.get(MagicValues.EMAIL) != null && !"".equals(searchMap.get(MagicValues.EMAIL))) {
                    predicateList.add(cb.like(root.get(MagicValues.EMAIL).as(String.class), "%" + (String) searchMap.get(MagicValues.EMAIL) + "%"));
                }
                //用户积分(文章数)
                if (searchMap.get(MagicValues.NUM) != null && !"".equals(searchMap.get(MagicValues.NUM))) {
                    predicateList.add(cb.like(root.get(MagicValues.NUM).as(String.class), "%" + (String) searchMap.get(MagicValues.NUM) + "%"));
                }
                //用户角色
                if (searchMap.get(MagicValues.ROLE) != null && !"".equals(searchMap.get(MagicValues.ROLE))) {
                    predicateList.add(cb.like(root.get(MagicValues.ROLE).as(String.class), "%" + (String) searchMap.get(MagicValues.ROLE) + "%"));
                }
                //用户密码
                if (searchMap.get(MagicValues.PASSWORD) != null && !"".equals(searchMap.get(MagicValues.PASSWORD))) {
                    predicateList.add(cb.like(root.get(MagicValues.PASSWORD).as(String.class), "%" + (String) searchMap.get(MagicValues.PASSWORD) + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };

    }

    public List<Users> findAll() {
        return usersDao.findAll();
    }

    public Users login(String username, String password) {
        String result = SHA.getResult(password);
        return usersDao.findByUsernameAndPassword(username, result);
    }

    public Users checkLogin(String username, String password) {
        return usersDao.findByUsernameAndPassword(username, password);
    }

    public Result userActive(String id, String key) {
        Users users = usersDao.findById(id).get();
        if (users.getStatue().equals("1")) {
            return new Result(false, StatusCode.OK, "用户已激活请勿重复提交");
        }
        if (users.getStatue().equals("2")) {
            return new Result(false, StatusCode.OK, "用户已通过审核即可登录,请勿重复提交");
        }
        if (users.getStatue().equals("0")) {
            //激活码校验
            String trueKey = (String) redisTemplate.boundHashOps("activeMap").get(id);
            if (trueKey == null || !trueKey.equals(key)) {
                return new Result(false, StatusCode.OK, "你是坏人！");
            }
            redisTemplate.boundHashOps("activeMap").delete(id);

            users.setStatue("1");
            usersDao.save(users);
            List<String> adminEmailList = new ArrayList<>();
            List<Users> admins = usersDao.findByRole("0");
            for (Users admin : admins) {
                adminEmailList.add(admin.getEmail());
            }
            try {
                mailSender.title("新用户审核")
                        .contentType(MailContentTypeEnum.HTML)
                        .content("<div>新注册用户" + users.getUsername() + ":</div><br>\n" +
                                "<div>邮箱:" + users.getEmail() + "</div><br>\n" +
                                "<div>等待审核</div>")
                        .targets(adminEmailList)
                        .send();
            } catch (Exception e) {
                return new Result(false, StatusCode.OK, "管理员邮件未发送");
            }
            return new Result(true, StatusCode.OK, "用户激活成功 耐心等待审核");
        }
        return new Result(false, StatusCode.OK, "未知错误");
    }
}