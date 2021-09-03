package com.epam.final_project.app.commands;

import com.epam.final_project.app.Page;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface Command {

    Page execute(HttpServletRequest request, HttpServletResponse response);
}
