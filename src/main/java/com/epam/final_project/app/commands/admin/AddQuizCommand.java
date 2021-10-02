package com.epam.final_project.app.commands.admin;

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

public class AddQuizCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddQuizCommand.class);

    private final QuizDAO quizDAO;

    public AddQuizCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        quizDAO = mySQLDAOFactory.getQuizDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equalsIgnoreCase("get")) {
            return new Page("/WEB-INF/jsp/admin/add-quiz.jsp", false);
        }
        try {
            Quiz quiz = createQuiz(request, quizDAO);
            if (quiz == null) {
                return new Page("/admin/add-quiz?error=true", true);
            }
            quizDAO.insert(quiz);
            return new Page("/admin/quizzes", true);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/admin/add-quiz?error=true", true);
        }
    }

    private Quiz createQuiz(HttpServletRequest request, QuizDAO quizDAO) throws DbException {
        String name = request.getParameter("name");
        String subject = request.getParameter("subject");
        int time = 0;
        if (!request.getParameter("time").isEmpty()) {
            time = Integer.parseInt(request.getParameter("time"));
        }
        String difficulty = request.getParameter("difficulty");
        if (!validateValues(quizDAO, name, time, difficulty, subject)) {
            return null;
        }
        return Quiz.createQuiz(name, time, difficulty, subject);
    }

    private boolean validateValues(QuizDAO quizDAO, String name, int time, String difficulty, String subject)
            throws DbException {
        if (name.isEmpty() && quizDAO.get(name) != null) {
            return false;
        }
        if (subject.isEmpty()) {
            return false;
        }
        if (difficulty.isEmpty()) {
            return false;
        }
        return time >= 0;
    }

}
