package io.binarycodes.vaadin.demo;

import io.binarycodes.vaadin.demo.dummy.DummyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.UserDetailsManager;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    private final DummyUserService dummyUserService;

    @Autowired
    public SecurityConfig(DummyUserService dummyUserService) {
        this.dummyUserService = dummyUserService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        super.configure(http);

        http.logout(config -> config.logoutSuccessUrl("/"));

        setLoginView(http, LoginView.class);
    }

    @Bean
    public UserDetailsManager userDetailsService() {
        return dummyUserService.dummyUserDetailsManager();
    }
}
