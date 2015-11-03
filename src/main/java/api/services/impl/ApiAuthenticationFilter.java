package api.services.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class ApiAuthenticationFilter extends UsernamePasswordAuthenticationFilter
{
    /**
     *
     * У этого проекта была такая архитектура, что при любом запросе требуется проверка ключа-хеша.
     * Лучше, конечно же, сделать авторизацию по токену/сессии, но здесь есть зависимость от старого клиента
     *
     * Как я понимаю, с точки зрения Spring Security, данное условие будет означать, что любая точка входа/страница
     * является страницей авторизации (/login, по умолчанию).
     *
     */
    @Override
    protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (auth != null) ? auth : super.attemptAuthentication(request, response);
    }

    /**
     *
     * Покапавшись в Spring Security я понял, что механизм предполагаемой авторизации представляет собой следущее:
     * 1. Если запрос со страницы входа – попытаться авторизовать.
     * 2. Если авторизация успешна – сделать редирект на URL, в зависимости от настроек: предыдущий, текущий и т.д.
     *
     * Ситуация такова, что в данном случае не нужно делать редирект на другие страницы.
     * Нам необходимо убедиться, что клиент авторизован и продолжит работу в текущем сеансе.
     * Чтобы "дойти" до вызова контроллера, необходимо полностью и без ошибок пройти FilterChain,
     * поэтому продолжим его, хотя, концептуально, это не нужно, т.к. наша цель уже выполнена.
     *
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        chain.doFilter(request, response);
    }

    /**
     *
     * Как я понял, используя @RequestBody мы лишаем себя возможности получать POST поля в фильтрах (через request.getParameter)
     * Да и вообще, прочитать тело POST запроса можно только один раз..
     * Как следствие, пришлось перенести логин и пароль в header
     * Таким образом, делая это домашнее задание,
     * я так и не смог обеспечить совместимость этого бэкенда со старым клиентом, хотя очень долго пытался
     *
     * см. мою проблему подробнее:
     * http://stackoverflow.com/questions/33457599/spring-security-authorization-each-request-in-rest-app-without-login-form
     *
     */
    @Override
    protected String obtainUsername(HttpServletRequest request) {
        return request.getHeader(getUsernameParameter());
    }

    @Override
    protected String obtainPassword(HttpServletRequest request) {
        return request.getHeader(getPasswordParameter());
    }
}
