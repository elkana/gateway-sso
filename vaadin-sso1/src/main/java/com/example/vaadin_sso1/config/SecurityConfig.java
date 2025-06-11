package com.example.vaadin_sso1.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.oidc.user.OidcUserAuthority;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

import com.vaadin.flow.spring.security.VaadinWebSecurity;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

    @Value("${vaadin.sso.login-page:/oauth2/authorization/keycloak}")
    String loginPage;

    @Value("${base.url}")
    String baseUrl;

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    boolean hasRoles(Authentication auth, String... roles) {
        return auth.getAuthorities().stream()
                .anyMatch(a -> Arrays.stream(roles).anyMatch(r -> a.getAuthority().equals("ROLE_" + r)));
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(r -> r
                .requestMatchers("/images/**", "/styles/**", "/icons/**", "/frontend/**", "/VAADIN/**")
                .permitAll());

        super.configure(http);
        // mandatory,
        // for testing localhost you may use
        System.out.println("Login page ke : " + loginPage);
        setOAuth2LoginPage(http, loginPage,
                baseUrl);
        // "{baseUrl}");
        // "{baseUrl}/session-ended"
        // http.csrf(csrf -> csrf.disable());
        http.headers(h -> h.frameOptions(f -> f.disable()));
        http.oauth2Login(l -> l.userInfoEndpoint(ui -> ui.userAuthoritiesMapper(userAuthoritiesMapperForKeycloak()))
                .successHandler((req, resp, auth) -> {
                    System.out.println("Authentication sukses : " + auth.getName());
                    if (hasRoles(auth, "SUPERUSER", "ADMIN"))
                        // resp.sendRedirect(req.getContextPath() + "/superuser");
                        resp.sendRedirect(baseUrl + "/superuser");
                    else if (hasRoles(auth, "CASHIER"))
                        // resp.sendRedirect(req.getContextPath() + "/cashier");
                        resp.sendRedirect(baseUrl + "/cashier");
                    else
                        throw new UsernameNotFoundException("Access denied, invalid role.");
                }));

        // MUST READ
        // https://docs.spring.io/spring-security/reference/reactive/oauth2/login/logout.html
        http.logout(l -> l
                .logoutSuccessHandler(oidcLogoutSuccessHandler()));
    }

    private LogoutSuccessHandler oidcLogoutSuccessHandler() {
        OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler = new OidcClientInitiatedLogoutSuccessHandler(
                this.clientRegistrationRepository);

        System.out.println("Base URL : " + baseUrl);
        // Sets the location that the End-User's User Agent will be redirected to
        // after the logout has been performed at the Provider
        oidcLogoutSuccessHandler.setPostLogoutRedirectUri("{baseUrl}");

        return oidcLogoutSuccessHandler;
    }

    @Bean
    public GrantedAuthoritiesMapper userAuthoritiesMapperForKeycloak() {
        return authorities -> {
            Set<GrantedAuthority> mappedAuthorities = new HashSet<>();
            var authority = authorities.iterator().next();
            if (authority instanceof OidcUserAuthority oidcUserAuthority) {
                var userInfo = oidcUserAuthority.getUserInfo();
                if (userInfo.hasClaim("realm_access")) {
                    var realmAccess = userInfo.getClaimAsMap("realm_access");
                    var roles = (Collection<String>) realmAccess.get("roles");
                    mappedAuthorities.addAll(roles.stream()
                            .map(r -> new SimpleGrantedAuthority("ROLE_" + r.toUpperCase()))
                            .toList());
                }
            }
            return mappedAuthorities;
        };
    }
}
