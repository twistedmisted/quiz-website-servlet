package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.commands.Command;
import com.epam.final_project.app.web.Page;
import com.epam.final_project.dao.MySQLDAOFactory;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class AllQuizzesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AllQuizzesCommand.class);

    private final QuizDAO quizDAO;

    public AllQuizzesCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        quizDAO = mySQLDAOFactory.getQuizDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        String sortBy = request.getParameter("sortBy");
        String showSubject = request.getParameter("subject");
        if (sortBy == null) {
            sortBy = "name";
        }
        try {
            List<Quiz> quizzes = getQuizzes(sortBy);
            request.setAttribute("quizzes", quizzes);
            request.setAttribute("subjects", quizDAO.getSubjects());
            request.setAttribute("showSubject", showSubject);
            request.setAttribute("sortBy", sortBy);
            return new Page("/WEB-INF/jsp/app/all-quizzes.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/app/home", true);
        }
    }

    private List<Quiz> getQuizzes(String sortBy) throws DbException {
        List<Quiz> quizzes;
        if (sortBy.equalsIgnoreCase("name")) {
            quizzes = quizDAO.getAllSortedByName();
        } else if (sortBy.equalsIgnoreCase("difficulty")) {
            quizzes = quizDAO.getAllSortedByDifficulty();
        } else if (sortBy.equalsIgnoreCase("questions")) {
            quizzes = quizDAO.getAllSortedByNumberOfQuestions();
        } else {
            quizzes = quizDAO.getAllBySubject(sortBy);
        }
        return quizzes;
    }

}
