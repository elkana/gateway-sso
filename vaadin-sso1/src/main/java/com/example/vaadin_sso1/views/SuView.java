package com.example.vaadin_sso1.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "superuser", layout = MainLayout.class)
@RolesAllowed("ROLE_SUPERUSER")  
public class SuView extends VerticalLayout {

    public SuView() {
        add(new H1("Hello SuperUser!"));
    }
}
