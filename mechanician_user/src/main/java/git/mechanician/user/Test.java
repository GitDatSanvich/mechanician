package git.mechanician.user;

import com.alibaba.fastjson.JSONObject;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import git.mechanician.user.core.MailSender;
import git.mechanician.user.entity.MailContentTypeEnum;
import git.mechanician.user.utils.HttpClientUtils;
import org.springframework.stereotype.Component;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Test
 * @Author GitDatSanvich
 * @Date 2019/6/12 9:19
 **/
@Component
public class Test {
    public static void mail(String[] args) throws Exception {
        MailSender mailSender = new MailSender();
        mailSender.title("邮件测试").contentType(MailContentTypeEnum.HTML).content("这是一封测试邮件").targets(new ArrayList<String>() {{
            add("tc704321764@outlook.com");
        }}).send();

        mailSender = new MailSender();
        mailSender.title("这是一封激活邮件")            //邮件发送
                .contentType(MailContentTypeEnum.HTML)
                .targets(new ArrayList<String>() {{
                    add("tc704321764@outlook.com");
                }}).content("<h3 style=\"width: 100%; text-align: center\">您好,感谢您使用这套经验分享平台.</h3>\n" +
                "<h3 style=\"width: 100%; text-align: center\">您的账号需要点击下面的链接进行激活.</h3>\n" +
                "<h3 style=\"width: 100%; text-align: center\">激活后,后台管理员(可能就是我或者其他人)会对您的身份进行核实.</h3>\n" +
                "<h3 style=\"width: 100%; text-align: center\">核实完成后.会以邮件的方式通知您是否通过,通过后即可登录使用此系统.</h3>\n" +
                "<h3 style=\"width: 100%; text-align: center\">如长时间没有人回复或者通知您是否可以登录,您也可以邮件回复/询问我.</h3>\n" +
                "<div style=\"width: 100%; text-align: center\">\n" +
                "    <h3><a href=" +
                //TODO 服务器地址+task/userActive/{id}
                ">激活!</a></h3>\n" +
                "</div>").send();
        Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
        InetAddress ip = null;
        while (allNetInterfaces.hasMoreElements()) {
            NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
            System.out.println(netInterface.getName());
            Enumeration addresses = netInterface.getInetAddresses();
            while (addresses.hasMoreElements()) {
                ip = (InetAddress) addresses.nextElement();
                if (ip instanceof Inet4Address) {
                    System.out.println("本机的IP = " + ip.getHostAddress());
                }
            }
        }

    }

 /*   @SuppressWarnings("InfiniteLoopStatement")
    public static void main(String[] args) throws Exception {
        long i = 1L;
        Map map = new HashMap();
        while (true) {
            JSONObject jsonObject = null;
            try {
                jsonObject = HttpClientUtils.httpGet("http://report.ebelter.com/bhcp-report-aly/healthRecord/json/getReport?physicalID=");
            } catch (Exception e) {
                System.out.println("无网络");
            }
            if (jsonObject != null) {
                try {
                    ActiveXComponent sap = new ActiveXComponent("Sapi.SpVoice");
                    Dispatch sapo = sap.getObject();
                    sap.setProperty("Volume", new Variant(100));
                    sap.setProperty("Rate", new Variant(0));
                    Dispatch.call(sapo, "Speak", new Variant("有网了!!!"));
                    i++;
                } catch (Exception e) {
                    System.out.println("发声失败");
                    throw new RuntimeException("发声失败");
                }
            }
        }
    }*/

    void printNumber() {
        for (int i = 1; i < 10; i++) {
            System.out.println("i = " + i);
        }
    }


    public static void main(String[] args) {
        JSONObject jsonObject = HttpClientUtils.httpPost("http://localhost:28591/haoLa/userInfo", "{\n" +
                "    \"timestamp\": 1546272000000,\n" +
                "    \"partner_openid\": \"u00000001\",\n" +
                "    \"sign\": \"OviwG4VATet2Vl6sjrfJVriyQCU3yRFuwFanAw1yENMBRSroQVx6lHvXsW4S6ICXGMPQEwBinHooDE7QESGrXVEYBDMJ7BenXYrqtELAezR3zrS/A/GJuH2jr8EA6n8oCwY8N1eOYRMnYln7YrXKjWDcljVwKB1ygRoBEE5sfZk=\"\n" +
                "}");
        String string = jsonObject.toJSONString();
        System.err.println(string);
    }
}
