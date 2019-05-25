package git.mechanician.task.cilent;

import git.mechanician.task.entity.Result;
import git.mechanician.task.pojo.Tools;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName ToolsClient
 * @Author GitDatSanvich
 * @Date 2019/2/25 15:42
 **/
@FeignClient("mechanician-tools")
public interface ToolsClient {
    @RequestMapping(value = "/tools/{id}", method = RequestMethod.GET)
    public Result findByTaskId(@PathVariable("id") String id);

    @RequestMapping(value = "tools/delete/{id}", method = RequestMethod.GET)
    public Result deleteByTaskId(@PathVariable("id") String id);


    @RequestMapping(value = "tools", method = RequestMethod.POST)
    public Result add(@RequestBody Tools tools);
}
