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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProfileCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ProfileCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        UserDAO userDAO = dbManager.getUserDAO();
        User user = (User) request.getSession().getAttribute("user");
        try {
            List<Quiz> userQuizzes = quizDAO.getQuizzesByUserId(user.getId());
            Map<Quiz, Integer> quizzesWithScore = new HashMap<>();
            for (Quiz quiz : userQuizzes) {
                quizzesWithScore.put(quiz, userDAO.getScore(user.getId(), quiz.getId()));
            }
            request.setAttribute("quizzesWithScore", quizzesWithScore);
            return new Page("/WEB-INF/jsp/app/profile.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/app/home", true);
        }
    }
}
