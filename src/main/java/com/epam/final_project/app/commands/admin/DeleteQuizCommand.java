package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.commands.Command;
import com.epam.final_project.app.web.Page;
import com.epam.final_project.dao.MySQLDAOFactory;
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

    private final QuizDAO quizDAO;

    private final QuestionDAO questionDAO;

    private final AnswersDAO answersDAO;

    private final VariantsDAO variantsDAO;

    public DeleteQuizCommand() {
        MySQLDAOFactory mySQLDAOFactory = new MySQLDAOFactory();
        questionDAO = mySQLDAOFactory.getQuestionDAO();
        quizDAO = mySQLDAOFactory.getQuizDAO();
        answersDAO = mySQLDAOFactory.getAnswersDAO();
        variantsDAO = mySQLDAOFactory.getVariantsDAO();
    }

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        long id = Long.parseLong(request.getParameter("id"));
        try {
            Quiz quiz = quizDAO.get(id);
            for (Question question : questionDAO.getAllByQuizId(quiz.getId())) {
                answersDAO.delete(question.getId());
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
