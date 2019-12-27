package git.mechanician.user.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Elaking
 */
public class CustomThreadPoolExecutor {

    private static Logger logger = LoggerFactory.getLogger(CustomThreadPoolExecutor.class);

    private ThreadPoolExecutor pool = null;

    private static List<Future<Object>> futures = new ArrayList<>();

    private void init() {
        this.pool = new ThreadPoolExecutor(
                5,
                55,
                20,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5000),
                new CustomThreadFactory(),
                new CustomRejectedExecutionHandler());
    }


    private void destroy() {
        if (this.pool != null) {
            this.pool.shutdownNow();
        }
    }


    private ExecutorService getCustomThreadPoolExecutor() {
        return this.pool;
    }

    private static class CustomThreadFactory implements ThreadFactory {

        private AtomicLong count = new AtomicLong(0);

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            String threadName = CustomThreadPoolExecutor.class.getSimpleName() + count.addAndGet(1);
            logger.info(threadName);
            t.setName(threadName);
            return t;
        }
    }

    private static class CustomRejectedExecutionHandler implements RejectedExecutionHandler {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
            if (!executor.isShutdown()) {
                logger.info("阻塞拒绝直接执行");
                /* 触发业务监控方法 */
                logger.info(Thread.currentThread().getName());
                List<Future> removeList = new ArrayList<>();
                for (Future<Object> future : futures) {
                    //清除已被取消的future和已经结束的
                    if (future.isDone() || future.isCancelled()) {
                        removeList.add(future);
                        continue;
                    }
                    try {
                        Object o = future.get(10, TimeUnit.MILLISECONDS);
                        logger.error(o.toString());
                    } catch (CancellationException e) {
                        e.printStackTrace();
                    } catch (InterruptedException ex) {
                        System.out.println("子线程被中断 ");
                    } catch (ExecutionException e) {
                        System.out.println("子线程执行出错");
                    } catch (TimeoutException e) {
                        System.out.println("处理超时");
                        future.cancel(true);
                    } catch (Exception e) {
                        logger.error("子线程被执行失败");
                        e.printStackTrace();
                    }
                }
                r.run();
                futures.removeAll(removeList);
            }
        }
    }

    /**
     * 测试构造的线程池
     *
     * @param args null
     */


    public static void main(String[] args) {

        Map<Long, Result> resultMap = new HashMap<>(16);

        CustomThreadPoolExecutor exec = new CustomThreadPoolExecutor();
        // 1.初始化
        exec.init();
        ExecutorService pool = exec.getCustomThreadPoolExecutor();
        List<Callable<Object>> tasksList = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            logger.info("生成第" + (i + 1) + "个任务!");
            int a = i;
            Callable<Object> task = () -> {
                Result result = new Result();
                int timeout = new Random().nextInt(20);
                result.setSleepTime(timeout + "");
                TimeUnit.MILLISECONDS.sleep(timeout);
                resultMap.put((long) a, result);
                return a + ":" + timeout;
            };
            tasksList.add(task);
        }
        for (Callable<Object> objectCallable : tasksList) {
            Future<Object> submit = pool.submit(objectCallable);
            CustomThreadPoolExecutor.futures.add(submit);
        }
        logger.info("完成");
    }
}