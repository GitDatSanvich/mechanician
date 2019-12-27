package git.mechanician.user;

import git.mechanician.user.core.MailSender;
import git.mechanician.user.utils.IdWorker;
import git.mechanician.user.utils.SHA;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

/**
 * @author GitDatSanvich
 */
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient

public class UserApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext content = SpringApplication.run(UserApplication.class, args);
        content.getBean(Test.class).printNumber();
        for (int i = 0; i < 10; i++) {
            System.out.println("-------------------------------------");
        }
        content.close();
    }

    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

    @Bean
    public SHA sha() {
        return new SHA();
    }

    @Bean
    public MailSender mailSender() {
        return new MailSender();
    }
}
