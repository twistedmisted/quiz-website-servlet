package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.exception.NoSuchArgumentException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteQuestionCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteQuestionCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            long questionId = getQuestionId(request);
            long quizId = getQuizId(request);
            DbManager dbManager = DbManager.getInstance();
            QuestionDAO questionDAO = dbManager.getQuestionDAO();
            questionDAO.delete(questionDAO.get(questionId));
            return new Page("/admin/quizzes/questions?id=" + quizId + "&page=" + request.getParameter("page"), true);
        } catch (NoSuchArgumentException | DbException e) {
            LOGGER.error(e);
            return new Page("/admin/quizzes?error=true", true);
        }
    }

    private long getQuizId(HttpServletRequest request) throws NoSuchArgumentException {
        return getId(request, "quiz_id");
    }

    private long getQuestionId(HttpServletRequest request) throws NoSuchArgumentException {
        return getId(request, "id");
    }

    private long getId(HttpServletRequest request, String parameter) throws NoSuchArgumentException {
        try {
            return Long.parseLong(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            LOGGER.error(e.getMessage());
            throw new NoSuchArgumentException("Error parse request parameter to long", e);
        }
    }

}
