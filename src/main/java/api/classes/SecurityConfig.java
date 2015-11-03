package api.classes;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.NullRequestCache;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import api.services.ClientManager;
import api.services.impl.*;
import api.dao.ClientDAO;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter
{
    @Autowired
    private ApplicationContext ctx;

    /**
     * Пояснения:
     *
     * 1. В этом проекте нет нужны запоминать клиентов, следовательно, сессии не нужны.
     * 2. Нет страниц регистрации/входа, логаута, нет привилегий и групп,
     *      поэтому, по большому счету, основные возможности Spring Security не используются
     * 3. Не нужно csrf, опять же, это не сайт с формами, дополнительный пайлоад не нужен.
     * 4. Любой запрос требует авторизации, в этом проекте не предполагается поддержка анонимных запросов.
     * 5. Запросы не кешируются, редиректы не производятся.
     *
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().anyRequest().hasAuthority("USER")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
                .and().requestCache().requestCache(new NullRequestCache())
                .and().csrf().disable();
        http.addFilterBefore(ctx.getBean("authenticationFilter", UsernamePasswordAuthenticationFilter.class),
                UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        ApiAuthenticationManager authenticationManager = new ApiAuthenticationManager();
        authenticationManager.setClientDao((ClientDAO) ctx.getBean("clientDAO"));
        authenticationManager.setClientManager((ClientManager) ctx.getBean("apiClientManager"));
        authenticationManager.setPasswordEncoder((PasswordEncoder) ctx.getBean("dummyPasswordEncoder"));
        return authenticationManager;
    }

    @Bean
    public UsernamePasswordAuthenticationFilter authenticationFilter() {
        UsernamePasswordAuthenticationFilter authenticationFilter = new ApiAuthenticationFilter();
        authenticationFilter.setAuthenticationManager(ctx.getBean("authenticationManager", AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler((HttpServletRequest req, HttpServletResponse res, Authentication auth) -> {});
        authenticationFilter.setUsernameParameter("clientKey");
        authenticationFilter.setPasswordParameter("accessKey");
        return authenticationFilter;
    }
}
