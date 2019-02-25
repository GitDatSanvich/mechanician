package git.mechanician.task.cilent;

import entity.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName TaskClient
 * @Author GitDatSanvich
 * @Date 2019/2/25 15:42
 **/
@FeignClient("mechanician-tools")
public interface TaskClient {
    @RequestMapping(value = "/tools/{id}", method = RequestMethod.GET)
    public Result findByTaskId(@PathVariable("id") String id);
}
