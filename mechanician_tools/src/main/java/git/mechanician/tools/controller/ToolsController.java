package git.mechanician.tools.controller;
import java.util.List;
import java.util.Map;

import git.mechanician.tools.entity.PageResult;
import git.mechanician.tools.entity.Result;
import git.mechanician.tools.entity.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import git.mechanician.tools.pojo.Tools;
import git.mechanician.tools.service.ToolsService;

/**
 * 控制器层
 * @author chen_Tang
 *
 */
@RestController
@CrossOrigin
@RequestMapping("/tools")
public class ToolsController {

	@Autowired
	private ToolsService toolsService;
	
	
	/**
	 * 查询全部数据
	 * @return
	 */
	@RequestMapping(method= RequestMethod.GET)
	public Result findAll(){
		return new Result(true, StatusCode.OK, "查询成功", toolsService.findAll());
	}
	
	/**
	 * 根据ID查询
	 * @param id ID
	 * @return
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.GET)
	public Result findByTaskId(@PathVariable String id) {
		Tools tool = toolsService.findByTaskId(id);
		if (tool != null) {
			return new Result(true, StatusCode.OK, "查询成功", tool);
		} else {
			Tools tools = new Tools();
			tools.setTools("这项任务没有保存工具呢~");
			return new Result(true, StatusCode.OK, "查询成功", tools);
		}
	}

	/**
	 * 分页+多条件查询
	 * @param searchMap 查询条件封装
	 * @param page 页码
	 * @param size 页大小
	 * @return 分页结果
	 */
	@RequestMapping(value="/search/{page}/{size}",method=RequestMethod.POST)
	public Result findSearch(@RequestBody Map searchMap , @PathVariable int page, @PathVariable int size){
		Page<Tools> pageList = toolsService.findSearch(searchMap, page, size);
		return  new Result(true,StatusCode.OK,"查询成功",  new PageResult<Tools>(pageList.getTotalElements(), pageList.getContent()) );
	}

	/**
     * 根据条件查询
     * @param searchMap
     * @return
     */
    @RequestMapping(value="/search",method = RequestMethod.POST)
    public Result findSearch( @RequestBody Map searchMap){
        return new Result(true,StatusCode.OK,"查询成功",toolsService.findSearch(searchMap));
    }
	
	/**
	 * 增加
	 * @param tools
	 */
	@RequestMapping(method=RequestMethod.POST)
	public Result add(@RequestBody Tools tools  ){
		toolsService.add(tools);
		return new Result(true,StatusCode.OK,"增加成功");
	}
	
	/**
	 * 修改
	 * @param tools
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.PUT)
	public Result update(@RequestBody Tools tools, @PathVariable String id ){
		tools.setId(id);
		toolsService.update(tools);		
		return new Result(true,StatusCode.OK,"修改成功");
	}
	
	/**
	 * 删除
	 * @param id
	 */
	@RequestMapping(value="/{id}",method= RequestMethod.DELETE)
	public Result delete(@PathVariable String id ){
		toolsService.deleteById(id);
		return new Result(true,StatusCode.OK,"删除成功");
	}

	@RequestMapping(value = "/delete/{id}", method = RequestMethod.GET)
	public Result deleteByTaskId(@PathVariable("id") String id) {
		toolsService.deleteByTask(id);
		return new Result(true, StatusCode.OK, "删除成功");
	}
}