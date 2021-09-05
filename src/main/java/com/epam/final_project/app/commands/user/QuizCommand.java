package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.entity.UserDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.dao.model.User;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class QuizCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(QuizCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        UserDAO userDAO = dbManager.getUserDAO();
        try {
            Quiz quiz = quizDAO.get(Long.parseLong(request.getParameter("id")));
            if (userDAO.haveQuiz(user.getId(), quiz.getId())) {
                int score = userDAO.getScore(user.getId(), quiz.getId());
                request.setAttribute("score", score);
            }
            request.setAttribute("quiz", quiz);
            return new Page("/WEB-INF/jsp/app/quiz.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/WEB-INF/jsp/app/home.jsp", true);
        }
    }
}
