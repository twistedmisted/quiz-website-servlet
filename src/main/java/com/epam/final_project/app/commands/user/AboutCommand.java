package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.web.Page;
import com.epam.final_project.app.commands.Command;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AboutCommand implements Command {
    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        return new Page("/WEB-INF/jsp/app/about.jsp", false);
    }
}
