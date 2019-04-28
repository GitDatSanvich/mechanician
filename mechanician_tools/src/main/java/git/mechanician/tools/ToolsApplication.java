package git.mechanician.tools;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import utils.IdWorker;
@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
public class ToolsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolsApplication.class, args);
	}

	@Bean
	public IdWorker idWorkker(){
		return new IdWorker(1, 1);
	}
}
