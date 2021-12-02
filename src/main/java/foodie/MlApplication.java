package foodie;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

//@SpringBootApplication(scanBasePackages = {"foodie.domain.client"})
@SpringBootApplication
@EnableScheduling
@MapperScan("foodie.domain.client")
public class MlApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(MlApplication.class, args);
    }
}
