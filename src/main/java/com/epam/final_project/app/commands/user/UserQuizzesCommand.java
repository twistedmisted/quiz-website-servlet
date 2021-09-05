package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.dao.model.User;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class UserQuizzesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(UserQuizzesCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        User user = (User) request.getSession().getAttribute("user");
        try {
            List<Quiz> userQuizzes = quizDAO.getQuizzesByUserId(user.getId());
            request.setAttribute("userQuizzes", userQuizzes);
            return new Page("/WEB-INF/jsp/app/user-quizzes.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e.getMessage());
            return new Page("/app/home", true);
        }
    }

}
