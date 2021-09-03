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

//@WebServlet("/App")
@WebServlet(name = "frontServlet", urlPatterns = {"/login/*", "/registration/*", "/admin/*", "/app/*"})
public class FrontController extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //String action = request.getParameter("action");

        // TODO: ADD BACK BUTTON

        // request url = /login
        // 1. if-else
        // 2.

        Command command = CommandsFactory.getCommand(request);
        Page page = command.execute(request, response);
        if (page == null) {
            dispatch(request, response, "/index.jsp");
        } else if(page.isRedirect()) {
            response.sendRedirect(page.getPath());
        } else {
            dispatch(request, response, page.getPath());
        }

//        if (action == null) {
//            response.sendRedirect("/app");
//        } else if (action.equals("login")) {
//            loginController.login(request, response);
//        } else if (action.equals("registration")) {
//            registrationController.register(request, response);
//        } else if (action.equals("home")) {
//            response.sendRedirect("/app/home");
//        } else if (action.equals("all-tests")) {
//            response.sendRedirect("/all-tests");
//        } else if (action.equals("my-tests")) {
//            response.sendRedirect("/my-tests");
//        } else if (action.equals("about")) {
//            response.sendRedirect("/my-tests");
//        } else if (action.equals("edit-user")) {
//            adminController.editUser(request, response);
//        } else if (action.equals("block-user")) {
//            adminController.blockUser(request, response);
//        } else if (action.equals("unblock-user")) {
//            adminController.unblockUser(request, response);
//        } else if (action.equals("delete-user")) {
//            adminController.deleteUser(request, response);
//        } else if (action.equals("delete-quiz")) {
//            adminController.deleteQuiz(request, response);
//        } else if (action.equals("edit-quiz")) {
//            adminController.editQuiz(request, response);
//        } else if (action.equals("delete-question")) {
//            adminController.deleteQuestion(request, response);
//        } else if (action.equals("add-question")) {
//            adminController.addQuestion(request, response);
//        } else if (action.equals("add-quiz")) {
//            adminController.addQuiz(request, response);
//        } else if (action.equals("start-quiz")) {
//            userController.startQuiz(request, response);
//        } else if (action.equals("next-question")) {
//            userController.nextQuestion(request, response);
//        } else if (action.equals("logout")) {
//            userController.logout(request, response);
//        } else if (action.equals("show-quizzes")) {
//            response.sendRedirect("/admin/quizzes");
//        } else if (action.equals("show-users")) {
//            adminController.showUsers(request, response, getServletContext());
//        }
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
