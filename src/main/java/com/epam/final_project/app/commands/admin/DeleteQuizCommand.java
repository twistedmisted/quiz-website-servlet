package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Quiz;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class DeleteQuizCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(DeleteQuizCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        QuizDAO quizDAO = DbManager.getInstance().getQuizDAO();
        try {
            Quiz quiz = quizDAO.get(id);
            quizDAO.delete(quiz);
            return new Page("/admin/quizzes?page=" + request.getParameter("page"), true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Page("/admin/quizzes?error=true", true);
        }
    }
}
