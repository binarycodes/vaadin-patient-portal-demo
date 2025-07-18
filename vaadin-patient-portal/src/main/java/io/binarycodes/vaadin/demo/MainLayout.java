package io.binarycodes.vaadin.demo;

import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Value;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.Layout;
import com.vaadin.flow.router.RouterLayout;
import com.vaadin.flow.spring.security.AuthenticationContext;
import com.vaadin.flow.theme.lumo.LumoUtility;

@Layout
@PermitAll
public class MainLayout extends Div implements RouterLayout {

    private final AuthenticationContext authenticationContext;
    private final String appName;

    public MainLayout(AuthenticationContext authenticationContext, @Value("${app.name}") String appName) {
        this.authenticationContext = authenticationContext;
        this.appName = appName;
        init();
        setSizeFull();
        addClassNames(LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN);
    }

    private void init() {
        var logoutButton = new Button("Logout", event -> authenticationContext.logout());
        var header = new H1(appName);

        var headerWrapper = new Div(header, logoutButton);
        headerWrapper.addClassNames(LumoUtility.Display.FLEX,
                LumoUtility.FlexDirection.ROW,
                LumoUtility.JustifyContent.BETWEEN,
                LumoUtility.Padding.Vertical.LARGE,
                LumoUtility.Padding.Horizontal.MEDIUM);
        add(headerWrapper);
    }
}
