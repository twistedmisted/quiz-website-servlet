package com.epam.final_project.app.web.filters;

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
public class LocalizationFilterTest {

    LocalizationFilter filter = new LocalizationFilter();

    @Mock
    HttpSession session;

    @Mock
    FilterChain chain;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Test
    public void doFilterTest_langIsNotNull() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn("ua");
        when(request.getSession()).thenReturn(session);
        filter.doFilter(request, response, chain);
        verify(request, times(1)).getSession();
    }

    @Test
    public void doFilterTest_langIsNull() throws ServletException, IOException {
        when(request.getParameter("lang")).thenReturn(null);
        when(request.getSession()).thenReturn(session);
        filter.doFilter(request, response, chain);
        verifyZeroInteractions(request.getSession());
    }

}
