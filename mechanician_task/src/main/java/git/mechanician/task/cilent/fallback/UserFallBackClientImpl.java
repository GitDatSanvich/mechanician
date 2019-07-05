package git.mechanician.task.cilent.fallback;

import git.mechanician.task.cilent.UserClient;
import git.mechanician.task.pojo.Users;

/**
 * @ClassName UserFallBackClientImpl
 * @Author GitDatSanvich
 * @Date 2019/7/5 9:33
 **/
public class UserFallBackClientImpl implements UserClient {
    @Override
    public Users login(Users users) {
        return null;
    }

    @Override
    public String signIn(Users users) {
        return null;
    }

    @Override
    public Users checkLogin(Users users) {
        return null;
    }

    @Override
    public String userActive(String id, String key) {
        return null;
    }
}
