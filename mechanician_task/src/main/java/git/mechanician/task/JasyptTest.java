package git.mechanician.task;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.EnvironmentPBEConfig;

/**
 * @author zhaosongbin
 * @date 2019/7/9
 */

public class JasyptTest {


    public static void main(String[] args) {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setPassword("DCF81C7C91962944CE2A3BBD52D127C7");
        standardPBEStringEncryptor.setConfig(config);
        String s1 = "jdbc:mysql://gzkj-mysql:3306/gk_health?useUnicode=true&characterEncoding=UTF8";
        String s2 = "jdbc:mysql://gzkj-mysql:3306/gk_step?useUnicode=true&characterEncoding=UTF8";
        String s3 = "jdbc:mysql://gzkj-mysql:3306/gk_taskpush?useUnicode=true&characterEncoding=UTF8";
        String s4 = "jdbc:mysql://gzkj-mysql:3306/sms?useUnicode=true&characterEncoding=UTF8";
        String s5 = "jdbc:mysql://gzkj-mysql:3306/gk_file?useUnicode=true&characterEncoding=UTF8";
        String s6 = "jdbc:mysql://gzkj-mysql:3306/gk_health_data?useUnicode=true&characterEncoding=UTF8";
        String s7 = "jdbc:mysql://gzkj-mysql:3306/gk_health_records?useUnicode=true&characterEncoding=UTF8";
        String s8 = "jdbc:mysql://gzkj-mysql:3306/gk_weixin?useUnicode=true&characterEncoding=UTF8";
        String s9 = "mongodb://gk_admin:123456@gzkj-mongo:27017/gk_health";
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s1) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s2) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s3) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s4) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s5) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s6) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s7) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s8) + "})");
        System.out.println("ENC({" + standardPBEStringEncryptor.encrypt(s9) + "})");
    }

    public void testDe() throws Exception {
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        EnvironmentPBEConfig config = new EnvironmentPBEConfig();
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setPassword("ljk");
        standardPBEStringEncryptor.setConfig(config);
        String encryptedText = "aHsFtlQjatrOP2s8bfLGkUG55z53KLNi";
        String plainText = standardPBEStringEncryptor.decrypt(encryptedText);
        System.out.println(plainText);
    }
}
