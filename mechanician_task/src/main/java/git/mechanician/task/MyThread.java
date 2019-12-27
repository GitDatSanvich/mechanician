package git.mechanician.task;

import com.alibaba.fastjson.JSONObject;
import git.mechanician.task.utils.ConversionUtil;
import git.mechanician.task.utils.HttpClientUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @ClassName MyThread
 * @Author GitDatSanvich
 * @Date 2019/8/28 18:26
 **/
public class MyThread extends Thread {
    private SimpleDateFormat sdf;
    private String dateString;
    private final Object object = new Object();

    public MyThread(SimpleDateFormat sdf, String dateString) {
        this.sdf = sdf;
        this.dateString = dateString;
    }

    @Override
    public void run() {
        try {
            Date date = sdf.parse(dateString);
            String dateStr;
            synchronized (object) {
                dateStr = sdf.format(date);
            }
            if (!dateStr.equals(dateString)) {
                System.out.println("ThreadName=" + this.getName() + "报错了，日期字符串：" + dateString + ",转换成的日期字符串：" + dateStr);
            } else {
                System.out.println("ThreadName=" + this.getName() + "成功，日期字符串：" + dateString);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 10000; i++) {
            int userId = r.nextInt(10000);
            Map<String, String> param = new HashMap<>();
            param.put("path", "/stepCount/insertStepMsg");
            param.put("value", "60000");
            param.put("userId", userId + "");
            JSONObject jsonObject = HttpClientUtils.httpPost("http://localhost:28590/integral/interceptReceive", ConversionUtil.objToJson(param));
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
