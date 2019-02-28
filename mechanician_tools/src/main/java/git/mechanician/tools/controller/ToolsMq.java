package git.mechanician.tools.controller;

import git.mechanician.tools.pojo.Tools;
import git.mechanician.tools.service.ToolsService;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Random;

/**
 * @ClassName ToolsMq
 * @Author GitDatSanvich
 * @Date 2019/2/26 12:21
 **/
@Component
@RabbitListener(queues = "test")
public class ToolsMq {
    @Autowired
    private ToolsService toolsService;
    @Autowired
    private RedisTemplate redisTemplate;

    @RabbitHandler
    public void ToolsSaver(String message) {
        String tools = message.substring(0, message.indexOf(":"));
        String toolId = message.substring(message.indexOf(":") + 1);
        String taskId = null;
        try {
            taskId = (String) redisTemplate.boundListOps("taskId").leftPop();
        } catch (Exception e) {
            e.printStackTrace();
            boolean a = true;
            while (a) {
                Object o = redisTemplate.boundListOps("taskId").leftPop();
                if (o == null) {
                    a = false;
                }
            }
            e.printStackTrace();
        }
        if (taskId == null || taskId.equals("")) {
            Random random = new Random();
            int nextInt = random.nextInt(99999);
            taskId = "wrongId" + nextInt;
        }
        Tools tool = new Tools();
        tool.setId(toolId);
        tool.setTools(tools);
        tool.setTask(taskId);
        toolsService.add(tool);
    }
}
