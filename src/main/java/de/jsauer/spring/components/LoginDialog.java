package de.jsauer.spring.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.Shortcuts;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Component
public class LoginDialog extends LoginOverlay {

    @Autowired
    private AuthenticationManager authManager;

    @Autowired
    private HttpServletRequest req;

    public LoginDialog() {
        super();
        //Basic settings
        this.setTitle("Valhalla");
        this.setDescription("Keeping it all in valhalla.");
        //Add listener to close this on close
        Shortcuts.addShortcutListener(this, this::close, Key.ESCAPE);

        //Set what shall happen if the user presses login
        this.addLoginListener(loginEvent -> {
            /*
            Set an authenticated user in Spring Security and Spring MVC
            spring-security
            */
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(loginEvent.getUsername(), loginEvent.getPassword());
            try {
                // Set authentication
                Authentication auth = authManager.authenticate(authReq);
                SecurityContext sc = SecurityContextHolder.getContext();
                sc.setAuthentication(auth);

                /*
                Navigate to the requested page:
                This is to redirect a user back to the originally requested URL – after they log in as we are not using
                Spring's AuthenticationSuccessHandler.
                */
                HttpSession session = req.getSession(false);
                DefaultSavedRequest savedRequest = (DefaultSavedRequest) session.getAttribute("SPRING_SECURITY_SAVED_REQUEST");
                String requestedURI = savedRequest != null ? savedRequest.getRequestURI() : "/";

                this.getUI().ifPresent(ui -> ui.navigate(StringUtils.removeStart(requestedURI, "/")));
            } catch (BadCredentialsException e) {
                Notification.show("Invalid username or password. Please try again.");
                this.setEnabled(true);
            }
        });
    }
}
