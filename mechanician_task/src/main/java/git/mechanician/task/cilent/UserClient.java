package git.mechanician.task.cilent;


import git.mechanician.task.pojo.Users;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("mechanician-user")
public interface UserClient {
    @RequestMapping(value = "users/login", method = RequestMethod.POST)
    public Users login(@RequestBody Users users);

    @RequestMapping(value = "users/signIn", method = RequestMethod.POST)
    public String signIn(@RequestBody Users users);

    @RequestMapping(value = "users/checkLogin", method = RequestMethod.POST)
    public Users checkLogin(@RequestBody Users users);

    @RequestMapping(value = "users/userActive", method = RequestMethod.POST)
    public String userActive(@RequestBody String id);
}
