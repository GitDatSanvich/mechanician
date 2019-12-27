package git.mechanician.user.controller;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import git.mechanician.user.entity.Result;
import git.mechanician.user.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.*;

/**
 * 统一异常处理类
 */
@ControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result error(Exception e) {
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, "执行出错");
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder().setNameFormat("demo-pool-%d").build();

        ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 5,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
        for (int i = 0; i < 5; i++) {
            singleThreadPool.execute(() -> {
                for (int j = 0; j < 5; j++) {
                    String format;
                    synchronized (dateFormat) {
                        format = dateFormat.format(new Date());
                    }
                    System.out.println(
                            Thread.currentThread().getName() + "\t" + format);
                }
            });
        }

        singleThreadPool.shutdown();
    }

}
