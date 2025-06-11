package com.example.vaadin_sso1.views;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Route;

import jakarta.annotation.security.RolesAllowed;

@Route(value = "cashier", layout = MainLayout.class)
@RolesAllowed("ROLE_CASHIER")
public class CashierView extends Main{
    public CashierView() {
        add(new H1("Hello Cashier!"));
    }
}
