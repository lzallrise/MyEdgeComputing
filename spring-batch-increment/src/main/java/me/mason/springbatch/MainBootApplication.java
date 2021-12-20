package me.mason.springbatch;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class MainBootApplication {

	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(MainBootApplication.class);
	    app.setBannerMode(Mode.OFF);
	    app.run(args);
	}
}
