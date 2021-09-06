package com.epam.final_project.app;

import com.epam.final_project.app.commands.Command;
import com.epam.final_project.app.commands.CommandsFactory;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(name = "frontServlet", urlPatterns = {"/login/*", "/registration/*", "/admin/*", "/app/*"})
public class FrontController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Command command = CommandsFactory.getCommand(request);
        Page page = command.execute(request, response);
        if (page == null) {
            dispatch(request, response, "/index.jsp");
        } else if(page.isRedirect()) {
            response.sendRedirect(page.getPath());
        } else {
            dispatch(request, response, page.getPath());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String url) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
