package com.epam.final_project.app.filters;

import com.epam.final_project.dao.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AuthenticationFilterTest {

    AuthenticationFilter filter = new AuthenticationFilter();

    @Mock
    HttpSession session;

    @Mock
    FilterChain chain;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Test
    public void doFilterTest_accessAllow() throws ServletException, IOException {
        User user =
                User.createUser("example@gmail.com", "example", "example", "user");
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getSession()).thenReturn(session);
        filter.doFilter(request, response, chain);
        verify(chain, times(1)).doFilter(request, response);
    }

    @Test
    public void doFilterTest_accessDenied() throws ServletException, IOException {
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        filter.doFilter(request, response, chain);
        verify(response, times(1)).sendRedirect("/login.jsp");
    }

}
