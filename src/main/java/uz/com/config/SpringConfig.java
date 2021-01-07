package uz.com.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ui.velocity.VelocityEngineFactoryBean;

@Configuration
public class SpringConfig {

    @Bean
    public VelocityEngineFactoryBean velocityEngine() {
        VelocityEngineFactoryBean bean = new VelocityEngineFactoryBean();
        bean.setConfigLocation(new ClassPathResource("/velocity.properties"));
        return bean;
    }

}
