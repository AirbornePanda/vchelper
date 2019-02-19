package de.jsauer.valhalla;


import com.vaadin.flow.server.ServletHelper;
import com.vaadin.flow.shared.ApplicationConstants;
import de.jsauer.valhalla.views.ErrorView;
import de.jsauer.valhalla.views.HeroDetailView;
import de.jsauer.valhalla.views.LimitBurstDetailView;
import de.jsauer.valhalla.views.LoginView;
import de.jsauer.valhalla.views.MainView;
import de.jsauer.valhalla.views.SkillElementDetailView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Stream;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * All allowed url's that can be accessed without authentication.
     * <p>
     * The root {@link MainView} is also here and not in {@link SecurityConfig#ALLOWED_CLASSES_REGEX}.
     * It's a special case because of different regex.
     */
    private static final String[] ALLOWED_URLS_REGEX = {
            "/frontend/.*",
            "/VAADIN/.*",

    };

    /**
     * All allowed views that can be accessed without authentication.
     * Must use the same classes as {@link SecurityConfig#ALLOWED_CLASSES_REGEX}.
     */
    private static final Class[] ALLOWED_CLASSES = {
            MainView.class,
            ErrorView.class,
            LoginView.class,
            HeroDetailView.class,
            LimitBurstDetailView.class,
            SkillElementDetailView.class

    };

    /**
     * All allowed view as their regex entry.
     * Must use the same classes as {@link SecurityConfig#ALLOWED_CLASSES}.
     */
    private static final String[] ALLOWED_CLASSES_REGEX = {
            "/$", //Root view has special regex
            "/" + ErrorView.VIEW_NAME + ".*",
            "/" + LoginView.VIEW_NAME + ".*",
            "/" + HeroDetailView.VIEW_NAME + ".*",
            "/" + SkillElementDetailView.VIEW_NAME + ".*",
            "/" + LimitBurstDetailView.VIEW_NAME + ".*"


    };

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf()
                    .disable() //csrf handled by vaadin
                .exceptionHandling()
                    .accessDeniedPage("/" + ErrorView.VIEW_NAME) //set error page
                    .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/" + LoginView.VIEW_NAME))
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .authorizeRequests()
                    //allow Vaadin URLs and the views without authentication
                    .regexMatchers(ALLOWED_URLS_REGEX)
                        .permitAll()
                    .regexMatchers(ALLOWED_CLASSES_REGEX)
                        .permitAll()
                    .requestMatchers(this::isFrameworkInternalRequest)
                        .permitAll()
                    // deny any other URL until authenticated
                    .anyRequest()
                        .fullyAuthenticated();
        /*
             Note that anonymous authentication is enabled by default, therefore;
             SecurityContextHolder.getContext().getAuthentication().isAuthenticated() always will return true.
             Look at LoginView.beforeEnter method.
             more info: https://docs.spring.io/spring-security/site/docs/4.0.x/reference/html/anonymous.html
             */
    }

    /**
     * Checks if a class is a public navigation target.
     *
     * @param target the corresponding class of the navigation target
     * @return false if the class is part of the {@link SecurityConfig#ALLOWED_CLASSES}
     */
    public static Boolean isAuthenticationRequired(final Class target) {
        return !Arrays.asList(ALLOWED_CLASSES).contains(target);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        // user and pass: admin
        // TODO Remove hardcoded login
        auth
                .inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password("$2a$10$obstjyWMAVfsNoKisfyCjO/DNfO9OoMOKNt5a6GRlVS7XNUzYuUbO").roles("ADMIN");
    }

    /**
     * Expose the AuthenticationManager (to be used in login)
     *
     * @return the authentication manager
     * @throws Exception if authentication manager can't be returned
     */
    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Tests if the request is an internal framework request. The test consists of
     * checking if the request parameter is present and if its value is consistent
     * with any of the request types know.
     *
     * @param request {@link HttpServletRequest}
     * @return true if is an internal framework request. False otherwise.
     */
    private boolean isFrameworkInternalRequest(HttpServletRequest request) {
        final String parameterValue = request.getParameter(ApplicationConstants.REQUEST_TYPE_PARAMETER);
        return parameterValue != null
                && Stream.of(ServletHelper.RequestType.values()).anyMatch(r -> r.getIdentifier().equals(parameterValue));
    }

}
