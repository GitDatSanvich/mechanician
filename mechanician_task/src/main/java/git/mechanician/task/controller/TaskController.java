package git.mechanician.task.controller;

import git.mechanician.task.cilent.HandOverClient;
import git.mechanician.task.cilent.ToolsClient;
import git.mechanician.task.cilent.UserClient;
import git.mechanician.task.entity.PageResult;
import git.mechanician.task.entity.Result;
import git.mechanician.task.entity.StatusCode;
import git.mechanician.task.pojo.Handover;
import git.mechanician.task.pojo.Task;
import git.mechanician.task.pojo.Tools;
import git.mechanician.task.pojo.Users;
import git.mechanician.task.service.TaskService;
import git.mechanician.task.utils.IdWorker;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * 控制器层
 *
 * @author chen_Tang
 */
@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ToolsClient toolsClient;
    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private HandOverClient handOverClient;
    @Autowired
    private UserClient userClient;
    @Autowired
    private IdWorker idWorker;

    @RequestMapping(value = "/handOver", method = RequestMethod.POST)
    public Result addHandOver(@RequestBody Map map) {
        String main = (String) map.get("main");
        System.err.println(main);

        Handover handover = new Handover();
        handover.setMain(main);
        return handOverClient.add(handover);
    }

    @RequestMapping(value = "/handOver/{id}", method = RequestMethod.GET)
    public Result deleteHandOver(@PathVariable("id") String id) {
        return handOverClient.delete(id);
    }

    @RequestMapping(value = "handOver", method = RequestMethod.GET)
    public Result findHandOver() {
        return handOverClient.findAll();
    }

    @RequestMapping(value = "/saveTools", method = RequestMethod.POST)
    public Result addTools(@RequestBody Tools tools) {
        String tool = tools.getTools() + ":" + tools.getId();
        rabbitTemplate.convertAndSend("test", tool);
        System.out.println("cunrule");
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(value = "/tools/{id}", method = RequestMethod.GET)
    public Result findToolsByTaksId(@PathVariable String id) {
        return toolsClient.findByTaskId(id);
    }


    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", taskService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", taskService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param searchMap 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap, @PathVariable int page, @PathVariable int size) {
        Page<Task> pageList = taskService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Task>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */

    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        searchMap.put("enable", "1");
        return new Result(true, StatusCode.OK, "查询成功", taskService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param task
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Task task) {
        Task resultTask = taskService.add(task);
        boolean a = true;
        while (a) {
            Object o = redisTemplate.boundListOps("taskId").leftPop();
            if (o == null) {
                a = false;
            }
        }
        redisTemplate.boundListOps("taskId").rightPush(resultTask.getId());
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param task
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Task task, @PathVariable String id) {
        task.setId(id);
        taskService.update(task);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Result delete(@PathVariable("id") String id) {
        taskService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * 用户登录
     *
     * @param user
     * @param session
     * @return
     */

    @RequestMapping(value = "/userLogin", method = RequestMethod.POST)
    public Result login(@RequestBody Users user, HttpSession session) {
        Users users = userClient.login(user);
        if (users == null) {
            return new Result(false, StatusCode.OK, "用户名或密码错误");
        }
        if (users.getStatue().equals("1")) {
            return new Result(false, StatusCode.OK, "用户待管理员审核");
        }
        if (users.getStatue().equals("0")) {
            return new Result(false, StatusCode.OK, "用户未激活");
        } else {
            String uuid = Long.toString(idWorker.nextId());
            System.err.println(uuid);
            session.setAttribute("uuid", uuid);
            session.setMaxInactiveInterval(9000);
            redisTemplate.boundHashOps("userLogin").put(uuid + "_username", users.getUsername());
            redisTemplate.boundHashOps("userLogin").put(uuid + "_password", users.getPassword());
            return new Result(true, StatusCode.OK, "登陆成功");
        }
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.GET)
    public Result checkLogin(HttpSession session) {
        String uuid = (String) session.getAttribute("uuid");
        System.err.println(uuid);
        if (uuid != null && !uuid.equals("")) {
            String username = (String) redisTemplate.boundHashOps("userLogin").get(uuid + "_username");
            String password = (String) redisTemplate.boundHashOps("userLogin").get(uuid + "_password");
            Users user = new Users();
            user.setUsername(username);
            user.setPassword(password);
            Users users = userClient.checkLogin(user);
            if (users == null || users.getUsername().equals("")) {
                return new Result(false, StatusCode.OK, "无此用户");
            } else {
                Users data = new Users();
                data.setUsername(users.getUsername());
                return new Result(true, StatusCode.OK, "已登录", data);
            }
        }
        return new Result(false, StatusCode.OK, "未登录");
    }

    @RequestMapping(value = "LogOff", method = RequestMethod.GET)
    public Result logOff(HttpSession session) {
        session.removeAttribute("uuid");
        return new Result(true, StatusCode.OK, "已登出");
    }

    @RequestMapping(value = "signIn", method = RequestMethod.POST)
    public Result signIn(@RequestBody Users users) {
        String message = userClient.signIn(users);
        if (message.equals("用户注册成功")) {
            return new Result(true, StatusCode.OK, message);
        } else {
            return new Result(false, StatusCode.OK, message);
        }
    }
}
