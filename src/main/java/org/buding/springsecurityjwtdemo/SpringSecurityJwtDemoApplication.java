package org.buding.springsecurityjwtdemo;

import org.buding.springsecurityjwtdemo.model.Role;
import org.buding.springsecurityjwtdemo.model.User;
import org.buding.springsecurityjwtdemo.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication
public class SpringSecurityJwtDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityJwtDemoApplication.class, args);
    }

    @Autowired
    UserService userService;

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Override
    public void run(String... args) throws Exception {
        User admin = new User();
        admin.setUsername("admin");
        admin.setPassword("admin");
        admin.setEmail("admin@email.com");
        admin.setRoles(new ArrayList<>(Arrays.asList(Role.ROLE_ADMIN)));
        userService.signup(admin);

        User client = new User();
        client.setUsername("client");
        client.setPassword("client");
        client.setEmail("client@email.com");
        client.setRoles(new ArrayList<Role>(Arrays.asList(Role.ROLE_CLIENT)));

        userService.signup(client);
    }
}
