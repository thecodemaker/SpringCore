package app;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ImportResource("classpath:/spring/database-config.xml")
@ComponentScan(basePackages = "app")
public class ApplicationConfig {

}
