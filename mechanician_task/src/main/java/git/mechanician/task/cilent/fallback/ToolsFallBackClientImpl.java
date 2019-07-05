package git.mechanician.task.cilent.fallback;

import git.mechanician.task.cilent.ToolsClient;
import git.mechanician.task.entity.Result;
import git.mechanician.task.pojo.Tools;

/**
 * @ClassName ToolsFallBackClientImpl
 * @Author GitDatSanvich
 * @Date 2019/7/5 9:32
 **/
public class ToolsFallBackClientImpl implements ToolsClient {
    @Override
    public Result findByTaskId(String id) {
        return null;
    }

    @Override
    public Result deleteByTaskId(String id) {
        return null;
    }

    @Override
    public Result add(Tools tools) {
        return null;
    }
}
