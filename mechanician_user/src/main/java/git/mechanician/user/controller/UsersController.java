package git.mechanician.user.controller;

import git.mechanician.user.entity.PageResult;
import git.mechanician.user.entity.Result;
import git.mechanician.user.entity.StatusCode;
import git.mechanician.user.pojo.Users;
import git.mechanician.user.service.UsersService;
import git.mechanician.user.utils.IdWorker;
import org.apache.http.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @ClassName UsersController
 * @Author GitDatSanvich
 * @Date 2019/5/29 9:43
 **/
@RestController
@CrossOrigin
@RequestMapping("/users")
public class UsersController {

    /**
     * 控制器层
     *
     * @author Administrator
     */


    @Autowired
    private UsersService usersService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", usersService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", usersService.findById(id));
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
        Page<Users> pageList = usersService.findSearch(searchMap, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Users>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param searchMap
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Map searchMap) {
        return new Result(true, StatusCode.OK, "查询成功", usersService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param users
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Users users) {
        usersService.add(users);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param users
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Users users, @PathVariable String id) {
        users.setId(id);
        usersService.update(users);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
    public Result delete(@PathVariable("id") String id) {
        usersService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     * @param users
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Users login(@RequestBody Users users) {
        return usersService.login(users.getUsername(), users.getPassword());
    }

    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    public String signIn(@RequestBody Users users) {
        return usersService.add(users);
    }

    @RequestMapping(value = "/checkLogin", method = RequestMethod.POST)
    public Users checkLogin(@RequestBody Users users) {
        Users users1 = usersService.checkLogin(users.getUsername(), users.getPassword());
        return users1;
    }

    @RequestMapping(value = "/userActive", method = RequestMethod.POST)
    public String userActive(@RequestBody String id) {
        return usersService.userActive(id).getMassage();
    }
}


