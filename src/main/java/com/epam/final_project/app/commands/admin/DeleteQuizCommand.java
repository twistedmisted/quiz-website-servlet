package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.AnswersDAO;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.dao.entity.QuizDAO;
import com.epam.final_project.dao.entity.VariantsDAO;
import com.epam.final_project.dao.model.Question;
import com.epam.final_project.dao.model.Quiz;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DeleteQuizCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(DeleteQuizCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        DbManager dbManager = DbManager.getInstance();
        QuizDAO quizDAO = dbManager.getQuizDAO();
        QuestionDAO questionDAO = dbManager.getQuestionDAO();
        AnswersDAO answerDAO = dbManager.getAnswerDAO();
        VariantsDAO variantsDAO = dbManager.getVariantsDAO();
        try {
            Quiz quiz = quizDAO.get(id);
            for (Question question : questionDAO.getAllByQuizId(quiz.getId())) {
                answerDAO.delete(question.getId());
                variantsDAO.delete(question.getId());
                questionDAO.delete(question);
            }
            quizDAO.delete(quiz);
            return new Page("/admin/quizzes?page=" + request.getParameter("page"), true);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            return new Page("/admin/quizzes?error=true", true);
        }
    }
}
