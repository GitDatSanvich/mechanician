package git.mechanician.task.cilent.fallback;

import git.mechanician.task.cilent.HandOverClient;
import git.mechanician.task.entity.Result;
import git.mechanician.task.pojo.Handover;

/**
 * @ClassName HandOverFallBackClientImpl
 * @Author GitDatSanvich
 * @Date 2019/7/5 9:31
 **/
public class HandOverFallBackClientImpl implements HandOverClient {
    @Override
    public Result findAll() {
        return null;
    }

    @Override
    public Result delete(String id) {
        return null;
    }

    @Override
    public Result add(Handover handover) {
        return null;
    }
}
