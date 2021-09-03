package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.dao.model.Question;
import com.epam.final_project.exception.NoSuchArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

import java.util.List;

public class ShowQuestionsCommand implements Command {

    private static final Logger LOGGER = Logger.getLogger(ShowQuestionsCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        DbManager dbManager = DbManager.getInstance();
        QuestionDAO questionDAO = dbManager.getQuestionDAO();
        try {
            long quizId = getId(request);
            List<Question> questions = questionDAO.getAllByQuizId(quizId);
            request.setAttribute("questions", questions);
            return new Page("/WEB-INF/jsp/admin/questions.jsp", false);
        } catch (DbException | NoSuchArgumentException e) {
            LOGGER.error(e);
            return new Page("/admin/quizzes?error=true", true);
        }
    }

    private long getId(HttpServletRequest request) throws NoSuchArgumentException {
        try {
            return Long.parseLong(request.getParameter("id"));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new NoSuchArgumentException("Error parse request parameter to long", e);
        }
    }

}
