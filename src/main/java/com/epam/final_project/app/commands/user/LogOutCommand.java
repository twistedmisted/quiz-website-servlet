package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.web.Page;
import com.epam.final_project.app.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class LogOutCommand implements Command {

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().invalidate();
        return new Page("/login.jsp", true);
    }
}
