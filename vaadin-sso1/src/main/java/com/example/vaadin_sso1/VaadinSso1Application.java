package com.example.vaadin_sso1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.component.page.AppShellConfigurator;

@SpringBootApplication
@Theme("my-theme")
public class VaadinSso1Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinSso1Application.class, args);
    }
}
