package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.dao.model.Quiz;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

public class AddQuizCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AddQuizCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equalsIgnoreCase("getByLoginAndPassword")) {
            return new Page("/WEB-INF/jsp/admin/add-quiz.jsp", false);
        }
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
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
        int time = 0;
        if (!request.getParameter("time").isEmpty()) {
            time = Integer.parseInt(request.getParameter("time"));
        }
        String difficulty = request.getParameter("difficulty");
        if (!validateValues(quizDAO, name, time, difficulty)) {
            return null;
        }
        return Quiz.createQuiz(name, time, difficulty);
    }

    private boolean validateValues(QuizDAO quizDAO, String name, int time, String difficulty) throws DbException {
        if (name.isEmpty() && quizDAO.get(name) != null) {
            return false;
        }

        if (difficulty.isEmpty()) {
            return false;
        }

        return time >= 0;
    }
}
