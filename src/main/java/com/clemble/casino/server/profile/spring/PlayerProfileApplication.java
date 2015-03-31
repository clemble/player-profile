package com.clemble.casino.server.profile.spring;

import com.clemble.casino.server.spring.WebBootSpringConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by mavarazy on 3/31/15.
 */
@Configuration
@Import({WebBootSpringConfiguration.class, PlayerProfileSpringConfiguration.class})
public class PlayerProfileApplication {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(PlayerProfileApplication.class, args);
    }

}
