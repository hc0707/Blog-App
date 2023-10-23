package io.mountblue.config;

import io.mountblue.repository.UserRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        // Get the HttpSession
        HttpSession session = request.getSession();

        // Store your object in the session
        session.setAttribute("user",userRepository.findByEmail(
                SecurityContextHolder.getContext().getAuthentication().getName()
        ));

        // Continue with the default behavior (e.g., redirecting to a specific URL)
        super.onAuthenticationSuccess(request, response, authentication);
    }
}

