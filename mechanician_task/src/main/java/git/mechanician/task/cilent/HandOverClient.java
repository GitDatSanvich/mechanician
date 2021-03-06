package git.mechanician.task.cilent;

import git.mechanician.task.cilent.fallback.HandOverFallBackClientImpl;
import git.mechanician.task.entity.Result;
import git.mechanician.task.pojo.Handover;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @ClassName HandOverClient
 * @Author GitDatSanvich
 * @Date 2019/2/27 17:01
 **/
@FeignClient(value = "mechanician-handOver", fallback = HandOverFallBackClientImpl.class)
public interface HandOverClient {
    @RequestMapping(value = "/handover", method = RequestMethod.GET)
    public Result findAll();

    @RequestMapping(value = "/handover/delete/{id}", method = RequestMethod.GET)
    public Result delete(@PathVariable("id") String id);

    @RequestMapping(value = "/handover", method = RequestMethod.POST)
    public Result add(@RequestBody Handover handover);
}
