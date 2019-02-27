package git.mechanician.handOver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import util.IdWorker;

@SpringBootApplication
public class HandOverApplication {

    public static void main(String[] args) {
        SpringApplication.run(HandOverApplication.class, args);
    }

    @Bean
    public IdWorker idWorkker() {
        return new IdWorker(1, 1);
    }

}
