package com.example.vaadin_sso1.views;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

import com.example.vaadin_sso1.services.ServiceSecurity;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.menubar.MenuBar;
import com.vaadin.flow.component.menubar.MenuBarVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexComponent.Alignment;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class MainLayout extends AppLayout {

    private final ServiceSecurity svcSecurity;

    public MainLayout(ServiceSecurity svcSecurity) {
        this.svcSecurity = svcSecurity;
        createHeader();
    }

    private void createHeader() {
        var btnSign = new HorizontalLayout();

        var auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth instanceof OAuth2AuthenticationToken token
                && token.getPrincipal() instanceof DefaultOidcUser oidcUser) {
            var avatar = new Avatar(auth.getName());

            var menuUser = new MenuBar();
            // https://vaadin.com/docs/latest/components/menu-bar
            menuUser.addThemeVariants(MenuBarVariant.LUMO_TERTIARY);

            var div = new HorizontalLayout(Alignment.CENTER, avatar, new Paragraph(oidcUser.getFullName()),
                    new Icon("lumo", "dropdown"));
            var userName = menuUser.addItem(div);
            // userName.add(div);
            userName.getSubMenu().addItem("Sign out", e -> svcSecurity.logout());

            btnSign.add(menuUser);
        } else {
            // var loginLink = new Anchor("/oauth2/authorization/keycloak", "Login with
            // KeyCloak");
            var loginLink = new Anchor("/oauth2/authorization/keycloak", "Login with KeyCloak");
            // loginLink.setRouterIgnore(true);
            btnSign.add(loginLink, new Button("Sign in",
                    // e ->
                    // UI.getCurrent().getPage().setLocation("http://192.168.56.101:8080/realms/java-techie/protocol/openid-connect/auth?response_type=code&client_id=kasir-vaadin&scope=openid%20profile%20email%20roles&state=4HtYHJqW3TOgl7rWCyMZ2AbMaRyt2yF6J998t1HejtE%3D&redirect_uri=http://localhost:8083/kasir/login/oauth2/code/keycloak&nonce=4Dw3O87TBwpVIa-K6KGNYzbUXNrBYdoZI5oMS9H2lZc")));
                    // e -> UI.getCurrent().getPage().setLocation("/kasir/user")));
                    // btnSign.add(new Button("Sign in",
                    e -> UI.getCurrent().navigate(SuView.class)));
            // e -> UI.getCurrent().navigate(UserView.class)));
            // // e -> UI.getCurrent().getPage().setLocation("/user")));
            // e -> UI.getCurrent().getPage().setLocation("/auth/login")));
        }

        // Create the header
        H1 title = new H1("My Application");
        var header = new HorizontalLayout(
                title,
                btnSign);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER);
        header.expand(title);
        header.setWidthFull();

        addToNavbar(header);
    }

}
