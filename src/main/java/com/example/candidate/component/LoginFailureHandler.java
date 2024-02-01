package com.example.candidate.component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


import lombok.extern.log4j.Log4j;
import org.apache.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@Log4j
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    private static final Logger auth_logs = Logger.getLogger("auth_logs");
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {
        setDefaultFailureUrl("/login?error=1");
        auth_logs.info("Попытка аутентификации с неверными авторизационными данными для пользователя '" + request.getParameter("username")+"'");
        //System.out.println(request.getParameter("username"));
        super.onAuthenticationFailure(request, response, exception);
    }

}
