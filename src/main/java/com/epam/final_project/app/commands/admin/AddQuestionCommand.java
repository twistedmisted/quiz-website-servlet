package com.epam.final_project.app.commands.admin;

import com.epam.final_project.app.web.Page;
import com.epam.final_project.app.commands.Command;
import com.epam.final_project.dao.DbManager;
import com.epam.final_project.dao.entity.AnswersDAO;
import com.epam.final_project.dao.entity.QuestionDAO;
import com.epam.final_project.dao.entity.VariantsDAO;
import com.epam.final_project.exception.DbException;
import com.epam.final_project.dao.model.Question;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

public class AddQuestionCommand implements Command {

    private static final Logger LOGGER = LogManager.getLogger(AddQuestionCommand.class);

    @Override
    public Page execute(HttpServletRequest request, HttpServletResponse response) {
        if (request.getMethod().equalsIgnoreCase("get")) {
            return new Page("/WEB-INF/jsp/admin/add-question.jsp", false);
        }
        DbManager dbManager = DbManager.getInstance();
        QuestionDAO questionDAO = dbManager.getQuestionDAO();
        VariantsDAO variantsDAO = dbManager.getVariantsDAO();
        AnswersDAO answersDAO = dbManager.getAnswerDAO();
        String prompt = request.getParameter("prompt");
        List<String> variants = new ArrayList<>();
        List<Character> answers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            char letter = (char) ('a' + i);
            String variant = request.getParameter(letter + "-input");
            if (!variant.isEmpty()) {
                variants.add(variant);
            }
            String answer = request.getParameter(String.valueOf(letter));
            if (answer != null) {
                answers.add(answer.charAt(0));
            }
        }
        if (!validateValues(prompt, variants, answers)) {
            return new Page("/admin/add-question?id=" + request.getParameter("id") + "&error=true", true);
        }
        try {
            long quizId = Long.parseLong(request.getParameter("id"));
            Question question = questionDAO.insert(Question.createQuestion(prompt, variants, answers), quizId);
            variantsDAO.insert(question);
            answersDAO.insert(question);
            return new Page("/admin/quizzes/questions?id=" + quizId, true);
        } catch (DbException e) {
            LOGGER.error(e);
            return new Page("/admin/add-question?id=" + request.getParameter("id") + "&error=true", true);
        }
    }

    private boolean validateValues(String prompt, List<String> variants, List<Character> answers) {
        if (prompt.isEmpty()) {
            return false;
        }

        if (variants.isEmpty()) {
            return false;
        }

        return !answers.isEmpty();
    }
}
