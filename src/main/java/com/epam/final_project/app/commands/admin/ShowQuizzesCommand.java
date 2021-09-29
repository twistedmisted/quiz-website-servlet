package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.web.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.model.Quiz;
import com.epam.final_project.exception.DbException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ShowQuizzesCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(ShowQuizzesCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        final int NUMBER_QUESTIONS_ON_PAGE = 5;
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        try {
            int page = getPage(request);
            int start = NUMBER_QUESTIONS_ON_PAGE * (page - 1);
            int totalNumberOfQuizzes = quizDAO.getNumber();
            int numberOfPages = getNumberOfPages(totalNumberOfQuizzes, NUMBER_QUESTIONS_ON_PAGE);
            List<Quiz> quizzes = quizDAO.getByRange(start, NUMBER_QUESTIONS_ON_PAGE);
            request.setAttribute("quizzes", quizzes);
            request.setAttribute("currentPage", page);
            request.setAttribute("numberOfPages", numberOfPages);
            return new Page("/WEB-INF/jsp/admin/quizzes.jsp", false);
        } catch (DbException e) {
            LOGGER.error(e.getMessage());
            return new Page("/admin?error=true", true);
        }
    }

    private int getPage(HttpServletRequest request) {
        if (request.getParameter("page") == null) {
            return 1;
        }
        return Integer.parseInt(request.getParameter("page"));
    }

    private int getNumberOfPages(int totalNumberOfQuizzes, int numberQuizzesOnPage) {
        if (totalNumberOfQuizzes == 0) {
            return 1;
        }
        if (totalNumberOfQuizzes % numberQuizzesOnPage == 0) {
            return totalNumberOfQuizzes / numberQuizzesOnPage;
        } else {
            return totalNumberOfQuizzes / numberQuizzesOnPage + 1;
        }
    }
}
