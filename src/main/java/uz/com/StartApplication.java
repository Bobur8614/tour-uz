package uz.com;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import uz.com.property.FileStorageProperties;
import uz.com.property.ServerProperties;

import javax.sql.DataSource;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class,
        ServerProperties.class
})
public class StartApplication extends SpringBootServletInitializer {

    @Value("${spring.liquibase.change-log}")
    private String changeLogDir;

    @Autowired
    private DataSource dataSource;

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(StartApplication.class);
    }

    /*@Bean
    public SpringLiquibase liquibase() {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog(changeLogDir);
        liquibase.setDataSource(dataSource);
        return liquibase;
    }*/
}
