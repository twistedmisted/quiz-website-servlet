package com.epam.final_project.app.commands;

import com.epam.final_project.app.commands.admin.AdminCommand;
import com.epam.final_project.exception.NotFoundCommandException;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CommandsFactoryTest {

    HttpServletRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        request = mock(HttpServletRequest.class);
    }

    @Test
    public void getCommand_correct() {
        when(request.getRequestURI()).thenReturn("example.com/admin");
        when(request.getContextPath()).thenReturn("example.com");
        when(request.getMethod()).thenReturn("GET");
        Command command = CommandsFactory.getCommand(request);
        assertTrue(command instanceof AdminCommand);
    }

    @Test(expected = NotFoundCommandException.class)
    public void getCommand_wrongRequest_exception() {
        when(request.getRequestURI()).thenReturn("example.com/wrong-url");
        when(request.getContextPath()).thenReturn("example.com");
        when(request.getMethod()).thenReturn("GET");
        Command command = CommandsFactory.getCommand(request);
        assertTrue(command instanceof AdminCommand);
    }

}
