package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.sql.SQLException;

public class EditQuizCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(EditQuizCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        long id = Long.parseLong(request.getParameter("id"));
        try {
            Quiz quiz = quizDAO.get(id);
            if (quiz == null) {
                return new Page("/admin/quizzes?page=" + request.getParameter("page"), true);
            }
            if (request.getMethod().equalsIgnoreCase("get")) {
                request.setAttribute("quiz", quiz);
                request.setAttribute("id", id);
                request.setAttribute("page", request.getParameter("page"));
                return new Page("/WEB-INF/jsp/admin/edit-quiz.jsp", false);
            }
            quizDAO.update(createNewQuiz(request, quizDAO, quiz));
            return new Page("/admin/quizzes?page=" + request.getParameter("page"), true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Page("/admin/quizzes?error=true", true);
        }
    }

    private Quiz createNewQuiz(HttpServletRequest request, QuizDAO quizDAO, Quiz quiz) throws DbException {
        String name = request.getParameter("name");
        int time = Integer.parseInt(request.getParameter("time"));
        String difficulty = request.getParameter("difficulty");
        String subject = request.getParameter("subject");

        if (!name.isEmpty() && quizDAO.get(name) == null) {
            quiz.setName(name);
        }

        if (!subject.isEmpty()) {
            quiz.setSubject(subject);
        }

        if (!difficulty.isEmpty()) {
            quiz.setDifficulty(difficulty);
        }

        if (time > 0) {
            quiz.setTime(time);
        }
        return quiz;
    }

}
