package git.mechanician.task.controller;

import entity.PageResult;
import entity.Result;
import entity.StatusCode;
import git.mechanician.task.cilent.ToolsClient;
import git.mechanician.task.pojo.Task;
import git.mechanician.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/task")
public class TaskController {

    @Autowired
    private TaskService taskService;
    @Autowired
    private ToolsClient toolsClient;

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
        return new Result(true, StatusCode.OK, "查询成功", taskService.findSearch(searchMap));
    }

    /**
     * 增加
     *
     * @param task
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Task task) {
        taskService.add(task);
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
}
