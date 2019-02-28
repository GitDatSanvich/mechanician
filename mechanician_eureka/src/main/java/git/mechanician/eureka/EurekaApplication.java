package git.mechanician.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.WebApplicationInitializer;

/**
 * @ClassName EurekaApplication
 * @Author GitDatSanvich
 * @Date 2019/2/25 12:35
 **/
@SpringBootApplication
@EnableEurekaServer
public class EurekaApplication extends SpringBootServletInitializer implements WebApplicationInitializer {

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {

        return application.sources(EurekaApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class);
    }
}