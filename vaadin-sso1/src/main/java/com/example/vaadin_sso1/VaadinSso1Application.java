package com.example.vaadin_sso1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vaadin.flow.component.page.AppShellConfigurator;
import com.vaadin.flow.theme.Theme;

@RestController
@SpringBootApplication
@Theme("my-theme")
public class VaadinSso1Application implements AppShellConfigurator {

    public static void main(String[] args) {
        SpringApplication.run(VaadinSso1Application.class, args);
    }

    // to display access token
    @GetMapping("/resource")
    public String getResource(@RegisteredOAuth2AuthorizedClient("keycloak") OAuth2AuthorizedClient authorizedClient) {
        return String.format("Resource info : %s", authorizedClient.getAccessToken().getTokenValue());
    }

}
