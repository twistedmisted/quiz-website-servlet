package com.epam.final_project.app.commands.user;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class AllQuizzesCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(AllQuizzesCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        String sortBy = request.getParameter("sortBy");
        String showSubject = request.getParameter("subject");
        String[] sortOptions = new String[]{
                "<option value=\"name\">Name</option>",
                "<option value=\"difficulty\">Difficulty</option>",
                "<option value=\"questions\">Number of questions</option>"};
        List<Quiz> quizzes = null;
        if (sortBy == null) {
            sortBy = "name";
        }
        try {
            List<String> subjects = quizDAO.getSubjects();
            if (showSubject != null) {
                quizzes = quizDAO.getAllBySubject(showSubject);
            } else if (sortBy.equalsIgnoreCase("name")) {
                quizzes = quizDAO.getAllSortedByName();
                sortOptions[0] = "<option value=\"name\" selected>Name</option>";
            } else if (sortBy.equalsIgnoreCase("difficulty")) {
                quizzes = quizDAO.getAllSortedByDifficulty();
                sortOptions[1] = "<option value=\"difficulty\" selected>Difficulty</option>";
            } else if (sortBy.equalsIgnoreCase("questions")) {
                quizzes = quizDAO.getAllSortedByNumberOfQuestions();
                sortOptions[2] = "<option value=\"questions\" selected>Number of questions</option>";
            }
            request.setAttribute("quizzes", quizzes);
            request.setAttribute("subjects", subjects);
            request.setAttribute("sortOptions", sortOptions);
            request.setAttribute("showSubject", showSubject);
            request.setAttribute("sortBy", sortBy);
            return new Page("/WEB-INF/jsp/app/all-quizzes.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/app/home", true);
        }
    }
}
