package git.mechanician.tools.controller;

import git.mechanician.tools.pojo.Tools;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @ClassName ToolsMq
 * @Author GitDatSanvich
 * @Date 2019/2/26 12:21
 **/
@Component
@RabbitListener(queues = "sms")
public class ToolsMq {
    @Autowired
    private ToolsController toolsController;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitHandler
    public void ToolsSaver(Map map) {
        String taskId = (String) redisTemplate.boundListOps("taskId").leftPop();
        String toolsId = (String) map.get("ToolsId");
        String tools = (String) map.get("tools");
        Tools tool = new Tools();
        tool.setTools(tools);
        tool.setTools(toolsId);
        tool.setTools(taskId);
        toolsController.add(tool);
    }
}
