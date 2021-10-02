package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.web.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.MySQLDAOFactory;
import com.epam.final_project.dao.entity.QuestionDAO;
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

    private final QuizDAO quizDAO;

    private final UserDAO userDAO;

    private final QuestionDAO questionDAO;

    public QuizCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        quizDAO = mySQLDAOFactory.getQuizDAO();
        userDAO = mySQLDAOFactory.getUserDAO();
        questionDAO = mySQLDAOFactory.getQuestionDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("user");
        long quizId = Long.parseLong(request.getParameter("id"));
        try {
            Quiz quiz = quizDAO.get(quizId);
            if (userDAO.haveQuiz(user.getId(), quiz.getId())) {
                int score = userDAO.getScore(user.getId(), quiz.getId());
                request.setAttribute("score", score);
            }
            request.setAttribute("quiz", quiz);
            if (questionDAO.getNumberQuestionsByQuiz(quizId) == 0) {
                request.setAttribute("isEmpty", true);
                request.setAttribute("score", -1);
            }
            return new Page("/WEB-INF/jsp/app/quiz.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/WEB-INF/jsp/app/home.jsp", true);
        }
    }

}
